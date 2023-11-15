package com.example.shoppinglistapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_list")
data class ShoppingList(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var firestoreId: String? = null, // ID do Firestore
    var name: String = "" // Valor padrão para o nome
)