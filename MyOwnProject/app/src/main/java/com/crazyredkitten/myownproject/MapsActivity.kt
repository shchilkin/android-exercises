package com.crazyredkitten.myownproject

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.custominfowindow.view.*

class CustomInfoWindow(private val context: Context) : GoogleMap.InfoWindowAdapter {
    override fun getInfoContents(p0: Marker?): View? {
        var mInfoView = (context as Activity).layoutInflater.inflate(R.layout.custominfowindow, null)
        mInfoView.nameTextView.text = p0?.title
        mInfoView.infoTextView.text = p0?.snippet
        return mInfoView
    }
    override fun getInfoWindow(p0: Marker?): View? {
        return null
    }
}

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    fun addNewMarker(view: View){
        val latitude = latitudeBox.text.toString().toDouble()
        val longitude = longitudeBox.text.toString().toDouble()
        val markerName = markerNameEditText.text.toString()
        var marketOptions = MarkerOptions().position(LatLng(latitude,longitude)).title(markerName)
        mMap.addMarker(marketOptions)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(latitude,longitude)))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude,longitude),5.0F))
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.myjson.com/bins/9p1lh"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                val coordinates = response.getJSONArray("coordinates")
                for (i in 0 until coordinates.length()) {
                    val cd = coordinates.getJSONObject(i)
                    val latitude:Double = cd.getDouble("latitude")
                    val longitude:Double = cd.getDouble("longitude")
                    val placeDescription:String = cd.getString("placedescription")
                    val tag:String = cd.getString("tag")

                    //Different colors for different tags
                    val colorT = when(tag){
                        "Company" -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
                        "University" -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)
                        "Home" -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)
                        else  -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
                    }

                    //Applying custom window for markers
                    val customInfoWindow = CustomInfoWindow(this)
                    mMap.setInfoWindowAdapter(customInfoWindow)
                    //A  custom marker
                    var mOptions = MarkerOptions()
                        .position(LatLng(latitude,longitude))
                        .title(placeDescription)
                        .snippet("Type: $tag")
                        .icon(colorT)

                    //adding markers
                    mMap.addMarker(mOptions)
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(latitude,longitude)))
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude,longitude),5.0F))
                }
            },
            Response.ErrorListener { error ->
                Log.e("error is", "$error")
            }
        )
        queue.add(jsonObjectRequest)
    }
}
