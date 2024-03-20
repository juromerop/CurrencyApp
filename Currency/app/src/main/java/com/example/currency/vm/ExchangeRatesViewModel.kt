package com.example.currency.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currency.model.ExchangeRatesResponse
import com.example.currency.network.APIService
import com.example.currency.network.RestEngine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ExchangeRatesViewModel : ViewModel() {

    private val _exchangeRates = MutableLiveData<ExchangeRatesResponse?>()
    val exchangeRates = _exchangeRates

    private val apiService = RestEngine.apiService

    fun fetchExchangeRates(apiKey: String, baseCurrency: String, symbols: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.getRates(apiKey, baseCurrency, symbols)
                if (response.isSuccessful) {
                    val data = response.body()
                    _exchangeRates.postValue(data)
                } else {
                    // Handle error
                }
            } catch (e: Exception) {
                // Handle network or parsing error
            }
        }
    }
}
