package com.katorabian.compose_news.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.katorabian.compose_news.domain.FeedPostItem
import com.katorabian.compose_news.domain.StatisticItem
import com.katorabian.compose_news.presentation.theme.ComposeNewsTheme

@Composable
fun PostCard(
    modifier: Modifier = Modifier,
    feedPost: FeedPostItem,
    onViewsClickListener: (StatisticItem) -> Unit,
    onShareClickListener: (StatisticItem) -> Unit,
    onCommentClickListener: (StatisticItem) -> Unit,
    onLikeClickListener: (StatisticItem) -> Unit
) {
    ComposeNewsTheme {
        Card(
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground),
            modifier = modifier.fillMaxWidth(),
            colors = CardColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = MaterialTheme.colorScheme.primary,
                disabledContentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {

            Column(modifier = Modifier.padding(8.dp)) {
                PostHeader(feedPost)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = feedPost.contentText)
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = painterResource(id = feedPost.contentImageResId),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1F),
                    contentScale = ContentScale.FillWidth
                )
                Spacer(modifier = Modifier.height(8.dp))
                PostStatistics(
                    statistics = feedPost.statistics,
                    onViewsClickListener = onViewsClickListener,
                    onShareClickListener = onShareClickListener,
                    onCommentClickListener = onCommentClickListener,
                    onLikeClickListener = onLikeClickListener
                )
            }
        }
    }
}

@Preview
@Composable
fun PostCardPreview() = PostCard(
    feedPost = FeedPostItem(),
    onViewsClickListener = {},
    onShareClickListener = {},
    onCommentClickListener = {},
    onLikeClickListener = {}
)