package com.lyvetech.transnature.features.tracking.ui.tracking_info.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lyvetech.transnature.core.util.LocationUtils
import com.lyvetech.transnature.databinding.TrackingItemBinding
import com.lyvetech.transnature.features.tracking.domain.model.Session
import java.text.SimpleDateFormat
import java.util.*

class TrackingInfoAdapter(
    private val context: Context
) : RecyclerView.Adapter<TrackingInfoAdapter.TrackingInfoViewHolder>() {

    inner class TrackingInfoViewHolder(private val binding: TrackingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val title = binding.tvTitle
        private val timestamp = binding.tvTimestamp
        private val time = binding.tvTime
        private val distance = binding.tvTotalDistance
        private val speed = binding.tvAvgSpeed
        private val kcal = binding.tvKcal

        @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
        fun bind(session: Session) {
            val calendar = Calendar.getInstance().apply {
                timeInMillis = session.timestamp
            }
            val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())

            title.text = session.title
            timestamp.text = dateFormat.format(calendar.time)
            distance.text = "Total distance: ${session.distanceInMeters / 1000f}km"
            time.text =
                "Total time: ${LocationUtils.getFormattedStopWatchTime(session.timeInMillis)}"
            speed.text = "Avg. Speed: ${session.averageSpeed}km/h"
            kcal.text = "Avg. calories: ${session.caloriesBurnt}kcal"
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Session>() {
        override fun areItemsTheSame(oldItem: Session, newItem: Session): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Session, newItem: Session): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackingInfoViewHolder {
        val binding = TrackingItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return TrackingInfoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: TrackingInfoViewHolder, position: Int) {
        val session = differ.currentList[position]
        holder.itemView.apply {
            holder.bind(session)
        }
    }
}
