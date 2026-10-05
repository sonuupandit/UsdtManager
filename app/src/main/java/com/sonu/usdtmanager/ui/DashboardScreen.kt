package com.sonu.usdtmanager.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate

@Composable
fun DashboardScreen(vm: MainViewModel) {
    val today = LocalDate.now()
    val weekAgo = today.minusDays(6)
    val monthStart = today.withDayOfMonth(1)

    val t = vm.summary(today, today)
    val w = vm.summary(weekAgo, today)
    val m = vm.summary(monthStart, today)
    val stock = vm.stock()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Dashboard", fontSize = 26.sp, fontWeight = FontWeight.Bold)
        if (vm.loading) LinearProgressIndicator(Modifier.fillMaxWidth())
        vm.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }

        Card(shape = RoundedCornerShape(16.dp)) {
            Column(Modifier.padding(16.dp)) {
                Text("Aaj ka Sell (Today)", color = MaterialTheme.colorScheme.primary, fontSize = 14.sp)
                Text(formatInr(t.sellAmount), fontSize = 34.sp, fontWeight = FontWeight.Bold)
                Text("${t.sellCount} transactions • ${formatQty(t.sellQty)}", fontSize = 13.sp)
                Spacer(Modifier.height(10.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Aaj ka Buy")
                    Text(formatInr(t.buyAmount), fontWeight = FontWeight.SemiBold)
                }
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Aaj ka Profit (approx)")
                    Text(formatInr(t.profit), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                }
            }
        }

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Card(Modifier.weight(1f), shape = RoundedCornerShape(16.dp)) {
                Column(Modifier.padding(16.dp)) {
                    Text("USDT Stock", fontSize = 12.sp)
                    Text(formatQty(stock), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text("total bought - sold", fontSize = 10.sp)
                }
            }
            Card(Modifier.weight(1f), shape = RoundedCornerShape(16.dp)) {
                Column(Modifier.padding(16.dp)) {
                    Text("Is Hafte Profit", fontSize = 12.sp)
                    Text(formatInr(w.profit), fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Text("last 7 days", fontSize = 10.sp)
                }
            }
        }

        Card(shape = RoundedCornerShape(16.dp)) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("Is Mahine (This Month)", fontWeight = FontWeight.SemiBold)
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Total Sell"); Text(formatInr(m.sellAmount), fontWeight = FontWeight.SemiBold)
                }
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Total Buy"); Text(formatInr(m.buyAmount), fontWeight = FontWeight.SemiBold)
                }
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Profit (approx)")
                    Text(formatInr(m.profit), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}
