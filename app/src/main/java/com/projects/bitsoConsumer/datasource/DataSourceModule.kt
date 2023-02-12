package com.projects.bitsoConsumer.datasource

import android.content.Context
import androidx.room.Room
import com.projects.bitsoConsumer.room.dao.AskBidsDao
import com.projects.bitsoConsumer.room.dao.TradesDao
import com.projects.bitsoConsumer.room.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Singleton
    @Provides
    @Named("Baseurl")
    fun getUri() = "https://api.bitso.com/v3/"

    @Singleton
    @Provides
    fun getRetrofit(@Named("Baseurl") baseurl: String): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor).build()
        return Retrofit.Builder()
            .baseUrl(baseurl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun restBinanceDataSource(retrofit: Retrofit): BitsoDataSource =
        retrofit.create(BitsoDataSource::class.java)

    @Singleton
    @Provides
    fun restBinanceDetailsDataSource(retrofit: Retrofit): BitsoDetailsDataSource =
        retrofit.create(BitsoDetailsDataSource::class.java)

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "user_database",
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun userdao(db: AppDatabase): AskBidsDao = db.askBidsDao()

    @Provides
    @Singleton
    fun tradesdao(db: AppDatabase): TradesDao = db.tradesDao()
}
