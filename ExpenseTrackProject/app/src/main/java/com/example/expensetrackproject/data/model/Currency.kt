package com.example.expensetrackproject.data.model

data class Currency(val symbol: String, val name: String){
    override fun toString(): String {
        return "$symbol ($name)"
    }
}
