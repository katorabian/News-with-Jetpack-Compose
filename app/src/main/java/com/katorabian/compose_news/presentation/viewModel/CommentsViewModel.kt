package com.katorabian.compose_news.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.katorabian.compose_news.domain.annotation.Temp
import com.katorabian.compose_news.domain.constant.ZERO_INT
import com.katorabian.compose_news.domain.model.FeedPostItem
import com.katorabian.compose_news.domain.model.PostCommentItem
import com.katorabian.compose_news.presentation.model.CommentsScreenState

class CommentsViewModel(
    feedPost: FeedPostItem
): ViewModel() {

    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)
    val screenState: LiveData<CommentsScreenState> = _screenState

    init {
        @Temp
        loadComments(feedPost)
    }

    private fun loadComments(feedPost: FeedPostItem) {
        val comments = List(10) { PostCommentItem(it) }
        _screenState.value = CommentsScreenState.Comments(
            feedPost = feedPost,
            comments = comments
        )
    }
}