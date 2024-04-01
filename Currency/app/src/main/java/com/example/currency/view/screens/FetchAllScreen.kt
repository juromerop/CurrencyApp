package com.example.currency.view.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.currency.R
import com.example.currency.constants.Constants
import com.example.currency.navigation.AppScreens
import com.example.currency.view.components.LineChart
import com.example.currency.view.components.TopBar
import com.example.currency.vm.ExchangeRatesViewModel
import getResourceIdForCurrency

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RatesPage(navController: NavController, vm: ExchangeRatesViewModel) {
    Scaffold(topBar = { TopBar("Fetch All Rates") }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            RatesPageContent(navController = navController, vm = vm)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RatesPageContent(navController: NavController, vm: ExchangeRatesViewModel) {
    val exchangeRates = vm.exchangeRates.observeAsState()

    exchangeRates.value?.let { ratesResponse ->
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
                    items(ratesResponse.results.toList()) { (currency, value) ->
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

@Composable
fun CurrencyExchangeRateItem(
    baseCurrency: String,
    convertedCurrency: String,
    conversionRate: Double,
    weeklyChange: Double,
    weeklyChangePercentage: Double,
    flagIcon: Int,
    onViewChartlick: () -> Unit
) {
    val formatedChange = String.format(
        "%.5f",
        weeklyChange
    )

    val formattedPercentage = String.format(
        "%.2f",
        weeklyChangePercentage
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(horizontal = 8.dp, vertical = 16.dp),
        border = BorderStroke(2.dp, Color.Black)
    ) {
        Row(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (flagIcon != -1) {
                        Image(
                            painter = painterResource(id = flagIcon),
                            contentDescription = "Flag for $baseCurrency"
                        )
                    }
                    Spacer(modifier = Modifier.size(4.dp))
                    Constants.currencies.get(baseCurrency)?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Clip
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Column {
                    Text(
                        text = "1 $convertedCurrency equals to",
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Clip
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Row {
                        Text(
                            text = String.format(
                                "%.8f",
                                conversionRate
                            ), // Format for up to 8 decimal places
                            style = MaterialTheme.typography.titleLarge,
                            maxLines = 1,
                            overflow = TextOverflow.Clip
                        )
                        Text(
                            text = " $baseCurrency",
                            style = MaterialTheme.typography.titleLarge,
                            maxLines = 1,
                            overflow = TextOverflow.Clip
                        )
                    }
                }
                Row(modifier = Modifier.padding(top = 8.dp)) { // Modify this row for the new content
                    // Add an Icon (optional)
                    if (weeklyChange > 0) {
                        Image(
                            painter = painterResource(id = R.drawable.increase),
                            colorFilter = ColorFilter.tint(
                                colorResource(id = R.color.button_green)
                            ),
                            contentDescription = "increase"
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.decrease),
                            contentDescription = "increase"
                        )
                    }
                    Text(
                        text = " $formatedChange ($formattedPercentage)%", // Your desired text
                        style = MaterialTheme.typography.bodySmall,
                        color = if (weeklyChange > 0) colorResource(id = R.color.button_green) else colorResource(
                            id = R.color.red
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Clip
                    )
                    Text(
                        text = " Weekly", // Your desired text
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Clip
                    )
                }
            }

            val dataDecrease: List<Pair<Int, Double>> = listOf(
                Pair(1, 3.2),
                Pair(2, 3.0),
                Pair(3, 3.1),
                Pair(4, 2.9),
                Pair(5, 2.8)
            )
            val dataIncrease: List<Pair<Int, Double>> = listOf(
                Pair(1, 3.2),
                Pair(2, 3.0),
                Pair(3, 3.1),
                Pair(4, 2.9),
                Pair(5, 3.0)
            )
            Column(modifier = Modifier.padding(end = 12.dp, top = 12.dp, bottom = 12.dp)) {
                LineChart(
                    data = if (weeklyChange > 0) dataIncrease else dataDecrease,
                    chartColor = if (weeklyChange > 0) R.color.button_green else R.color.red
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onViewChartlick()
                        }, text = "View chart -> ", style = MaterialTheme.typography.bodySmall,
                    color = colorResource(id = R.color.main_purple)
                )
            }
        }

    }
}
