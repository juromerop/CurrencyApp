package com.example.currency.model

import com.google.gson.annotations.SerializedName

data class CurrencyResponse(
    @SerializedName("currencies") val currencies: Map<String, String>,
    @SerializedName("ms") val ms: Int
)
