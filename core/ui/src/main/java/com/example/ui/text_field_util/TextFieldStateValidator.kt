package com.solidict.ui.text_field_util.condition_validator

class TextFieldStateValidator(
    val errorMassage: String,
    private val condition: (String) -> Boolean
) {

    fun validate(text: String): Boolean {
        return condition(text)
    }

}