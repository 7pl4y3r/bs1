package com.apps.a7pl4y3r.yourweek.databases

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private const val Col0 = "ID"
private const val Col1 = "StartTimeHour"
private const val Col2 = "StartTimeMinute"
private const val Col3 = "EndTimeHour"
private const val Col4 = "EndTimeMinute"
private const val Col5 = "Task"

class Daydb(context: Context, private val nameOfDb: String) : SQLiteOpenHelper(context, nameOfDb, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $nameOfDb ($Col0 INTEGER PRIMARY KEY AUTOINCREMENT,$Col1 TEXT,$Col2 TEXT,$Col3 TEXT," +
                "$Col4 TEXT,$Col5 TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $nameOfDb")
        onCreate(db)
    }

    fun insertData(startTimeHour: String?, startTimeMinute: String?, endTimeHour: String?, endTimeMinute: String?, task: String?): Boolean {

        val contentValues = ContentValues()
        contentValues.put(Col1,startTimeHour)
        contentValues.put(Col2,startTimeMinute)
        contentValues.put(Col3,endTimeHour)
        contentValues.put(Col4,endTimeMinute)
        contentValues.put(Col5,task)

        return this.writableDatabase.insert(nameOfDb, null, contentValues) != -1L
    }

    fun getData(): Cursor = this.writableDatabase.rawQuery("select * from $nameOfDb",null)

    fun updateData(id: String, startTimeHour: String, startTimeMinute: String, endTimeHour: String, endTimeMinute: String, task: String): Boolean {

        val contentValues = ContentValues()
        contentValues.put(Col1,startTimeHour)
        contentValues.put(Col2,startTimeMinute)
        contentValues.put(Col3,endTimeHour)
        contentValues.put(Col4,endTimeMinute)
        contentValues.put(Col5,task)

        this.writableDatabase.update(nameOfDb,contentValues, "ID = ?", arrayOf(id))

        return true
    }

    fun deleteData(id: String): Int? = this.writableDatabase.delete(nameOfDb, "ID = ?", arrayOf(id))
}