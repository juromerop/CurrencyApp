package com.example.currency.view.screens

import CurrencyPicker
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.currency.navigation.AppScreens
import com.example.currency.view.components.TopBar
import com.example.currency.vm.ExchangeRatesViewModel
import getResourceIdForCurrency

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FetchOneScreen(navController: NavController, vm: ExchangeRatesViewModel) {
    Scaffold(topBar = { TopBar("Fetch One Rate") }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            FetchOneScreenContent(navController = navController, vm = vm)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FetchOneScreenContent(navController: NavController, vm: ExchangeRatesViewModel) {
    val exchangeRates = vm.fetchOneRate.observeAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CurrencyPicker(viewModel = vm) { vm.fetchOne("2d7e76630f-e86becf22e-sayyau") }
            exchangeRates.value?.let { ratesResponse ->
                // Row for displaying the base currency
                Text(text = "Base: ${ratesResponse.base}")

                // Row for displaying the date
                Text(text = "Updated: ${ratesResponse.updated}")

                // Lazy column for displaying each currency and its value
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(ratesResponse.result.toList()) { (currency, value) ->
                        val flagIcon = getResourceIdForCurrency(currency)
                        val weeklyChange = vm.calculateWeeklyChange(currency, value)
                        val percentage = vm.calculatePercentageChange(currency, value)
                        CurrencyExchangeRateItem(
                            currency,
                            ratesResponse.base,
                            value,
                            weeklyChange,
                            percentage,
                            flagIcon
                        ) {
                            navController.navigate(route = AppScreens.HistoricalScreen.route)
                            vm.fetchHistoricalChart(
                                "2d7e76630f-e86becf22e-sayyau",
                                fromCurrency = ratesResponse.base,
                                toCurrency = currency
                            )
                        }
                    }
                }
            }
        }
    }
}
