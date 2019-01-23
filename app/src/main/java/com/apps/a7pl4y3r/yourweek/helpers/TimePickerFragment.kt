package com.apps.a7pl4y3r.yourweek.helpers

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment

import com.apps.a7pl4y3r.yourweek.independent.settHourFormat
import com.apps.a7pl4y3r.yourweek.independent.valueSettHourFormat

import java.util.*

class TimePickerFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        return TimePickerDialog(activity, activity as TimePickerDialog.OnTimeSetListener?, hour, minute, is24HourFormat())
    }

    private fun is24HourFormat(): Boolean {

        val timeFormat = (activity as Context).getSharedPreferences(settHourFormat, Context.MODE_PRIVATE)
            .getInt(valueSettHourFormat, 1)

        return timeFormat == 2
    }

}