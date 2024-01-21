package dev.engine.storage;

import android.os.Environment;
import android.provider.MediaStore;

import dev.base.DevSource;
import dev.engine.storage.listener.OnInsertListener;
import dev.utils.app.PathUtils;

/**
 * detail: Storage Engine 接口
 * @author Ttt
 * <pre>
 *     关于存储空间及兼容建议查看 {@link PathUtils}
 *     <p></p>
 *     外部存储空间 ( SDCard ) 正常指的是
 *     {@link Environment#DIRECTORY_PICTURES}
 *     {@link Environment#DIRECTORY_DCIM}
 *     {@link Environment#DIRECTORY_MUSIC}
 *     {@link Environment#DIRECTORY_DOWNLOADS}
 *     适配高版本的 {@link MediaStore} 操作, 可参考 {@link dev.utils.app.MediaStoreUtils}
 *     <p></p>
 *     内部存储空间分
 *     内部存储 : /data/data/package/ 目录
 *     外部存储 ( 私有目录 ) : /storage/emulated/0/Android/data/package/ 目录
 *     具体需要区分存储位置可以在 {@link EngineItem} 子类新增参数判断
 * </pre>
 */
public interface IStorageEngine<Item extends IStorageEngine.EngineItem, Result extends IStorageEngine.EngineResult> {

    /**
     * detail: Storage ( Data、Params ) Item
     * @author Ttt
     */
    class EngineItem {
    }

    /**
     * detail: Storage Result
     * @author Ttt
     */
    class EngineResult {
    }

    // =============
    // = 对外公开方法 =
    // =============

    // ==========
    // = 外部存储 =
    // ==========

    /**
     * 插入一张图片到外部存储空间 ( SDCard )
     * @param params   Storage ( Data、Params ) Item
     * @param source   数据来源
     * @param listener 插入多媒体资源事件
     */
    void insertImageToExternal(
            Item params,
            DevSource source,
            OnInsertListener<Item, Result> listener
    );

    /**
     * 插入一条视频到外部存储空间 ( SDCard )
     * @param params   Storage ( Data、Params ) Item
     * @param source   数据来源
     * @param listener 插入多媒体资源事件
     */
    void insertVideoToExternal(
            Item params,
            DevSource source,
            OnInsertListener<Item, Result> listener
    );

    /**
     * 插入一条音频到外部存储空间 ( SDCard )
     * @param params   Storage ( Data、Params ) Item
     * @param source   数据来源
     * @param listener 插入多媒体资源事件
     */
    void insertAudioToExternal(
            Item params,
            DevSource source,
            OnInsertListener<Item, Result> listener
    );

    /**
     * 插入一条文件资源到外部存储空间 ( SDCard )
     * @param params   Storage ( Data、Params ) Item
     * @param source   数据来源
     * @param listener 插入多媒体资源事件
     */
    void insertDownloadToExternal(
            Item params,
            DevSource source,
            OnInsertListener<Item, Result> listener
    );

    /**
     * 插入一条多媒体资源到外部存储空间 ( SDCard )
     * @param params   Storage ( Data、Params ) Item
     * @param source   数据来源
     * @param listener 插入多媒体资源事件
     */
    void insertMediaToExternal(
            Item params,
            DevSource source,
            OnInsertListener<Item, Result> listener
    );

    // ==========
    // = 内部存储 =
    // ==========

    /**
     * 插入一张图片到内部存储空间
     * @param params   Storage ( Data、Params ) Item
     * @param source   数据来源
     * @param listener 插入多媒体资源事件
     */
    void insertImageToInternal(
            Item params,
            DevSource source,
            OnInsertListener<Item, Result> listener
    );

    /**
     * 插入一条视频到内部存储空间
     * @param params   Storage ( Data、Params ) Item
     * @param source   数据来源
     * @param listener 插入多媒体资源事件
     */
    void insertVideoToInternal(
            Item params,
            DevSource source,
            OnInsertListener<Item, Result> listener
    );

    /**
     * 插入一条音频到内部存储空间
     * @param params   Storage ( Data、Params ) Item
     * @param source   数据来源
     * @param listener 插入多媒体资源事件
     */
    void insertAudioToInternal(
            Item params,
            DevSource source,
            OnInsertListener<Item, Result> listener
    );

    /**
     * 插入一条文件资源到内部存储空间
     * @param params   Storage ( Data、Params ) Item
     * @param source   数据来源
     * @param listener 插入多媒体资源事件
     */
    void insertDownloadToInternal(
            Item params,
            DevSource source,
            OnInsertListener<Item, Result> listener
    );

    /**
     * 插入一条多媒体资源到内部存储空间
     * <pre>
     *     并不局限于多媒体, 如文本存储、其他文件写入等
     * </pre>
     * @param params   Storage ( Data、Params ) Item
     * @param source   数据来源
     * @param listener 插入多媒体资源事件
     */
    void insertMediaToInternal(
            Item params,
            DevSource source,
            OnInsertListener<Item, Result> listener
    );
}