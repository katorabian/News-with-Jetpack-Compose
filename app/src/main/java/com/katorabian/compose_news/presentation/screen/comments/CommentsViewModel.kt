package com.katorabian.compose_news.presentation.screen.comments

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katorabian.compose_news.common.constant.FLOW_RETRY_TIMEOUT_MILLIS
import com.katorabian.compose_news.data.repository.CommentsRepositoryImpl
import com.katorabian.compose_news.domain.annotation.Temp
import com.katorabian.compose_news.domain.model.FeedPostItem
import com.katorabian.compose_news.domain.model.PostCommentItem
import com.katorabian.compose_news.domain.usecase.GetCommentsUseCase
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.launch

class CommentsViewModel(
    application: Application,
    private val feedPost: FeedPostItem
): ViewModel() {

    private val repository = CommentsRepositoryImpl(application)
    private val getCommentsUC = GetCommentsUseCase(repository)

    private var commentsSaved: List<PostCommentItem> = emptyList()
    private val commentsUpdateTrigger = MutableSharedFlow<Unit>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )
    val commentsState: Flow<CommentsScreenState> = flow {
        emit(CommentsScreenState.FirstLoad)
        @Temp delay(500)
        commentsUpdateTrigger.collect {
            if (commentsSaved.isNotEmpty()) return@collect
            emit(
                CommentsScreenState.CommentsList(
                    comments = commentsSaved,
                    isLoadMore = true
                )
            )
            val resultList = (commentsSaved + loadComments()).distinctBy { it.id }
            commentsSaved = resultList
            emit(
                CommentsScreenState.CommentsList(
                    comments = resultList,
                    isLoadMore = false
                )
            )
        }
    }.retry {
        delay(FLOW_RETRY_TIMEOUT_MILLIS)
        true
    }

    private suspend fun loadComments(): List<PostCommentItem> {
        return getCommentsUC.get(feedPost)
    }

    fun triggerLoadComments() = viewModelScope.launch {
        commentsUpdateTrigger.emit(Unit)
    }
}