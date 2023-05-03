package com.example.myapplication.di

import android.app.Application
import com.example.myapplication.BuildConfig
import com.example.myapplication.network.AuthApi
import com.example.myapplication.repository.AuthDataSource
import com.example.myapplication.repository.WalletRepository
import com.example.myapplication.tabs.WalletApi
import com.example.myapplication.util.PrefManager

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitInstance {

    val BASE_URL = "https://zignuts.dev/silencio/api/"


    class SupportInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
//            if (request.url.encodedPath.contains(actionAuth)) {
//                return chain.proceed(request)
//            }
//            if (request.url.encodedPath.contains(SUBSCRIPTION_PLAN)) {
//                Timber.d("Inside SUBSCRIPTION_PLAN")
//                request = request.newBuilder()
//                    .header(
//                        "Authorization",
//                        "Bearer ${PrefManager.getString(PrefManager.APPLICATION_TOKEN)}"
//                    )
//                    .header("Accept", "application/json")
//                    .header("Content-Type", "application/json")
//                    .build()
//                return chain.proceed(request)
//            }

        if (PrefManager.getString(PrefManager.ACCESS_TOKEN).isNotEmpty()) {
            request = request.newBuilder()
                .header(
                    "x-auth",
                    PrefManager.getString(PrefManager.ACCESS_TOKEN)
                )
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build()
            return chain.proceed(request)
        }
//                .header(
//                    "Currency",
//                    "${PrefManager.getString(PrefManager.CURRENCY_CODE)}"
//                )
        request = request.newBuilder()
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .build()
        return chain.proceed(request)
    }
}



    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {

        val interceptor = SupportInterceptor()
        val logging = HttpLoggingInterceptor()
        if(BuildConfig.DEBUG){
            logging.level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(logging)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideRecipeApiSrvice(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }
    @Singleton
    @Provides
    fun provideRecipeDataSource(application: Application,api: AuthApi): AuthDataSource {
        return AuthDataSource(application,api)
    }


    @Singleton
    @Provides
    fun walletApiService(retrofit: Retrofit): WalletApi {
        return retrofit.create(WalletApi::class.java)
    }

    @Singleton
    @Provides
    fun walletDataSource(api: WalletApi): WalletRepository {
        return WalletRepository(api)
    }


}
