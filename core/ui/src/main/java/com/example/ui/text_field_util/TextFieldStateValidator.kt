package com.example.ui.text_field_util

class TextFieldStateValidator(
    val errorMessage: String,
    private val condition: (String) -> Boolean
) {

    fun validate(text: String): Boolean {
        return condition(text)
    }

}