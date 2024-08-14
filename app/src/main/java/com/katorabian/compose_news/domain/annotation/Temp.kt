package com.katorabian.compose_news.domain.annotation

import com.katorabian.compose_news.common.constant.EMPTY_STRING
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.CONSTRUCTOR
import kotlin.annotation.AnnotationTarget.EXPRESSION
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.annotation.AnnotationTarget.LOCAL_VARIABLE
import kotlin.annotation.AnnotationTarget.PROPERTY
import kotlin.annotation.AnnotationTarget.PROPERTY_GETTER
import kotlin.annotation.AnnotationTarget.PROPERTY_SETTER
import kotlin.annotation.AnnotationTarget.TYPEALIAS

@Retention(AnnotationRetention.SOURCE)
@Target(CLASS, FUNCTION, PROPERTY, CONSTRUCTOR, PROPERTY_SETTER, PROPERTY_GETTER, TYPEALIAS, LOCAL_VARIABLE, EXPRESSION)
@MustBeDocumented
public annotation class Temp(val message: String = EMPTY_STRING)