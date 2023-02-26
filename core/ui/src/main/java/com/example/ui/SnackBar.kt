package com.example.ui

import androidx.annotation.StringRes
import com.example.ui.SnackBar.StringSnackBar

/**
 * A sealed class to make dealing with [String] and [StringRes] snackBar messages easier.
 */
sealed interface SnackBar {
    data class StringSnackBar(val message: String) : SnackBar
    data class StringResourceSnackBar(@StringRes val id: Int) : SnackBar
}

fun String.toSnackBar(): SnackBar {
    return StringSnackBar(this)
}

fun Int.toSnackBar(): SnackBar {
    return SnackBar.StringResourceSnackBar(this)
}