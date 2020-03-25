package com.crazyredkitten.employeesapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_employee.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class EmployeeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee)

        // get data from intent
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            val employeeString = bundle!!.getString("employee")
            val employee = JSONObject(employeeString)
                //declaring a values (Employee info)
            val name = employee["firstName"]
            val surName = employee["lastName"]
            val jobTitle = employee["title"]
            val email = employee["email"]
            val phone = employee["phone"]
            val department = employee["department"]
            val imageURL = employee["image"]

            nameTextView.text = ("$surName $name")
            titleTextView.text = ("$jobTitle")
            emailTextView.text = ("$email")
            phoneTextView.text = ("$phone")
            departmentTextView.text = ("$department")
            Glide.with(applicationContext).load(imageURL).into(imageView2)
        }
    }
}
