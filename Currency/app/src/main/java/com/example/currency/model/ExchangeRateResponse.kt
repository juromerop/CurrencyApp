package com.example.currency.model

import com.google.gson.annotations.SerializedName

data class ExchangeRatesResponse(
    @SerializedName("base") val base: String,
    @SerializedName("results") val results: Map<String, Double>,
    @SerializedName("updated") val updated: String,
    @SerializedName("ms") val milliseconds: Int
)
