package com.lyvetech.transnature.features.profile.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.lyvetech.transnature.core.util.Constants.KEY_HEIGHT
import com.lyvetech.transnature.core.util.Constants.KEY_NAME
import com.lyvetech.transnature.core.util.Constants.KEY_WEIGHT
import com.lyvetech.transnature.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

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
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUserDetails()
        manageBindingViews()
    }

    private fun manageBindingViews() {
        binding.btnEditDetails.setOnClickListener {
            btnEditPressed()
        }

        binding.btnSaveProfile.setOnClickListener {
            if (isFilledWhenBtnSavePressed()) {
                setUpUserDetails()
                Snackbar.make(
                    requireView(), "Saved changes",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                Snackbar.make(
                    requireView(), "Oops, fields can't be empty..",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setUpUserDetails() {
        with(binding) {
            tvUserName.text = sharedPref.getString(KEY_NAME, "")
            tvUserWeight.text = "${sharedPref.getFloat(KEY_WEIGHT, 0f)} kg."
            tvUserHeight.text = "${sharedPref.getFloat(KEY_HEIGHT, 0f)} cm."
        }
    }

    private fun btnEditPressed() {
        with(binding) {
            clProfileDetails.visibility = View.GONE
            clEditProfileDetails.visibility = View.VISIBLE
            etUserName.setText(sharedPref.getString(KEY_NAME, ""))
            etUserWeight.setText(sharedPref.getFloat(KEY_WEIGHT, 0f).toString())
            etUserHeight.setText(sharedPref.getFloat(KEY_HEIGHT, 0f).toString())
        }
    }

    private fun isFilledWhenBtnSavePressed(): Boolean {

        with(binding) {
            val userName = etUserName.text.toString()
            val userWeight = etUserWeight.text.toString()
            val userHeight = etUserHeight.text.toString()

            if (userName.isEmpty() || userWeight.isEmpty()) {
                return false
            }

            sharedPref.edit()
                .putString(KEY_NAME, userName)
                .putFloat(KEY_WEIGHT, userWeight.toFloat())
                .putFloat(KEY_HEIGHT, userHeight.toFloat())
                .apply()

            clEditProfileDetails.visibility = View.GONE
            clProfileDetails.visibility = View.VISIBLE
        }

        return true
    }
}