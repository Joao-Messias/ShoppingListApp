package com.example.shoppinglistapp.ui.new_list

import AddProductDialogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.shoppinglistapp.data.Product
import com.example.shoppinglistapp.databinding.FragmentNewListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewListFragment : Fragment() {

    private var _binding: FragmentNewListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NewListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.buttonAddProduct.setOnClickListener {
            val addProductDialogFragment = AddProductDialogFragment()
            addProductDialogFragment.setOnProductAddedListener(object : AddProductDialogFragment.OnProductAddedListener {
                override fun onProductAdded(product: Product) {
                    viewModel.adicionarProduto(product)
                }
            })
            addProductDialogFragment.show(parentFragmentManager, "AddProductDialogFragment")
        }

        binding.buttonSaveList.setOnClickListener {
            salvarLista()
        }

        return root
    }

    private fun salvarLista() {
        val nomeDaLista = binding.editTextListName.text.toString()
        viewModel.salvarLista(nomeDaLista)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
