package com.example.expensetrackproject.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensetrackproject.databinding.FragmentHistoryBinding
import com.example.expensetrackproject.ui.home.HomeViewModel

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var transactionAdapter: TransactionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]

        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()

        homeViewModel.transactions.observe(viewLifecycleOwner) { transactions ->
            val reversedTransactions = transactions.reversed()
            transactionAdapter.submitList(reversedTransactions)
            if (transactions.isEmpty()) {
                binding.recyclerViewTransactions.visibility = View.GONE
                binding.textNoTransactions.visibility = View.VISIBLE
            } else {
                binding.recyclerViewTransactions.visibility = View.VISIBLE
                binding.textNoTransactions.visibility = View.GONE
            }
        }

        return root
    }

    private fun setupRecyclerView() {
        transactionAdapter = TransactionAdapter()
        binding.recyclerViewTransactions.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = transactionAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}