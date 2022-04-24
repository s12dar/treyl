package com.lyvetech.transnature.features.feed_info.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.lyvetech.transnature.R
import com.lyvetech.transnature.core.util.Constants.BUNDLE_TRAIL_KEY
import com.lyvetech.transnature.core.util.OnboardingUtils
import com.lyvetech.transnature.databinding.FragmentFeedInfoBinding
import com.lyvetech.transnature.features.feed.domain.model.Trail
import com.lyvetech.transnature.features.feed_info.presentation.adapter.ImgRefsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedInfoFragment : Fragment() {

    private lateinit var binding: FragmentFeedInfoBinding
    private lateinit var currentTrail: Trail
    private lateinit var imgRefsAdapter: ImgRefsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideTopAppBar()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFeedInfoBinding.inflate(inflater, container, false)
        managePassedArguments(
            argument = arguments
        )
        (activity as OnboardingUtils).hideBottomNav()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUI()
        manageBindingViews()
        setUpImgViewPager()
    }

    private fun managePassedArguments(argument: Bundle?) {
        argument?.let {
            currentTrail = it.getSerializable(BUNDLE_TRAIL_KEY) as Trail
        }
    }

    private fun subscribeUI() {
        currentTrail.let { trail ->
            binding.apply {
                tvTitle.text = trail.name
                tvAboutContent.text = trail.desc
                tvDuration.text = "${trail.averageTimeInMillis / 3600000} h"
                tvDistance.text = "${trail.distanceInMeters / 1000.0} km"
                tvDifficulty.text = trail.difficultyLevel
                tvPeakPoint.text = "${trail.peakPointInMeters} M"
                tvAccessionContent.text = trail.accession
                tvWarningsContent.text = trail.warning

                // Glide takes care of setting fetched image uri to holder
//                if (trail.imgRefs.first().isNotEmpty()) {
//                    Glide.with(requireContext())
//                        .asBitmap()
//                        .load(trail.imgRefs.first().toUri())
//                        .into(binding.ivTrail)
//                } else {
//                    Glide.with(requireContext())
//                        .load(requireContext().getDrawable(R.drawable.img))
//                        .into(binding.ivTrail)
//                }
            }
        }
    }

    private fun setUpImgViewPager() {
        imgRefsAdapter =
            ImgRefsAdapter(requireContext(), currentTrail.imgRefs)

        val welcomeViewPager = binding.vpImgRefs
        welcomeViewPager.adapter = imgRefsAdapter

        setUpIndicators()
        setUpCurrentIndicator(0)

        welcomeViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setUpCurrentIndicator(position)
            }
        })
    }

    private fun setUpIndicators() {
        val indicators = arrayOfNulls<ImageView>(imgRefsAdapter.itemCount)
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(requireContext().applicationContext)
            indicators[i]!!.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext().applicationContext,
                    R.drawable.indicator_inactive
                )
            )
            indicators[i]!!.layoutParams = layoutParams
            binding.indicators.addView(indicators[i])
        }
    }

    private fun setUpCurrentIndicator(index: Int) {
        val childCount = binding.indicators.childCount
        for (i in 0 until childCount) {
            val imageView = binding.indicators.getChildAt(i) as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext().applicationContext,
                        R.drawable.indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext().applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
            }
        }
    }

    private fun hideTopAppBar() {
        activity?.actionBar?.hide()
    }

    private fun manageBindingViews() {
        with(binding) {
            fabNavigate.setOnClickListener {
                findNavController().navigate(R.id.action_feedInfoFragment_to_trackingFragment)
            }
        }
    }
}