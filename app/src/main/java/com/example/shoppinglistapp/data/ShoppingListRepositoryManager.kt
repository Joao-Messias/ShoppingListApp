package com.example.shoppinglistapp.data

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import javax.inject.Inject

class ShoppingListRepositoryManager @Inject constructor(
    private val localRepository: ShoppingListRepositorySQL,
    private val firebaseRepository: ShoppingListRepositoryFirebase,
    private val context: Context // Injete o contexto aqui
) : ShoppingListRepository {

    suspend fun insertShoppingListWithProducts(shoppingList: ShoppingList, products: List<Product>) {
        localRepository.insertShoppingListWithProducts(shoppingList, products)
        if (isNetworkAvailable()) {
            Log.d("FirebaseDebug", "Tentando salvar lista no Firestore: $shoppingList")
            firebaseRepository.saveShoppingList(shoppingList)
            products.forEach { firebaseRepository.saveProduct(it) }
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}
