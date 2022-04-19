package com.lyvetech.transnature.features.feed.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
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

class FeedAdapter(
    private val context: Context
) : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    inner class FeedViewHolder(private val binding: TrailItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val title = binding.tvTitle
        private val subTitle = binding.tvSubtitle

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(trail: Trail) {
            title.text = trail.name
            subTitle.text = trail.desc

            // Glide takes care of setting fetched image uri to holder
            if (trail.imgUrl?.isNotEmpty() == true) {
                Glide.with(context)
                    .asBitmap()
                    .load(trail.imgUrl.toUri())
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
            return oldItem.imgUrl == newItem.imgUrl
        }

        override fun areContentsTheSame(oldItem: Trail, newItem: Trail): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val binding = TrailItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return FeedViewHolder(binding)
    }

    override fun getItemCount(): Int {
        Log.i("DEBUG", differ.currentList.size.toString())
        return differ.currentList.size
    }

    private var onItemClickListener: ((Trail) -> Unit)? = null

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
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
}
