package com.example.expensetrackproject.data.model

import com.example.expensetrackproject.data.model.Category
import java.util.Date



data class Transaction(
    val amount: Double,
    val description: String,
    val type: String,
    val category: Category,
    val date : Date,
    val recurrence: RecurrenceType = RecurrenceType.NONE
)
