package com.apps.a7pl4y3r.yourweek.independent

import android.app.TimePickerDialog
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.TimePicker
import android.widget.Toast
import com.apps.a7pl4y3r.yourweek.R
import com.apps.a7pl4y3r.yourweek.helpers.TimePickerFragment
import kotlinx.android.synthetic.main.activity_theme_switcher.*
import java.util.*

class ThemeSwitcher : AppCompatActivity(), TimePickerDialog.OnTimeSetListener {

    private var timePickerId = -1
    private var strHour1 = ""
    private var strMin1 = ""
    private var strHour2 = ""
    private var strMin2 = ""
    private lateinit var c1: Calendar
    private lateinit var c2: Calendar

    private fun initCalendars(){
        c1 = Calendar.getInstance()
        c2 = Calendar.getInstance()
    }

    private fun getRbId(id: Int): String {
        return findViewById<RadioButton>(if (id == 1)
            rgTheme1.checkedRadioButtonId
        else
            rgTheme2.checkedRadioButtonId).text.toString()
    }

    private fun getThemeId(id: Int): Int{

        when(getRbId(id)) {

            "Sky blue" -> return 1
            "Ocean dark blue" -> return 2
            "Grass green" -> return 3
            "Mars red" -> return 4
            "AMOLED dark" -> return 5
        }
        return -1
    }

    private fun dataIsValid(): Boolean {
        return true
    }

    private fun setThemeForSwitch(id: Int){

        val sharedPreferences = getSharedPreferences(settTheme, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val sharedPreferencesTheme = getSharedPreferences(SettChangedTheme, Context.MODE_PRIVATE)
        val editorTheme = sharedPreferencesTheme.edit()

        when(getRbId(id)){

            "Sky blue" -> {
                editor.putInt(valueSettTheme,1)
                editorTheme.putBoolean(valueSettChangedTheme,true)
                editor.apply()
                editorTheme.apply()

            }
            "Ocean dark blue" -> {
                editor.putInt(valueSettTheme,2)
                editorTheme.putBoolean(valueSettChangedTheme,true)
                editor.apply()
                editorTheme.apply()
                Toast.makeText(this, "This theme might not look good!!!", Toast.LENGTH_LONG).show()

            }
            "Grass green" -> {
                editor.putInt(valueSettTheme,3)
                editorTheme.putBoolean(valueSettChangedTheme,true)
                editor.apply()
                editorTheme.apply()

            }
            "Mars red" -> {
                editor.putInt(valueSettTheme,4)
                editorTheme.putBoolean(valueSettChangedTheme,true)
                editor.apply()
                editorTheme.apply()

            }
            "AMOLED dark" -> {
                editor.putInt(valueSettTheme,5)
                editorTheme.putBoolean(valueSettChangedTheme,true)
                editor.apply()
                editorTheme.apply()

            }
        }
    }

    private fun setSwitchTheme() {

        getSharedPreferences(setSwitchTheme, Context.MODE_PRIVATE).edit()
                .putBoolean(valSetSwitchTheme, true).apply()

        getSharedPreferences(setSwitchTheme1, Context.MODE_PRIVATE).edit()
                .putInt(valSetSwitchTheme1, getThemeId(1)).apply()

        getSharedPreferences(setSwitchTheme2, Context.MODE_PRIVATE).edit()
                .putInt(valSetSwitchTheme2, getThemeId(2)).apply()

        getSharedPreferences(setSwitchTime1, Context.MODE_PRIVATE).edit()
                .putInt(valSetSwitchTime1, c1.get(Calendar.HOUR_OF_DAY)).apply()

        getSharedPreferences(setSwitchTime2, Context.MODE_PRIVATE).edit()
                .putInt(valSetSwitchTime2, c2.get(Calendar.HOUR_OF_DAY)).apply()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setAppTheme(this)
        setContentView(R.layout.activity_theme_switcher)
        initCalendars()

        btTime1.setOnClickListener {
            timePickerId = 1
            val time1Picker = TimePickerFragment()
            time1Picker.show(supportFragmentManager, "Time1")
        }

        btTime2.setOnClickListener {
            timePickerId = 2
            val time2Picker = TimePickerFragment()
            time2Picker.show(supportFragmentManager, "Time2")
        }

        fabSaveSwitchTheme.setOnClickListener {
            Toast.makeText(this, "Alarm created!", Toast.LENGTH_SHORT).show()
            setSwitchTheme()
        }
    }

    private fun getTime(hour: Int, minute: Int, id: Int): String{

        val strHour: String
        val strMinute: String
        val sharedPreferences = getSharedPreferences(settHourFormat, Context.MODE_PRIVATE)

        if(sharedPreferences.getInt(valueSettHourFormat, 1) == 1){
            //American
            if(hour >= 12){
                strHour = "${hour - 12}"
                strMinute = if(minute < 10) "0$minute PM" else "$minute PM"

            } else {
                strHour = "$hour"
                strMinute = if(minute < 10) "0$minute AM" else "$minute AM"
            }

        } else {
            //International
            strHour = if (hour < 10) "0$hour" else "$hour"
            strMinute = if (minute < 10) "0$minute" else "$minute"
        }

        when(id){

            1 -> {
                strHour1 = strHour
                strMin1 = strMinute
            }
            2 -> {
                strHour2 = strHour
                strMin2 = strMinute
            }
        }
        return "$strHour:$strMinute"
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

        when(timePickerId){

            1 -> {
                Toast.makeText(this, "Chosen time is ${getTime(hourOfDay, minute, 1)}", Toast.LENGTH_LONG).show()
                c1.set(Calendar.HOUR_OF_DAY, hourOfDay)
                c1.set(Calendar.MINUTE, minute)
            }
            2 -> {
                Toast.makeText(this, "Chosen time is ${getTime(hourOfDay, minute, 2)}", Toast.LENGTH_LONG).show()
                c2.set(Calendar.HOUR_OF_DAY, hourOfDay)
                c2.set(Calendar.MINUTE, minute)
            }
        }
    }
}
