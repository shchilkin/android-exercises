package com.example.sumcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun getResult(view: View){

        var value1  = findViewById<EditText>(R.id.FirstValue)
        var value2 = findViewById<EditText>(R.id.SecondValue)

        //var value1  = findViewById(R.id.FirstValue)  as EditText
        //var value2 = findViewById(R.id.SecondValue) as EditText

        var firstValue = Integer.parseInt(value1.text.toString())
        var secondValue = Integer.parseInt(value2.text.toString())
        //var firstValue = value1
        //var secondValue = value2


        var sum = firstValue + secondValue

        ResultBox.setText("Your value is: $sum")


    }
}