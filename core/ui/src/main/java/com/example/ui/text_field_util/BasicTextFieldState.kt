package com.example.ui.text_field_util

import com.example.common.AppConstants.TEXT_FIELD_EMPTY_ERROR_MASSAGE
import com.solidict.ui.text_field_util.condition_validator.TextFieldStateValidator

class BasicTextFieldState : TextFieldState() {

    init {
        addValidators(
            TextFieldStateValidator(
                errorMassage = TEXT_FIELD_EMPTY_ERROR_MASSAGE,
                condition = { text: String -> text.isNotBlank() }
            )
        )
    }
}