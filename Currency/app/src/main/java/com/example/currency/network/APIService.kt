package com.example.currency.network

import com.example.currency.model.ExchangeRatesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("latest")
    suspend fun getRates(
        @Query("access_key") apiKey: String,
        @Query("base") baseCurrency: String?,
        @Query("symbols") symbols: String?
    ): Response<ExchangeRatesResponse>
}
