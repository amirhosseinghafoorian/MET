package com.example.designsystem.theme

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.compositionLocalOf
import com.example.designsystem.theme.ScreenType.*

/**
 * [FoP] : Foldable on Portrait
 *
 * [FoL] : Foldable on Landscape, also Tablet on Landscape
 *
 * [ToP] : Tablet on Portrait
 */
sealed interface ScreenType {
    object Phone : ScreenType
    object FoP : ScreenType
    object FoL : ScreenType
    object ToP : ScreenType
    object Desktop : ScreenType
}

val LocalScreenType = compositionLocalOf<ScreenType> { Phone }

fun determineScreenType(
    windowSizeClass: WindowSizeClass?
): ScreenType {

    windowSizeClass?.let { screen ->
        return when {
            screen.widthSizeClass == WindowWidthSizeClass.Compact &&
                    screen.heightSizeClass == WindowHeightSizeClass.Medium -> Phone
            screen.widthSizeClass == WindowWidthSizeClass.Medium &&
                    screen.heightSizeClass == WindowHeightSizeClass.Medium -> FoP
            screen.widthSizeClass == WindowWidthSizeClass.Expanded &&
                    screen.heightSizeClass == WindowHeightSizeClass.Medium -> FoL
            screen.widthSizeClass == WindowWidthSizeClass.Medium &&
                    screen.heightSizeClass == WindowHeightSizeClass.Expanded -> ToP
            screen.widthSizeClass == WindowWidthSizeClass.Expanded &&
                    screen.heightSizeClass == WindowHeightSizeClass.Expanded -> Desktop
            else -> Phone
        }
    } ?: run {
        return Phone
    }
}