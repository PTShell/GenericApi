package dev.retrofit

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

// ====================
// = 请求方法协程扩展函数 =
// ====================

// ===========================================================================
// = 在 request_coroutines.kt 基础上减少 start、success、error、finish 方法体传参 =
// ===========================================================================

// ==================
// = CoroutineScope =
// ==================

/**
 * 执行请求
 * 无任何额外逻辑封装, 支持自定义解析、处理等代码
 */
inline fun <T> CoroutineScope.simpleScopeExecuteRequest(
    // 请求方法体
    crossinline block: suspend () -> T?,
    // 当前请求每个阶段进行通知
    callback: Notify.Callback<T>,
    // 全局通知回调方法 ( 创建一个全局通用传入 )
    globalCallback: Notify.GlobalCallback? = null
): Job {
    return launch {
        finalExecute(
            block, start = {}, success = {},
            error = {}, finish = {},
            callback, globalCallback
        )
    }
}

/**
 * 执行请求
 * 封装为 Base.Response、Base.Result 进行响应
 */
inline fun <T, R : Base.Response<T>> CoroutineScope.simpleScopeExecuteResponseRequest(
    // 请求方法体
    crossinline block: suspend () -> R?,
    // 当前请求每个阶段进行通知
    callback: Notify.ResultCallback<T, R>,
    // 全局通知回调方法 ( 创建一个全局通用传入 )
    globalCallback: Notify.GlobalCallback? = null
): Job {
    return launch {
        finalExecuteResponse(
            block, start = {}, success = {},
            error = {}, finish = {},
            callback, globalCallback
        )
    }
}

// =============
// = ViewModel =
// =============

inline fun <T> ViewModel.simpleLaunchExecuteRequest(
    // 请求方法体
    crossinline block: suspend () -> T?,
    // 当前请求每个阶段进行通知
    callback: Notify.Callback<T>,
    // 全局通知回调方法 ( 创建一个全局通用传入 )
    globalCallback: Notify.GlobalCallback? = null
): Job {
    return viewModelScope.simpleScopeExecuteRequest(
        block, callback, globalCallback
    )
}

inline fun <T, R : Base.Response<T>> ViewModel.simpleLaunchExecuteResponseRequest(
    // 请求方法体
    crossinline block: suspend () -> R?,
    // 当前请求每个阶段进行通知
    callback: Notify.ResultCallback<T, R>,
    // 全局通知回调方法 ( 创建一个全局通用传入 )
    globalCallback: Notify.GlobalCallback? = null
): Job {
    return viewModelScope.simpleScopeExecuteResponseRequest(
        block, callback, globalCallback
    )
}

// =============
// = Lifecycle =
// =============

inline fun <T> Lifecycle.simpleLaunchExecuteRequest(
    // 请求方法体
    crossinline block: suspend () -> T?,
    // 当前请求每个阶段进行通知
    callback: Notify.Callback<T>,
    // 全局通知回调方法 ( 创建一个全局通用传入 )
    globalCallback: Notify.GlobalCallback? = null
): Job {
    return coroutineScope.simpleScopeExecuteRequest(
        block, callback, globalCallback
    )
}

inline fun <T, R : Base.Response<T>> Lifecycle.simpleLaunchExecuteResponseRequest(
    // 请求方法体
    crossinline block: suspend () -> R?,
    // 当前请求每个阶段进行通知
    callback: Notify.ResultCallback<T, R>,
    // 全局通知回调方法 ( 创建一个全局通用传入 )
    globalCallback: Notify.GlobalCallback? = null
): Job {
    return coroutineScope.simpleScopeExecuteResponseRequest(
        block, callback, globalCallback
    )
}

// ==================
// = LifecycleOwner =
// ==================

inline fun <T> LifecycleOwner.simpleLaunchExecuteRequest(
    // 请求方法体
    crossinline block: suspend () -> T?,
    // 当前请求每个阶段进行通知
    callback: Notify.Callback<T>,
    // 全局通知回调方法 ( 创建一个全局通用传入 )
    globalCallback: Notify.GlobalCallback? = null
): Job {
    return lifecycleScope.simpleScopeExecuteRequest(
        block, callback, globalCallback
    )
}

inline fun <T, R : Base.Response<T>> LifecycleOwner.simpleLaunchExecuteResponseRequest(
    // 请求方法体
    crossinline block: suspend () -> R?,
    // 当前请求每个阶段进行通知
    callback: Notify.ResultCallback<T, R>,
    // 全局通知回调方法 ( 创建一个全局通用传入 )
    globalCallback: Notify.GlobalCallback? = null
): Job {
    return lifecycleScope.simpleScopeExecuteResponseRequest(
        block, callback, globalCallback
    )
}