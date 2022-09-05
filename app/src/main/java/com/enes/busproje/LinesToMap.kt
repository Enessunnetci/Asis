package com.enes.busproje

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.toBitmap

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.enes.busproje.databinding.ActivityLinesToMapBinding
import com.enes.busproje.model.Service
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class LinesToMap : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var binding: ActivityLinesToMapBinding
    private lateinit var bitmap: Bitmap
    private lateinit var markerPosition: LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLinesToMapBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        bitmap =
            baseContext.let{
                AppCompatResources.getDrawable(this,R.drawable.ic_stop)!!.toBitmap()
            }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        getLastKnownLocation()
        //val lineName = intent.getStringExtra("line_name")
       // binding.textViewTitle.text = lineName
        val services = intent.getParcelableExtra<Service>("Service")
        services?.routes?.forEach {
            it.points?.forEach { point ->
                if (point.stopId != null) {
                    markerPosition = point.latitude?.let { it1 -> point.longitude?.let { it2 ->
                        LatLng(it1,
                            it2
                        )
                    } }!!
                    mMap.addMarker(
                        MarkerOptions()
                            .position(markerPosition)
                            .title(point.stopId)
                            .icon(bitmap.let { BitmapDescriptorFactory.fromBitmap(bitmap) })
                    )
                }
            }
        }
        /* val serviceList : ArrayList<Services>?  = intent.getParcelableExtra("arraylist")

         if (serviceList != null) {
             for (servicess:Services in serviceList){
                 println( servicess.name)
             }
         }*/
    }
    private fun getLastKnownLocation() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    val userLocation = LatLng(location.latitude, location.longitude)
                    val bitmap =
                        baseContext.let {
                            AppCompatResources.getDrawable(
                                this,
                                R.drawable.vector_user_location
                            )!!.toBitmap()
                        }
                    mMap.addMarker(
                        MarkerOptions()
                            .position(userLocation)
                            .icon(bitmap.let { BitmapDescriptorFactory.fromBitmap(it) })
                            .title("Your Location")
                    )
                }

            }
    }
}