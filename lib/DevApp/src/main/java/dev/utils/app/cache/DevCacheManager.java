package dev.utils.app.cache;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import dev.utils.DevFinal;
import dev.utils.LogPrintUtils;
import dev.utils.app.image.ImageUtils;
import dev.utils.common.CloseUtils;
import dev.utils.common.FileUtils;
import dev.utils.common.cipher.Cipher;

/**
 * detail: 缓存管理类
 * @author Ttt
 */
final class DevCacheManager {

    // 不同地址配置缓存对象
    protected static final Map<String, DevCache> sInstanceMaps    = new HashMap<>();
    // 日志 TAG
    private final          String                TAG              = DevCacheManager.class.getSimpleName();
    // 文件后缀
    private static final   String                DATA_EXTENSION   = ".data";
    private static final   String                CONFIG_EXTENSION = ".config";
    // 缓存地址
    private final          String                mCachePath;
    // 通用加解密中间层
    private final          Cipher                mCipher;
    // 总缓存大小
    private final          AtomicLong            mCacheSize       = new AtomicLong();
    // 总缓存的文件总数
    private final          AtomicInteger         mCacheCount      = new AtomicInteger();

    public DevCacheManager(
            String cachePath,
            Cipher cipher
    ) {
        this.mCachePath = cachePath;
        this.mCipher    = cipher;
        // 计算文件信息
        calculateCacheSizeAndCacheCount();
    }

    /**
     * 计算 cacheSize 和 cacheCount
     */
    private void calculateCacheSizeAndCacheCount() {
        new Thread(() -> {
            long size = 0, count = 0;
            if (mCachePath != null) {
                File[] cachedFiles = new File(mCachePath).listFiles();
                if (cachedFiles != null) {
                    for (File file : cachedFiles) {
                        if (file != null && file.isFile()) {
                            String fileName = file.getName();
                            if (fileName.endsWith(CONFIG_EXTENSION)) {
                                String        key  = FileUtils.getFileNotSuffix(fileName);
                                DevCache.Data data = _mapGetData(key);
                                if (data != null) {
                                    size += data.getSize();
                                    count += 1;
                                }
                            }
                        }
                    }
                    mCacheSize.set(size);
                    mCacheCount.set((int) count);
                }
            }
        }).start();
    }

    // =============
    // = 对外公开方法 =
    // =============

    public String getCachePath() {
        return mCachePath;
    }

    // =

    public void remove(String key) {
        if (TextUtils.isEmpty(key)) return;
        File dataFile   = _getKeyDataFile(key);
        File configFile = _getKeyConfigFile(key);
        long size       = getDataFileSize(mCachePath, key);
        if (FileUtils.deleteFile(dataFile)
                && FileUtils.deleteFile(configFile)) {
            mCacheSize.addAndGet(-size);
            mCacheCount.addAndGet(-1);
            mDataMaps.remove(key); // 移除缓存
        }
    }

    public void removeForKeys(String[] keys) {
        if (keys == null) return;
        for (String key : keys) {
            remove(key);
        }
    }

    public boolean contains(String key) {
        return _isExistKeyFile(key);
    }

    public boolean isDue(String key) {
        DevCache.Data data = _mapGetData(key);
        if (data != null) return data.isDue();
        return true;
    }

    public void clear() {
        new Thread(() -> {
            HashSet<String> keys = new HashSet<>(mDataMaps.keySet());
            for (String key : keys) {
                remove(key);
            }
        }).start();
    }

    public void clearDue() {
        new Thread(() -> {
            HashSet<String> keys = new HashSet<>(mDataMaps.keySet());
            for (String key : keys) {
                if (isDue(key)) remove(key);
            }
        }).start();
    }

    public void clearType(int type) {
        new Thread(() -> {
            HashSet<String> keys = new HashSet<>(mDataMaps.keySet());
            for (String key : keys) {
                DevCache.Data data = _mapGetData(key);
                if (data != null && data.getType() == type) {
                    remove(key);
                }
            }
        }).start();
    }

    public DevCache.Data getItemByKey(String key) {
        return _mapGetData(key);
    }

    public List<DevCache.Data> getKeys() {
        return new ArrayList<>(mDataMaps.values());
    }

    public List<DevCache.Data> getPermanentKeys() {
        List<DevCache.Data> lists = new ArrayList<>();
        HashSet<String>     keys  = new HashSet<>(mDataMaps.keySet());
        for (String key : keys) {
            DevCache.Data data = _mapGetData(key);
            if (data != null && data.isPermanent()) {
                lists.add(data);
            }
        }
        return lists;
    }

    public int getCount() {
        return mCacheCount.get();
    }

    public long getSize() {
        return mCacheSize.get();
    }

    // =======
    // = 存储 =
    // =======

