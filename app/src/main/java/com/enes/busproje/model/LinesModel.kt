package com.enes.busproje.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LinesModel(
    @SerializedName("last_updated")
    val lastUpdated: Long?,
    val services: List<Service>?,
) : Parcelable
@Parcelize
data class Service(
    val name: String?,
    val description: String?,
    @SerializedName("service_type")
    val serviceType: String?,
    val routes: List<Route>?,
): Parcelable
@Parcelize
data class Route(
    val destination: String?,
    val points: List<Point>?,
    val stops: List<String>?,
): Parcelable
@Parcelize
data class Point(
    @SerializedName("stop_id")
    val stopId: String?,
    val latitude: Double?,
    val longitude: Double?,
): Parcelable

