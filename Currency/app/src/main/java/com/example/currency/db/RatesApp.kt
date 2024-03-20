package com.example.currency.db

import android.content.Context
import androidx.room.Room

class RatesApp() {
    val room by lazy {
        Room
            .databaseBuilder(applicationContext, RatesDb::class.java, "ExchangeRate")
            .build()
    }

    companion object {
        lateinit var applicationContext: Context
    }
}