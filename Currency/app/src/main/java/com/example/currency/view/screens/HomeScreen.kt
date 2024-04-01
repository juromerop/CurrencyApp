package com.example.currency.view.screens

import CurrencyPickerMenu
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.currency.R
import com.example.currency.navigation.AppScreens
import com.example.currency.view.components.TopBar
import com.example.currency.vm.ExchangeRatesViewModel


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController, vm: ExchangeRatesViewModel) {
    Scaffold(topBar = { TopBar("Currency App") }) {
        HomeContent(navController = navController, vm = vm)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeContent(navController: NavController, vm: ExchangeRatesViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val from by vm.from.collectAsState()
        val expandedFrom by vm.expandedFrom.collectAsState()
        val flagFrom by vm.selectedCurrencyFlagFrom.collectAsState()
        CurrencyPickerMenu(
            vm,
            { vm.updateFrom(it) },
            { vm.setExpandedFrom(it) },
            { vm.setSelectedCurrencyFlagFrom(it) },
            from,
            "From",
            expandedFrom,
            flagFrom
        )
        Spacer(modifier = Modifier.size(24.dp))
        Button(
            onClick = {
                navController.navigate(route = AppScreens.FetchAllScreen.route)
                vm.fetchAll("2d7e76630f-e86becf22e-sayyau")
            },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.main_purple))
        ) {
            Text(text = "Fetch all rates", modifier = Modifier.wrapContentSize())
        }
        Spacer(modifier = Modifier.size(24.dp))
        Button(
            onClick = {
                navController.navigate(route = AppScreens.FetchOneScreen.route)
            },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.main_purple))
        ) {
            Text(text = "Fetch one rate", modifier = Modifier.wrapContentSize())
        }
        Spacer(modifier = Modifier.size(24.dp))
        Button(
            onClick = {
                navController.navigate(route = AppScreens.FetchMultiScreen.route)
                vm.fetchMulti("2d7e76630f-e86becf22e-sayyau")
            },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.main_purple))
        ) {
            Text(text = "Fetch multiple rates", modifier = Modifier.wrapContentSize())
        }
    }
}