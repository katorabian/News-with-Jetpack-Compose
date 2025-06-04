package com.katorabian.terminal.data.net

import com.katorabian.terminal.data.dto.BarsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/v2/aggs/ticker/AAPL/range/{timeframe}/{from}/{to}?adjusted=true&sort=desc&limit=50000&apiKey=kNTpR0PcNe8XWT9t5TkK88I0LZHoYtAN")
    suspend fun loadBars(
        @Path("from") from: String,
        @Path("to") to: String,
        @Path("timeframe") timeframe: String
    ): BarsResponse
}