package com.example.shoppinglistapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_list", primaryKeys = ["id"])
data class ShoppingList(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val products: List<Product>)
{
    constructor(): this(0, "", emptyList())
}
