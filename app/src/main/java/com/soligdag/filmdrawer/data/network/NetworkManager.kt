package com.soligdag.filmdrawer.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class NetworkManager {
    companion object {
        private const val TMDB_API_BASE_URL = "https://api.themoviedb.org/3/"
        private const val TMDB_API_TOKEN = "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0MWQxNzU4ZjhhZGE0ZGFmOGVlYjUxMzMwNjRkODJmYSIsInN1YiI6IjY1MjZmNGUzODEzODMxMDBjNDg5YTY5MCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.o8ps6XFTtbxoCw1zepMLynDDPJmHckFyf48EZxpdIFs"
        private var mInstance: NetworkManager? = null

        @Synchronized
        fun getInstance(): NetworkManager? {
            if (mInstance == null) {
                mInstance = NetworkManager()
            }
            return mInstance
        }

    }

    private fun getRetrofitInstanceForGoogleAPIs(readTimeOut: Long = 20): Retrofit { // was 20
        var builder = OkHttpClient().newBuilder()


        val okHttpClient = builder
            .connectTimeout(10, TimeUnit.SECONDS) // was 10
            .readTimeout(readTimeOut, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS) // was 20
            .build()
        var url = TMDB_API_BASE_URL

        return Retrofit.Builder().baseUrl(url)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient)
            .build()
    }
}