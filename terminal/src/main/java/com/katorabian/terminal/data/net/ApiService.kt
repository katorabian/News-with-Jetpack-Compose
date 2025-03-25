package com.katorabian.terminal.data.net

import com.katorabian.terminal.data.dto.BarsResponse
import retrofit2.http.GET

interface ApiService {

    @GET("./v2/aggs/ticker/AAPL/range/1/hour/2023-01-09/2025-02-10?adjusted=true&sort=desc&limit=50000&apiKey=kNTpR0PcNe8XWT9t5TkK88I0LZHoYtAN")
    suspend fun loadBars(): BarsResponse
}