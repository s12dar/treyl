package com.lyvetech.transnature.features.feed.presentation.feed_info

import android.os.Bundle
import android.util.Xml
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import com.lyvetech.transnature.R
import com.lyvetech.transnature.core.util.Constants.BUNDLE_ROUTE_KEY
import com.lyvetech.transnature.core.util.Constants.BUNDLE_TRAIL_KEY
import com.lyvetech.transnature.core.util.Constants.TAG_CARAS
import com.lyvetech.transnature.core.util.Constants.TAG_CASCADA
import com.lyvetech.transnature.core.util.Constants.TAG_LOREM
import com.lyvetech.transnature.core.util.Constants.TAG_SCARITA
import com.lyvetech.transnature.core.util.OnboardingUtils
import com.lyvetech.transnature.databinding.FragmentFeedInfoBinding
import com.lyvetech.transnature.features.feed.domain.model.Trail
import com.lyvetech.transnature.features.feed.presentation.feed_info.adapter.FeedInfoAdapter
import dagger.hilt.android.AndroidEntryPoint
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject


@AndroidEntryPoint
class FeedInfoFragment : Fragment() {

    private val viewModel: FeedInfoViewModel by viewModels()
    private lateinit var binding: FragmentFeedInfoBinding
    private lateinit var currentTrail: Trail
    private lateinit var feedInfoAdapter: FeedInfoAdapter

    @Inject
    lateinit var bundle: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFeedInfoBinding.inflate(inflater, container, false)
        (activity as OnboardingUtils).hideBottomNav()
        (activity as OnboardingUtils).showTopAppBar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        managePassedArguments(arguments)
        subscribeUI()
        manageBindingViews()
        setUpImgViewPager()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_top_app_bar, menu)
        if (currentTrail.isFav) {
            menu.getItem(0).icon =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_fav_true_24dp)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorite -> {
                manageActionButtonSelection(item)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun manageActionButtonSelection(item: MenuItem) {
        if (!currentTrail.isFav) {
            currentTrail.isFav = true
            viewModel.updateTrail(currentTrail)
            item.icon =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_fav_true_24dp)

            Snackbar.make(
                requireView(),
                R.string.txt_trail_saved,
                Snackbar.LENGTH_SHORT
            ).show()
        } else {
            currentTrail.isFav = false
            viewModel.updateTrail(currentTrail)
            item.icon =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_fav_false_24dp)

            Snackbar.make(
                requireView(),
                R.string.txt_trail_removed,
                Snackbar.LENGTH_SHORT
            ).show()
        }
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
            }
        }
    }

    private fun setUpImgViewPager() {
        feedInfoAdapter =
            FeedInfoAdapter(requireContext(), currentTrail.imgRefs)

        val welcomeViewPager = binding.vpImgRefs
        welcomeViewPager.adapter = feedInfoAdapter

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
        val indicators = arrayOfNulls<ImageView>(feedInfoAdapter.itemCount)
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(requireContext().applicationContext)
            indicators[i]?.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext().applicationContext,
                    R.drawable.indicator_inactive
                )
            )
            indicators[i]?.layoutParams = layoutParams
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

    private fun manageBindingViews() {
        currentTrail.route = getConvertedGpx()
        bundle.apply {
            putSerializable(BUNDLE_ROUTE_KEY, currentTrail)
        }

        with(binding) {
            fabNavigate.setOnClickListener {
                findNavController().navigate(
                    R.id.action_feedInfoFragment_to_trackingFragment,
                    bundle
                )
            }
        }
    }

    // Parses the waypoint (wpt tags) data into native objects from a GPX stream.
    @Throws(XmlPullParserException::class, IOException::class)
    private fun loadGpxData(parser: XmlPullParser, gpxIn: InputStream?): MutableList<LatLng> {
        // We use a List<> as we need subList for paging later
        val latLngs = mutableListOf<LatLng>()
        parser.setInput(gpxIn, null)
        parser.nextTag()
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            if (parser.name == "wpt") {
                // Save the discovered latitude/longitude attributes in each <wpt>.
                latLngs.add(
                    LatLng(
                        parser.getAttributeValue(null, "lat").toDouble(),
                        parser.getAttributeValue(null, "lon").toDouble()
                    )
                )
            }
            // Otherwise, skip irrelevant data
        }
        return latLngs
    }

    private fun getConvertedGpx(): MutableList<LatLng> {
        val inputStream: InputStream
        when (currentTrail.tag) {
            TAG_SCARITA -> {
                inputStream = resources.openRawResource(R.raw.trail_scarita)
            }
            TAG_CARAS -> {
                inputStream = resources.openRawResource(R.raw.trail_caras)
            }
            TAG_CASCADA -> {
                inputStream = resources.openRawResource(R.raw.trail_cascada)
            }
            TAG_LOREM -> {
                inputStream = resources.openRawResource(R.raw.trail_lorem)
            }
            else -> {
                inputStream = resources.openRawResource(R.raw.trail_lorem)
            }
        }

        return loadGpxData(Xml.newPullParser(), inputStream)
    }
}