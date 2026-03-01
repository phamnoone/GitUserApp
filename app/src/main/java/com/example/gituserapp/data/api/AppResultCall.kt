package com.example.gituserapp.data.api

import com.example.gituserapp.domain.model.ApiResponse
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.jvm.java

class AppResultCallAdapterFactory private constructor() : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit,
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) {
            return null
        }

        val callType = getParameterUpperBound(0, returnType as ParameterizedType)
        if (getRawType(callType) != ApiResponse::class.java) {
            return null
        }

        val resultType = getParameterUpperBound(0, callType as ParameterizedType)
        return AppResultCallAdapter(resultType)
    }

    companion object {
        fun create(): AppResultCallAdapterFactory = AppResultCallAdapterFactory()
    }
}

class AppResultCallAdapter(
    private val resultType: Type
) : CallAdapter<Type, Call<ApiResponse<Type>>> {

    override fun responseType(): Type = resultType

    override fun adapt(call: Call<Type>): Call<ApiResponse<Type>> {
        return AppResultCall(call)
    }
}


class AppResultCall<T : Any>(private val proxy: Call<T>) : Call<ApiResponse<T>> {

    override fun enqueue(callback: Callback<ApiResponse<T>>) {
        proxy.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val networkResult = handleApi(response)
                callback.onResponse(this@AppResultCall, Response.success(networkResult))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val networkResult = if (t is java.io.IOException) {
                    ApiResponse.Error.NetworkError<T>(t)
                } else {
                    ApiResponse.Error.UnknownError<T>(t)
                }
                callback.onResponse(this@AppResultCall, Response.success(networkResult))
            }
        })
    }

    override fun execute(): Response<ApiResponse<T>> = throw NotImplementedError()

    override fun clone(): Call<ApiResponse<T>> = AppResultCall(proxy.clone())

    override fun request(): Request = proxy.request()

    override fun timeout(): Timeout = proxy.timeout()

    override fun isExecuted(): Boolean = proxy.isExecuted

    override fun isCanceled(): Boolean = proxy.isCanceled

    override fun cancel() {
        proxy.cancel()
    }

    private fun <T : Any> handleApi(response: Response<T>): ApiResponse<T> {
        return try {
            val body = response.body()
            if (response.isSuccessful && body != null) {
                ApiResponse.Success(body)
            } else {
                ApiResponse.Error.HttpError(code = response.code(), errorBody = response.errorBody()?.string())
            }
        } catch (e: HttpException) {
            ApiResponse.Error.HttpError(code = e.code(), errorBody = e.response()?.errorBody()?.string())
        } catch (e: java.io.IOException) {
            ApiResponse.Error.NetworkError(e)
        } catch (e: Throwable) {
            ApiResponse.Error.UnknownError(e)
        }
    }
}