    public boolean put(
            String key,
            int value,
            long validTime
    ) {
        return _put(key, DevCache.INT, String.valueOf(value).getBytes(), validTime);
    }

    public boolean put(
            String key,
            long value,
            long validTime
    ) {
        return _put(key, DevCache.LONG, String.valueOf(value).getBytes(), validTime);
    }

    public boolean put(
            String key,
            float value,
            long validTime
    ) {
        return _put(key, DevCache.FLOAT, String.valueOf(value).getBytes(), validTime);
    }

    public boolean put(
            String key,
            double value,
            long validTime
    ) {
        return _put(key, DevCache.DOUBLE, String.valueOf(value).getBytes(), validTime);
    }

    public boolean put(
            String key,
            boolean value,
            long validTime
    ) {
        return _put(key, DevCache.BOOLEAN, String.valueOf(value).getBytes(), validTime);
    }

    public boolean put(
            String key,
            String value,
            long validTime
    ) {
        if (value == null) return false;
        return _put(key, DevCache.STRING, value.getBytes(), validTime);
    }

    public boolean put(
            String key,
            byte[] value,
            long validTime
    ) {
        return _put(key, DevCache.BYTES, value, validTime);
    }

    public boolean put(
            String key,
            Bitmap value,
            long validTime
    ) {
        return _put(key, DevCache.BITMAP, ImageUtils.bitmapToByte(value), validTime);
    }

    public boolean put(
            String key,
            Drawable value,
            long validTime
    ) {
        return _put(key, DevCache.DRAWABLE, ImageUtils.bitmapToByte(
                ImageUtils.drawableToBitmap(value)
        ), validTime);
    }

