package com.example.shoppinglistapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.btnViewAllLists

        binding.btnViewAllLists.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_navigation_lists)
        }

        binding.btnCreateNewList.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_navigation_new_list)
        }

        return root
    }
}
