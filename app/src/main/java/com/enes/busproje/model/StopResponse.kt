package com.enes.busproje.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StopResponse(
    @SerializedName("last_updated")
    val lastUpdated: Long,
    @SerializedName("stops")
    @Expose
    val stops: ArrayList<Stop>
)
data class Stop(
    @SerializedName("atco_code")
    val atcoCode: String?,
    val atco_latitude: Double?,
    val atco_longitude: Double?,
    val destinations: List<String?>?,
    val direction: String?,
    val identifier: String?,
    val latitude: Double?,
    val locality: Any?,
    val longitude: Double?,
    val name: String?,
    val orientation: Int?,
    val service_type: String?,
    val services: List<String?>?,
    val stop_id: Int?
)
