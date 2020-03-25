package com.crazyredkitten.myownproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_employee_list.*


class EmployeeList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_list)

        //Custom name in the action bar
        supportActionBar?.title = "The employees list"
        //Using LinearManager as a layout manager for recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        //instantiate the RequestQueue
        val queue = Volley.newRequestQueue(this)
        //URL to JSON data
        val url =  "https://api.myjson.com/bins/160gb9"
        //creating request and listeners
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                //get data from JSON
                val employees = response.getJSONArray("people")
                //create adapter with data
                recyclerView.adapter = EmployeesAdapter(employees)
            },
            Response.ErrorListener { error ->
                Log.d("JSON", error.toString())
            }
        )
        queue.add(jsonObjectRequest)
    }
}
