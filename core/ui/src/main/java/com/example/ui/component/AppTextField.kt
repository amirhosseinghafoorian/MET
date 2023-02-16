package com.example.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import com.example.designsystem.theme.spacing
import com.example.ui.text_field_util.TextFieldState

@Composable
fun AppTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    textFieldModifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    maxLines: Int = Int.MAX_VALUE,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    shape: Shape = MaterialTheme.shapes.medium,
    color: Color = MaterialTheme.colors.onSurface,
    backgroundColor: Color = MaterialTheme.colors.surface,
    placeholder: String = "",
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    showError: Boolean = true,
    textStyle: TextStyle = MaterialTheme.typography.body1.copy(
        textAlign = TextAlign.Start,
    ),
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(
        textColor = color,
        disabledTextColor = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
        disabledLeadingIconColor = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
        disabledTrailingIconColor = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
        disabledPlaceholderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
        cursorColor = color,
        backgroundColor = backgroundColor,
        focusedLabelColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        errorIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent
    ),
) {
    Column(modifier = modifier) {
        if (state.isError() && showError) {
            Text(
                text = state.errorMessage ?: "",
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(bottom = MaterialTheme.spacing.extraSmall)
            )
        }

        TextField(
            value = state.text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            enabled = enabled,
            readOnly = readOnly,
            isError = state.isError() && showError,
            textStyle = textStyle,
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.secondary.copy(alpha = 0.5f)
                )
            },
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            modifier = Modifier
                .fillMaxWidth()
                .then(textFieldModifier),
            maxLines = maxLines,
            visualTransformation = visualTransformation,
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            shape = shape,
            colors = colors
        )
    }
}