package com.example.currency.network

import com.example.currency.model.ConvertResponse
import com.example.currency.model.CurrencyResponse
import com.example.currency.model.ExchangeRatesResponse
import com.example.currency.model.FetchOneResponse
import com.example.currency.model.HistoricalResponse
import com.example.currency.model.TimeSeriesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface APIService {
    @GET("/fetch-all")
    suspend fun getRates(
        @Query("api_key") apiKey: String,
        @Query("from") from: String = "COP",
    ): Response<ExchangeRatesResponse>

    @GET("/fetch-one")
    suspend fun getSingleCurrencyRate(
        @Query("api_key") apiKey: String,
        @Query("from") from: String = "COP",
        @Query("to") to: String = "USD"
    ): Response<FetchOneResponse>

    @GET("/fetch-multi")
    suspend fun fetchMulti(
        @Query("api_key") apiKey: String,
        @Query("from") from: String = "COP",
        @Query("to") to: String = "USD,EUR"
    ): Response<ExchangeRatesResponse>

    @GET("/time-series")
    suspend fun getTimeSeries(
        @Query("api_key") apiKey: String,
        @Query("start") start: String,
        @Query("end") end: String,
        @Query("from") from: String = "COP",
        @Query("to") to: String = "USD",
        @Query("interval") interval: String = "P1D"
    ): Response<TimeSeriesResponse>

    @GET("/historical")
    suspend fun getHistoricalRates(
        @Query("api_key") apiKey: String,
        @Query("date") date: String,
        @Query("from") from: String = "COP",
        @Query("to") targetCurrencies: String?
    ): Response<HistoricalResponse>

}
