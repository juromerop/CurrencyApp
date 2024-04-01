import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.currency.R
import com.example.currency.vm.ExchangeRatesViewModel

@Composable
fun CurrencyPicker(viewModel: ExchangeRatesViewModel, onGoClicked: () -> Unit) {
    val from by viewModel.from.collectAsState()
    val to by viewModel.to.collectAsState()
    val expandedFrom by viewModel.expandedFrom.collectAsState()
    val expandedTo by viewModel.expandedTo.collectAsState()
    val flagFrom by viewModel.selectedCurrencyFlagFrom.collectAsState()
    val flagTo by viewModel.selectedCurrencyFlagTo.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Select a currency",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            CurrencyPickerMenu(
                viewModel,
                { viewModel.updateFrom(it) },
                { viewModel.setExpandedFrom(it) },
                { viewModel.setSelectedCurrencyFlagFrom(it) },
                from,
                "From",
                expandedFrom,
                flagFrom
            )
            Spacer(modifier = Modifier.size(8.dp))
            Image(imageVector = Icons.Filled.ArrowForward, contentDescription = "Arrow")
            Spacer(modifier = Modifier.size(8.dp))
            CurrencyPickerMenu(
                viewModel,
                { viewModel.updateTo(it) },
                { viewModel.setExpandedTo(it) },
                { viewModel.setSelectedCurrencyFlagTo(it) },
                to,
                "To",
                expandedTo,
                flagTo
            )
            Spacer(modifier = Modifier.size(24.dp))

            Button(
                onClick = { onGoClicked() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.button_green),
                    contentColor = Color.White
                ),
                shape = MaterialTheme.shapes.small,
                border = BorderStroke(width = 2.dp, colorResource(id = R.color.button_green)),
            ) {
                Text(text = "Go", color = Color.White)
            }
        }

    }
}

@Composable
fun CurrencyPickerMenu(
    viewModel: ExchangeRatesViewModel,
    onCurrencySelected: (String) -> Unit,
    onExpand: (Boolean) -> Unit,
    onCurrencyFlagSelected: (Int) -> Unit,
    currencyValue: String,
    buttonPlaceholder: String,
    expanded: Boolean,
    selectedCurrencyFlag: Int
) {
    Box {
        TextButton(
            onClick = { onExpand(true) },
            shape = MaterialTheme.shapes.small,
            border = BorderStroke(width = 2.dp, Color.Black),
        ) {
            Text(currencyValue.takeIf { it.isNotEmpty() } ?: buttonPlaceholder, color = Color.Black)
            if (selectedCurrencyFlag != -1) {
                Spacer(modifier = Modifier.size(12.dp))
                Image(
                    painter = painterResource(id = selectedCurrencyFlag),
                    contentDescription = "Flag"
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpand(false) },
            modifier = Modifier.requiredSizeIn(maxHeight = 200.dp)
        ) {
            viewModel.currencyList.forEach { currency ->
                val flagIcon = getResourceIdForCurrency(currency)
                DropdownMenuItem(
                    onClick = {
                        onCurrencyFlagSelected(flagIcon)
                        onCurrencySelected(currency)
                        onExpand(false)
                    },
                    interactionSource = remember { MutableInteractionSource() },
                    text = { Text(text = currency) },
                    trailingIcon = {
                        if (flagIcon != -1) {
                            Image(
                                painter = painterResource(id = flagIcon),
                                contentDescription = "Flag for $currency"
                            )
                        } else Image(
                            imageVector = Icons.Filled.Info,
                            contentDescription = "Not available",
                            colorFilter = ColorFilter.tint(Color.Red)
                        )
                    }

                )
            }
        }
    }
}

@SuppressLint("DiscouragedApi")
@Composable
fun getResourceIdForCurrency(currencyCode: String): Int {
    val context = LocalContext.current
    // Construct the resource name based on the currency code
    val resourceName = "flag_${currencyCode.lowercase()}"
    // Use resources.getIdentifier to get the resource ID dynamically
    val resourceId = context.resources.getIdentifier(resourceName, "drawable", context.packageName)
    // Return the resource ID if found, otherwise return a default flag resource
    return if (resourceId != 0) {
        resourceId
    } else {
        -1 // Default flag resource if specific flag not found
    }
}

