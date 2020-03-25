package com.example.launchmap

import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun openTheMap(view: View){
        val latitude = LatitudeBox.text.toString().toDouble()
        val longitude = LongitudeBox.text.toString().toDouble()

        val location = Uri.parse("geo: $latitude,$longitude")
        val mapIntent = Intent(Intent.ACTION_VIEW, location)

        val activities: List<ResolveInfo> = packageManager.queryIntentActivities(mapIntent, 0)
        val isIntentSafe: Boolean = activities.isNotEmpty()

        if (isIntentSafe) {
            startActivity(mapIntent)
        } else {
            Toast.makeText(this, "There is no activity to handle map intent!", Toast.LENGTH_LONG).show();
        }

    }
}
