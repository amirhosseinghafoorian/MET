package com.example.data.model

import androidx.annotation.Keep

@Keep
data class Measurement(
    val elementDescription: Any?,
    val elementMeasurements: ElementMeasurements?,
    val elementName: String?
)