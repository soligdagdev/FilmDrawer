package com.soligdag.filmdrawer.data

import android.content.Context
import androidx.room.Room
import com.soligdag.filmdrawer.data.network.NetworkAPIService
import com.soligdag.filmdrawer.data.repositories.MediaRepository
import com.soligdag.filmdrawer.data.repositories.MediaRepositoryImpl
import com.soligdag.filmdrawer.data.room.FilmDrawerDatabase
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class DefaultAppContainer(private val appContext : Context) : AppContainer {

    private val baseAPIURl = "https://api.themoviedb.org/3/"

    private val retrofitService: NetworkAPIService by lazy {
        getRetrofitInstanceForTMDBAPIs().create(NetworkAPIService::class.java)
    }

    private val roomDatabase = Room.databaseBuilder(context = appContext, FilmDrawerDatabase::class.java, "FilmDrawerDatabase").build()

    override val mediaRepository: MediaRepository by lazy { MediaRepositoryImpl(retrofitService) }


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
}