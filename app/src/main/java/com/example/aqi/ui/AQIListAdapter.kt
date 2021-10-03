package com.example.aqi.ui

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aqi.R
import com.example.aqi.data.AQIData
import com.example.aqi.databinding.ListItemLayoutBinding
import java.text.SimpleDateFormat

class AQIListAdapter(private val clickListener: OnItemClickListener) :
    ListAdapter<AQIData, AQIListAdapter.ViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = currentList[position]
        val context: Context = holder.itemView.context
        holder.bind(currentItem, context)

    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    private class DiffCallback : DiffUtil.ItemCallback<AQIData>() {
        override fun areItemsTheSame(oldItem: AQIData, newItem: AQIData) =
            oldItem.city == newItem.city


        override fun areContentsTheSame(oldItem: AQIData, newItem: AQIData) =
            oldItem == newItem

    }


    inner class ViewHolder(private val binding: ListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = currentList[position]
                    if (item != null) {
                        clickListener.onItemClick(item.city)
                    }
                }
            }
        }

        fun bind(aqiData: AQIData, context: Context) {
            binding.apply {
                cityNameText.text = aqiData.city
                val roundedAqi: String = "%.2f".format(aqiData.aqi)
                indexNumberText.text = roundedAqi
                val simpleDateFormat = SimpleDateFormat("hh:mm a")
                timeStampText.text = simpleDateFormat.format(aqiData.updatedAt)
                val background: GradientDrawable = indexNumberText.background as GradientDrawable

                val intAqi = aqiData.aqi.toInt()
                when {
                    intAqi <= 50 -> {
                        indexText.text = "Good"
                        background.setColor(ContextCompat.getColor(context, R.color.green))
                    }
                    intAqi <= 100 -> {
                        indexText.text = "Satisfactory"
                        background.setColor(ContextCompat.getColor(context, R.color.light_green))
                    }
                    intAqi <= 200 -> {
                        indexText.text = "Moderate"
                        background.setColor(ContextCompat.getColor(context, R.color.yellow))
                    }
                    intAqi <= 300 -> {
                        indexText.text = "Poor"
                        background.setColor(ContextCompat.getColor(context, R.color.orange))
                    }
                    intAqi <= 400 -> {
                        indexText.text = "Very Poor"
                        background.setColor(ContextCompat.getColor(context, R.color.red))
                    }
                    intAqi <= 500 -> {
                        indexText.text = "Severe"
                        background.setColor(ContextCompat.getColor(context, R.color.dark_red))
                    }
                }
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(city: String)
    }


}