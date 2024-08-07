package com.katorabian.compose_news.presentation.screen.comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.katorabian.compose_news.domain.model.FeedPostItem

class CommentsViewModelFactory(
    private val feedPost: FeedPostItem
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommentsViewModel(feedPost) as T
    }
}