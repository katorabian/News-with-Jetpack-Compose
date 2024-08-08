package com.katorabian.compose_news.domain.annotation

import com.katorabian.compose_news.domain.constant.EMPTY_STRING
import kotlin.annotation.AnnotationTarget.EXPRESSION
import kotlin.annotation.AnnotationTarget.FUNCTION

@Retention(AnnotationRetention.SOURCE)
@Target(FUNCTION, EXPRESSION)
@MustBeDocumented
public annotation class DateTimeFormatting