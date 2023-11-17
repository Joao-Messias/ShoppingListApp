package com.example.shoppinglistapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM product WHERE listId = :listId")
    fun getProductsByListId(listId: Long): Flow<List<Product>>
    @Query("SELECT * FROM product")
    fun getAll(): Flow<List<Product>>

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product): Long

    @Update(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun update(product: Product)

    @Query("DELETE FROM product WHERE id = :id")
    suspend fun delete(id: kotlin.Long)

    @Transaction
    @Query("SELECT * FROM shopping_list WHERE id = :listId")
    fun getShoppingListWithProducts(listId: Int): Flow<ShoppingListWithProducts>


}