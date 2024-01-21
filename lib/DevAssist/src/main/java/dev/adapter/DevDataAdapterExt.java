package dev.adapter;

import android.app.Activity;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import dev.assist.EditTextWatcherAssist;
import dev.base.DevObject;
import dev.base.DevPage;
import dev.base.multiselect.DevMultiSelectMap;
import dev.base.state.CommonState;
import dev.base.state.RequestState;
import dev.callback.DevCallback;
import dev.callback.DevItemClickCallback;
import dev.utils.common.assist.FlagsValue;

/**
 * detail: DataManager RecyclerView Adapter Extend
 * @author Ttt
 * <pre>
 *     在 {@link DevDataAdapter} 基础上
 *     新增: Object 存储、分页信息、通用回调、Item 回调、输入监听辅助类、多选辅助类 功能变量
 * </pre>
 */
public abstract class DevDataAdapterExt<T, VH extends RecyclerView.ViewHolder>
        extends DevDataAdapter<T, VH> {

    public DevDataAdapterExt() {
    }

    public DevDataAdapterExt(Context context) {
        super(context);
    }

    public DevDataAdapterExt(Activity activity) {
        super(activity);
    }

    // =============
    // = 对外公开方法 =
    // =============

    // 通用回调
    protected DevCallback<T>               mCallback;
    // 通用 Item Click 回调
    protected DevItemClickCallback<T>      mItemCallback;
    // 通用 Object
    protected DevObject<T>                 mObject            = new DevObject<>();
    // Page 实体类
    protected DevPage<T>                   mPage              = DevPage.getDefault();
    // 标记值计算存储 ( 位运算符 )
    protected FlagsValue                   mFlags             = new FlagsValue();
    // 通用状态
    protected CommonState<T>               mState             = new CommonState<>();
    // 请求状态
    protected RequestState<T>              mRequestState      = new RequestState<>();
    // EditText 输入监听辅助类
    protected EditTextWatcherAssist<T>     mTextWatcherAssist = new EditTextWatcherAssist<>();
    // 多选辅助类
    protected DevMultiSelectMap<String, T> mMultiSelectMap    = new DevMultiSelectMap<>();

    /**
     * 获取通用回调
     * @return {@link DevCallback}
     */
    public DevCallback<T> getCallback() {
        return mCallback;
    }

    /**
     * 设置通用回调
     * @param callback {@link DevCallback}
     * @return {@link DevDataAdapterExt}
     */
    public DevDataAdapterExt<T, VH> setCallback(final DevCallback<T> callback) {
        this.mCallback = callback;
        return this;
    }

    /**
     * 获取通用 Item Click 回调
     * @return {@link DevItemClickCallback}
     */
    public DevItemClickCallback<T> getItemCallback() {
        return mItemCallback;
    }

    /**
     * 设置通用 Item Click 回调
     * @param itemCallback {@link DevItemClickCallback}
     * @return {@link DevDataAdapterExt}
     */
    public DevDataAdapterExt<T, VH> setItemCallback(final DevItemClickCallback<T> itemCallback) {
        this.mItemCallback = itemCallback;
        return this;
    }

    /**
     * 获取通用 Object
     * @return {@link DevObject}
     */
    public DevObject<T> getObject() {
        return mObject;
    }

    /**
     * 设置通用 Object
     * @param object {@link DevObject}
     * @return {@link DevDataAdapterExt}
     */
    public DevDataAdapterExt<T, VH> setObject(final DevObject<T> object) {
        this.mObject = object;
        return this;
    }

    /**
     * 获取 Page 实体类
     * @return {@link DevPage}
     */
    public DevPage<T> getPage() {
        return mPage;
    }

    /**
     * 设置 Page 实体类
     * @param pageConfig 页数配置信息
     * @return {@link DevDataAdapterExt}
     */
    public DevDataAdapterExt<T, VH> setPage(final DevPage.PageConfig pageConfig) {
        return setPage(new DevPage<>(pageConfig));
    }

    /**
     * 设置 Page 实体类
     * @param page     页数
     * @param pageSize 每页请求条数
     * @return {@link DevDataAdapterExt}
     */
    public DevDataAdapterExt<T, VH> setPage(
            final int page,
            final int pageSize
    ) {
        return setPage(new DevPage<>(page, pageSize));
    }

    /**
     * 设置 Page 实体类
     * @param page Page 实体类
     * @return {@link DevDataAdapterExt}
     */
    public DevDataAdapterExt<T, VH> setPage(final DevPage<T> page) {
        this.mPage = page;
        return this;
    }

    /**
     * 获取标记值计算存储 ( 位运算符 ) 实体类
     * @return {@link FlagsValue}
     */
    public FlagsValue getFlags() {
        return mFlags;
    }

    /**
     * 设置标记值计算存储 ( 位运算符 ) 实体类
     * @param flags {@link FlagsValue}
     * @return {@link DevDataAdapterExt}
     */
    public DevDataAdapterExt<T, VH> setFlags(final FlagsValue flags) {
        this.mFlags = flags;
        return this;
    }

    /**
     * 获取通用状态实体类
     * @return {@link CommonState}
     */
    public CommonState<T> getState() {
        return mState;
    }

    /**
     * 设置通用状态实体类
     * @param state {@link CommonState}
     * @return {@link DevDataAdapterExt}
     */
    public DevDataAdapterExt<T, VH> setState(final CommonState<T> state) {
        this.mState = state;
        return this;
    }

    /**
     * 获取请求状态实体类
     * @return {@link RequestState}
     */
    public RequestState<T> getRequestState() {
        return mRequestState;
    }

    /**
     * 设置请求状态实体类
     * @param requestState {@link RequestState}
     * @return {@link DevDataAdapterExt}
     */
    public DevDataAdapterExt<T, VH> setRequestState(final RequestState<T> requestState) {
        this.mRequestState = requestState;
        return this;
    }

    /**
     * 获取 EditText 输入监听辅助类
     * @return {@link EditTextWatcherAssist}
     */
    public EditTextWatcherAssist<T> getTextWatcherAssist() {
        return mTextWatcherAssist;
    }

    /**
     * 设置 EditText 输入监听辅助类
     * @param textWatcherAssist {@link EditTextWatcherAssist}
     * @return {@link DevDataAdapterExt}
     */
    public DevDataAdapterExt<T, VH> setTextWatcherAssist(final EditTextWatcherAssist<T> textWatcherAssist) {
        this.mTextWatcherAssist = textWatcherAssist;
        return this;
    }

    /**
     * 获取多选辅助类
     * @return {@link DevMultiSelectMap}
     */
    public DevMultiSelectMap<String, T> getMultiSelectMap() {
        return mMultiSelectMap;
    }

    /**
     * 设置多选辅助类
     * @param multiSelectMap {@link DevMultiSelectMap}
     * @return {@link DevDataAdapterExt}
     */
    public DevDataAdapterExt<T, VH> setMultiSelectMap(final DevMultiSelectMap<String, T> multiSelectMap) {
        this.mMultiSelectMap = multiSelectMap;
        return this;
    }
}