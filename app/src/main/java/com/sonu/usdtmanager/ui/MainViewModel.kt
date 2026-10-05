package com.sonu.usdtmanager.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonu.usdtmanager.data.Transaction
import com.sonu.usdtmanager.data.TransactionRepo
import kotlinx.coroutines.launch
import java.time.LocalDate

class MainViewModel : ViewModel() {

    var txns by mutableStateOf(listOf<Transaction>())
        private set
    var loading by mutableStateOf(false)
        private set
    var error by mutableStateOf<String?>(null)
        private set

    init { refresh() }

    fun refresh() {
        viewModelScope.launch {
            loading = true
            error = null
            try {
                txns = TransactionRepo.getAll().sortedByDescending { it.local_date + it.id.toString().padStart(10, '0') }
            } catch (e: Exception) {
                error = e.message ?: "Network error"
            } finally {
                loading = false
            }
        }
    }

    fun add(type: String, customer: String, amountInr: Double, rate: Double, note: String, onDone: (Boolean) -> Unit) {
        if (amountInr <= 0 || rate <= 0) { onDone(false); return }
        viewModelScope.launch {
            loading = true
            try {
                TransactionRepo.add(
                    Transaction(
                        type = type,
                        customer = customer,
                        amount_inr = amountInr,
                        rate = rate,
                        usdt_qty = amountInr / rate,
                        note = note,
                        local_date = LocalDate.now().toString()
                    )
                )
                refresh()
                onDone(true)
            } catch (e: Exception) {
                error = e.message
                loading = false
                onDone(false)
            }
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch {
            try { TransactionRepo.delete(id) } catch (_: Exception) {}
            refresh()
        }
    }

    // ---- Summary helpers ----

    private fun inRange(from: LocalDate?, to: LocalDate?): List<Transaction> =
        txns.filter { t ->
            val d = t.local_date
            (from == null || d >= from.toString()) && (to == null || d <= to.toString())
        }

    fun summary(from: LocalDate?, to: LocalDate?): Summary {
        val list = inRange(from, to)
        val sells = list.filter { it.type == "SELL" }
        val buys = list.filter { it.type == "BUY" }
        val sellAmount = sells.sumOf { it.amount_inr }
        val sellQty = sells.sumOf { it.usdt_qty }
        val buyAmount = buys.sumOf { it.amount_inr }
        val buyQty = buys.sumOf { it.usdt_qty }
        // Profit: jitna USDT becha uska (sell revenue) minus wahi qty average buy rate par
        val avgBuy = if (buyQty > 0) buyAmount / buyQty else 0.0
        val profit = if (avgBuy > 0) sellAmount - sellQty * avgBuy else 0.0
        return Summary(sellAmount, sellQty, buyAmount, buyQty, sells.size, buys.size, profit)
    }

    // All-time stock = total bought - total sold
    fun stock(): Double {
        val s = summary(null, null)
        return s.buyQty - s.sellQty
    }

    data class Summary(
        val sellAmount: Double, val sellQty: Double,
        val buyAmount: Double, val buyQty: Double,
        val sellCount: Int, val buyCount: Int,
        val profit: Double
    )
}
