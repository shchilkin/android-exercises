package com.crazyredkitten.roomshoppinglist

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface ShoppingListDao{

    @Query("SELECT*from shopping_list_table")
    fun getAll(): MutableList<ShoppingListItem>

    @Insert
    fun incert(item: ShoppingListItem) : Long

    @Query("DELETE FROM shopping_list_table WHERE id =:itemId")
    fun delete (itemId: Int)
}