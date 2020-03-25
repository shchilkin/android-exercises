package com.crazyredkitten.employeesfragmentsapp

import android.app.DownloadManager
import android.content.Intent
import android.os.Build.VERSION_CODES.P
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_detail.view.*
import org.json.JSONObject

class EmployeesAdapter(private val parentActivity: MainActivity,
                       private val twoPane: Boolean)
    : RecyclerView.Adapter<EmployeesAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeesAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.employee_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = MainActivity.employees.length()

    override fun onBindViewHolder(holder: EmployeesAdapter.ViewHolder, position: Int) {
        //employee to bind UI
        val employee: JSONObject = MainActivity.employees.getJSONObject(position)
        //name
        holder.nameTextView.text = employee["lastName"].toString() + "" + employee["firstName"].toString()
        //title
        holder.titleTextView.text = employee["title"].toString()
        //email
        holder.emailTextView.text = employee["email"].toString()
        //phone
        holder.phoneTextView.text = employee["phone"].toString()
        //department
        holder.departmentTextView.text = employee["department"].toString()
        //image
        val radius = R.dimen.corner_radius
        val requestOptions = RequestOptions()
        requestOptions.override(300,300)
        requestOptions.centerCrop()
        requestOptions.transform(RoundedCorners(radius))
        Glide
            .with(holder.imageView.context)
            .load(employee["image"].toString())
            .apply(requestOptions)
            .into(holder.imageView)
        with(holder.itemView) {
            tag = position
            setOnClickListener(onClickListener)
        }
    }

    //View Holder class to hold UI views
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val nameTextView: TextView = view.nameTextView
        val titleTextView: TextView = view.titleTextView
        val emailTextView: TextView = view.emailTextView
        val phoneTextView: TextView = view.phoneTextView
        val departmentTextView: TextView = view.departmentTextView
        val imageView: ImageView = view.imageView
    }

    private val onClickListener: View.OnClickListener

    companion object{ var position: Int = 0 }

    init {
        onClickListener = View.OnClickListener { v ->
            // getting the item position
            position = v.tag as Int
            //if landscape then show detail fragment
            if (twoPane) {
                parentActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.item_detail_container, DetailFragment())
                    .commit()
                //if portrait then use explicit intent to show employee
            } else {
                val intent = Intent(v.context, DetailActivity::class.java)
                v.context.startActivity(intent)
            }
        }
        //show list and detail side by side (on startup)
        if (twoPane) {
            parentActivity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.item_detail_container, DetailFragment())
                .commit()
        }
    }
}