package com.example.androidpolygon.model

import com.google.gson.annotations.SerializedName

data class Geometry(
    @SerializedName("geometry")
    val geometry: Coordinates
)