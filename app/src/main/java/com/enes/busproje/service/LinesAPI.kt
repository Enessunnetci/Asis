package com.enes.busproje.service

import com.enes.busproje.model.LinesModel
import retrofit2.Call
import retrofit2.http.GET

interface LinesAPI {
    @GET("services")
    fun getData(): Call<LinesModel>
}