package com.example.currency.view.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.currency.R
import com.example.currency.view.components.DetailedLineChart
import com.example.currency.view.components.TopBar
import com.example.currency.vm.ExchangeRatesViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoricalScreen(navController: NavController, vm: ExchangeRatesViewModel) {
    val timeSeries = vm.timeSeries.observeAsState()
    val toCurrency by vm.toHistoricalChart.collectAsState()


    Scaffold(topBar = { TopBar("Historical chart") }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            timeSeries.value?.let { timeSeriesResponse ->
                val currencyData = timeSeriesResponse.results[toCurrency] ?: emptyMap()
                val pairList = currencyData.map { entry ->
                    val date = LocalDate.parse(entry.key)
                    Pair(date.dayOfMonth, entry.value)
                }
                //val percentageData = getPercentageChange(pairList)


                DetailedLineChart(data = pairList, chartColor = R.color.button_green)
            }
        }
    }
}

fun getPercentageChange(data: List<Pair<Int, Double>>): List<Pair<Int, Double>> {
    val result = mutableListOf<Pair<Int, Double>>()
    data.forEachIndexed { index, (date, value) ->
        if (index > 0) {
            val previousValue = data[index - 1].second
            val change = ((value - previousValue) / previousValue) * 100
            result.add(Pair(date, change))
        } else {
            result.add(Pair(date, value)) // No change for the first day
        }
    }
    return result
}