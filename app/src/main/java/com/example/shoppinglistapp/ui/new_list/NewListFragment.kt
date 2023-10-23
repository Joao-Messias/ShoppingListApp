package com.example.shoppinglistapp.ui.new_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglistapp.databinding.FragmentNewListBinding

class NewListFragment : Fragment() {

    private var _binding: FragmentNewListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NewListViewModel::class.java)

        _binding = FragmentNewListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.editTextListName
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}