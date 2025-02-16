package com.example.expensetrackproject.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.expensetrackproject.MainActivity
import com.example.expensetrackproject.R
import com.example.expensetrackproject.adapter.CategoryAdapter
import com.example.expensetrackproject.data.model.Category
import com.example.expensetrackproject.databinding.FragmentTransactionInputBinding
import java.util.Date



class TransactionInputFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentTransactionInputBinding? = null
    private val binding get() = _binding!!
    private lateinit var selectedCategory: Category
    private var isIncome: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransactionInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).setBottomNavVisibility(View.GONE)
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]

        // Get el the argument isIncome
        isIncome = arguments?.getBoolean("income_or_expense", true) ?: false
        binding.transactionTypeText.text = if (isIncome) getString(R.string.add_income_button) else getString(R.string.add_expense_button)

        //  Get the categories
        val categories = getCategories(isIncome)
        val categoryAdapter = CategoryAdapter(requireContext(), categories)
        binding.categoryGridView.adapter = categoryAdapter

        binding.categoryGridView.setOnItemClickListener { _, _, position, _ ->
            selectedCategory = categories[position]

            if (isAmountValid()) {
                val amount = binding.amountEditText.text.toString().toDouble()
                val description = binding.descriptionEditText.text.toString().ifEmpty { getString(R.string.no_description) }
                val currentDate = Date()
                if (isIncome) {
                    homeViewModel.addIncome(amount, description, selectedCategory, currentDate)
                } else {
                    homeViewModel.addExpense(amount, description, selectedCategory, currentDate)
                }
                findNavController().popBackStack() // Return to HomeFragment
            } else {
                Toast.makeText(requireContext(), getString(R.string.invalid_amount_error), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isAmountValid(): Boolean {
        val amount = binding.amountEditText.text.toString()
        return amount.isNotEmpty() && amount.toDoubleOrNull() != null
    }

    private fun getCategories(isIncome: Boolean): List<Category> {
        return if (isIncome) {
            listOf(
                Category("Salary", R.drawable.ic_salary),
                Category("Gift", R.drawable.ic_gift),
                Category("Investments", R.drawable.ic_investments),
                Category("Freelance", R.drawable.ic_free_work),
                Category("Rental", R.drawable.ic_rental),
                Category("Other", R.drawable.ic_other),
            )
        } else {
            listOf(
                Category("Car", R.drawable.ic_car),
                Category("Bills", R.drawable.ic_bills),
                Category("Food", R.drawable.ic_food),
                Category("Entertainment", R.drawable.ic_entertainment),
                Category("Clothes", R.drawable.ic_clothes),
                Category("Communication", R.drawable.ic_communications),
                Category("Eating Out", R.drawable.ic_eating_out),
                Category("Gift", R.drawable.ic_gift),
                Category("Health", R.drawable.ic_health),
                Category("Pets", R.drawable.ic_pets),
                Category("Taxi", R.drawable.ic_taxi),
                Category("Transport", R.drawable.ic_transport),
                Category("Sports", R.drawable.ic_sports),
                Category("Education", R.drawable.ic_education),
                Category("Investments", R.drawable.ic_investments),
                Category("Other", R.drawable.ic_other),
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as MainActivity).setBottomNavVisibility(View.VISIBLE)
        _binding = null
    }
}
