package com.example.ui.component

import android.content.Context
import androidx.compose.material.ScaffoldState
import com.example.ui.SnackBar

suspend fun ScaffoldState.showAppSnackBar(
    context: Context,
    snackBar: SnackBar
) {
    when (snackBar) {
        is SnackBar.StringResourceSnackBar -> {
            val message = context.getString(snackBar.id)
            snackbarHostState.showSnackbar(message)
        }
        is SnackBar.StringSnackBar -> {
            snackbarHostState.showSnackbar(snackBar.message)
        }
    }
}