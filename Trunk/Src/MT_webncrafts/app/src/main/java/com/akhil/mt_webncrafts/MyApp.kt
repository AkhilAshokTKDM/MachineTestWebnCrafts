package com.akhil.mt_webncrafts

import android.app.Application
import android.content.Context
import com.facebook.drawee.backends.pipeline.Fresco

class MyApp : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: MyApp? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        // initialize for any
        Fresco.initialize(this)
        // Use ApplicationContext.
        // example: SharedPreferences etc...
        val context: Context = MyApp.applicationContext()
    }
}