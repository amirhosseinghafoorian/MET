package com.example.ui.text_field_util

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.common.firstONull

abstract class TextFieldState {

    var text by mutableStateOf("")
    var errorMessage by mutableStateOf<String?>(null)

    private val limiters = mutableListOf<TextFieldStateLimiter>()
    private val validators = mutableListOf<TextFieldStateValidator>()

    open fun validate(): Boolean {
        errorMessage = validators.firstONull { validator ->
            !validator.validate(text)
        }?.errorMessage

        return isValid()
    }

    fun isError() = errorMessage != null
    fun isValid() = errorMessage == null

    fun onChanged(newText: String) {
        if (
            newText.isEmpty()
            || limiters.all { limiter -> limiter.isValid(newText) }
        ) {
            text = newText
            errorMessage = null
        }
    }

    fun addLimiters(vararg limiters: TextFieldStateLimiter) {
        limiters.forEach { limiterItem ->
            this.limiters.add(limiterItem)
        }
    }

    fun addValidators(vararg validator: TextFieldStateValidator) {
        validator.forEach { validatorItem ->
            validators.add(validatorItem)
        }
    }

}