package com.example.currency.model

import com.google.gson.annotations.SerializedName

data class ExchangeRatesResponse(
    @SerializedName("success") val isSuccess: Boolean,
    @SerializedName("timestamp") val timestamp: Long,
    @SerializedName("base") val baseCurrency: String,
    @SerializedName("date") val date: String,
    @SerializedName("rates") val exchangeRates: Map<String, Double>
)
