package com.example.expensetrackproject.ui.history

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetrackproject.R
import com.example.expensetrackproject.databinding.ItemTransactionBinding
import com.example.expensetrackproject.data.model.Transaction
import java.text.SimpleDateFormat
import java.util.Locale

class TransactionAdapter : ListAdapter<Transaction, TransactionAdapter.TransactionViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTransactionBinding.inflate(inflater, parent, false)
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = getItem(position)
        holder.bind(transaction)
    }

    class TransactionViewHolder(private val binding: ItemTransactionBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(transaction: Transaction) {
            binding.textTransactionCategory.text = transaction.category.name
            val sharedPreferences = itemView.context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
            val currencySymbol = sharedPreferences.getString("currency", "$") ?: "$"
            binding.textTransactionAmount.text = "${transaction.amount} $currencySymbol"
            binding.textTransactionDescription.text = transaction.description

            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            binding.textTransactionDate.text = dateFormat.format(transaction.date)

            if (transaction.type == "Income") {
                binding.textTransactionAmount.setTextColor(ContextCompat.getColor(itemView.context, R.color.green_gradient))
            } else {
                binding.textTransactionAmount.setTextColor(ContextCompat.getColor(itemView.context, R.color.red_gradient))
            }

            binding.iconTransactionType.setImageResource(transaction.category.icon)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem.amount == newItem.amount && oldItem.type == newItem.type && oldItem.category == newItem.category
        }
    }
}