import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.data.Product

class AddProductDialogFragment : DialogFragment() {

    interface OnProductAddedListener {
        fun onProductAdded(product: Product)
    }

    private var listener: OnProductAddedListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.fragment_add_product, null)

            builder.setView(view)
                .setPositiveButton("Salvar") { dialog, id ->
                    val productName = view.findViewById<EditText>(R.id.editTextProductName).text.toString()
                    val productQuantity = view.findViewById<EditText>(R.id.editTextProductQuantity).text.toString().toIntOrNull() ?: 0
                    Log.d("AddProductDialog", "Produto: $productName, Quantidade: $productQuantity")
                    if (productName.isNotBlank()) {
                        val product = Product(name = productName, quantity = productQuantity)
                        Log.d("AddProductDialog", "Chamando onProductAdded com: $product")
                        listener?.onProductAdded(product)
                    }
                }
                .setNegativeButton("Cancelar") { dialog, id ->
                    dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    fun setOnProductAddedListener(listener: OnProductAddedListener) {
        this.listener = listener
    }
}
