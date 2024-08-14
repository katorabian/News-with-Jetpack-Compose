package com.katorabian.compose_news.presentation.screen.comments

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katorabian.compose_news.data.repository.CommentsRepository
import com.katorabian.compose_news.domain.annotation.Temp
import com.katorabian.compose_news.domain.model.FeedPostItem
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CommentsViewModel(
    application: Application,
    private val feedPost: FeedPostItem
): ViewModel() {

    private val repository = CommentsRepository(application)
    private val excHandler = CoroutineExceptionHandler { _, exception ->
        Log.e(this@CommentsViewModel::class.simpleName, Log.getStackTraceString(exception))
    }

    private val _commentsState = MutableStateFlow(CommentsScreenState())
    val commentsState = _commentsState.asStateFlow()

    init {
        loadComments()
    }

    fun loadComments() {
        viewModelScope.launch(excHandler) {
            @Temp("Т.к. не добавлял отметку о последнем полученном элементе, нет смысла оставлять возможность дозагрузки")
            if (_commentsState.value.comments.isNotEmpty()) return@launch

            _commentsState.value = _commentsState.value.copy(
                isLoading = true
            )
            @Temp delay(2000)
            val newList = repository.getComments(feedPost)
            _commentsState.value = _commentsState.value.copy(
                isLoading = false,
                comments = (_commentsState.value.comments + newList).distinctBy { it.id }
            )
        }
    }
}