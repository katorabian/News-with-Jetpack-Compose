package com.katorabian.compose_news.presentation.screen.news

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.katorabian.compose_news.R
import com.katorabian.compose_news.domain.model.StatisticItem
import com.katorabian.compose_news.domain.model.StatisticType
import com.katorabian.compose_news.presentation.theme.DarkRed

@Composable
fun PostStatistics(
    statistics: List<StatisticItem>,
    onCommentClickListener: (StatisticItem) -> Unit,
    onLikeClickListener: (StatisticItem) -> Unit,
    isFavourite: Boolean
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Row(modifier = Modifier.weight(1F)) {
            val viewsItem = statistics.getItemByType(StatisticType.VIEWS)
            IconWithText(
                iconResId = R.drawable.ic_views_count,
                value = viewsItem.getCountFormated()
            )
        }
        Row(
            modifier = Modifier.weight(1F),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val sharesItem = statistics.getItemByType(StatisticType.SHARES)
            IconWithText(
                iconResId = R.drawable.ic_share,
                value = sharesItem.getCountFormated()
            )
            val commentsItem = statistics.getItemByType(StatisticType.COMMENTS)
            IconWithText(
                iconResId = R.drawable.ic_comment,
                value = commentsItem.getCountFormated(),
                onItemClickListener = { onCommentClickListener(commentsItem) }
            )
            val likesItem = statistics.getItemByType(StatisticType.LIKES)
            IconWithText(
                iconResId = if (isFavourite) R.drawable.ic_like_set else R.drawable.ic_like,
                value = likesItem.getCountFormated(),
                iconTint = if (isFavourite) DarkRed else MaterialTheme.colorScheme.onSecondary,
                onItemClickListener = { onLikeClickListener(likesItem) }
            )
        }
    }
}

private fun List<StatisticItem>.getItemByType(type: StatisticType): StatisticItem {
    return this.find { it.type == type} ?: StatisticItem(StatisticType.VIEWS)
        ?: throw IllegalStateException("There is no type ${type.name} in collection")
}

@Composable
fun IconWithText(
    @DrawableRes iconResId: Int,
    value: String,
    iconTint: Color = MaterialTheme.colorScheme.onSecondary,
    onItemClickListener: (() -> Unit)? = null
) {
    val modifier = if (onItemClickListener == null) {
        Modifier
    } else {
        Modifier.clickable {
            onItemClickListener()
        }
    }

    Row(modifier = modifier) {
        Icon(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(20.dp),
            painter = painterResource(id = iconResId),
            contentDescription = null,
            tint = iconTint
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = value,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Preview
@Composable
fun IconWithTextPreview() {
    IconWithText(iconResId = R.drawable.ic_views_count, value = "999") {}
}