package com.lyvetech.transnature.features.feed.presentation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lyvetech.transnature.R
import com.lyvetech.transnature.core.util.Constants.BUNDLE_TRAIL_KEY
import com.lyvetech.transnature.core.util.Constants.QUERY_PAGE_SIZE
import com.lyvetech.transnature.core.util.OnboardingUtils
import com.lyvetech.transnature.databinding.FragmentFeedBinding
import com.lyvetech.transnature.features.feed.presentation.adapter.FeedAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@AndroidEntryPoint
class FeedFragment : Fragment() {

    private lateinit var binding: FragmentFeedBinding
    private val viewModel: FeedViewModel by viewModels()
    private lateinit var feedAdapter: FeedAdapter
    private lateinit var state: StateFlow<FeedScreenViewState>

    @Inject
    lateinit var bundle: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFeedBinding.inflate(inflater, container, false)
        (activity as OnboardingUtils).showBottomNav()
        setRecyclerView()
        state = viewModel.trailState
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.getMainLooper())
            .postDelayed({ getTrails() }, 2000)

        manageTrailClicked()
    }

    private fun getTrails() {
        Log.i("Hi Serdar", state.value.toString())
        feedAdapter.differ.submitList(state.value.trailItems)
    }

    private var isError = false
    private var isLoading = false
    private var isLastPage = false
    private var isScrolling = false

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as GridLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNoErrors = !isError
            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate =
                isNoErrors && isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                        isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                viewModel.trailState.value.trailItems
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
        feedAdapter = FeedAdapter(requireContext())
        binding.rvTrails.apply {
            adapter = feedAdapter
            layoutManager = GridLayoutManager(context, 2)
            addOnScrollListener(this@FeedFragment.scrollListener)
        }
    }

    private fun manageTrailClicked() {
        feedAdapter.setOnItemClickListener {
            bundle.apply {
                putSerializable(BUNDLE_TRAIL_KEY, it)
            }
            findNavController().navigate(R.id.action_feedFragment_to_feedInfoFragment, bundle)
        }
    }
}