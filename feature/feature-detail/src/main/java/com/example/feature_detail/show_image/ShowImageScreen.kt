package com.example.feature_detail.show_image

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.ui.component.AppAsyncImage

@Composable
fun ShowImageRoute(imageUrl: String?) {
    AppAsyncImage(
        imageUrl = imageUrl,
        modifier = Modifier
            .fillMaxSize()
            .clip(MaterialTheme.shapes.medium),
        backgroundColor = MaterialTheme.colors.background
    )
}