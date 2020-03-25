package com.crazyredkitten.employeesfragmentsapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_detail.view.*

class DetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //get root view
        val rootView = inflater.inflate(R.layout.item_detail, container, false)

        //show employee if there is a selection made in recycler view's adapter
        if (EmployeesAdapter.position != -1){
            val employee = MainActivity.employees.getJSONObject(EmployeesAdapter.position)

            //show data in the UI
            employee?.let {
                rootView.nameTextView.text = it.getString("lastName") + "" + it.getString("firstName")
                rootView.titleTextView.text = it.getString("title")
                rootView.emailTextView.text = it.getString("email")
                rootView.phoneTextView.text = it.getString("phone")
                rootView.departmentTextView.text = it.getString("department")
                val requestOptions = RequestOptions()
                requestOptions.override(500,500)
                Glide
                    .with(this)
                    .load(it.getString("image"))
                    .apply(requestOptions)
                    .into(rootView.imageView)
            }
        }
        return rootView
    }
}
