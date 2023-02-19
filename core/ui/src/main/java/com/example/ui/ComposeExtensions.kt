package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.valentinilk.shimmer.shimmer

@Composable
fun HeightSpacer(value: Dp) {
    Spacer(modifier = Modifier.height(value))
}

@Composable
fun WidthSpacer(value: Dp) {
    Spacer(modifier = Modifier.width(value))
}

fun Modifier.clickableIf(enabled: Boolean, onClick: () -> Unit): Modifier {
    return if (enabled) {
        this.clickable { onClick() }
    } else this
}

@Composable
fun Modifier.dynamicShimmer(
    shouldShow: Boolean = true,
    background : Color = MaterialTheme.colors.surface
): Modifier {
    return if (shouldShow)
        background(background)
            .shimmer()
            .background(MaterialTheme.colors.secondary)
    else background(background)
}