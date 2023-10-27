package com.example.shoppinglistapp.data
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistapp.databinding.FragmentItemEditProductBinding
import com.example.shoppinglistapp.ui.edit_list.EditListViewModel

class EditListAdapter(private val viewModel: EditListViewModel) : ListAdapter<Product, EditListAdapter.ProductViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ProductViewHolder(private val binding: FragmentItemEditProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product, onProductChanged: (Product) -> Unit, onDeleteProduct: (Product) -> Unit) {
            binding.editTextProductNameLista.setText(product.name)
            binding.editTextProductQuantityLista.setText(product.quantity.toString())
            binding.checkBoxProductLista.isChecked = product.checked

            binding.editTextProductNameLista.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    onProductChanged(product.copy(name = s.toString()))
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })

            binding.editTextProductQuantityLista.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val quantity = s.toString().toIntOrNull() ?: 0
                    onProductChanged(product.copy(quantity = quantity))
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })

            binding.checkBoxProductLista.setOnCheckedChangeListener { _, isChecked ->
                onProductChanged(product.copy(checked = isChecked))
            }

            binding.buttonDeleteProduct.setOnClickListener {
                onDeleteProduct(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            FragmentItemEditProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentProduct = getItem(position)
        holder.bind(currentProduct, { updatedProduct ->
            viewModel.updateProduct(position, updatedProduct)
        }, { productToDelete ->
            viewModel.deleteProduct(productToDelete)
        })
    }


}
