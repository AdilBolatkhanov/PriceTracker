package com.example.pricetracker.di

import android.content.Context
import androidx.room.Room
import com.example.pricetracker.data.local.ProductsDao
import com.example.pricetracker.data.local.ProductsDatabase
import com.example.pricetracker.data.remote.TrackerApi
import com.example.pricetracker.util.Constant.BASE_URL
import com.example.pricetracker.util.Constant.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteProductsDataSource

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class LocalProductsDataSource

    @Singleton
    @Provides
    fun provideProductsDatabase(
        @ApplicationContext context: Context
    ): ProductsDatabase =
        Room.databaseBuilder(context, ProductsDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideProductsDao(
        database: ProductsDatabase
    ): ProductsDao = database.productsDao()

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideTrackerApi(
        client: OkHttpClient
    ): TrackerApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(TrackerApi::class.java)
    }
}