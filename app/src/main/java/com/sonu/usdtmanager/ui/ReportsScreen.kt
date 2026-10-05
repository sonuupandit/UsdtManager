package com.sonu.usdtmanager.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate

@Composable
fun ReportsScreen(vm: MainViewModel) {
    var from by remember { mutableStateOf<LocalDate?>(null) }
    var to by remember { mutableStateOf<LocalDate?>(null) }
    var label by remember { mutableStateOf("All Time") }

    val today = LocalDate.now()
    val s = vm.summary(from, to)

    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Reports", fontSize = 26.sp, fontWeight = FontWeight.Bold)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(selected = label == "Today",
                onClick = { from = today; to = today; label = "Today" }, label = { Text("Aaj") })
            FilterChip(selected = label == "Week",
                onClick = { from = today.minusDays(6); to = today; label = "Week" }, label = { Text("7 din") })
            FilterChip(selected = label == "Month",
                onClick = { from = today.withDayOfMonth(1); to = today; label = "Month" }, label = { Text("Mahina") })
            FilterChip(selected = label == "All Time",
                onClick = { from = null; to = null; label = "All Time" }, label = { Text("Sab") })
        }

        Text(label, fontSize = 14.sp, color = MaterialTheme.colorScheme.outline)

        Card(shape = RoundedCornerShape(16.dp)) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Sell", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("${s.sellCount} transactions"); Text(formatInr(s.sellAmount), fontWeight = FontWeight.SemiBold)
                }
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("USDT sold"); Text(formatQty(s.sellQty))
                }
            }
        }

        Card(shape = RoundedCornerShape(16.dp)) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Buy", fontWeight = FontWeight.SemiBold)
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("${s.buyCount} transactions"); Text(formatInr(s.buyAmount), fontWeight = FontWeight.SemiBold)
                }
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("USDT bought"); Text(formatQty(s.buyQty))
                }
            }
        }

        Card(shape = RoundedCornerShape(16.dp)) {
            Column(Modifier.padding(16.dp)) {
                Text("Profit (approx)", fontSize = 13.sp)
                Text(formatInr(s.profit), fontSize = 30.sp, fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary)
                Text("sell revenue - (sold qty × average buy rate)", fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.outline)
            }
        }
    }
}
