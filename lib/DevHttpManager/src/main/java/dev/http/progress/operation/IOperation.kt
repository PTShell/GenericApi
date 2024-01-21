package dev.http.progress.operation

import android.os.Handler
import dev.http.progress.Progress
import okhttp3.OkHttpClient

/**
 * detail: Progress Operation 通用方法定义接口
 * @author Ttt
 */
interface IOperation {

    // ============
    // = 初始化方法 =
    // ============

    /**
     * 进行拦截器包装 ( 必须调用 )
     * @param builder Builder
     * @return Builder
     */
    fun wrap(builder: OkHttpClient.Builder): OkHttpClient.Builder

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 是否已调用 wrap 方法
     * @return `true` yes, `false` no
     */
    fun isUseWrap(): Boolean

    /**
     * 是否废弃不使用状态
     * @return `true` yes, `false` no
     */
    fun isDeprecated(): Boolean

    /**
     * 是否全局默认操作对象
     * @return `true` yes, `false` no
     */
    fun isDefault(): Boolean

    /**
     * 是否监听上下行
     * @return `true` yes, `false` no
     */
    fun isTypeAll(): Boolean

    /**
     * 是否监听上行
     * @return `true` yes, `false` no
     */
    fun isTypeRequest(): Boolean

    /**
     * 是否监听下行
     * @return `true` yes, `false` no
     */
    fun isTypeResponse(): Boolean

    // ===========
    // = get/set =
    // ===========

    /**
     * 获取 Progress Operation 实现方式类型
     * @return 实现方式类型
     */
    fun getPlanType(): Int

    // =

    /**
     * 获取回调刷新时间 ( 毫秒 )
     * @return 回调刷新时间 ( 毫秒 )
     */
    fun getRefreshTime(): Long

    /**
     * 设置回调刷新时间 ( 毫秒 )
     * @param refreshTime 回调刷新时间 ( 毫秒 )
     * @return IOperation
     */
    fun setRefreshTime(refreshTime: Long): IOperation

    /**
     * 重置回调刷新时间 ( 毫秒 )
     * @return IOperation
     */
    fun resetRefreshTime(): IOperation

    // =

    /**
     * 获取全局 Progress Callback
     * @return Progress Callback
     */
    fun getCallback(): Progress.Callback?

    /**
     * 设置全局 Progress Callback
     * @param callback Progress Callback
     * @return IOperation
     */
    fun setCallback(callback: Progress.Callback?): IOperation

    /**
     * 移除全局 Progress Callback
     * @return IOperation
     */
    fun removeCallback(): IOperation

    // =

    /**
     * 获取回调 UI 线程通知 Handler
     * @return 回调 UI 线程通知 Handler
     */
    fun getHandler(): Handler?

    /**
     * 设置回调 UI 线程通知 Handler
     * @param handler 回调 UI 线程通知 Handler
     * @return IOperation
     */
    fun setHandler(handler: Handler?): IOperation

    /**
     * 重置回调 UI 线程通知 Handler
     * @return IOperation
     */
    fun resetHandler(): IOperation

    // ====================
    // = 操作方法 - 对外公开 =
    // ====================

    /**
     * 移除自身在 Manager Map 中的对象值, 并且标记为废弃状态
     * 会释放所有数据、监听事件且不处理任何监听
     */
    fun removeSelfFromManager()

    /**
     * 释放指定监听事件
     * @param progress Progress
     * @param callback 上传、下载回调接口
     * @return `true` success, `false` fail
     */
    fun recycleListener(
        progress: Progress,
        callback: Progress.Callback
    ): Boolean

    // ====================
    // = Request Listener =
    // ====================

    /**
     * 添加指定 url 上行监听事件
     * @param url 请求 url
     * @param callback 上传、下载回调接口
     * @return `true` success, `false` fail
     */
    fun addRequestListener(
        url: String,
        callback: Progress.Callback
    ): Boolean

    /**
     * 清空指定 url 上行所有监听事件
     * @param url 请求 url
     * @return `true` success, `false` fail
     */
    fun clearRequestListener(url: String): Boolean

    /**
     * 清空指定 url 上行所有监听事件
     * @param progress Progress
     * @return `true` success, `false` fail
     */
    fun clearRequestListener(progress: Progress?): Boolean

    /**
     * 移除指定 url 上行监听事件
     * @param url 请求 url
     * @param callback 上传、下载回调接口
     * @return `true` success, `false` fail
     */
    fun removeRequestListener(
        url: String,
        callback: Progress.Callback
    ): Boolean

    /**
     * 移除指定 url 上行监听事件
     * @param progress Progress
     * @param callback 上传、下载回调接口
     * @return `true` success, `false` fail
     */
    fun removeRequestListener(
        progress: Progress?,
        callback: Progress.Callback
    ): Boolean

    // =====================
    // = Response Listener =
    // =====================

    /**
     * 添加指定 url 下行监听事件
     * @param url 请求 url
     * @param callback 上传、下载回调接口
     * @return `true` success, `false` fail
     */
    fun addResponseListener(
        url: String,
        callback: Progress.Callback
    ): Boolean

    /**
     * 清空指定 url 下行所有监听事件
     * @param url 请求 url
     * @return `true` success, `false` fail
     */
    fun clearResponseListener(url: String): Boolean

    /**
     * 清空指定 url 下行所有监听事件
     * @param progress Progress
     * @return `true` success, `false` fail
     */
    fun clearResponseListener(progress: Progress?): Boolean

    /**
     * 移除指定 url 下行监听事件
     * @param url 请求 url
     * @param callback 上传、下载回调接口
     * @return `true` success, `false` fail
     */
    fun removeResponseListener(
        url: String,
        callback: Progress.Callback
    ): Boolean

    /**
     * 移除指定 url 下行监听事件
     * @param progress Progress
     * @param callback 上传、下载回调接口
     * @return `true` success, `false` fail
     */
    fun removeResponseListener(
        progress: Progress?,
        callback: Progress.Callback
    ): Boolean
}