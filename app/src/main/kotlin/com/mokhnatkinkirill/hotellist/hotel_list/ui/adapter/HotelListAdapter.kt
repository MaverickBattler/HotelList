package com.mokhnatkinkirill.hotellist.hotel_list.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mokhnatkinkirill.hotellist.R
import com.mokhnatkinkirill.hotellist.databinding.HotelListItemBinding

class HotelListAdapter(
    private val onItemClickListener: (Int) -> Unit
) : ListAdapter<HotelListModel, HotelListAdapter.HotelListViewHolder>(HotelListItemDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelListViewHolder {
        return HotelListViewHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: HotelListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onItemClickListener)
    }

    class HotelListViewHolder(
        private val binding: HotelListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun inflateFrom(parent: ViewGroup): HotelListViewHolder {
                return HotelListViewHolder(
                    HotelListItemBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            }
        }

        fun bind(
            item: HotelListModel,
            onItemClickListener: (Int) -> Unit,
        ) {
            val context = binding.root.context
            binding.apply {
                listItem.setOnClickListener {
                    onItemClickListener(item.id)
                }
                hotelNameTextview.text = item.name
                hotelAddressTextview.text = item.address
                suitesAvailableTextview.text = item.suitesAvailable
                distanceToHotelTextview.text = item.distance
                star1Imageview.setImageDrawable(
                    AppCompatResources.getDrawable(context, item.star1)
                )
                star2Imageview.setImageDrawable(
                    AppCompatResources.getDrawable(context, item.star2)
                )
                star3Imageview.setImageDrawable(
                    AppCompatResources.getDrawable(context, item.star3)
                )
                star4Imageview.setImageDrawable(
                    AppCompatResources.getDrawable(context, item.star4)
                )
                star5Imageview.setImageDrawable(
                    AppCompatResources.getDrawable(context, item.star5)
                )
            }
        }
    }
}