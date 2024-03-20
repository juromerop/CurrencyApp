package com.example.currency.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestEngine {
    companion object {
        private const val URL = "https://api.exchangeratesapi.io/v1/"

        private fun getInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder().serializeNulls().create()
                    )
                )
                .build()
        }

        val apiService = getInstance().create(APIService::class.java)
    }
}