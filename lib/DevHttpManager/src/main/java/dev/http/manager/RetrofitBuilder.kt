package dev.http.manager

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * detail: Retrofit Builder 接口
 * @author Ttt
 */
interface RetrofitBuilder {

    /**
     * 创建 Retrofit Builder
     * @param oldRetrofit 上一次构建的 Retrofit
     * @param httpUrl 构建使用指定 baseUrl
     * @param okHttp OkHttpClient 构建全局复用
     * @return Retrofit.Builder
     */
    fun createRetrofitBuilder(
        oldRetrofit: Retrofit?,
        httpUrl: HttpUrl?,
        okHttp: OkHttpClient.Builder?
    ): Retrofit.Builder

    // ==========
    // = 通知事件 =
    // ==========

    /**
     * 重新构建前调用
     * @param key String
     * @param oldRetrofit 上一次构建的 Retrofit
     * 在 [createRetrofitBuilder] 之前调用
     */
    fun onResetBefore(
        key: String,
        oldRetrofit: Retrofit?
    )

    /**
     * 重新构建后调用
     * @param key String
     * @param newRetrofit 重新构建的 Retrofit 对象
     * 在 [createRetrofitBuilder] 之后调用
     */
    fun onReset(
        key: String,
        newRetrofit: Retrofit?
    )
}