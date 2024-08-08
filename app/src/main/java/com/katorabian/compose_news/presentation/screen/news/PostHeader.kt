package com.katorabian.compose_news.presentation.screen.news

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.katorabian.compose_news.domain.model.FeedPostItem

@Composable
fun PostHeader(
    feedPostItem: FeedPostItem
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.background(MaterialTheme.colorScheme.primary)
    ) {
        AsyncImage(
            model = feedPostItem.communityImageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.weight(1F),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = feedPostItem.communityName,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = feedPostItem.publicationDate,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }

        Icon(
            imageVector = Icons.Rounded.MoreVert,
            contentDescription = "more",
            tint = MaterialTheme.colorScheme.onSecondary
        )
    }
}