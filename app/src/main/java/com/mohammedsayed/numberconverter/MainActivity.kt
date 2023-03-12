package com.mohammedsayed.numberconverter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.chip.ChipGroup
import java.lang.Long.*

class MainActivity : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var tvResult: TextView
    private lateinit var choice: ChipGroup
    private lateinit var etNumber: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initVariables()
    }

    private fun initVariables() {
        button = findViewById(R.id.btn)
        tvResult = findViewById(R.id.result)
        choice = findViewById(R.id.choice)
        etNumber = findViewById(R.id.editTextNumberDecimal)
    }

    fun convert(view: View) {
        if (etNumber.text.isNotBlank()) {
            try {
                val numString = etNumber.text.toString()
                tvResult.text = when (choice.checkedChipId) {
                    R.id.chipOctal -> numString.toBaseString { toOctalString(it) }
                    R.id.chipHex -> numString.toBaseString { toHexString(it) }
                    else -> numString.toBaseString { toBinaryString(it) }
                }
            } catch (e: Exception) {
                tvResult.text = e.message
            }
        }
    }

    private fun String.toBaseString(baseConverter: (Long) -> String): String {
        return takeUnless { it.contains('.') }
            ?.let {
                baseConverter(this.toLong())
            } ?: split(".")
            .reduce { a, b -> a.toBaseString(baseConverter) + "." + b.toBaseString(baseConverter) }
    }

    fun copy(view: View) {
        if (tvResult.text.isBlank()) return

        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText("label", tvResult.text.toString()))
    }
}