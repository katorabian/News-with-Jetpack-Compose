package com.katorabian.compose_news.ui.theme

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.katorabian.compose_news.R
import com.katorabian.compose_news.domain.StatisticItem
import com.katorabian.compose_news.domain.StatisticType

@Preview
@Composable
fun PostStatistics(
    statistics: List<StatisticItem> = emptyList()
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Row(modifier = Modifier.weight(1F)) {
            val viewsItem = statistics.getItemByType(StatisticType.VIEWS)
            IconWithText(
                R.drawable.ic_views_count,
                viewsItem.count.toString()
            )
        }
        Row(
            modifier = Modifier.weight(1F),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val sharesItem = statistics.getItemByType(StatisticType.SHARES)
            IconWithText(
                R.drawable.ic_share,
                sharesItem.count.toString()
            )
            val commentsItem = statistics.getItemByType(StatisticType.COMMENTS)
            IconWithText(
                R.drawable.ic_comment,
                commentsItem.count.toString()
            )
            val likesItem = statistics.getItemByType(StatisticType.LIKES)
            IconWithText(
                R.drawable.ic_like,
                likesItem.count.toString()
            )
        }
    }
}

private fun List<StatisticItem>.getItemByType(type: StatisticType): StatisticItem {
    return this.find { it.type == type} ?: StatisticItem(StatisticType.VIEWS)
        ?: throw IllegalStateException("There is no type ${type.name} in collection")
}

@Composable
fun IconWithText(@DrawableRes iconResId: Int, value: String) {
    Row {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondary
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = value,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}