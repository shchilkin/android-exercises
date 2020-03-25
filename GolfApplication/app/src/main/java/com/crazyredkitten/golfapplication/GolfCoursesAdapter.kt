package com.crazyredkitten.golfapplication

import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_golf_course.view.*
import kotlinx.android.synthetic.main.golfcourse_item.view.*
import kotlinx.android.synthetic.main.golfcourse_item.view.addressTextView
import kotlinx.android.synthetic.main.golfcourse_item.view.imageView
import org.json.JSONArray
import org.json.JSONObject

class GolfCoursesAdapter(private val golfCourses: JSONArray) : RecyclerView.Adapter<GolfCoursesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.golfcourse_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = golfCourses.length()

    override fun onBindViewHolder(holder: ViewHolder , position: Int) {
        //employee to bind UI
        val golfCourse: JSONObject = golfCourses.getJSONObject(position)
        //name
        holder.courseTextView.text = golfCourse["course"].toString()
        holder.addressTextView.text = golfCourse["address"].toString()
        holder.phoneTextView.text = golfCourse["phone"].toString()
        holder.websiteTextView.text = golfCourse["web"].toString()
        Glide.with(holder.imageView.context).load("http://ptm.fi/materials/golfcourses/"+golfCourse["image"].toString()).into(holder.imageView)
    }

    //View holder class to hold UI views
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val courseTextView: TextView = view.courseTextView
        val addressTextView: TextView = view.addressTextView
        val phoneTextView: TextView = view.phoneTextView
        val websiteTextView: TextView = view.websiteTextView
        val imageView: ImageView = view.imageView

        //add a item click listener
        init {
            itemView.setOnClickListener{
                //create an explicit intent
                val intent = Intent(view.context, GolfCourseActivity::class.java)
                //add selected employee json as a string data
                intent.putExtra("golfCourse",golfCourses[adapterPosition].toString())
                view.context.startActivity(intent)
            }
        }
    }
}
