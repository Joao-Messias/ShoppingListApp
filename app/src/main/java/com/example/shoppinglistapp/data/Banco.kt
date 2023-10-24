package com.example.shoppinglistapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.concurrent.Volatile

@Database(entities = [Product::class, ShoppingList::class], version = 1)
abstract class Banco : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun shoppingListDao(): ShoppingListDao
    companion object{

        @Volatile
        private var INSTANCE: Banco? = null

        fun get(context: Context): Banco {
            if (INSTANCE == null) {

                synchronized(this){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        Banco::class.java,
                        "shopping_list_app.db"
                    ).build()
                }
            }
            return INSTANCE!!
        }

    }
}