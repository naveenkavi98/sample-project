package com.example.sample.ui.selectImage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sample.R
import com.example.sample.databinding.ListItemSelectImageBinding


class SelectImageAdapter(var data: List<Media>, val multiSelection: Boolean) : RecyclerView.Adapter<SelectImageAdapter.SelectImageViewHolder>(){

    private var onItemClicked : OnItemClicked? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectImageViewHolder {
        val binding = ListItemSelectImageBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return SelectImageViewHolder(binding)
    }


    override fun onBindViewHolder(holder: SelectImageViewHolder, position: Int) {
        holder.onBind(data[position], onItemClicked, multiSelection)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun notifyAdapter(newData: List<Media>) {
        data = newData
        notifyDataSetChanged()
    }

    class SelectImageViewHolder(val binding: ListItemSelectImageBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(
            data: Media,
            onItemClicked: OnItemClicked?,
            multiSelection: Boolean
        ) {
            Glide.with(itemView.context)
                .load(data.path)
                .apply(RequestOptions().centerCrop())
                .into(binding.imgView)
            binding.txtCount.text = data.itemCount.toString()
            if (multiSelection) {
                binding.lnrSelected.visibility = View.VISIBLE
            }
            else {
                binding.lnrSelected.visibility = View.GONE
            }
            if (data.selected) {
                binding.lnrSelected.setBackgroundResource(R.drawable.ic_selected)
                binding.viewSelected.setBackgroundResource(R.drawable.img_stroke_boarder)
                binding.txtCount.visibility = View.VISIBLE
            }
            else {
                binding.lnrSelected.setBackgroundResource(R.drawable.ic_not_selected)
                binding.viewSelected.setBackgroundResource(0)
                binding.txtCount.visibility = View.GONE
            }
            binding.viewSelected.setOnClickListener {
                onItemClicked?.itemClicked(data, adapterPosition)
            }
        }
    }

    fun setOnItemClicked(listener: OnItemClicked) {
        onItemClicked = listener
    }

    interface OnItemClicked {
        fun itemClicked(data: Media, position: Int)
    }


}