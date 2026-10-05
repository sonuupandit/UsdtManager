package com.sonu.usdtmanager.data

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.serialization.json.Json

object Supabase {
    val client = createSupabaseClient(
        supabaseUrl = SupabaseConfig.SUPABASE_URL,
        supabaseKey = SupabaseConfig.SUPABASE_ANON_KEY
    ) {
        install(Postgrest)
        json = Json {
            ignoreUnknownKeys = true
            encodeDefaults = false
        }
    }
}
