package com.crazyredkitten.showgolfcoursesinmap

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.android.volley.Response
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.custominfowindow.view.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng






class CustomInfoWindow(private val context: Context) : GoogleMap.InfoWindowAdapter {
    override fun getInfoContents(p0: Marker?): View? {
        var mInfoView = (context as Activity).layoutInflater.inflate(R.layout.custominfowindow, null)
        mInfoView.golfCourseNameTextView.text = p0?.title
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

        //Instantiate the RequestQueue
        val queue = Volley.newRequestQueue(this)
        //URL to JSON
        val url = "http://ptm.fi/materials/golfcourses/golf_courses.json"
        //Make a request
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,  null,
                Response.Listener { response ->
                    val golfCourses = response.getJSONArray("courses")
                    for (i in 0 until golfCourses.length()) {
                        val courses = golfCourses.getJSONObject(i)
                        val type:String = courses.getString("type")
                        val latitude:Double = courses.getDouble("lat")
                        val longitude:Double = courses.getDouble("lng")
                        val course:String = courses.getString("course")
                        val address:String = courses.getString("address")
                        val phone:String = courses.getString("phone")
                        val email:String = courses.getString("email")
                        val website:String = courses.getString("web")
                        val imageLink:String= courses.getString("image")
                        val description:String = courses.getString("text")
                        val image = "http://ptm.fi/materials/golfcourses/$imageLink"



                           val colorT = when (type){
                               "Kulta" -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)
                               "Kulta/Etu" -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)
                               "?" ->   BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
                               "Etu" -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)
                               else  -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
                           }

                        //Applying custom window
                        val customInfoWindow = CustomInfoWindow(this)
                        mMap.setInfoWindowAdapter(customInfoWindow)
                        //Custom marker
                        var mOptions =    MarkerOptions()
                            .position(LatLng(latitude,longitude))
                            .title(course)
                            .snippet(
                                "Address: $address\n" +
                                        "E-mail: $email\n" +
                                        "Phone: $phone\n" +
                                        "Link: $website"
                            ).icon(colorT)
                        mMap.addMarker(mOptions)
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(latitude,longitude)))
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude,longitude),5.0F))
                    }
                },
                Response.ErrorListener{ error->
                     Log.e("error", "$error")
                }
            )
            queue.add(jsonObjectRequest)
        }
    }

