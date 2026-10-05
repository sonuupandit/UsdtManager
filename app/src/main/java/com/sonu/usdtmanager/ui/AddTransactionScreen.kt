package com.sonu.usdtmanager.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AddTransactionScreen(vm: MainViewModel, onSaved: () -> Unit) {
    var type by remember { mutableStateOf("SELL") }
    var customer by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var rate by remember { mutableStateOf("106") }
    var note by remember { mutableStateOf("") }
    var msg by remember { mutableStateOf<String?>(null) }

    val amountD = amount.toDoubleOrNull() ?: 0.0
    val rateD = rate.toDoubleOrNull() ?: 0.0
    val qty = if (rateD > 0) amountD / rateD else 0.0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Naya Transaction", fontSize = 26.sp, fontWeight = FontWeight.Bold)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(
                selected = type == "SELL",
                onClick = { type = "SELL"; rate = "106" },
                label = { Text("SELL (₹106)") }
            )
            FilterChip(
                selected = type == "BUY",
                onClick = { type = "BUY"; rate = "103" },
                label = { Text("BUY (₹103)") }
            )
        }

        OutlinedTextField(
            value = customer,
            onValueChange = { customer = it },
            label = { Text(if (type == "SELL") "Customer ka naam / UTR" else "Supplier ka naam") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount (₹ INR)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = rate,
            onValueChange = { rate = it },
            label = { Text("Rate (₹ per USDT)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = note,
            onValueChange = { note = it },
            label = { Text("Note (optional)") },
            modifier = Modifier.fillMaxWidth()
        )

        Card(shape = RoundedCornerShape(12.dp)) {
            Text(
                "USDT qty: ${formatQty(qty)}",
                Modifier.padding(14.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Button(
            onClick = {
                vm.add(type, customer.trim(), amountD, rateD, note.trim()) { ok ->
                    msg = if (ok) {
                        customer = ""; amount = ""; note = ""
                        "Save ho gaya ✓"
                    } else "Save nahi hua - input check karo"
                }
            },
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) { Text("SAVE", fontSize = 16.sp, fontWeight = FontWeight.Bold) }

        msg?.let { Text(it, color = MaterialTheme.colorScheme.primary) }
    }
}
