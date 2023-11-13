package com.example.shoppinglistapp.di

import android.content.Context
import com.example.shoppinglistapp.data.Banco
import com.example.shoppinglistapp.data.ProductDao
import com.example.shoppinglistapp.data.ShoppingListDao
import com.example.shoppinglistapp.data.ShoppingListRepositorySQL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBanco(@ApplicationContext context: Context): Banco {
        return Banco.get(context)
    }

    @Provides
    fun provideShoppingListDao(banco: Banco): ShoppingListDao {
        return banco.shoppingListDao()
    }

    @Provides
    fun provideProductDao(banco: Banco): ProductDao {
        return banco.productDao()
    }

    @Provides
    @Singleton
    fun provideShoppingListRepository(
        shoppingListDao: ShoppingListDao,
        productDao: ProductDao
    ): ShoppingListRepositorySQL {
        return ShoppingListRepositorySQL(shoppingListDao, productDao)
    }
}
