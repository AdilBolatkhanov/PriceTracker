package com.example.pricetracker.di

import android.content.Context
import androidx.room.Room
import com.example.pricetracker.data.TrackerRepositoryImpl
import com.example.pricetracker.data.local.ProductLocalDataSource
import com.example.pricetracker.data.local.ProductLocalDataSourceImpl
import com.example.pricetracker.data.local.database.ProductsDao
import com.example.pricetracker.data.local.database.ProductsDatabase
import com.example.pricetracker.data.remote.ProductRemoteDataSource
import com.example.pricetracker.data.remote.ProductRemoteDataSourceImpl
import com.example.pricetracker.data.remote.api.TrackerApi
import com.example.pricetracker.domain.repository.TrackerRepository
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
    @RemoteProductsDataSource
    @Provides
    fun provideRemoteDataSource(
        api: TrackerApi
    ): ProductRemoteDataSource = ProductRemoteDataSourceImpl(api)

    @Singleton
    @LocalProductsDataSource
    @Provides
    fun provideLocalDataSource(
        dao: ProductsDao
    ): ProductLocalDataSource = ProductLocalDataSourceImpl(dao)

    @Singleton
    @Provides
    fun provideTrackerRepository(
        @RemoteProductsDataSource remoteDataSource: ProductRemoteDataSource,
        @LocalProductsDataSource localDataSource: ProductLocalDataSource,
        @ApplicationContext context: Context
    ): TrackerRepository {
        return TrackerRepositoryImpl(
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource,
            context = context
        )
    }

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