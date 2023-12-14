package com.soligdag.filmdrawer.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.soligdag.filmdrawer.data.network.NetworkAPIService
import com.soligdag.filmdrawer.data.repositories.MediaRepository
import com.soligdag.filmdrawer.data.repositories.MediaRepositoryImpl
import com.soligdag.filmdrawer.data.repositories.UserDataRepository
import com.soligdag.filmdrawer.data.repositories.UserDataRepositoryImpl
import com.soligdag.filmdrawer.data.room.FilmDrawerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private val baseAPIURl = "https://api.themoviedb.org/3/"


    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): FilmDrawerDatabase {
        return Room.databaseBuilder(context = appContext , FilmDrawerDatabase::class.java, "FilmDrawerDatabase").build()
    }

    @Provides
    @Singleton
    fun provideAPIService() : NetworkAPIService {
        return getRetrofitInstanceForTMDBAPIs().create(NetworkAPIService::class.java)
    }




    private fun getRetrofitInstanceForTMDBAPIs(readTimeOut: Long = 20): Retrofit { // was 20
        val builder = OkHttpClient().newBuilder()
        val okHttpClient = builder
            .connectTimeout(10, TimeUnit.SECONDS) // was 10
            .readTimeout(readTimeOut, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS) // was 20
            .build()

        return Retrofit.Builder().baseUrl(baseAPIURl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideMediaRepository(apiService : NetworkAPIService) : MediaRepository {
        return MediaRepositoryImpl(apiService)
        //return MediaRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideUserDataRepository(roomDatabase: FilmDrawerDatabase) : UserDataRepository {
        return UserDataRepositoryImpl(roomDatabase)
    }
}





/*
private val baseAPIURl = "https://api.themoviedb.org/3/"

private val retrofitService: NetworkAPIService by lazy {
    getRetrofitInstanceForTMDBAPIs().create(NetworkAPIService::class.java)
}

private val roomDatabase = Room.databaseBuilder(context = appContext, FilmDrawerDatabase::class.java, "FilmDrawerDatabase").build()

override val mediaRepository: MediaRepository by lazy { MediaRepositoryImpl(retrofitService, roomDatabase) }


private fun getRetrofitInstanceForTMDBAPIs(readTimeOut: Long = 20): Retrofit { // was 20
    val builder = OkHttpClient().newBuilder()
    val okHttpClient = builder
        .connectTimeout(10, TimeUnit.SECONDS) // was 10
        .readTimeout(readTimeOut, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS) // was 20
        .build()

    return Retrofit.Builder().baseUrl(baseAPIURl)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient)
        .build()
}*/
