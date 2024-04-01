package com.example.currency.navigation

sealed class AppScreens(val route: String) {
    object HomeScreen : AppScreens("home_screen")
    object FetchAllScreen : AppScreens("fetch_all_screen")
    object FetchOneScreen : AppScreens("fetch_one_screen")
    object FetchMultiScreen : AppScreens("fetch_multi_screen")
    object ConversionScreen : AppScreens("conversion_screen")
    object HistoricalScreen : AppScreens("historical_screen")

}