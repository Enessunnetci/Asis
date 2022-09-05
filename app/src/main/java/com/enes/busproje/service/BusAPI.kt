package com.enes.busproje.service

import com.enes.busproje.model.BusModel
import retrofit2.Call
import retrofit2.http.GET

interface BusAPI{
     @GET("vehicle_locations")
     fun getData() : Call<BusModel>
 }
