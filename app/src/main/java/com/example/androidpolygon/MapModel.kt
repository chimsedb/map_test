package com.example.androidpolygon

import com.google.gson.annotations.SerializedName

data class MapModel(
    @SerializedName("data")
    val points: List<List<Float>>
)