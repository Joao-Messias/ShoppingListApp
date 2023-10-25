import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.data.Product

class ProductAdapter : ListAdapter<Product, ProductAdapter.ProductViewHolder>(createDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        Log.d("ProductAdapter", "onCreateViewHolder chamado")
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentProduct = getItem(position)
        Log.d("ProductAdapter", "onBindViewHolder chamado com: $currentProduct na posição $position")
        holder.bind(currentProduct)
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productNameTextView: TextView = itemView.findViewById(R.id.textViewProductName)
        private val productQuantityTextView: TextView = itemView.findViewById(R.id.textViewProductQuantity)

        fun bind(product: Product) {
            Log.d("ProductAdapter", "bind chamado com: $product")
            productNameTextView.text = product.name
            productQuantityTextView.text = product.quantity.toString()
        }
    }

    companion object {
        private fun createDiffCallback() = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }
        }
    }
}
