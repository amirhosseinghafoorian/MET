package com.example.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import com.example.designsystem.theme.spacing
import com.example.ui.Icon
import com.example.ui.Icon.DrawableResourceIcon
import com.example.ui.Icon.ImageVectorIcon
import com.example.ui.WidthSpacer
import com.example.ui.clickableIf

@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    isTopBarVisible: Boolean = true,
    canNavigateBack: Boolean = true,
    backgroundColor: Color = MaterialTheme.colors.surface,
    @StringRes title: Int? = null,
    actionIcon: Icon? = null,
    onActionIconClick: (() -> Unit)? = null,
    onNavigateUp: (() -> Unit)? = null
) {
    if (isTopBarVisible) {
        TopAppBar(
            modifier = modifier,
            backgroundColor = backgroundColor,
            content = {
                WidthSpacer(MaterialTheme.spacing.small)

                Box(
                    modifier = Modifier
                        .size(MaterialTheme.spacing.extraLarge)
                        .clip(MaterialTheme.shapes.medium)
                        .clickableIf(canNavigateBack) {
                            onNavigateUp?.invoke()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (canNavigateBack) Icon(
                        imageVector = if (LocalLayoutDirection.current == LayoutDirection.Ltr)
                            Icons.Default.ArrowBack
                        else Icons.Default.ArrowForward,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onSurface
                    )
                }

                WidthSpacer(MaterialTheme.spacing.medium)

                Text(
                    modifier = Modifier.weight(1f),
                    text = title?.let { stringResource(it) } ?: "",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W700,
                    color = MaterialTheme.colors.onSurface
                )

                WidthSpacer(MaterialTheme.spacing.medium)

                Box(
                    modifier = Modifier
                        .size(MaterialTheme.spacing.extraLarge)
                        .clip(MaterialTheme.shapes.medium)
                        .clickableIf(actionIcon != null) {
                            onActionIconClick?.invoke()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    actionIcon?.let { icon ->
                        when (icon) {
                            is DrawableResourceIcon -> Icon(
                                painter = painterResource(icon.id),
                                contentDescription = null
                            )
                            is ImageVectorIcon -> Icon(
                                imageVector = icon.imageVector,
                                contentDescription = null
                            )
                        }

                    }
                }

                WidthSpacer(MaterialTheme.spacing.small)
            }
        )
    }
}