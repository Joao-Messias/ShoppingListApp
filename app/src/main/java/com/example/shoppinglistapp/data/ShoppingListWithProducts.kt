package com.example.shoppinglistapp.data

import androidx.room.Embedded
import androidx.room.Relation

data class ShoppingListWithProducts(
    @Embedded val shoppingList: ShoppingList,
    @Relation(
        parentColumn = "id",
        entityColumn = "listId"
    )
    val products: List<Product>
)
