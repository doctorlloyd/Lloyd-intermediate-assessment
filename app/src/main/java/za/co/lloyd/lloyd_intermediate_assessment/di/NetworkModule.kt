package za.co.lloyd.lloyd_intermediate_assessment.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import za.co.lloyd.lloyd_intermediate_assessment.data.remote.ToDoApiService
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    /** Provides BaseUrl as string */
    @Singleton
    @Provides
    fun provideBaseURL(): String {
        return "https://api.openweathermap.org"
    }

    /** Provides LoggingInterceptor for api information */
    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    /** Provides custom OkHttp */
    @Singleton
    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()

        okHttpClient.callTimeout(10, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(10, TimeUnit.SECONDS)
        okHttpClient.readTimeout(10, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(10, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(loggingInterceptor)
        return okHttpClient.build()
    }

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    /** Provides converter factory for retrofit */
    @Singleton
    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    /** Provides ApiServices client for Retrofit */
    @Singleton
    @Provides
    fun provideRetrofitClient(baseUrl: String, okHttpClient: OkHttpClient, converterFactory: Converter.Factory): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient).addConverterFactory(converterFactory).build()
    }

    /** Provides Api Service using retrofit */
    @Singleton
    @Provides
    fun provideRestApiService(retrofit: Retrofit): ToDoApiService {
        return retrofit.create(ToDoApiService::class.java)
    }
}