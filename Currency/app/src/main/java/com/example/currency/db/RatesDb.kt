package com.example.currency.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.currency.model.ExchangeRate

@Database(
    entities = [ExchangeRate::class],
    version = 1
)
abstract class RatesDb : RoomDatabase() {
    abstract fun RatesDAO(): RatesDAO
}