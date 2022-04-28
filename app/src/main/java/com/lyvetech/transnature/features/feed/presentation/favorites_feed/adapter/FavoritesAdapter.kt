package com.lyvetech.transnature.features.feed.presentation.favorites_feed.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lyvetech.transnature.R
import com.lyvetech.transnature.databinding.TrailItemBinding
import com.lyvetech.transnature.features.feed.domain.model.Trail

class FavoritesAdapter(
    private val context: Context
) : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    inner class FavoritesViewHolder(private val binding: TrailItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val title = binding.tvTitle
        private val peakPoint = binding.tvPeakPoint

        @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
        fun bind(trail: Trail) {
            title.text = trail.name.substringBefore("-")
            peakPoint.text = "${trail.peakPointInMeters} M"

            // Glide takes care of setting fetched image uri to holder
            if (trail.imgRefs.first().isNotEmpty()) {
                Glide.with(context)
                    .asBitmap()
                    .load(trail.imgRefs.first().toUri())
                    .into(binding.ivTrail)
            } else {
                Glide.with(context)
                    .load(context.getDrawable(R.drawable.img))
                    .into(binding.ivTrail)
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Trail>() {
        override fun areItemsTheSame(oldItem: Trail, newItem: Trail): Boolean {
            return oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(oldItem: Trail, newItem: Trail): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val binding = TrailItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Trail) -> Unit)? = null
    private var onSaveClickedListener: ((Trail) -> Unit)? = null

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val trail = differ.currentList[position]
        holder.itemView.apply {
            holder.bind(trail)
            setOnClickListener {
                onItemClickListener?.let { it(trail) }
            }
        }
    }

    fun setOnItemClickListener(listener: (Trail) -> Unit) {
        onItemClickListener = listener
    }

    fun setOnSaveClickListener(listener: (Trail) -> Unit) {
        onSaveClickedListener = listener
    }
}
