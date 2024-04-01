package com.example.currency.view.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.currency.R


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    showBackArrow: Boolean = false, // Optional back arrow
    onBackClick: () -> Unit = {}, // Callback for back arrow click
    onHelpClick: () -> Unit = {}  // Callback for help icon click
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            if (showBackArrow) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    ) // Use Icons.ArrowBack or your custom icon
                }
            }
        },
        actions = {
            IconButton(onClick = onHelpClick) {
                Icon(
                    Icons.Default.Info,
                    contentDescription = "Help",
                    tint = Color.White
                ) // Use Icons.Help or your custom icon
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.main_purple),
            titleContentColor = Color.White
        )
    )
}