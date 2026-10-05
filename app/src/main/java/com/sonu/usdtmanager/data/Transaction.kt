package com.sonu.usdtmanager.data

import kotlinx.serialization.Serializable

@Serializable
data class Transaction(
    val id: Long? = null,
    val type: String,            // "BUY" or "SELL"
    val customer: String = "",
    val amount_inr: Double,     // total INR paid/received
    val rate: Double,            // price per USDT (e.g. 103 buy / 106 sell)
    val usdt_qty: Double,        // amount_inr / rate
    val note: String = "",
    val local_date: String = ""  // "2026-10-05" for day-wise reports
)
