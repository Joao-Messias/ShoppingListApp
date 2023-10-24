package com.example.shoppinglistapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_list")
data class ShoppingList(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
)
{
    constructor(): this(0, "")
}
