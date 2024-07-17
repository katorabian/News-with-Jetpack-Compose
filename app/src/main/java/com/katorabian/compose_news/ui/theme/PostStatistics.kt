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

@Preview
@Composable
fun PostStatistics() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Row(modifier = Modifier.weight(1F)) {
            IconWithText(R.drawable.ic_views_count, "916")
        }
        Row(
            modifier = Modifier.weight(1F),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconWithText(R.drawable.ic_share, "7")
            IconWithText(R.drawable.ic_comment, "8")
            IconWithText(R.drawable.ic_like, "23")
        }
    }
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