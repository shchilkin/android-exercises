package com.crazyredkitten.golfapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Use LinearManager as a layout manager for recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        //JSON data request
        //Instantiate the RequestQueue
        val queue = Volley.newRequestQueue(this)
        //URL to JSON
        val url = "http://ptm.fi/materials/golfcourses/golf_courses.json"
        //create the request and listeners
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                //Get courses from JSON
                val golfCourses = response.getJSONArray("courses")
                //Create Golf Courses adapter with course data
                recyclerView.adapter = GolfCoursesAdapter(golfCourses)
            },
            Response.ErrorListener { error ->
                Log.d("JSON", error.toString())
            }
        )
        queue.add(jsonObjectRequest)
    }
}
