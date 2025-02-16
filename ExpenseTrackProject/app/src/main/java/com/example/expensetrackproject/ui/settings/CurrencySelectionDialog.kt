package com.example.expensetrackproject.ui.settings

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.expensetrackproject.data.model.Currency
import com.example.expensetrackproject.databinding.DialogCurrencySelectionBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CurrencySelectionDialog : BottomSheetDialogFragment() {

    private var _binding: DialogCurrencySelectionBinding? = null
    private val binding get() = _binding!!

    private var onCurrencySelected: ((Currency) -> Unit)? = null

    fun setOnCurrencySelectedListener(listener: (Currency) -> Unit) {
        onCurrencySelected = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogCurrencySelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currencies = listOf(
            Currency("€", "Euro"),
            Currency("$", "Dollar"),
            Currency("£", "Pound"),
            Currency("¥", "Yen"),
            Currency("zł", "Zloty")
        )
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, currencies.map { it })
        binding.listCurrencies.adapter = adapter

        binding.listCurrencies.setOnItemClickListener { _, _, position, _ ->
            val selectedCurrency = currencies[position]
            onCurrencySelected?.invoke(selectedCurrency)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "CurrencySelectionDialog"
    }
}