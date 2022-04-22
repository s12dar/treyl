package com.lyvetech.transnature.features.feed_info.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
            binding.tvTitle.text = trail.name
            binding.tvDuration.text = "54 minutes"
            binding.tvDistance.text = "2.7 km"
            binding.tvDifficulty.text = "Medium"
            binding.tvLocation.text = "Sare Mare, Romania"
            binding.tvAboutContent.text =
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur."
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