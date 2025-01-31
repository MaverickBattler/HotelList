package com.mokhnatkinkirill.hotellist.hotel_list.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.mokhnatkinkirill.hotellist.R

class SortingSpinnerAdapter(
    private val context: Context,
    private val items: List<String>
) : BaseAdapter() {

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.sorting_spinner_item, parent, false)
        val textView: TextView = view.findViewById(R.id.spinner_item_text)
        textView.text = items[position]
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.sorting_spinner_dropdown_item, parent, false)
        val textView: TextView = view.findViewById(R.id.spinner_item)
        textView.text = items[position]
        return view
    }
}