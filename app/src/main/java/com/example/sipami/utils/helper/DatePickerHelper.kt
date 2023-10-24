package com.example.sipami.utils.helper

import android.app.DatePickerDialog
import android.content.Context
import android.widget.EditText
import java.util.*

class DatePickerHelper(private val context: Context) {

    fun showDatePickerDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            editText.context,
            { _, year, month, dayOfMonth ->
                val date = "$year-${month + 1}-$dayOfMonth"
                editText.setText(date)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }
}
