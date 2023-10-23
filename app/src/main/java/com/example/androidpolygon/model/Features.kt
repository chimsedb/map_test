package com.example.androidpolygon.model

import com.google.gson.annotations.SerializedName

data class Features (
    @SerializedName("features")
    val geometryList : List<Geometry>
)