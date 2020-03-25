package com.crazyredkitten.employeesfragmentsapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.item_list.*
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    private var twoPane : Boolean = false
    companion object{var employees: JSONArray = JSONArray() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (item_detail_container != null) {
            //only in landscape mode
            twoPane = true
        }

        //load employees if not loaded< if loaded setup recycler view
        if (employees.length() == 0) loadJsonData()
        else setupRecyclerView(employees)
    }

    private fun loadJsonData() {
        //instantiate a RequestQueue
        val queue = Volley.newRequestQueue(this)
        // URL to JSON
        val url = "http://ptm.fi/data/android_employees.json"
        // Creating request and listeners
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                //Store loaded json to static employees
                employees = response.getJSONArray("employees")
                //setup recycler VIew
                setupRecyclerView(employees)
            },
            Response.ErrorListener { error ->
                Log.d("JSOn", error.toString())
            }
        )
        //Adding a request
        queue.add(jsonObjectRequest)
    }

    private fun setupRecyclerView(employees: JSONArray) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = EmployeesAdapter(this, twoPane)

    }
}
