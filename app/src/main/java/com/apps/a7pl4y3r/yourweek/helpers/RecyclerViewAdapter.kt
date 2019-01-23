package com.apps.a7pl4y3r.yourweek.helpers

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.apps.a7pl4y3r.yourweek.R
import com.example.alin.yourweek.helpers.ItemOfRV

class RecyclerViewAdapter(
    private val context: Context,
    private val itemList: ArrayList<ItemOfRV>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private var mListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int, card: CardView)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    class ViewHolder(view: View, listener: OnItemClickListener?) : RecyclerView.ViewHolder(view) {
        val card: CardView = view.findViewById(R.id.card_task)
        val task: TextView = view.findViewById(R.id.tv_task)

        init {
            view.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION && listener != null)
                    listener.onItemClick(adapterPosition, card)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.greyText))
        holder.task.text = itemList[position].task
    }
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_task, parent, false), mListener)
    override fun getItemCount(): Int = itemList.size
}