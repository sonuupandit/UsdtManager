package com.sonu.usdtmanager.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LedgerScreen(vm: MainViewModel) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Ledger", fontSize = 26.sp, fontWeight = FontWeight.Bold)
            IconButton(onClick = { vm.refresh() }) {
                Icon(Icons.Default.Refresh, contentDescription = "refresh")
            }
        }
        if (vm.loading) LinearProgressIndicator(Modifier.fillMaxWidth())
        vm.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(vm.txns, key = { it.id ?: it.hashCode() }) { t ->
                val isSell = t.type == "SELL"
                Card(shape = RoundedCornerShape(12.dp)) {
                    Row(
                        Modifier.fillMaxWidth().padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    color = if (isSell) MaterialTheme.colorScheme.primary
                                            else MaterialTheme.colorScheme.tertiary,
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        t.type,
                                        Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(Modifier.width(8.dp))
                                Text(t.local_date, fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
                            }
                            Spacer(Modifier.height(4.dp))
                            Text(
                                formatInr(t.amount_inr) + "  •  " + formatQty(t.usdt_qty),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text("Rate ₹${t.rate}" +
                                (if (t.customer.isNotBlank()) " • ${t.customer}" else "") +
                                (if (t.note.isNotBlank()) " • ${t.note}" else ""),
                                fontSize = 12.sp)
                        }
                        IconButton(onClick = { t.id?.let { vm.delete(it) } }) {
                            Icon(Icons.Default.Delete, contentDescription = "delete",
                                tint = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }
    }
}
