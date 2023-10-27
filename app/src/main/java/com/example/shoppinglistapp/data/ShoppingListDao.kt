package com.example.shoppinglistapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListDao {
    @Update
    suspend fun update(shoppingList: ShoppingList)

    @Query("SELECT * FROM shopping_list")
    fun getAll(): Flow<List<ShoppingList>>

    @Query("SELECT * FROM shopping_list WHERE id = :id")
    suspend fun getById(id: Long): ShoppingList

    @Query("SELECT * FROM shopping_list WHERE name LIKE :name")
    fun getByNameLike(name: String): Flow<List<ShoppingList>>

    @Query("DELETE FROM shopping_list WHERE id = :id")
    suspend fun delete(id: kotlin.Long)

    @Query("DELETE FROM shopping_list")
    suspend fun deleteAll()

    @Insert
    suspend fun insert(shoppingList: ShoppingList): Long


    @Query("UPDATE shopping_list SET name = :name WHERE id = :id")
    suspend fun update(id: Int, name: String)
}