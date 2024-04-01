package com.example.currency.view.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.currency.view.components.TopBar
import com.example.currency.vm.ExchangeRatesViewModel

@Composable
fun FetchMultiScreen(navController: NavController, vm: ExchangeRatesViewModel) {
    Scaffold(topBar = { TopBar("Fetch Multiple Rates") }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            RatesPageContent(navController = navController, vm = vm)
        }
    }
}
