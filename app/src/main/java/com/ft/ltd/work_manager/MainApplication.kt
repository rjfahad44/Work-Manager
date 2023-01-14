package com.ft.ltd.work_manager

import android.app.Application
import android.content.Context


class MainApplication: Application() {
    private lateinit var appContext: Context
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}