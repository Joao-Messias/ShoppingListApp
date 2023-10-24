package com.example.shoppinglistapp.data

class ShoppingListRepository(private val shoppingListDao: ShoppingListDao, private val productDao: ProductDao) {

    suspend fun insertShoppingListWithProducts(shoppingList: ShoppingList, products: List<Product>) {
        val listId = shoppingListDao.insert(shoppingList)
        products.forEach { product ->
            product.listId = listId
            productDao.insert(product)
        }
    }
}

