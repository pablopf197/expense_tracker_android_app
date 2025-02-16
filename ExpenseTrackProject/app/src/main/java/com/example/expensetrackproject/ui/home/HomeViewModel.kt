package com.example.expensetrackproject.ui.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.expensetrackproject.data.model.Category
import com.example.expensetrackproject.data.model.Transaction
import java.util.Date

class HomeViewModel : ViewModel() {

    private val _balance = MutableLiveData<Double>().apply { value = 0.00 }
    val balance: LiveData<Double> get() = _balance

    private val _transactions = MutableLiveData<List<Transaction>>(emptyList())
    val transactions: LiveData<List<Transaction>> get() = _transactions

    fun addIncome(amount: Double,description: String, category: Category, date: Date) {
        _balance.value = _balance.value?.plus(amount)
        addTransaction(amount, description,  "Income", category, date)
        println(balance.value)
    }

    fun addExpense(amount: Double, description: String, category: Category, date : Date) {
        _balance.value = _balance.value?.minus(amount)
        addTransaction(-amount,description,  "Expense", category, date)
    }

    private fun addTransaction(amount: Double, description: String, type: String, category: Category, date: Date) {
        val currentList = _transactions.value.orEmpty()
        _transactions.value = currentList + Transaction(amount,description,  type, category, date)
    }
}
