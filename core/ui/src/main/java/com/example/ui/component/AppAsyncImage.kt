package com.example.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import coil.compose.AsyncImage
import com.example.ui.clickableIf

sealed interface ImageState {
    object Loading : ImageState
    object Success : ImageState
    object Error : ImageState
    object Empty : ImageState
}

@Composable
fun AppAsyncImage(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    backgroundColor: Color = MaterialTheme.colors.background,
    onClick: (() -> Unit)? = null
) {
    var imageState by remember {
        mutableStateOf<ImageState>(ImageState.Empty)
    }

    AppAsyncImage(
        modifier = modifier,
        imageUrl = imageUrl,
        backgroundColor = backgroundColor,
        imageState = imageState,
        onSuccess = {
            imageState = ImageState.Success
        },
        onError = {
            imageState = ImageState.Error
        },
        onLoading = {
            imageState = ImageState.Loading
        },
        onClick = onClick
    )
}

@Composable
private fun AppAsyncImage(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    backgroundColor: Color,
    imageState: ImageState,
    onSuccess: () -> Unit,
    onError: () -> Unit,
    onLoading: () -> Unit,
    onClick: (() -> Unit)? = null
) {

    imageUrl?.let { url ->
        Box(
            modifier = modifier
        ) {
            if (imageState != ImageState.Error) {
                AsyncImage(
                    model = url,
                    contentDescription = null,
                    modifier = modifier.clickableIf(
                        imageState == ImageState.Success && onClick != null
                    ) {
                        onClick?.invoke()
                    },
                    onLoading = { onLoading() },
                    onSuccess = { onSuccess() },
                    onError = { onError() }
                )

                if (imageState == ImageState.Loading) {
                    Box(
                        modifier = modifier.background(backgroundColor)
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                        )
                    }
                }
            } else {
                Box(
                    modifier = modifier.background(backgroundColor)
                )
            }
        }
    } ?: run {
        Box(
            modifier = modifier.background(backgroundColor)
        )
    }
}