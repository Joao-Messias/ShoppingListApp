package com.example.shoppinglistapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    fun getAll(): Flow<List<Product>>

    @Insert
    suspend fun insert(product: Product): Long

    @Update
    suspend fun update(product: Product)

    @Query("DELETE FROM product WHERE id = :id")
    suspend fun delete(id: Int)

    @Transaction
    @Query("SELECT * FROM shopping_list WHERE id = :listId")
    fun getShoppingListWithProducts(listId: Int): Flow<ShoppingListWithProducts>


}