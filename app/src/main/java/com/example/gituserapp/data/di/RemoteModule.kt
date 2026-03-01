package com.example.gituserapp.data.di

import com.example.gituserapp.base.KeyConstant
import com.example.gituserapp.data.api.AppResultCallAdapterFactory
import com.example.gituserapp.data.api.GithubApi
import com.gituserapp.BuildConfig
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor.Chain
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import kotlin.apply
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Provides
    @Singleton
    fun createRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .connectTimeout(KeyConstant.Network.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(KeyConstant.Network.WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(KeyConstant.Network.READ_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor { chain: Chain ->
                val originalRequest = chain.request()

                val request = originalRequest.newBuilder()
                    .header("Accept", "application/vnd.github+json")
                    .header("X-GitHub-Api-Version", "2022-11-28")
                    .build()

                chain.proceed(request)
            }

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            client.addInterceptor(logging)
        }

        val gson = GsonBuilder()
            .setDateFormat(KeyConstant.API_DATE_FORMAT)
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.GITHUB_ENDPOINT)
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(AppResultCallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun createGithubApi(retrofit: Retrofit): GithubApi {
        return retrofit.create(GithubApi::class.java)
    }
}
