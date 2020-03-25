package com.crazyredkitten.roomshoppinglist

import android.arch.persistence.room.Room
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), AskShoppingListItemDialogFragment.AddDialogListener{


    //Shopping list items
    private var shoppingList: MutableList<ShoppingListItem> = ArrayList()
    //Shopping list adapter
    private lateinit var adapter: ShoppingListAdapter
    //Shopping List Room database
    private lateinit var db: ShoppingListRoomDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //Use LinearManager as a layout manager for recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        //Create Adapter for recyclerView
        adapter = ShoppingListAdapter(shoppingList)
        recyclerView.adapter = adapter

        //Create database and get instance
        db = Room.databaseBuilder(applicationContext, ShoppingListRoomDatabase::class.java, "hs_db").build()
        //load all shopping list items
        loadShoppingListItems()

        // add a new item to shopping list
        fab.setOnClickListener { view ->
            // create and show dialog
            val dialog = AskShoppingListItemDialogFragment()
            dialog.show(supportFragmentManager, "AskNewItemDialogFragment")
        }

        // Initialize swipe in recyclerView
         fun initSwipe() {
            // Create Touch Callback
            val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                // Swiped
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    // adapter delete item position
                    val position = viewHolder.adapterPosition
                    // Create a Handler Object
                    val handler = Handler(Handler.Callback {
                        // Toast message
                        Toast.makeText(applicationContext,it.data.getString("message"), Toast.LENGTH_SHORT).show()
                        // Notify adapter data change
                        adapter.update(shoppingList)
                        true
                    })
                    // Get remove item id
                    var id = shoppingList[position].id
                    // Remove from UI list
                    shoppingList.removeAt(position)
                    // Remove from db
                    Thread(Runnable {
                        db.shoppingListDao().delete(id)
                        val message = Message.obtain()
                        message.data.putString("message","Item deleted from db!")
                        handler.sendMessage(message)
                    }).start()
                }

                // Moved
                override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
                    return true
                }

            }
            // Attach Item Touch Helper to recyclerView
            val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
            itemTouchHelper.attachToRecyclerView(recyclerView)
        }
    }

    private fun loadShoppingListItems(){

        //Creating a handler object
        val handler = Handler(Handler.Callback {
            //Toast message
            Toast.makeText(applicationContext,it.data.getString("message"), Toast.LENGTH_SHORT).show()
            //Notify adapyer data change
            adapter.update(shoppingList)
            true
        })

        //Create a new Thread to insert data to database
        Thread(Runnable {
            shoppingList = db.shoppingListDao().getAll()
            val message = Message.obtain()
            if (shoppingList.size > 0)
                message.data.putString("message","All shopping list items read from db!")
            else
                message.data.putString("message","Shopping list is empty")
            handler.sendMessage(message)
        }).start()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
    // Add a new shopping list item to db
    override fun onDialogPositiveClick(item: ShoppingListItem) {
        // Create a Handler Object
        val handler = Handler(Handler.Callback {
            // Toast message
            Toast.makeText(applicationContext,it.data.getString("message"), Toast.LENGTH_SHORT).show()
            // Notify adapter data change
            adapter.update(shoppingList)
            true
        })
        // Create a new Thread to insert data to database
        Thread(Runnable {
            // insert and get autoincrement id of the item
            val id = db.shoppingListDao().incert(item)
            // add to view
            item.id = id.toInt()
            shoppingList.add(item)
            val message = Message.obtain()
            message.data.putString("message","Item added to db!")
            handler.sendMessage(message)
        }).start()
    }
}
