package com.crazyredkitten.employeesapp

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.employee_item.view.*
import org.json.JSONArray
import org.json.JSONObject

class EmployeesAdapter(private val employees: JSONArray) : RecyclerView.Adapter<EmployeesAdapter.ViewHolder>() {

    // create UI View Holder from XML layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeesAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.employee_item, parent, false)
        return ViewHolder(view)
    }

    // return item count in employees
    override fun getItemCount(): Int = employees.length()

    // bind data to UI View Holder
    override fun onBindViewHolder(holder: EmployeesAdapter.ViewHolder, position: Int) {
        // employee to bind UI
        val employee: JSONObject = employees.getJSONObject(position)
        // Get a image URL from JSON file
        val imageURL = employee["image"].toString()
        // name
        holder.nameTextView.text = employee["lastName"].toString() + " " + employee["firstName"].toString()
        holder.titleTextView.text = employee["title"].toString()
        holder.emailTextView.text = employee["email"].toString()
        holder.phoneTextView.text = employee["phone"].toString()
        holder.departmentTextView.text = employee["department"].toString()
        Glide.with(holder.imageView.context).load(imageURL).into(holder.imageView)
    }

    // View Holder class to hold UI views
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.nameTextView
        val titleTextView: TextView = view.titleTextView
        val emailTextView: TextView = view.emailTextView
        val phoneTextView: TextView = view.phoneTextView
        val departmentTextView: TextView = view.departmentTextView
        val imageView: ImageView = view.imageView

        init {
            itemView.setOnClickListener {
                // create an explicit intent
                val intent = Intent(view.context, EmployeeActivity::class.java)
                // add selected employee json as a string data
                intent.putExtra("employee",employees[adapterPosition].toString())
                // start a new activity
                view.context.startActivity(intent)
            }
        }
    }
}