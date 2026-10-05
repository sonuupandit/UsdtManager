package com.sonu.usdtmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sonu.usdtmanager.ui.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UsdtManagerTheme {
                AppRoot()
            }
        }
    }
}

@Composable
fun AppRoot(vm: MainViewModel = viewModel()) {
    var tab by remember { mutableIntStateOf(0) }
    val titles = listOf("Dashboard", "Add", "Ledger", "Reports")
    val icons = listOf(Icons.Default.Home, Icons.Default.AddCircle, Icons.Default.List, Icons.Default.ShoppingCart)

    Scaffold(
        bottomBar = {
            NavigationBar {
                titles.forEachIndexed { i, title ->
                    NavigationBarItem(
                        selected = tab == i,
                        onClick = { tab = i },
                        icon = { Icon(icons[i], contentDescription = title) },
                        label = { Text(title) }
                    )
                }
            }
        }
    ) { padding ->
        Box(Modifier.padding(padding)) {
            when (tab) {
                0 -> DashboardScreen(vm)
                1 -> AddTransactionScreen(vm) { }
                2 -> LedgerScreen(vm)
                3 -> ReportsScreen(vm)
            }
        }
    }
}
