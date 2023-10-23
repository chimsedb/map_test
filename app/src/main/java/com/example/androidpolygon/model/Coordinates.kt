package com.example.androidpolygon.model

import com.google.gson.annotations.SerializedName

data class Coordinates (
    @SerializedName("coordinates")
    val points: List<List<List<Float>>>
)
