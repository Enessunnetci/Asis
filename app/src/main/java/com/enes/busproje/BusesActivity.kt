package com.enes.busproje

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.enes.busproje.databinding.ActivityBusesBinding
import com.enes.busproje.model.BusLocations
import com.enes.busproje.model.BusModel
import com.enes.busproje.service.BusAPI
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BusesActivity : AppCompatActivity(), OnMapReadyCallback {

    var runnable: Runnable = Runnable {}
    var handler: Handler = Handler(Looper.getMainLooper())
    private var one = 0
    private lateinit var bitmap : Bitmap


    private lateinit var binding: ActivityBusesBinding
    private lateinit var mMap: GoogleMap
    private var busLocationsList: ArrayList<BusLocations>? = null
    var latlngList = arrayListOf<LatLng>()

    companion object {
        const val BASE_URL = "https://tfe-opendata.com/api/v1/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityBusesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        bitmap =
            baseContext.let {
                AppCompatResources.getDrawable(
                    this@BusesActivity,
                    R.drawable.ic_bus
                )!!.toBitmap()
            }


        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        getData()

    }

    private fun getData() {
        runnable = object : Runnable {
            override fun run() {
                val retrofit = Retrofit.Builder()
                    .baseUrl(Stops.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val service = retrofit.create(BusAPI::class.java)
                val call = service.getData()
                call.enqueue(object : Callback<BusModel> {
                    override fun onFailure(call: Call<BusModel>, t: Throwable) {
                        t.printStackTrace()
                    }
                    override fun onResponse(
                        call: Call<BusModel>,
                        response: Response<BusModel>
                    ) {
                        if (response.isSuccessful) {
                            mMap.clear() //TODO
                            response.body()?.let {
                                busLocationsList = ArrayList(it.vehicles)
                                val newLatLng = arrayListOf<LatLng>()
                                for (bus: BusLocations in busLocationsList!!) {
                                    val aLatLong = LatLng(bus.latitude, bus.longitude)
                                    newLatLng.add(aLatLong)
                                    try {
                                        if (one == 0) {
                                            latlngList.add(aLatLong)
                                        }else{
                                            latlngList.forEachIndexed { index, latLng ->
                                                if(latlngList[index].latitude != newLatLng[index].latitude || latlngList[index].longitude != newLatLng[index].longitude ){
                                                    println(index)
                                                    println(" $latLng.latitude     "+latLng.longitude)
                                                    val newPosition = LatLng( newLatLng[index].latitude, newLatLng[index].longitude)
                                                    latlngList[index] = newPosition
                                                }
                                            }
                                        }

                                    }catch (exception :Exception){
                                        exception.printStackTrace()
                                    }



                                }
                                latlngList.forEach {LatLng->



                                    mMap.addMarker(
                                        MarkerOptions()
                                            .position(LatLng)
                                            .snippet(
                                                "${LatLng.latitude}\n" +
                                                        "${LatLng.latitude}"
                                            )
                                            .icon(bitmap.let {
                                                BitmapDescriptorFactory.fromBitmap(bitmap) })
                                            .title("Bus Location")//TODO -> BUSID
                                    )
                                }
                                one = 1
                            }

                        }

                    }
                })

                handler.postDelayed(this, 15000)// this refers to runnable.

            }

        }
        handler.post(runnable)

    }

}