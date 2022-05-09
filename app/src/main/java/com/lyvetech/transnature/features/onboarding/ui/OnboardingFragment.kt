package com.lyvetech.transnature.features.onboarding.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.lyvetech.transnature.R
import com.lyvetech.transnature.core.util.Constants.KEY_FIRST_TIME_TOGGLE
import com.lyvetech.transnature.core.util.Constants.KEY_HEIGHT
import com.lyvetech.transnature.core.util.Constants.KEY_NAME
import com.lyvetech.transnature.core.util.Constants.KEY_WEIGHT
import com.lyvetech.transnature.databinding.FragmentOnboardingBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnboardingFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingBinding

    @Inject
    lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        manageViewBindings()
    }

    private fun manageViewBindings() {
        with(binding) {
            btnStart.setOnClickListener {
                if (isWritePersonalPropertiesToSharedPrefSuccessful()) {
                    findNavController().navigate(R.id.action_onboardingFragment_to_feedFragment)
                }
            }
        }
    }

    private fun isWritePersonalPropertiesToSharedPrefSuccessful(): Boolean {
        val name = binding.etName.text.toString()
        val weight = binding.etWeight.text.toString()
        val height = binding.etHeight.text.toString()

        if (name.isEmpty()) {
            binding.tilName.error = getString(R.string.err_empty_field)
            return false
        }
        if (weight.isEmpty()) {
            binding.tilWeight.error = getString(R.string.err_empty_field)
            return false
        }
        if (height.isEmpty()) {
            binding.tilHeight.error = getString(R.string.err_empty_field)
            return false
        }

        sharedPref.edit()
            .putString(KEY_NAME, name)
            .putFloat(KEY_WEIGHT, weight.toFloat())
            .putFloat(KEY_HEIGHT, height.toFloat())
            .putBoolean(KEY_FIRST_TIME_TOGGLE, false)
            .apply()

        return true
    }
}