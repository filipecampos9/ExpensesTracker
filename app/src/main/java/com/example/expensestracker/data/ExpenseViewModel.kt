package com.example.expensestracker.data

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

data class Expense(val name: String, val amount: Double, val isPositive: Boolean)

class ExpenseViewModel : ViewModel() {
    private val _expenses = mutableStateListOf<Expense>()
    val expenses: List<Expense> get() = _expenses
    val totalIncome: Double
        get() = _expenses.filter { it.isPositive }.sumOf { it.amount }
    val totalExpenses: Double
        get() = _expenses.filter { !it.isPositive }.sumOf { -it.amount }
    val totalBalance: Double
        get() = totalIncome + totalExpenses

    fun addExpense(expense: Expense) {
        _expenses.add(expense)
    }

    fun isValidAmount(amount: String): Boolean {
        return amount.toDoubleOrNull() != null && !amount.contains(",")
    }
}