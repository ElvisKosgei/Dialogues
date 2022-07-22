package com.example.dialogues

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.util.stream.DoubleStream.builder
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val basicButton: Button = findViewById(R.id.basicButton)
        val customButton: Button = findViewById(R.id.customButton)

        basicButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Are you sure you want to delete?")
            builder.setMessage("One you delete, you won't be able to recover")
            builder.setPositiveButton("No") { _, _ ->
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("Yes") { _, _ ->
                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
            }
            //builder.create().show()

            //MAKING SURE THE USER CANNOT CANCEL THE DIALOGUE BY CLICKING OUTSIDE
            val alert: AlertDialog = builder.create()
            alert.setCancelable(false)
            alert.show()
        }

        customButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val layout = layoutInflater.inflate(R.layout.custom_dialog, null)
            builder.setTitle("Interest Calculator")
            val edtPrincipal : EditText = layout.findViewById(R.id.edtPrincipal)
            val edtPeriod : EditText = layout.findViewById(R.id.edtPeriod)
            val edtRate : EditText = layout.findViewById(R.id.edtRate)

            builder.setView(layout)
            builder.setPositiveButton("Calculate") {_,_,->
                //collect data inside this button
                val principal = edtPrincipal.text.toString().trim().toDoubleOrNull()
               /*  toDoubleOrNull function ensures that if
                 the user enters a character that isn't a number, th app will not crash*/
                val period = edtPeriod.text.toString().trim().toDoubleOrNull()
                val rate = edtRate.text.toString().trim().toDoubleOrNull()
                if (principal != null && period != null && rate != null) {
                    calculateInterest(principal, rate, period)
                } else {
                    Toast.makeText(this, "Invalid value", Toast.LENGTH_SHORT).show()
                }
             /*   if (principal.isNotEmpty() && period.isNotEmpty() && rate.isNotEmpty()) {
                    calculateInterest(principal = principal.toDouble(), period = period.toDouble(), rate = rate.toDouble(),
                        numberOfTimes = 2
                    )
                }*/
            }
            builder.show()
        }
    }

    fun calculateInterest(principal: Double, rate: Double, period: Double, numberOfTimes: Int = 1) {
        val base = numberOfTimes * 100
        val amount = principal * (1 + rate / base).pow(period * numberOfTimes)
        Toast.makeText(this, "KES $amount", Toast.LENGTH_SHORT).show()
    }
}