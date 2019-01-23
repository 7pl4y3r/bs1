package com.apps.a7pl4y3r.yourweek.dayfragments

import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.apps.a7pl4y3r.yourweek.R
import com.apps.a7pl4y3r.yourweek.databases.Daydb
import com.example.alin.yourweek.helpers.ItemOfRV
import com.apps.a7pl4y3r.yourweek.helpers.RecyclerViewAdapter

class Monday : Fragment() {

    private var cnt: Context? = null
    private var res: Cursor? = null
    private lateinit var mondayDb: Daydb
    private val dayName = "Monday"

    private fun getCursor() {
        mondayDb = Daydb(cnt!!, dayName)
        res = mondayDb.getData()
    }

    private fun hasData(): Boolean {
        return res!!.count != 0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.fragment_day, container, false)
        val dayName = view.findViewById<TextView>(R.id.dayName)
        val recyclerView: RecyclerView = view.findViewById(R.id.dayRecyclerView)

        dayName.text = this.dayName
        getCursor()
        if (hasData()) {

            setRecyclerView(recyclerView)
            mondayDb.close()
            res!!.close()

        } else {

            val itemList = ArrayList<ItemOfRV>()
            itemList.add(ItemOfRV("No tasks for today!", 0))
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(cnt!!)
            recyclerView.adapter = RecyclerViewAdapter(cnt!!, itemList)
        }
        return view
    }

    private fun setRecyclerView(recyclerView: RecyclerView) {

        val itemList = ArrayList<ItemOfRV>()
        res!!.moveToFirst()
        do {

            itemList.add(ItemOfRV("${res!!.getString(5)}\n${res!!.getString(1)}" +
                    ":${res!!.getString(2)} - ${res!!.getString(3)}:${res!!.getString(4)}", 0))

        } while (res!!.moveToNext())


        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(cnt!!)
        recyclerView.adapter = RecyclerViewAdapter(cnt!!, itemList)
    }

    override fun onAttach(context: Context?) {
        cnt = context
        super.onAttach(context)
    }
}