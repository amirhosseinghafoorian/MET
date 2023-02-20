package com.example.feature_detail.navigation

import com.example.common.AppConstants.keyId

const val keyImage = "image"
const val detailNavigationRoute = "navigation_detail?$keyId={id}"

fun generateDetailNavigationRoute(id : Int) : String {
    return "navigation_detail?$keyId=$id"
}

sealed class DetailScreens(val route: String) {
    object DetailScreenRoute : DetailScreens("screen_detail")
    object ShowImageScreenRoute : DetailScreens("screen_show_Image?$keyImage={image}") {
        fun generateRoute(imageUrl : String) : String {
            return "screen_show_Image?$keyImage=$imageUrl"
        }
    }
}