package com.example.sample.ui.main.fragment

import android.graphics.Color
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.sample.Model.response.EventsResponse
import com.example.sample.databinding.ListItemZooEventsBinding


class ZooEventListAdapter(var data: ArrayList<EventsResponse>) : RecyclerView.Adapter<ZooEventListAdapter.ZooEventListViewHolder>(){

    private var onItemClicked : OnItemClicked? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZooEventListViewHolder {
        val binding = ListItemZooEventsBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ZooEventListViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ZooEventListViewHolder, position: Int) {
        holder.onBind(data[position], onItemClicked)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ZooEventListViewHolder(val binding: ListItemZooEventsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(
            data: EventsResponse,
            onItemClicked: OnItemClicked?
        ) {
            binding.txtEventTitle.text = Html.fromHtml(data.eventName + " - " + data.eventDates)
            binding.txtEventDescription.text = Html.fromHtml(data.eventName)
            //binding.txtEventDescription.setTextColor(itemView.context.getColor(android.R.color.secondary_text_dark))
            binding.txtEventDescription.setTextColor(Color.parseColor("#727272"))
            if (data.eventImage.isNotEmpty()) {
                Glide.with(itemView.context)
                    .load("https://www.aazp.in/aazp-dashboards/images/events/" + data.eventImage[0].image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.imgEvent)
            }
            binding.root.setOnClickListener {
                //onItemClicked?.itemClicked(data, bindingAdapterPosition)
            }
        }
    }

    fun setOnItemClicked(listener: OnItemClicked) {
        onItemClicked = listener
    }

    interface OnItemClicked {
        fun itemClicked(data: EventsResponse, position: Int)
    }


}