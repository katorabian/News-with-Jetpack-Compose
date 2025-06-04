package com.katorabian.terminal.presentation.screen.terminal

import android.os.Build
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katorabian.terminal.data.net.ApiFactory
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class TerminalViewModel: ViewModel() {

    //TODO( Use DI )
    private val apiService = ApiFactory.apiService

    private val _state = MutableStateFlow<TerminalScreenState>(TerminalScreenState.Initial)
    val state = _state.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(
            this@TerminalViewModel::class.java.simpleName,
            "Exception caught:\n${Log.getStackTraceString(throwable)}"
        )
    }

    init {
        loadBarList()
    }

    fun loadBarList(timeFrame: TimeFrame = TimeFrame.HOUR_1) {
        _state.value = TerminalScreenState.Loading

        viewModelScope.launch(exceptionHandler) {
            val range = get2YearsRange()
            val barList = apiService.loadBars(
                from = range.first,
                to = range.second,
                timeframe = timeFrame.urlPath
            ).barList

            _state.value = TerminalScreenState.Content(
                barList = barList,
                timeFrame = timeFrame
            )
        }
    }

    private fun get2YearsRange(): Pair<String, String> {
        val today = getCurrentDate()
        val dateFormat = SimpleDateFormat(API_DATE_PATTERN, Locale.UK)
        val to = dateFormat.format(today)
        val from = dateFormat.minus2Years(today)
        return from to to
    }

    private fun DateFormat.minus2Years(initialDate: Date): String {
        val calendar = Calendar.getInstance()
        calendar.time = initialDate
        val year = calendar.get(Calendar.YEAR) - YEARS_RANGE
        calendar.set(Calendar.YEAR, year)
        return format(calendar.time)
    }

    private fun getCurrentDate() = run {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val instant = ZonedDateTime.now(ZoneOffset.UTC).toInstant()
            Date.from(instant)
        } else {
            Calendar.getInstance(TimeZone.getTimeZone("UTC")).time
        }
    }

    companion object {
        private const val API_DATE_PATTERN = "yyyy-MM-dd"//2023-01-09
        private const val YEARS_RANGE = 2
    }
}