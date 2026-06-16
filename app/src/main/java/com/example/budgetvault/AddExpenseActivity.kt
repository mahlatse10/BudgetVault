package com.example.budgetvault

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AddExpenseActivity : AppCompatActivity() {

    // Data model
    data class Expense(
        val amount: Double,
        val description: String,
        val category: String
    )

    companion object {
        val expenseList = mutableListOf<Expense>()

        // shared budget so dashboard can access it
        var monthlyBudget = 2000.0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(50, 50, 50, 50)

        val amountInput = EditText(this)
        amountInput.hint = "Enter amount"
        amountInput.inputType =
            android.text.InputType.TYPE_CLASS_NUMBER or
                    android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL

        val descriptionInput = EditText(this)
        descriptionInput.hint = "Enter description"

        val categoryInput = EditText(this)
        categoryInput.hint = "Enter category (e.g. Food, Transport)"

        val saveBtn = Button(this)
        saveBtn.text = "Save Expense"

        val summaryBtn = Button(this)
        summaryBtn.text = "Show Category Totals"

        val viewText = TextView(this)
        viewText.text = "Saved Expenses will appear here"

        // Budget status display
        val budgetStatus = TextView(this)
        budgetStatus.text = "Budget: R${monthlyBudget} | Spent: R0.0"

        // calculate total spent
        fun getTotalSpent(): Double {
            var total = 0.0
            for (e in expenseList) {
                total += e.amount
            }
            return total
        }

        // SAVE EXPENSE
        saveBtn.setOnClickListener {

            val amountText = amountInput.text.toString()
            val desc = descriptionInput.text.toString()
            val category = categoryInput.text.toString()

            if (amountText.isNotEmpty() && desc.isNotEmpty() && category.isNotEmpty()) {

                val amount = amountText.toDouble()

                val expense = Expense(amount, desc, category)
                expenseList.add(expense)

                // show list
                viewText.text = expenseList.joinToString("\n\n") {
                    "R${it.amount} - ${it.description} (${it.category})"
                }

                // clear inputs
                amountInput.text.clear()
                descriptionInput.text.clear()
                categoryInput.text.clear()

                // update totals
                val totalSpent = getTotalSpent()

                budgetStatus.text =
                    "Budget: R${monthlyBudget} | Spent: R$totalSpent"

                // warning system
                if (totalSpent > monthlyBudget) {
                    Toast.makeText(
                        this,
                        "WARNING: Budget exceeded! R$totalSpent / R${monthlyBudget}",
                        Toast.LENGTH_LONG
                    ).show()
                }

            } else {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        // CATEGORY TOTALS
        summaryBtn.setOnClickListener {

            val totals = mutableMapOf<String, Double>()

            for (e in expenseList) {
                totals[e.category] = totals.getOrDefault(e.category, 0.0) + e.amount
            }

            viewText.text = if (totals.isEmpty()) {
                "No expenses yet"
            } else {
                totals.entries.joinToString("\n") {
                    "${it.key}: R${it.value}"
                }
            }
        }

        // ADD VIEWS
        layout.addView(amountInput)
        layout.addView(descriptionInput)
        layout.addView(categoryInput)
        layout.addView(saveBtn)
        layout.addView(summaryBtn)
        layout.addView(budgetStatus)
        layout.addView(viewText)

        setContentView(layout)
    }
}