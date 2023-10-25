package com.example.shoppinglistapp.ui.new_list

import AddProductDialogFragment
import ProductAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglistapp.data.Product
import com.example.shoppinglistapp.databinding.FragmentNewListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewListFragment : Fragment() {

    private var _binding: FragmentNewListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NewListViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()
        setupAddProductButton()
        setupSaveListButton()

        // Coletando os valores do Flow e atualizando o adaptador
        lifecycleScope.launch {
            viewModel.produtos.collect { produtos ->
                productAdapter.submitList(produtos)
            }
        }

        return root
    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter()
        binding.recyclerViewProducts.adapter = productAdapter
        binding.recyclerViewProducts.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupAddProductButton() {
        binding.buttonAddProduct.setOnClickListener {
            val addProductDialogFragment = AddProductDialogFragment()
            addProductDialogFragment.setOnProductAddedListener(object : AddProductDialogFragment.OnProductAddedListener {
                override fun onProductAdded(product: Product) {
                    Log.d("NewListFragment", "onProductAdded chamado com: $product")
                    viewModel.adicionarProduto(product)
                    Log.d("NewListFragment", "adicionarProduto chamado")
                }
            })
            addProductDialogFragment.show(parentFragmentManager, "AddProductDialogFragment")
        }
    }

    private fun setupSaveListButton() {
        binding.buttonSaveList.setOnClickListener {
            salvarLista()
        }
    }

    private fun salvarLista() {
        val nomeDaLista = binding.editTextListName.text.toString()
        if (nomeDaLista.isNotBlank()) {
            viewModel.salvarLista(nomeDaLista)
            binding.editTextListName.text.clear()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listaSalvaComSucesso.observe(viewLifecycleOwner) { listaSalva ->
            if (listaSalva) {
                mostrarMensagem("Lista cadastrada com Sucesso")
                viewModel.resetListaSalvaComSucesso()
            }
        }
    }

    private fun mostrarMensagem(mensagem: String) {
        Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
