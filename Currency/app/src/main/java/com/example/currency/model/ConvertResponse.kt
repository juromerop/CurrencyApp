package com.example.currency.model

import com.google.gson.annotations.SerializedName

data class ConvertResponse(
    @SerializedName("base") val baseCurrency: String,
    @SerializedName("amount") val amount: Float,
    @SerializedName("result") val result: Map<String, ConversionDetails>,
    @SerializedName("ms") val milliseconds: Int
) {
    data class ConversionDetails(
        @SerializedName("value") val convertedAmount: Double,
        @SerializedName("rate") val exchangeRate: Double
    )
}

