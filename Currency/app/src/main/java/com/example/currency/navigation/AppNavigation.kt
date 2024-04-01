package com.example.currency.navigation

import android.os.Build
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.currency.view.screens.FetchMultiScreen
import com.example.currency.view.screens.FetchOneScreen
import com.example.currency.view.screens.HistoricalScreen
import com.example.currency.view.screens.HomeScreen
import com.example.currency.view.screens.RatesPage
import com.example.currency.vm.ExchangeRatesViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(viewModel: ExchangeRatesViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.HomeScreen.route) {
        composable(route = AppScreens.HomeScreen.route) {
            HomeScreen(navController = navController, vm = viewModel)
        }
        composable(route = AppScreens.FetchAllScreen.route) {
            RatesPage(navController = navController, vm = viewModel)
        }
        composable(route = AppScreens.FetchMultiScreen.route) {
            FetchMultiScreen(navController = navController, vm = viewModel)
        }
        composable(route = AppScreens.FetchOneScreen.route) {
            FetchOneScreen(navController = navController, vm = viewModel)
        }
        composable(route = AppScreens.ConversionScreen.route) {

        }
        composable(route = AppScreens.HistoricalScreen.route) {
            HistoricalScreen(navController = navController, vm = viewModel)
        }

    }

}