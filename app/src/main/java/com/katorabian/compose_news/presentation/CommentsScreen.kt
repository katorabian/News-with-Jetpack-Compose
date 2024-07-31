package com.katorabian.compose_news.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.katorabian.compose_news.R
import com.katorabian.compose_news.domain.FeedPostItem
import com.katorabian.compose_news.domain.PostCommentItem
import com.katorabian.compose_news.domain.constant.ZERO_INT
import com.katorabian.compose_news.presentation.theme.ComposeNewsTheme

@Composable
fun CommentsScreen(
    modifier: Modifier = Modifier,
    postItem: FeedPostItem,
    comments: List<PostCommentItem>
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CommentsScreenHeader(postItem = postItem)
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(comments) { commentItem ->
                SingleComment(
                    commentItem = commentItem
                )
            }
        }
    }
}

@Preview
@Composable
fun CommentsScreenPreview() = ComposeNewsTheme {
    CommentsScreen(
        postItem = FeedPostItem(ZERO_INT),
        comments = List(16) { PostCommentItem(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsScreenHeader(postItem: FeedPostItem) {
    TopAppBar(
        title = {
            Text(
                text = "Comments for FeedPost Id: ${postItem.id}",
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 8.dp)
            )
        },
        navigationIcon = {
            Spacer(modifier = Modifier.width(16.dp))
            IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Preview
@Composable
private fun CommentsScreenHeaderPreview()  = ComposeNewsTheme {
    CommentsScreenHeader(postItem = FeedPostItem(id = ZERO_INT))
}


@Composable
private fun SingleComment(
    modifier: Modifier = Modifier,
    commentItem: PostCommentItem
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.width(24.dp))
        Image(
            painter = painterResource(id = R.drawable.comment_author_avatar),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1F)) {
            Text(
                text = "Author Comment Id: ${commentItem.id}",
                fontSize = 13.sp
            )
            Text(
                text = commentItem.commentText,
                fontSize = 14.sp
            )
            Text(
                text = commentItem.publicationDate,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview
@Composable
private fun SingleCommentPreview() = ComposeNewsTheme {
    SingleComment(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        commentItem = PostCommentItem(ZERO_INT)
    )
}