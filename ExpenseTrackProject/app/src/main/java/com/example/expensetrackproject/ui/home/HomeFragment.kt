package com.example.expensetrackproject.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.expensetrackproject.MainActivity
import com.example.expensetrackproject.R
import com.example.expensetrackproject.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]

        binding.buttonAddIncome.setOnClickListener { showTransactionInputFragment(true) } // Income
        binding.buttonAddExpense.setOnClickListener { showTransactionInputFragment(false) } // Expense

        // Observe changes in the balance
        homeViewModel.balance.observe(viewLifecycleOwner) { newBalance ->
            updateBalance(newBalance)
        }
        showBottomNav()
    }

    private fun showTransactionInputFragment(isIncome: Boolean) {
        val bundle = Bundle().apply {
            putBoolean("income_or_expense", isIncome)
        }
        // Navigate to the TransactionInputFragment
        findNavController().navigate(R.id.action_navigation_home_to_navigation_transaction_input, bundle)

        hideBottomNav()
    }

    private fun updateBalance(balance: Double) {
        val sharedPreferences = context?.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        val currencySymbol = sharedPreferences?.getString("currency", "$") ?: "$"
        val formattedBalance = getString(R.string.text_balance_value, balance) + currencySymbol
        binding.textBalanceValue.text = formattedBalance

        val colorRes = if (balance < 0) R.color.balance_negative else R.color.balance_positive
        binding.textBalanceValue.setTextColor(ContextCompat.getColor(requireContext(), colorRes))
    }

    private fun hideBottomNav() {
        (requireActivity() as? MainActivity)?.setBottomNavVisibility(View.GONE)
    }

    private fun showBottomNav() {
        (requireActivity() as? MainActivity)?.setBottomNavVisibility(View.VISIBLE)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

