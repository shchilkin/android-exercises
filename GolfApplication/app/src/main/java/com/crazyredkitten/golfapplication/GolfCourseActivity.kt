package com.crazyredkitten.golfapplication

import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Browser
import android.support.v7.app.ActionBar
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_golf_course.*
import org.json.JSONObject
import org.w3c.dom.Text
import android.text.style.UnderlineSpan
import android.text.SpannableString
import android.widget.Toolbar
import kotlinx.android.synthetic.main.activity_golf_course.addressTextView
import kotlinx.android.synthetic.main.activity_golf_course.imageView
import kotlinx.android.synthetic.main.golfcourse_item.*

class GolfCourseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_golf_course)
        //get data from intent
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            val golfCourseString = bundle!!.getString("golfCourse")
            val golfCourse = JSONObject(golfCourseString)
            val name =  golfCourse["course"]
            val description =  golfCourse["text"]
            val imageURL =  golfCourse["image"]
            val website = golfCourse["web"]
            val phone = golfCourse ["phone"]
            val address = golfCourse["address"]
            val email = golfCourse["email"]
            val latitude = golfCourse["lat"]
            val longitude = golfCourse["lng"]

            supportActionBar?.title = "$name"

            emailTextView.text = ("$email")
            textTextView.text = ("$description")

            latTextView.text = ("$latitude")
            lngTextView.text = ("$longitude")
            webLinkTextView.text
            val content = SpannableString("$website")
            content.setSpan(UnderlineSpan(), 0, content.length, 0)
            webLinkTextView.text = content

            phoneNumberTextView.text = ("$phone")
            addressTextView.text = ("$address")
            Glide.with(applicationContext).load("http://ptm.fi/materials/golfcourses/$imageURL").into(imageView)
        }
    }
    fun openTheMap(view: View){
        val latitude = latTextView.text.toString().toDouble()
        val longitude = lngTextView.text.toString().toDouble()

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

    fun openTheBrowser(view: View){
        val url = webLinkTextView.text.toString()
        val browserIntent = Intent(Intent.ACTION_VIEW)
        browserIntent.data = Uri.parse(url)

        val activities: List<ResolveInfo> = packageManager.queryIntentActivities(browserIntent, 0)
        val isIntentSafe: Boolean = activities.isNotEmpty()

        if (isIntentSafe) {
            startActivity(browserIntent)
        } else {
            Toast.makeText(this, "There is no activity to handle browser intent!", Toast.LENGTH_LONG).show()
        }
    }
}
