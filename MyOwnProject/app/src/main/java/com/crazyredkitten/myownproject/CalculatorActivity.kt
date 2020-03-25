package com.crazyredkitten.myownproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_calculator_activity.*

class CalculatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator_activity)

        //Adding custom name to the heading
        supportActionBar?.title = "Basic Calculator"
    }
    fun valueSum(view: View){
        //capturing values from text boxes
        val value1 = findViewById<EditText>(R.id.firstValueEditText)
        val value2 = findViewById<EditText>(R.id.secondValueEditText)

        when{
            value1.length()<1 && value2.length()<1 -> Toast.makeText(this,"Please enter values!", Toast.LENGTH_LONG).show()
            value1.length()<1 -> Toast.makeText(this,"Please enter e!", Toast.LENGTH_LONG).show()
            value2.length()<1 -> Toast.makeText(this,"Please enter the second value!", Toast.LENGTH_LONG).show()
            else -> { //converting values into needed type
                var firstValue  = Integer.parseInt(value1.text.toString())
                var secondValue= Integer.parseInt(value2.text.toString())

                //calculation
                var sum = firstValue + secondValue

                //showing the result in UI
                resultBox.text = "The sum of values is $sum"
            }
        }
    }
    fun valueSubtraction(view: View){
        //capturing values from text boxes
        val value1 = findViewById<EditText>(R.id.firstValueEditText)
        val value2 = findViewById<EditText>(R.id.secondValueEditText)

        when{
            value1.length()<1 && value2.length()<1 -> Toast.makeText(this,"Please enter values!", Toast.LENGTH_LONG).show()
            value1.length()<1 -> Toast.makeText(this,"Please enter the first value!", Toast.LENGTH_LONG).show()
            value2.length()<1 -> Toast.makeText(this,"Please enter the second value!", Toast.LENGTH_LONG).show()
            else -> { //converting values into needed type
                var firstValue = Integer.parseInt(value1.text.toString())
                var secondValue = Integer.parseInt(value2.text.toString())

                //calculation
                var subtraction = firstValue - secondValue

                //showing the result in UI
                resultBox.text = "The subtraction of values is $subtraction"
            }
        }
    }
    fun valueDivision(view: View){
        //capturing values from text boxes
        val value1 = findViewById<EditText>(R.id.firstValueEditText)
        val value2 = findViewById<EditText>(R.id.secondValueEditText)

        //checking for empty fields
        when {
            value1.length()<1 && value2.length()<1 -> Toast.makeText(this,"Please enter values!", Toast.LENGTH_LONG).show()
            value1.length()<1 -> Toast.makeText(this,"Please enter the first value!", Toast.LENGTH_LONG).show()
            value2.length()<1 -> Toast.makeText(this,"Please enter the second value!", Toast.LENGTH_LONG).show()
            else -> {
                //converting values into needed type
                var firstValue = value1.text.toString().toDouble()
                var secondValue = value2.text.toString().toDouble()

                //calculation
                var division = firstValue / secondValue
                //showing the result in UI
                resultBox.text = "The division of values is $division"
            }
        }
    }
    fun valueMultiplicaton(view: View){
        //capturing values from text boxes
        val value1 = findViewById<EditText>(R.id.firstValueEditText)
        val value2 = findViewById<EditText>(R.id.secondValueEditText)

        when {
            value1.length()<1 && value2.length()<1 -> Toast.makeText(this,"Please enter values!", Toast.LENGTH_LONG).show()
            value1.length()<1 -> Toast.makeText(this,"Please enter the first value!", Toast.LENGTH_LONG).show()
            value2.length()<1 -> Toast.makeText(this,"Please enter the second value!", Toast.LENGTH_LONG).show()
            else ->{ //converting values into needed type
                var firstValue = Integer.parseInt(value1.text.toString())
                var secondValue = Integer.parseInt(value2.text.toString())

                //calculation
                var multiplication = firstValue * secondValue

                //showing the result in UI
                resultBox.text = "The multiplication of values is $multiplication"}
        }
    }
}
