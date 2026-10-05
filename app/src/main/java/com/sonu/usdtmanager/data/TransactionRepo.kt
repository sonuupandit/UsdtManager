package com.sonu.usdtmanager.data

import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.filter

object TransactionRepo {
    private val table get() = Supabase.client.postgrest["transactions"]

    suspend fun getAll(): List<Transaction> =
        table.select().decodeList<Transaction>()

    suspend fun add(t: Transaction) {
        table.insert(t)
    }

    suspend fun delete(id: Long) {
        table.delete {
            filter { Transaction::id eq id }
        }
    }
}
