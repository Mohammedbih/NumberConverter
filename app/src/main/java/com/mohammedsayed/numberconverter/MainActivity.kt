package com.mohammedsayed.numberconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var tvResult: TextView
    private lateinit var choice: RadioGroup
    private lateinit var etNumber: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.btn)
        tvResult = findViewById(R.id.result)
        choice = findViewById(R.id.choice)
        etNumber = findViewById(R.id.editTextNumberDecimal)
    }

    fun convert(view: View) {
        if (etNumber.text.isNotBlank()){
            val num = etNumber.text.toString().toInt()

            tvResult.text = when(choice.checkedRadioButtonId){
                R.id.radOctal -> Integer.toOctalString(num)
                R.id.radHexa -> Integer.toHexString(num)
               else -> Integer.toBinaryString(num)
            }
        }
    }
}