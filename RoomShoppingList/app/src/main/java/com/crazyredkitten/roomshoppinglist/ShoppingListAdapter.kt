package com.crazyredkitten.roomshoppinglist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.recyclerview_item.view.*

// Shopping List Adapter
class ShoppingListAdapter (var shoppingList: MutableList<ShoppingListItem>) : RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>() {

    // create UI View Holder from XML layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.recyclerview_item, parent, false)
        return ViewHolder(view)
    }

    // return shopping list size
    override fun getItemCount(): Int = shoppingList.size

    // bind data to UI View Holder
    override fun onBindViewHolder(holder: ShoppingListAdapter.ViewHolder, position: Int) {
        // item to bind UI
        val item: ShoppingListItem = shoppingList[position]
        // name, count, price
        holder.nameTextView.text = item.name
        holder.countTextView.text = item.count.toString()
        holder.priceTextView.text = item.price.toString()
    }

    // View Holder class to hold UI views
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.nameTextView
        val countTextView: TextView = view.countTextView
        val priceTextView: TextView = view.priceTextView
    }

    // update data inside adapter
    fun update(newList: MutableList<ShoppingListItem>) {
        shoppingList = newList
        notifyDataSetChanged()
    }
}