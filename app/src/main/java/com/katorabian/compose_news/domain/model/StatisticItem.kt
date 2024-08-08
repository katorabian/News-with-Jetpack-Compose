package com.katorabian.compose_news.domain.model

import android.os.Parcelable
import com.katorabian.compose_news.domain.constant.ZERO_INT
import kotlinx.parcelize.Parcelize
import java.util.Locale

@Parcelize
data class StatisticItem(
    val type: StatisticType,
    val count: Int = ZERO_INT
): Parcelable {

    fun getCountFormated(): String {
        val countFormated = if (count > ONE_HUNDRED_THOUSAND) {
            String.format("%sK", count / THOUSAND)
        } else if (count > THOUSAND) {
            String.format(
                Locale.getDefault(),
                "%.1fK",
                1F * count / THOUSAND
            )
        } else {
            count.toString()
        }

        return countFormated
    }

    private companion object {
        const val THOUSAND = 1_000
        const val ONE_HUNDRED_THOUSAND = 100_000
    }
}

enum class StatisticType {
    VIEWS, COMMENTS, SHARES, LIKES
}