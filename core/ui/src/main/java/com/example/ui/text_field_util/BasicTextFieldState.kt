package com.example.ui.text_field_util

import com.example.common.AppConstants.TEXT_FIELD_EMPTY_ERROR_MESSAGE

class BasicTextFieldState : TextFieldState() {

    init {
        addValidators(
            TextFieldStateValidator(
                errorMessage = TEXT_FIELD_EMPTY_ERROR_MESSAGE,
                condition = { text: String -> text.isNotBlank() }
            )
        )
    }
}