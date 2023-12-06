package com.soligdag.filmdrawer

import android.app.Application
import com.soligdag.filmdrawer.data.AppContainer
import com.soligdag.filmdrawer.data.DefaultAppContainer

class FilmDrawerApplication : Application() {
    companion object {
        lateinit var container : AppContainer
    }
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(applicationContext)
    }
}