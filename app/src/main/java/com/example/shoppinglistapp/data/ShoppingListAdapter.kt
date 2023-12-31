package com.example.shoppinglistapp.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.example.shoppinglistapp.databinding.ItemShoppingListBinding

class ShoppingListAdapter(private val onItemClicked: (ShoppingList) -> Unit) : ListAdapter<ShoppingList, ShoppingListAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemShoppingListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val shoppingList = getItem(position)
        holder.bind(shoppingList)
    }

    class ViewHolder(private val binding: ItemShoppingListBinding, val onItemClicked: (ShoppingList) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        fun bind(shoppingList: ShoppingList) {
            binding.textViewListName.text = shoppingList.name
            binding.root.setOnClickListener {
                onItemClicked(shoppingList)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ShoppingList>() {
        override fun areItemsTheSame(oldItem: ShoppingList, newItem: ShoppingList): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShoppingList, newItem: ShoppingList): Boolean {
            return oldItem == newItem
        }
    }
}
