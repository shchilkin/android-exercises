package com.crazyredkitten.showplacesinmap

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import android.R.attr.name
import android.util.EventLogTags
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONObject

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
        // Instantiate the RequestQueue
        val queue = Volley.newRequestQueue(this)
        //URL to JSON data
        val url = "https://api.myjson.com/bins/t1esa"
        // A request for retrieving a JSONObject response body at a given URL
        val jsonObjectRequest = JsonObjectRequest(
        Request.Method.GET, url, null,
            Response.Listener { response ->
                val coordinates = response.getJSONArray("coordinates")
                for (i in 0 until coordinates.length()) {
                    val cd = coordinates.getJSONObject(i)
                    val latitude:Double = cd.getDouble("latitude")
                    val longitude:Double = cd.getDouble("longitude")
                    val placeDescription:String = cd.getString("placedescription")
                    //Adding markers to the map
                    mMap.addMarker(MarkerOptions().position(LatLng(latitude,longitude)).title(placeDescription))
                }
            },
            Response.ErrorListener { error ->
                Log.e("error is", "$error")
            }
        )
        queue.add(jsonObjectRequest)


    }
}
