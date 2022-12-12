package ru.geekbrains.android2_2.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(
    val city: String,
    val lat: Double = 0.0,
    val lon: Double = 0.0,
) : Parcelable
