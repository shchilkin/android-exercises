package com.crazyredkitten.myownproject

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.crazyredkitten.myownproject.CalculatorActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun mapActivity(view: View){
        //Toast.makeText(applicationContext, "There are no map activity yet", Toast.LENGTH_LONG).show()
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
    }
    fun OpenCalculator(view: View){
        //Toast.makeText(applicationContext, "There are no calculator activity yet", Toast.LENGTH_LONG).show()
        val intent = Intent(this, CalculatorActivity::class.java)
        startActivity(intent)
    }
    fun employeeActivity(view: View){
        val intent = Intent(this, EmployeeList::class.java)
        startActivity(intent)
    }
}
