package com.example.currency.vm

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currency.model.ConvertResponse
import com.example.currency.model.ExchangeRatesResponse
import com.example.currency.model.FetchOneResponse
import com.example.currency.model.HistoricalResponse
import com.example.currency.model.TimeSeriesResponse
import com.example.currency.network.RestEngine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class ExchangeRatesViewModel : ViewModel() {

    val currencyList = listOf(
        "AED",
        "AFN",
        "ALL",
        "AMD",
        "ANG",
        "AOA",
        "ARS",
        "AUD",
        "AWG",
        "AZN",
        "BAM",
        "BBD",
        "BDT",
        "BGN",
        "BHD",
        "BIF",
        "BMD",
        "BND",
        "BOB",
        "BRL",
        "BSD",
        "BTN",
        "BWP",
        "BZD",
        "CAD",
        "CDF",
        "CHF",
        "CLF",
        "CLP",
        "CNH",
        "CNY",
        "COP",
        "CUP",
        "CVE",
        "CZK",
        "DJF",
        "DKK",
        "DOP",
        "DZD",
        "EGP",
        "ERN",
        "ETB",
        "EUR",
        "FJD",
        "FKP",
        "GBP",
        "GEL",
        "GHS",
        "GIP",
        "GMD",
        "GNF",
        "GTQ",
        "GYD",
        "HKD",
        "HNL",
        "HRK",
        "HTG",
        "HUF",
        "IDR",
        "ILS",
        "INR",
        "IQD",
        "IRR",
        "ISK",
        "JMD",
        "JOD",
        "JPY",
        "KES",
        "KGS",
        "KHR",
        "KMF",
        "KPW",
        "KRW",
        "KWD",
        "KYD",
        "KZT",
        "LAK",
        "LBP",
        "LKR",
        "LRD",
        "LSL",
        "LYD",
        "MAD",
        "MDL",
        "MGA",
        "MKD",
        "MMK",
        "MNT",
        "MOP",
        "MRU",
        "MUR",
        "MVR",
        "MWK",
        "MXN",
        "MYR",
        "MZN",
        "NAD",
        "NGN",
        "NOK",
        "NPR",
        "NZD",
        "OMR",
        "PAB",
        "PEN",
        "PGK",
        "PHP",
        "PKR",
        "PLN",
        "PYG",
        "QAR",
        "RON",
        "RSD",
        "RUB",
        "RWF",
        "SAR",
        "SCR",
        "SDG",
        "SEK",
        "SGD",
        "SHP",
        "SLL",
        "SOS",
        "SRD",
        "SYP",
        "SZL",
        "THB",
        "TJS",
        "TMT",
        "TND",
        "TOP",
        "TRY",
        "TTD",
        "TWD",
        "TZS",
        "UAH",
        "UGX",
        "USD",
        "UYU",
        "UZS",
        "VND",
        "VUV",
        "WST",
        "XAF",
        "XCD",
        "XDR",
        "XOF",
        "XPF",
        "YER",
        "ZAR",
        "ZMW"
    )

    private val _exchangeRates = MutableLiveData<ExchangeRatesResponse?>()
    val exchangeRates = _exchangeRates

    private val _fetchOneRate = MutableLiveData<FetchOneResponse?>()
    val fetchOneRate = _fetchOneRate

    private val _historicalRates =
        MutableLiveData<HistoricalResponse?>()
    val historicalRates = _historicalRates

    private val _timeSeries =
        MutableLiveData<TimeSeriesResponse?>()
    val timeSeries = _timeSeries

    private val _conversion = MutableLiveData<ConvertResponse?>()
    val conversion = _conversion

    private val _from = MutableStateFlow("")
    val from: StateFlow<String> = _from.asStateFlow()

    private val _to = MutableStateFlow("")
    val to: StateFlow<String> = _to.asStateFlow()

    private val _toMulti = MutableStateFlow("")
    val toMulti: StateFlow<String> = _toMulti.asStateFlow()

    private val _toHistoricalChart = MutableStateFlow("")
    val toHistoricalChart: StateFlow<String> = _toHistoricalChart.asStateFlow()

    private val _expandedFrom = MutableStateFlow(false)
    val expandedFrom: StateFlow<Boolean> = _expandedFrom.asStateFlow()

    private val _expandedTo = MutableStateFlow(false)
    val expandedTo: StateFlow<Boolean> = _expandedTo.asStateFlow()

    private val _selectedCurrencyFlagFrom = MutableStateFlow(-1)
    val selectedCurrencyFlagFrom: StateFlow<Int> = _selectedCurrencyFlagFrom.asStateFlow()

    private val _selectedCurrencyFlagTo = MutableStateFlow(-1)
    val selectedCurrencyFlagTo: StateFlow<Int> = _selectedCurrencyFlagTo.asStateFlow()

    private val _amount = MutableStateFlow(1f)
    val amount: StateFlow<Float> = _amount.asStateFlow()

    private val apiService = RestEngine.apiService

    fun fetchAll(apiKey: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val defaultCurrency = _from.value.ifEmpty { "COP" }
                val response = apiService.getRates(apiKey = apiKey, from = defaultCurrency)
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

    fun fetchOne(apiKey: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val defaultCurrency = _from.value.ifEmpty { "COP" }
                val defaultConverted = _to.value.ifEmpty { "USD" }
                val response = apiService.getSingleCurrencyRate(
                    apiKey = apiKey,
                    from = defaultCurrency,
                    to = defaultConverted
                )
                if (response.isSuccessful) {
                    val data = response.body()
                    _fetchOneRate.postValue(data)
                } else {
                    // Handle error
                }
            } catch (e: Exception) {
                // Handle network or parsing error
            }
        }
    }

    fun fetchMulti(apiKey: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val defaultCurrency = _from.value.ifEmpty { "COP" }
                val defaultConverted = _toMulti.value.ifEmpty { "USD,EUR,JPY,CAD,AUD,CHF,CNY,ZAR" }
                val response = apiService.fetchMulti(
                    apiKey = apiKey,
                    from = defaultCurrency,
                    to = defaultConverted
                )
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchHistoricalChart(apiKey: String, fromCurrency: String, toCurrency: String) {
        _toHistoricalChart.value = toCurrency
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val end = LocalDate.now()
                val start = end.minusDays(14) // Subtract i days from today
                val response = apiService.getTimeSeries(
                    apiKey = apiKey,
                    from = fromCurrency,
                    to = toCurrency,
                    start = start.toString(),
                    end = end.toString()
                )
                if (response.isSuccessful) {
                    val data = response.body()
                    _timeSeries.postValue(data)
                } else {
                    // Handle error
                }
            } catch (e: Exception) {
                // Handle network or parsing error
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun iterateThroughDates(numberOfDays: Long) {
        val defaultCurrency = _from.value.ifEmpty { "COP" }
        val today = LocalDate.now()
        val date = today.minusDays(numberOfDays) // Subtract i days from today
        historical("2d7e76630f-e86becf22e-sayyau", date.toString(), defaultCurrency)
    }

    fun calculateWeeklyChange(key: String, value: Double): Double {
        val before = _historicalRates.value?.results?.get(key)
        if (before != null) {
            return value - before
        }
        return 0.0
    }

    fun calculatePercentageChange(key: String, newValue: Double): Double {
        val oldValue = _historicalRates.value?.results?.get(key)
        return ((newValue - oldValue!!) / oldValue) * 100
    }

    private fun historical(apiKey: String, date: String, fromCurrency: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.getHistoricalRates(
                    apiKey = apiKey,
                    date = date,
                    from = fromCurrency,
                    targetCurrencies = null
                )
                if (response.isSuccessful) {
                    val data = response.body()
                    _historicalRates.postValue(data)
                } else {
                    // Handle error
                }
            } catch (e: Exception) {
                // Handle network or parsing error
            }
        }
    }


    fun updateFrom(newValue: String) {
        _from.value = newValue
    }

    fun updateTo(newValue: String) {
        _to.value = newValue
    }

    fun updateAmount(newValue: Float) {
        _amount.value = newValue
    }

    fun setExpandedFrom(value: Boolean) {
        _expandedFrom.value = value
    }

    fun setExpandedTo(value: Boolean) {
        _expandedTo.value = value
    }

    fun setSelectedCurrencyFlagFrom(flagIcon: Int) {
        _selectedCurrencyFlagFrom.value = flagIcon
    }

    fun setSelectedCurrencyFlagTo(flagIcon: Int) {
        _selectedCurrencyFlagTo.value = flagIcon
    }
}

