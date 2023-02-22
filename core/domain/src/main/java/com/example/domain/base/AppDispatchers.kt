package com.example.domain.base

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.BINARY
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.annotation.AnnotationTarget.VALUE_PARAMETER

@Qualifier
@Retention(BINARY)
@Target(VALUE_PARAMETER, FUNCTION)
annotation class DefaultDispatcher

@Qualifier
@Retention(BINARY)
@Target(VALUE_PARAMETER, FUNCTION)
annotation class IoDispatcher