    public boolean put(
            String key,
            Serializable value,
            long validTime
    ) {
        ObjectOutputStream oos = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(value);
            byte[] bytes = baos.toByteArray();
            return _put(key, DevCache.SERIALIZABLE, bytes, validTime);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "put - Serializable");
        } finally {
            CloseUtils.closeIOQuietly(oos);
        }
        return false;
    }

    public boolean put(
            String key,
            Parcelable value,
            long validTime
    ) {
        try {
            Parcel parcel = Parcel.obtain();
            value.writeToParcel(parcel, 0);
            byte[] bytes = parcel.marshall();
            parcel.recycle();
            return _put(key, DevCache.PARCELABLE, bytes, validTime);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "put - Parcelable");
        }
        return false;
    }

    public boolean put(
            String key,
            JSONObject value,
            long validTime
    ) {
        if (value == null) return false;
        return _put(key, DevCache.JSON_OBJECT, value.toString().getBytes(), validTime);
    }

    public boolean put(
            String key,
            JSONArray value,
            long validTime
    ) {
        if (value == null) return false;
        return _put(key, DevCache.JSON_ARRAY, value.toString().getBytes(), validTime);
    }

    // =======
    // = 获取 =
    // =======

    public int getInt(String key) {
        return getInt(key, DevFinal.DEFAULT.INT);
    }

    public long getLong(String key) {
        return getLong(key, DevFinal.DEFAULT.LONG);
    }

    public float getFloat(String key) {
        return getFloat(key, DevFinal.DEFAULT.FLOAT);
    }

    public double getDouble(String key) {
        return getDouble(key, DevFinal.DEFAULT.DOUBLE);
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, DevFinal.DEFAULT.BOOLEAN);
    }

    public String getString(String key) {
        return getString(key, null);
    }

    public byte[] getBytes(String key) {
        return getBytes(key, null);
    }

    public Bitmap getBitmap(String key) {
        return getBitmap(key, null);
    }

    public Drawable getDrawable(String key) {
        return getDrawable(key, null);
    }

    public Object getSerializable(String key) {
        return getSerializable(key, null);
    }

    public <T> T getParcelable(
            String key,
            Parcelable.Creator<T> creator
    ) {
        return getParcelable(key, creator, null);
    }

    public JSONObject getJSONObject(String key) {
        return getJSONObject(key, null);
    }

    public JSONArray getJSONArray(String key) {
        return getJSONArray(key, null);
    }

    // =

    public int getInt(
            String key,
            int defaultValue
    ) {
        DevCache.Data data = _mapGetData(key);
        if (data != null) {
            if (data.isDue()) {
                remove(key);
            } else {
                try {
                    byte[] bytes = _get(key);
                    return Integer.parseInt(new String(bytes));
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, "getInt");
                }
            }
        }
        return defaultValue;
    }

    public long getLong(
            String key,
            long defaultValue
    ) {
        DevCache.Data data = _mapGetData(key);
        if (data != null) {
            if (data.isDue()) {
                remove(key);
            } else {
                try {
                    byte[] bytes = _get(key);
                    return Long.parseLong(new String(bytes));
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, "getLong");
                }
            }
        }
        return defaultValue;
    }

    public float getFloat(
            String key,
            float defaultValue
    ) {
        DevCache.Data data = _mapGetData(key);
        if (data != null) {
            if (data.isDue()) {
                remove(key);
            } else {
                try {
                    byte[] bytes = _get(key);
                    return Float.parseFloat(new String(bytes));
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, "getFloat");
                }
            }
        }
        return defaultValue;
    }

    public double getDouble(
            String key,
            double defaultValue
    ) {
        DevCache.Data data = _mapGetData(key);
        if (data != null) {
            if (data.isDue()) {
                remove(key);
            } else {
                try {
                    byte[] bytes = _get(key);
                    return Double.parseDouble(new String(bytes));
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, "getDouble");
                }
            }
        }
        return defaultValue;
    }

    public boolean getBoolean(
            String key,
            boolean defaultValue
    ) {
        DevCache.Data data = _mapGetData(key);
        if (data != null) {
            if (data.isDue()) {
                remove(key);
            } else {
                try {
                    byte[] bytes = _get(key);
                    return Boolean.parseBoolean(new String(bytes));
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, "getBoolean");
                }
            }
        }
        return defaultValue;
    }

    public String getString(
            String key,
            String defaultValue
    ) {
        DevCache.Data data = _mapGetData(key);
        if (data != null) {
            if (data.isDue()) {
                remove(key);
            } else {
                try {
                    byte[] bytes = _get(key);
                    return new String(bytes);
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, "getString");
                }
            }
        }
        return defaultValue;
    }

    public byte[] getBytes(
            String key,
            byte[] defaultValue
    ) {
        DevCache.Data data = _mapGetData(key);
        if (data != null) {
            if (data.isDue()) {
                remove(key);
            } else {
                try {
                    return _get(key);
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, "getBytes");
                }
            }
        }
        return defaultValue;
    }

    public Bitmap getBitmap(
            String key,
            Bitmap defaultValue
    ) {
        DevCache.Data data = _mapGetData(key);
        if (data != null) {
            if (data.isDue()) {
                remove(key);
            } else {
                try {
                    byte[] bytes = _get(key);
                    return ImageUtils.decodeByteArray(bytes);
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, "getBitmap");
                }
            }
        }
        return defaultValue;
    }

    public Drawable getDrawable(
            String key,
            Drawable defaultValue
    ) {
        DevCache.Data data = _mapGetData(key);
        if (data != null) {
            if (data.isDue()) {
                remove(key);
            } else {
                try {
                    byte[] bytes  = _get(key);
                    Bitmap bitmap = ImageUtils.decodeByteArray(bytes);
                    return ImageUtils.bitmapToDrawable(bitmap);
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, "getDrawable");
                }
            }
        }
        return defaultValue;
    }

    public Object getSerializable(
            String key,
            Object defaultValue
    ) {
        DevCache.Data data = _mapGetData(key);
        if (data != null) {
            if (data.isDue()) {
                remove(key);
            } else {
                ObjectInputStream ois = null;
                try {
                    byte[] bytes = _get(key);
                    ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
                    return ois.readObject();
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, "getSerializable");
                } finally {
                    CloseUtils.closeIOQuietly(ois);
                }
            }
        }
        return defaultValue;
    }

    public <T> T getParcelable(
            String key,
            Parcelable.Creator<T> creator,
            T defaultValue
    ) {
        DevCache.Data data = _mapGetData(key);
        if (data != null) {
            if (data.isDue()) {
                remove(key);
            } else {
                try {
                    byte[] bytes  = _get(key);
                    Parcel parcel = Parcel.obtain();
                    parcel.unmarshall(bytes, 0, bytes.length);
                    parcel.setDataPosition(0);
                    return creator.createFromParcel(parcel);
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, "getParcelable");
                }
            }
        }
        return defaultValue;
    }

    public JSONObject getJSONObject(
            String key,
            JSONObject defaultValue
    ) {
        DevCache.Data data = _mapGetData(key);
        if (data != null) {
            if (data.isDue()) {
                remove(key);
            } else {
                try {
                    byte[] bytes = _get(key);
                    return new JSONObject(new String(bytes));
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, "getJSONObject");
                }
            }
        }
        return defaultValue;
    }

    public JSONArray getJSONArray(
            String key,
            JSONArray defaultValue
    ) {
        DevCache.Data data = _mapGetData(key);
        if (data != null) {
            if (data.isDue()) {
                remove(key);
            } else {
                try {
                    byte[] bytes = _get(key);
                    return new JSONArray(new String(bytes));
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, "getJSONArray");
                }
            }
        }
        return defaultValue;
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 获取 Key 数据文件
     * @param key 存储 key
     * @return Key 数据文件
     */
    private File _getKeyDataFile(final String key) {
        if (TextUtils.isEmpty(key)) return null;
        return FileUtils.getFile(mCachePath, key + DATA_EXTENSION);
    }

    /**
     * 获取 Key 配置文件
     * @param key 存储 key
     * @return Key 配置文件
     */
    private File _getKeyConfigFile(final String key) {
        if (TextUtils.isEmpty(key)) return null;
        return FileUtils.getFile(mCachePath, key + CONFIG_EXTENSION);
    }

    /**
     * 获取存储数据大小
     * @param path 文件地址
     * @param key  存储 key
     * @return 存储数据大小
     */
    public static long getDataFileSize(
            final String path,
            final String key
    ) {
        if (TextUtils.isEmpty(key)) return 0L;
        return FileUtils.getFileLength(
                FileUtils.getFile(path, key + DATA_EXTENSION)
        );
    }

    /**
     * 判断是否存在 Key 配置、数据文件
     * @param key 存储 key
     * @return {@code true} yes, {@code false} no
     */
    private boolean _isExistKeyFile(final String key) {
        if (TextUtils.isEmpty(key)) return false;
        return FileUtils.isFileExists(_getKeyDataFile(key))
                && FileUtils.isFileExists(_getKeyConfigFile(key));
    }

    // ========
    // = Data =
    // ========

    // 缓存 Data
    private final HashMap<String, DevCache.Data> mDataMaps = new HashMap<>();

    private DevCache.Data _mapGetData(final String key) {
        if (TextUtils.isEmpty(key)) return null;
        DevCache.Data data = mDataMaps.get(key);
        if (data == null) {
            data = _getData(key);
            if (data != null) {
                mDataMaps.put(key, data);
            }
        }
        return data;
    }

    // =

    /**
     * Data Format JSON String
     * @param data 数据源
     * @return JSON String
     */
    private String _toDataString(final DevCache.Data data) {
        // Data JSON Format
        return String.format(
                "{\"key\":\"%s\",\"type\":%d,\"saveTime\":%d,\"validTime\":%d}",
                data.getKey(), data.getType(),
                data.getSaveTime(), data.getValidTime()
        );
    }

    /**
     * 读取配置初始化 Data
     * @param key 存储 key
     * @return {@link DevCache.Data}
     */
    private DevCache.Data _getData(final String key) {
        if (!_isExistKeyFile(key)) return null;
        try {
            File       configFile = _getKeyConfigFile(key);
            String     config     = new String(FileUtils.readFileBytes(configFile));
            JSONObject jsonObject = new JSONObject(config);
            if (jsonObject.has("key")
                    && jsonObject.has("type")
                    && jsonObject.has("saveTime")
                    && jsonObject.has("validTime")
            ) {
                String _key      = jsonObject.getString("key");
                int    type      = jsonObject.getInt("type");
                long   saveTime  = jsonObject.getLong("saveTime");
                long   validTime = jsonObject.getLong("validTime");
                return new DevCache.Data(mCachePath, _key,
                        type, saveTime, validTime
                );
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "_getData");
        }
        return null;
    }

    // =

    /**
     * 保存方法 ( 最终调用 )
     * @param key       保存的 key
     * @param type      保存类型
     * @param bytes     保存数据
     * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
     * @return {@code true} success, {@code false} fail
     */
    private boolean _put(
            String key,
            int type,
            byte[] bytes,
            long validTime
    ) {
        if (TextUtils.isEmpty(key)) return false;
        if (bytes != null && mCipher != null) {
            try {
                bytes = mCipher.encrypt(bytes);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "_put - encrypt");
                bytes = null;
            }
        }
        if (bytes == null) return false;
        DevCache.Data data   = _mapGetData(key);
        long          size   = getDataFileSize(mCachePath, key);
        boolean       result = FileUtils.saveFile(_getKeyDataFile(key), bytes);
        if (result) {
            if (data != null) {
                data.setSaveTime(System.currentTimeMillis())
                        .setType(type).setValidTime(validTime);
                mCacheSize.addAndGet(-size);
                mCacheSize.addAndGet(getDataFileSize(mCachePath, key));
            } else {
                data = new DevCache.Data(mCachePath, key, type,
                        System.currentTimeMillis(), validTime
                );
                mCacheSize.addAndGet(getDataFileSize(mCachePath, key));
                mCacheCount.incrementAndGet();
                mDataMaps.put(key, data);
            }
            FileUtils.saveFile(_getKeyConfigFile(key), _toDataString(data).getBytes());
        }
        return result;
    }

    /**
     * 获取方法 ( 最终调用 )
     * @param key 保存的 key
     * @return 保存的数据
     */
    private byte[] _get(String key) {
        byte[] bytes = FileUtils.readFileBytes(_getKeyDataFile(key));
        if (bytes != null && mCipher != null) {
            try {
                bytes = mCipher.decrypt(bytes);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "_get - decrypt");
                bytes = null;
            }
        }
        return bytes;
    }
}