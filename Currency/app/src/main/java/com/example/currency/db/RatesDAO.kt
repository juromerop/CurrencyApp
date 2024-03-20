package com.example.currency.db


import androidx.room.Dao
import androidx.room.Query
import com.example.currency.model.ExchangeRate
import kotlinx.coroutines.flow.Flow

@Dao
interface RatesDAO {
    @Query("SELECT * FROM ExchangeRate")
    fun getAllRates(): Flow<List<ExchangeRate>>
}
