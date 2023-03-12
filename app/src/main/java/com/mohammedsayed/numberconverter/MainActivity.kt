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
import android.widget.Toast
import com.google.android.material.chip.ChipGroup
import java.lang.Long.*

class MainActivity : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var tvResult: TextView
    private lateinit var choiceFrom: ChipGroup
    private lateinit var choiceTo: ChipGroup
    private lateinit var etNumber: EditText

    var result = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initVariables()
    }

    private fun initVariables() {
        button = findViewById(R.id.btn)
        tvResult = findViewById(R.id.result)
        choiceTo = findViewById(R.id.choice)
        choiceFrom = findViewById(R.id.choice_from)
        etNumber = findViewById(R.id.editText)
    }

    fun convert(view: View) {
        try {
            when (choiceFrom.checkedChipId) {
                R.id.chipDecFrom -> {
                    result = etNumber.text.toString()
                }
                R.id.chipBinFrom -> {
                    result = etNumber.text.toString().toLong(2).toString()
                }
                R.id.chipOctalFrom -> {
                    result = etNumber.text.toString().toLong(8).toString()
                }
                R.id.chipHexFrom -> {
                    result = etNumber.text.toString().toLong(16).toString()
                }
            }
            if (etNumber.text.isNotBlank()) {
                tvResult.text = when (choiceTo.checkedChipId) {
                    R.id.chipOctal -> result.toBaseString { toOctalString(it) }
                    R.id.chipHex -> result.toBaseString { toHexString(it) }
                    R.id.chipBin -> result.toBaseString { toBinaryString(it) }
                    else -> result
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show()
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
        Toast.makeText(this, "Copied to clipboard", Toast.LENGTH_SHORT).show()
    }

    fun clear(view: View) = etNumber.text.clear()
}