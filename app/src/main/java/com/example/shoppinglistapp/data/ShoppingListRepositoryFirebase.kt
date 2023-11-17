package com.example.shoppinglistapp.data

import android.util.Log
import com.example.shoppinglistapp.di.ProductsRef
import com.example.shoppinglistapp.di.ShoppingListsRef
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject


class ShoppingListRepositoryFirebase @Inject constructor(
    @ShoppingListsRef val shoppingListsRef: CollectionReference,
    @ProductsRef private val productsRef: CollectionReference

) {
    private var _shoppingLists = MutableStateFlow(listOf<ShoppingList>())
    val shoppingLists: Flow<List<ShoppingList>>
        get() = _shoppingLists.asStateFlow()

    init {
        shoppingListsRef.addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                val lists = snapshot.documents.mapNotNull { it.toObject<ShoppingList>() }
                _shoppingLists.value = lists
            }
        }
    }

    suspend fun deleteAllShoppingLists() {
        shoppingListsRef.get().addOnSuccessListener { snapshot ->
            snapshot.documents.forEach { doc ->
                doc.reference.delete()
            }
        }
        productsRef.get().addOnSuccessListener { snapshot ->
            snapshot.documents.forEach { doc ->
                doc.reference.delete()
            }
        }
    }

    suspend fun saveProduct(product: Product) {
        if (product.id != 0L) {
            if (product.firestoreId.isNullOrEmpty()) {
                val doc = productsRef.document()
                product.firestoreId = doc.id
                doc.set(product)
            } else {
                productsRef.document(product.firestoreId!!).set(product)
            }
        } else {
            Log.e("Repository", "Tentativa de salvar produto sem ID do SQL")
        }
    }

    suspend fun update(shoppingList: ShoppingList){
        shoppingListsRef.document(shoppingList.firestoreId!!).set(shoppingList)
    }

    suspend fun updateProduct(product: Product) {
        product.firestoreId?.let {
            productsRef.document(it).set(product)
        } ?: Log.e("Repository", "Tentativa de atualizar produto sem firestoreId")
    }
    suspend fun saveShoppingList(shoppingList: ShoppingList) {
        if (shoppingList.id != 0L) { // Verifica se o ID do SQL é válido
            if (shoppingList.firestoreId.isNullOrEmpty()) {
                val doc = shoppingListsRef.document()
                shoppingList.firestoreId = doc.id
                doc.set(shoppingList)
            } else {
                shoppingListsRef.document(shoppingList.firestoreId!!).set(shoppingList)
            }
        } else {
            Log.e("Repository", "Tentativa de salvar lista de compras sem ID do SQL")
        }
    }



    suspend fun deleteShoppingList(shoppingList: ShoppingList) {
        if (!shoppingList.firestoreId.isNullOrEmpty()) {
            shoppingListsRef.document(shoppingList.firestoreId!!).delete()
        }
    }

    fun getAllShoppingLists(): Flow<List<ShoppingList>> = _shoppingLists

    suspend fun insertShoppingListWithProducts(shoppingList: ShoppingList, products: List<Product>, sqlId: Long) {
        // Define o ID do SQL na ShoppingList
        shoppingList.id = sqlId

        // Garante que shoppingList tenha um firestoreId válido
        val shoppingListFirestoreId = shoppingList.firestoreId ?: shoppingListsRef.document().id
        shoppingList.firestoreId = shoppingListFirestoreId

        // Salva a ShoppingList no Firebase
        shoppingListsRef.document(shoppingListFirestoreId).set(shoppingList)

        // Processa cada produto
        products.forEach { product ->
            // Define o ID do SQL no produto
            product.listId = sqlId

            // Garante que o produto tenha um firestoreId válido
            val productFirestoreId = product.firestoreId ?: productsRef.document().id
            product.firestoreId = productFirestoreId

            // Salva o produto no Firebase
            productsRef.document(productFirestoreId).set(product)
        }
    }


    fun deleteList(currentList: ShoppingList) {
        // deleta todos os produtos que tem listId = currentList.id,
        productsRef.whereEqualTo("listId", currentList.id).get().addOnSuccessListener { snapshot ->
            snapshot.documents.forEach { doc ->
                doc.reference.delete()
            }
        }
        shoppingListsRef.whereEqualTo("id", currentList.id).get().addOnSuccessListener { snapshot ->
            snapshot.documents.forEach { doc ->
                doc.reference.delete()
            }
        }
    }

    suspend fun deleteProduct(product: Product) {
        product.firestoreId?.let {
            productsRef.document(it).delete()
        } ?: Log.e("Repository", "Tentativa de deletar produto sem firestoreId")
    }



    fun getProductsByListId(shoppingList: ShoppingList): Flow<List<Product>> = callbackFlow {
        val listener = productsRef.whereEqualTo("shoppingListId", shoppingList.firestoreId)
            .addSnapshotListener { snapshot, _ ->
                val products = snapshot?.documents?.mapNotNull { it.toObject<Product>() }.orEmpty()
                trySend(products)
            }
        awaitClose { listener.remove() }
    }

    fun getListById(shoppingList: ShoppingList): Flow<ShoppingList?> = callbackFlow {
        val listener = shoppingListsRef.document(shoppingList.firestoreId!!)
            .addSnapshotListener { snapshot, _ ->
                val shoppingList = snapshot?.toObject<ShoppingList>()
                trySend(shoppingList)
            }
        awaitClose { listener.remove() }
    }


}

