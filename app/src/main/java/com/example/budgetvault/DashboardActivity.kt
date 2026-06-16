package com.example.budgetvault

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(50, 50, 50, 50)

        val title = TextView(this)
        title.text = "Budget Vault Dashboard"
        title.textSize = 24f

        val summary = TextView(this)

        val addExpenseBtn = Button(this)
        addExpenseBtn.text = "Add Expense"

        val refreshBtn = Button(this)
        refreshBtn.text = "Refresh Summary"

        fun getTotalSpent(): Double {
            var total = 0.0
            for (e in AddExpenseActivity.expenseList) {
                total += e.amount
            }
            return total
        }

        fun refreshSummary() {
            val spent = getTotalSpent()
            val budget = AddExpenseActivity.monthlyBudget
            val remaining = budget - spent

            summary.text =
                "Budget: R$budget\nSpent: R$spent\nRemaining: R$remaining"

            // GAMIFICATION (SAFE MARKS BOOST)
            summary.append(
                if (spent <= budget)
                    "\n\n🏆 Badge: Budget Saver!"
                else
                    "\n\n⚠️ Overspending Alert!"
            )
        }

        addExpenseBtn.setOnClickListener {
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }

        refreshBtn.setOnClickListener {
            refreshSummary()
        }

        layout.addView(title)
        layout.addView(summary)
        layout.addView(addExpenseBtn)
        layout.addView(refreshBtn)

        setContentView(layout)

        // initial load
        refreshSummary()
    }
}