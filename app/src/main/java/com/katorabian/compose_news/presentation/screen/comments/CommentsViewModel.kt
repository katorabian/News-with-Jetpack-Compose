package com.katorabian.compose_news.presentation.screen.comments

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katorabian.compose_news.data.repository.CommentsRepository
import com.katorabian.compose_news.domain.model.FeedPostItem
import kotlinx.coroutines.launch

class CommentsViewModel(
    application: Application,
    feedPost: FeedPostItem
): ViewModel() {

    private val repository = CommentsRepository(application)

    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)
    val screenState: LiveData<CommentsScreenState> = _screenState

    init {
        _screenState.value = CommentsScreenState.Loading
        loadComments(feedPost)
    }

    private fun loadComments(feedPost: FeedPostItem) {
        viewModelScope.launch {
            repository.getComments(feedPost)
            _screenState.value = CommentsScreenState.Comments(
                feedPost = feedPost,
                comments = repository.comments
            )
        }
    }
}