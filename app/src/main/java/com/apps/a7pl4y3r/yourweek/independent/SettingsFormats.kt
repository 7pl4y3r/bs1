package com.apps.a7pl4y3r.yourweek.independent

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import com.apps.a7pl4y3r.yourweek.R
import kotlinx.android.synthetic.main.activity_settings_formats.*

class SettingsFormats : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAppTheme(this)
        setContentView(R.layout.activity_settings_formats)
        setDateFormat()
        setHourFormat()
        setNumOfDays()

        btCancel.setOnClickListener {
            finish()
        }

        btSaveFormats.setOnClickListener {

            saveDateFormat()
            saveHourFormat()
            saveDaysInWeek()
            finish()
        }
    }

    private fun setDateFormat() {

        val sharedPreferences = getSharedPreferences(settDateFormat, Context.MODE_PRIVATE)
        when(sharedPreferences.getInt(valueSettDateFormat, 1)){
            1 -> rbFormatOne.isChecked = true
            2 -> rbFormatTwo.isChecked = true
        }
    }

    private fun setHourFormat() {

        val sharedPreferences = getSharedPreferences(settHourFormat, Context.MODE_PRIVATE)
        when(sharedPreferences.getInt(valueSettHourFormat, 1)){
            1 -> rbFormat12Hour.isChecked = true
            2 -> rbFormat24Hour.isChecked = true
        }
    }

    private fun setNumOfDays() {
        val sharedPreferences = getSharedPreferences(settNumOfDays, Context.MODE_PRIVATE)
        when(sharedPreferences.getBoolean(valueSettNumOfDays, false)){
            false -> rbFive.isChecked = true
            true -> rbSeven.isChecked = true
        }
    }

    private fun saveDateFormat() {

        val radioButtonId = rgDateFormat.checkedRadioButtonId
        val radioButton = findViewById<RadioButton>(radioButtonId)
        val sharedPreferences = getSharedPreferences(settDateFormat, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        if(radioButton.text == "MM, DD, YYYY (American, default)")
            editor.putInt(valueSettDateFormat, 1)
        else
            editor.putInt(valueSettDateFormat, 2)

        editor.apply()
        Toast.makeText(this, "Date format updated", Toast.LENGTH_SHORT).show()

    }

    private fun saveHourFormat() {

        val sharedPreferences = getSharedPreferences(settHourFormat, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val radioButton = findViewById<RadioButton>(rgTimeFormat.checkedRadioButtonId)
        when(radioButton.text.toString()) {
            "12 hour format (American, default)" -> editor.putInt(valueSettHourFormat, 1)
            "24 hour format (International)" -> editor.putInt(valueSettHourFormat, 2)
        }

        editor.apply()
        Toast.makeText(this, "Time format updated", Toast.LENGTH_SHORT).show()

    }

    private fun saveDaysInWeek() {

        val editor = getSharedPreferences(settNumOfDays, Context.MODE_PRIVATE).edit()
        val radioButton = findViewById<RadioButton>(rgDaysInWeek.checkedRadioButtonId)
        when(radioButton.text.toString()) {
            "5 (default)" -> editor.putBoolean(valueSettNumOfDays, false)
            "7" -> editor.putBoolean(valueSettNumOfDays, true)
        }

        editor.apply()
        Toast.makeText(this, "Number of days in Your Week was changed", Toast.LENGTH_SHORT).show()
    }

}