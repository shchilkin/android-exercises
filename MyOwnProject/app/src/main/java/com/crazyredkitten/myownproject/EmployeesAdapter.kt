package com.crazyredkitten.myownproject

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.people_item.view.*
import org.json.JSONArray
import org.json.JSONObject

class EmployeesAdapter(private val employees: JSONArray) : Adapter<EmployeesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): EmployeesAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(p0.context)
        val view = layoutInflater.inflate(R.layout.people_item, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = employees.length()

    override fun onBindViewHolder(holder: EmployeesAdapter.ViewHolder, p1: Int) {
        //data to bind UI
        val person: JSONObject = employees.getJSONObject(p1)
        //name
        holder.nameTextView.text = person["last_name"].toString() + " " + person["first_name"].toString()
        holder.jobTextView.text = person["jobTitle"].toString()
        holder.departmentTextView.text =  person["department"].toString()
        holder.emailTextView.text = person["email"].toString()
        holder.phoneTextView.text=  person["phone"].toString()
        val imageURL = person["image"].toString()
        Glide.with(holder.imageView.context).load(imageURL).into(holder.imageView)
    }

    inner class  ViewHolder(view:View): RecyclerView.ViewHolder(view){
        val nameTextView: TextView = view.personTextView
        val jobTextView: TextView = view.jobTextVIew
        val departmentTextView: TextView = view.departmentTextView
        val emailTextView: TextView = view.emailTextView
        val phoneTextView: TextView = view.phoneTextView
        val imageView: ImageView = view.imageView
    }

}
