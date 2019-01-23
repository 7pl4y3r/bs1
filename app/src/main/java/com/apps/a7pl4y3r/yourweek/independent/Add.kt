package com.apps.a7pl4y3r.yourweek.independent

import android.app.TimePickerDialog
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TimePicker
import android.widget.Toast
import com.apps.a7pl4y3r.yourweek.R
import com.apps.a7pl4y3r.yourweek.databases.*
import com.apps.a7pl4y3r.yourweek.helpers.*
import kotlinx.android.synthetic.main.activity_add.*

class Add : AppCompatActivity(), TimePickerDialog.OnTimeSetListener {

    private var timePickerID: Int? = 0
    private var strStartHour: String? = null
    private var strStartMinute: String? = null
    private var strEndHour: String? = null
    private var strEndMinute: String? = null
    private var wantsUpdate: Boolean = false

    private var pivDay = -1
    private var dayOfSet = 0
    private var maxDay = 6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAppTheme(this)
        setContentView(R.layout.activity_add)
        initData()
        setActionBar()

        //Opens the first timePickerDialog and set it's id to 1 for use in onTimeSet()
        tvChosenStartTime.setOnClickListener {
            timePickerID = 1
            val startTimePicker = TimePickerFragment()
            startTimePicker.show(supportFragmentManager,"Start time")
        }

        //Opens the second timePickerDialog and set it's id to 2 for use in onTimeSet()
        tvChosenEndTime.setOnClickListener {
            timePickerID = 2
            val endTimePicker = TimePickerFragment()
            endTimePicker.show(supportFragmentManager,"End time")
        }

        btExitAdd.setOnClickListener { finish() }

        btMin1Day.setOnClickListener {

            if (pivDay + dayOfSet > 0) {
                dayOfSet--
                setActionBar()

            } else toastMessage(this, "You have already reached Monday!", true)
        }

        btPlus1Day.setOnClickListener {

            if (pivDay + dayOfSet < maxDay) {
                dayOfSet++
                setActionBar()

            } else toastMessage(this, "You have already reached Sunday", true)
        }

        //Inserts data in the database on button click
        btCreate.setOnClickListener{

            if (dataIsValid()) {
                addTask()
                val sharedPreferences = getSharedPreferences(settTaskWasAdded, Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putBoolean(valueSettTaskWasAdded, true)
                editor.apply()

                wantsUpdate = true
            } else Toast.makeText(this,"I need more info in order to create your task",Toast.LENGTH_SHORT).show()
        }
    }

    private fun initData() {
        wantsUpdate = false
        pivDay = intent.getIntExtra("DAY", 0)
        maxDay = if (getSharedPreferences(settNumOfDays, Context.MODE_PRIVATE)
                .getBoolean(valueSettNumOfDays, false)) 4 else 6
    }

    private fun setActionBar() {
        setSupportActionBar(toolbarAdd)
        toolbarAdd.title = "Add task - ${dayId()}"
    }

    private fun addTask() {

        val db = Daydb(this, dayId())
        if (db.insertData(strStartHour, strStartMinute, strEndHour, strEndMinute, etTask.text.toString())) {
            Toast.makeText(this, "Task remembered!", Toast.LENGTH_SHORT).show()

            tvChosenStartTime.text = ("No chosen start time")
            tvChosenEndTime.text = ("No chosen end time")
            etTask.text = null
            db.close()

        } else Toast.makeText(this, "ERROR!\nTask could not be remembered!", Toast.LENGTH_SHORT).show()
    }

    private fun dataIsValid(): Boolean {

        val startTime = tvChosenStartTime.text.toString()
        val endTime = tvChosenEndTime.text.toString()

        if(etTask.text.isEmpty() || startTime == "No chosen start time" || endTime == "No chosen end time")
            return false

        return true
    }

    private fun dayId(): String {

        when(pivDay + dayOfSet) {

            0 -> return "Monday"
            1 -> return "Tuesday"
            2 -> return "Wednesday"
            3 -> return "Thursday"
            4 -> return "Friday"
            5 -> return "saturday"
            6 -> return "Sunday"

        }
        return "LOLLOL"
    }

    /*Returns the time chosen by the user in the timePickerDialog for both cases
     *in the desired format. Depends on the settings. The default is american.
     */
    private fun getTime(hour: Int, minute: Int, id: Int): String {

        val strHour: String
        val strMinute: String
        val sharedPreferences = getSharedPreferences(settHourFormat, Context.MODE_PRIVATE)

        if (sharedPreferences.getInt(valueSettHourFormat, 1) == 1) {
            //American
            if(hour >= 12) {
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
                strStartHour = strHour
                strStartMinute = strMinute
            }
            2 -> {
                strEndHour = strHour
                strEndMinute = strMinute
            }
        }
        return "$strHour:$strMinute"
    }

    override fun onTimeSet(p0: TimePicker?, hour: Int, minute: Int) {

        when(timePickerID) {
            1 -> tvChosenStartTime.text = getTime(hour, minute, 1)
            2 -> tvChosenEndTime.text = getTime(hour,minute, 2)
        }
    }
}