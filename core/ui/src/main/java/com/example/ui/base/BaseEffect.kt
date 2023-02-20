package com.example.ui.base

import android.os.Bundle
import com.example.ui.SnackBar

sealed interface BaseEffect {
    data class ShowSnackBar(val snackBar: SnackBar) : BaseEffect
    data class Navigate(val params: Bundle? = null) : BaseEffect
}