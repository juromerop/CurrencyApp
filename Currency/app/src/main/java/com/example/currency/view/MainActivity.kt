package com.example.currency.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.currency.db.RatesApp
import com.example.currency.ui.theme.CurrencyTheme
import com.example.currency.vm.ExchangeRatesViewModel

class MainActivity : ComponentActivity() {
    val viewModel: ExchangeRatesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RatesApp.applicationContext = applicationContext
        setContent {
            CurrencyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun RatesPage(vm : ExchangeRatesViewModel){
    val exchangeRates = vm.exchangeRates.observeAsState()

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CurrencyTheme {
        Greeting("Android")
    }
}

