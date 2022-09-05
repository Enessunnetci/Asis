package com.enes.busproje.service

import com.enes.busproje.model.StopResponse
import retrofit2.Call
import retrofit2.http.GET

interface StopsAPI {
    @GET("stops")
    fun getData() : Call<StopResponse>
}