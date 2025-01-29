package com.mokhnatkinkirill.hotellist.hotel_list.ui.adapter

import androidx.recyclerview.widget.DiffUtil

class HotelListItemDiffUtilCallback : DiffUtil.ItemCallback<HotelListModel>() {
    override fun areItemsTheSame(oldItem: HotelListModel, newItem: HotelListModel): Boolean =
        (oldItem.id == newItem.id)

    override fun areContentsTheSame(oldItem: HotelListModel, newItem: HotelListModel): Boolean =
        (oldItem == newItem)
}