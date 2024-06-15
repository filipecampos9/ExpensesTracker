package com.example.expensestracker.data

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

data class Expense(val name: String, val amount: Double, val isPositive: Boolean)

class ExpenseViewModel : ViewModel() {
    private val _expenses = mutableStateListOf<Expense>()
    val expenses: List<Expense> get() = _expenses

    fun addExpense(expense: Expense) {
        _expenses.add(expense)
    }

    fun getTotalExpense():Double {
        return _expenses.sumOf { if (it.isPositive) it.amount.toDouble() else -it.amount.toDouble()}
    }
}