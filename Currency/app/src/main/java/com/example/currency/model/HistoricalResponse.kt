package com.example.currency.model

import com.google.gson.annotations.SerializedName

data class HistoricalResponse(
    @SerializedName("date") val date: String,
    @SerializedName("base") val base: String,
    @SerializedName("results") val results: Map<String, Double>,
    @SerializedName("ms") val milliseconds: Int
)

data class TimeSeriesResponse(
    @SerializedName("start") val start: String,
    @SerializedName("end") val end: String,
    @SerializedName("interval") val interval: String,
    @SerializedName("base") val base: String,
    @SerializedName("results") val results: Map<String, Map<String, Double>>,
    @SerializedName("ms") val ms: Int
)
