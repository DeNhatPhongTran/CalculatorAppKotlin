package com.example.convertingcurrency

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var editTextAmount: EditText
    private lateinit var spinnerFrom: Spinner
    private lateinit var spinnerTo: Spinner
    private lateinit var textViewResult: TextView
    private lateinit var textViewExchangeRate: TextView

    private val currencies = listOf("USD", "EUR", "VND", "JPY", "GBP")
    private val exchangeRates = mapOf(
        "USD" to 1.0,
        "EUR" to 0.85,
        "VND" to 23000.0,
        "JPY" to 110.0,
        "GBP" to 0.75
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextAmount = findViewById(R.id.editTextAmount)
        spinnerFrom = findViewById(R.id.spinnerFrom)
        spinnerTo = findViewById(R.id.spinnerTo)
        textViewResult = findViewById(R.id.textViewResult)
        textViewExchangeRate = findViewById(R.id.textViewExchangeRate)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter

        val updateResult = {
            val amount = editTextAmount.text.toString().toDoubleOrNull()
            val fromCurrency = spinnerFrom.selectedItem.toString()
            val toCurrency = spinnerTo.selectedItem.toString()

            val rateFrom = exchangeRates[fromCurrency]
            val rateTo = exchangeRates[toCurrency]

            if (rateFrom != null && rateTo != null) {
                val exchangeRate = rateTo / rateFrom
                textViewExchangeRate.text = "Tỷ giá: 1 $fromCurrency = %.4f $toCurrency".format(exchangeRate)

                if (amount != null) {
                    val result = amount * exchangeRate
                    val symbols = DecimalFormatSymbols(Locale.US)
                    val formatter = DecimalFormat("#,###.#########", symbols)
                    val str = formatter.format(result)
                    textViewResult.text = "Kết quả: $str $toCurrency"
                } else {
                    textViewResult.text = ""
                }
            } else {
                textViewExchangeRate.text = "Tỷ giá: Không xác định"
                textViewResult.text = ""
            }
        }

        editTextAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateResult()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        val onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                updateResult()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spinnerFrom.onItemSelectedListener = onItemSelectedListener
        spinnerTo.onItemSelectedListener = onItemSelectedListener
    }
}

