package com.example.currency.model

import com.google.gson.annotations.SerializedName

data class FetchOneResponse(
    @SerializedName("base") val base: String,
    @SerializedName("result") val result: Map<String, Double>,
    @SerializedName("updated") val updated: String,
    @SerializedName("ms") val milliseconds: Int
)
