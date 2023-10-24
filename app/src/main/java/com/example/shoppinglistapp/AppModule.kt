package com.example.shoppinglistapp.di

import android.content.Context
import com.example.shoppinglistapp.data.Banco
import com.example.shoppinglistapp.data.ShoppingListRepository
import com.example.shoppinglistapp.ui.new_list.NewListViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    @Provides
    fun provideShoppingListRepository(
        @ApplicationContext context: Context
    ): ShoppingListRepository {
        val shoppingListDao = Banco.get(context).shoppingListDao()
        val productDao = Banco.get(context).productDao()
        return ShoppingListRepository(shoppingListDao, productDao)
    }

    @Provides
    fun provideNewListViewModel(
        repository: ShoppingListRepository
    ): NewListViewModel {
        return NewListViewModel(repository)
    }
}
