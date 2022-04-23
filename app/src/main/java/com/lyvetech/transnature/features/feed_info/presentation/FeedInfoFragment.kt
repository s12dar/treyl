package com.lyvetech.transnature.features.feed_info.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.lyvetech.transnature.R
import com.lyvetech.transnature.core.util.Constants.BUNDLE_TRAIL_KEY
import com.lyvetech.transnature.core.util.OnboardingUtils
import com.lyvetech.transnature.databinding.FragmentFeedInfoBinding
import com.lyvetech.transnature.features.feed.domain.model.Trail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedInfoFragment : Fragment() {

    private lateinit var binding: FragmentFeedInfoBinding
    private lateinit var currentTrail: Trail

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
                tvDuration.text = "${trail.averageTimeInMillis / 60000} minutes"
                tvDistance.text = "${trail.distanceInMeters / 1000} km"
                tvDifficulty.text = trail.difficultyLevel
                tvLocation.text = trail.location

                // Glide takes care of setting fetched image uri to holder
                if (trail.imgUrl.isNotEmpty()) {
                    Glide.with(requireContext())
                        .asBitmap()
                        .load(trail.imgUrl.toUri())
                        .into(binding.ivTrail)
                } else {
                    Glide.with(requireContext())
                        .load(requireContext().getDrawable(R.drawable.img))
                        .into(binding.ivTrail)
                }
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