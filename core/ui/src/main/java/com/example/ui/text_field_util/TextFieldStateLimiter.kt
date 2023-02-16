package com.example.ui.text_field_util

class TextFieldStateLimiter(
    private val condition: (String) -> Boolean
) {

    fun isValid(text: String): Boolean {
        return condition(text)
    }

}