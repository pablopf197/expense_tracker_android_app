package com.example.expensetrackproject.ui.settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.expensetrackproject.R
import com.example.expensetrackproject.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        sharedPreferences = requireContext().getSharedPreferences("AppSettings", Context.MODE_PRIVATE)

        updateCurrencyDisplay()

        binding.buttonSelectCurrency.setOnClickListener {
            showCurrencySelectionDialog()
        }
        binding.buttonSchedule.setOnClickListener {
            scheduleFixedTransaction()
        }
        val recurrenceOptions = resources.getStringArray(R.array.recurrence_options)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, recurrenceOptions)
        binding.spinnerRecurrence.adapter = adapter

        return root
    }

    private fun showCurrencySelectionDialog() {
        val dialog = CurrencySelectionDialog()
        dialog.setOnCurrencySelectedListener { currency ->
            sharedPreferences.edit().putString("currency", currency.symbol).apply()
            updateCurrencyDisplay()
        }
        dialog.show(parentFragmentManager, CurrencySelectionDialog.TAG)
    }

    @SuppressLint("SetTextI18n")
    private fun updateCurrencyDisplay() {
        val savedCurrencySymbol = sharedPreferences.getString("currency", "$")
        binding.textCurrentCurrency.text =  getString(R.string.current_currency) + " $savedCurrencySymbol"
        binding.buttonSelectCurrency.text = getString(R.string.change_currency)
    }
    private fun scheduleFixedTransaction() {
        val amountText = binding.amountEditText.text.toString().trim()
        val selectedTypeId = binding.radioGroupType.checkedRadioButtonId
        val selectedRecurrence = binding.spinnerRecurrence.selectedItem.toString()

        if (amountText.isEmpty() || amountText.toDoubleOrNull() == null || amountText.toDouble() <= 0) {
            Toast.makeText(requireContext(), getString(R.string.invalid_amount_error), Toast.LENGTH_SHORT).show()
            return
        }

        if (selectedTypeId == -1) {
            Toast.makeText(requireContext(), getString(R.string.error_select_transaction_type), Toast.LENGTH_SHORT).show()
            return
        }
        if (selectedRecurrence == getString(R.string.none)) {
            Toast.makeText(requireContext(), getString(R.string.error_select_recurrence), Toast.LENGTH_SHORT).show()
            return
        }

        Toast.makeText(requireContext(), getString(R.string.transaction_scheduled), Toast.LENGTH_SHORT).show()

        binding.amountEditText.text?.clear()
        binding.radioGroupType.clearCheck()
        binding.spinnerRecurrence.setSelection(0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
