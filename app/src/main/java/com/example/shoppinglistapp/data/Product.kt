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
    val id: Long = 0,
    var firestoreId: String? = null, // ID do Firestore
    var name: String,
    var quantity: Int,
    var checked: Boolean = false,
    var listId: Long = 0
)

