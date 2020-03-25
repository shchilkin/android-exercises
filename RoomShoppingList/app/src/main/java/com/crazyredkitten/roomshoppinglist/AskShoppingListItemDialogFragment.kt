package com.crazyredkitten.roomshoppinglist

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.OrientationEventListener
import kotlinx.android.synthetic.main.dialog_ask_new_shopping_list_item.view.*
import java.lang.ClassCastException
import java.lang.IllegalStateException

class AskShoppingListItemDialogFragment : DialogFragment(){
    //Use this instance of the interface to deliver action events
    private lateinit var mListener: AddDialogListener

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    interface AddDialogListener{
        fun onDialogPositiveClick(item: ShoppingListItem)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        //Verify that the host activity implements the callback interface
        try {
            //instanyiate the AddDialogListener so we can send events to the host
            mListener = context as AddDialogListener
        } catch (e: ClassCastException) {
            //The activity doesn't implement the interface, throw exception
            throw  ClassCastException((context.toString()) + "must implement AddDialogListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Custom layout for dialog
            val customView = LayoutInflater.from(context).inflate(R.layout.dialog_ask_new_shopping_list_item, null)

            // Build dialog
            val builder = AlertDialog.Builder(it)
            builder.setView(customView)
            builder.setTitle(R.string.dialog_title)
                .setMessage(R.string.dialog_message)
                .setPositiveButton(R.string.dialog_ok) { dialog, id ->
                    // create a ShoppingList item
                    val name = customView.nameEditText.text.toString()
                    val count = customView.countEditText.text.toString().toInt()
                    val price = customView.priceEditText.text.toString().toDouble()
                    val item = ShoppingListItem(0, name, count, price)
                    mListener.onDialogPositiveClick(item)
                    dialog.dismiss()
                }
                .setNegativeButton(R.string.dialog_cancel) { dialog, id ->
                    dialog.dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}