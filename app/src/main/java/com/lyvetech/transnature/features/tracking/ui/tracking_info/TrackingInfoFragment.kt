package com.lyvetech.transnature.features.tracking.ui.tracking_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lyvetech.transnature.R
import com.lyvetech.transnature.core.util.Constants
import com.lyvetech.transnature.core.util.OnboardingUtils
import com.lyvetech.transnature.databinding.FragmentTrackingInfoBinding
import com.lyvetech.transnature.features.tracking.domain.model.Session
import com.lyvetech.transnature.features.tracking.ui.tracking_info.adapter.TrackingInfoAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackingInfoFragment : Fragment() {

    private lateinit var binding: FragmentTrackingInfoBinding
    private val viewModel: TrackingInfoViewModel by viewModels()
    private lateinit var trackingInfoAdapter: TrackingInfoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTrackingInfoBinding.inflate(inflater, container, false)
        (activity as OnboardingUtils).hideTopAppBar()
        (activity as OnboardingUtils).showBottomNav()
        setRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getSessions()
        manageBindingViews()
    }

    private var isError = false
    private var isLoading = false
    private var isLastPage = false
    private var isScrolling = false

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNoErrors = !isError
            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate =
                isNoErrors && isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                        isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    private fun setRecyclerView() {
        trackingInfoAdapter = TrackingInfoAdapter(requireContext())
        binding.rvSessions.apply {
            adapter = trackingInfoAdapter
            layoutManager = LinearLayoutManager(context)
            addOnScrollListener(this@TrackingInfoFragment.scrollListener)
        }
    }

    private fun getSessions() {
        val trails = viewModel.getSessions()
        trackingInfoAdapter.differ.submitList(trails)
    }

    private fun manageBindingViews() {
        with(binding) {
            fabReset.setOnClickListener {
                MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
                    .setTitle(resources.getString(R.string.alert_title))
                    .setMessage(resources.getString(R.string.alert_subtitle))
                    .setPositiveButton(resources.getString(R.string.alert_pos_button)) { _, _ ->
                        resetTrails()
                    }
                    .setNegativeButton(resources.getString(R.string.alert_neg_button)) { _, _ -> }
                    .show()
            }
        }
    }

    private fun resetTrails() {
        viewModel.deleteAllSessions()
        getSessions()
    }
}