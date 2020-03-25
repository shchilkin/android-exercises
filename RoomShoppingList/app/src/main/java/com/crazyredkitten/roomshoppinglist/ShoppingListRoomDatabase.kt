package com.crazyredkitten.roomshoppinglist

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [ShoppingListItem::class], version = 1)
abstract class ShoppingListRoomDatabase :RoomDatabase(){
    abstract fun shoppingListDao(): ShoppingListDao
}