package com.lyvetech.transnature.features.feed.ui.feed_info.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lyvetech.transnature.R
import com.lyvetech.transnature.databinding.VpImgItemBinding

class FeedInfoAdapter(
    private val context: Context,
    private val imgRefs: List<String>
) :
    RecyclerView.Adapter<FeedInfoAdapter.WelcomeViewHolder>() {
    private lateinit var binding: VpImgItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WelcomeViewHolder {
        val inflater = LayoutInflater.from(context)
        binding = VpImgItemBinding.inflate(inflater, parent, false)
        val view: View = binding.root
        return WelcomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: WelcomeViewHolder, position: Int) {
        holder.setImage(imgRefs[position])
    }

    override fun getItemCount(): Int {
        return imgRefs.size
    }

    inner class WelcomeViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun setImage(img: String) {
            if (img.isNotEmpty()) {
                Glide.with(context)
                    .asBitmap()
                    .load(img)
                    .into(binding.ivTrail)
            } else {
                Glide.with(context)
                    .load(context.getDrawable(R.drawable.img))
                    .into(binding.ivTrail)
            }
        }
    }
}