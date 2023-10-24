package com.example.shoppinglistapp.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "product",
    foreignKeys = [
        ForeignKey(
            entity = ShoppingList::class,
            parentColumns = ["id"],
            childColumns = ["listId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["listId"])]
)
data class Product (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val quantity: Int,
    val checked: Boolean,
    var listId: Long
)
{
    constructor(quantity: Int, name: String) : this(0, "", 0, false, 0)
}
