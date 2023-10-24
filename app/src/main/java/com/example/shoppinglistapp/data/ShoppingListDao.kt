package com.example.shoppinglistapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListDao {
    @Query("SELECT * FROM shopping_list")
    suspend fun getAll(): List<ShoppingList>

    @Query("SELECT * FROM shopping_list WHERE id = :id")
    suspend fun getById(id: Int): ShoppingList

    @Query("SELECT * FROM shopping_list WHERE name LIKE :name")
     fun getByNameLike(name: String): Flow<List<ShoppingList>>

    @Query("DELETE FROM shopping_list WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM shopping_list")
    suspend fun deleteAll()

    @Insert
    suspend fun insert(shoppingList: ShoppingList)

    @Query("UPDATE shopping_list SET name = :name WHERE id = :id")
    suspend fun update(id: Int, name: String)
}