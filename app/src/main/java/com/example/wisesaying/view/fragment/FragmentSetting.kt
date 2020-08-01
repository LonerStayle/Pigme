package com.example.wisesaying.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.wisesaying.R
import com.example.wisesaying.databinding.FragmentMainBinding
import com.example.wisesaying.databinding.FragmentSettingsBinding
import com.example.wisesaying.preference.PrefFragmentSetting
import com.example.wisesaying.preference.PrefRequestPremisson
import com.example.wisesaying.preference.PrefVisibility
import com.example.wisesaying.view.constscore.UsageMark
import com.example.wisesaying.view.toast.toastShort
import com.example.wisesaying.view.viewbase.BaseFragment
import kotlinx.android.synthetic.main.fragment_main.*


class FragmentSetting() : BaseFragment<FragmentSettingsBinding>(R.layout.fragment_settings) {

    override fun FragmentSettingsBinding.setEventListener() {
        switchWidgetSettingPremisson.apply {
            isChecked =
                PrefFragmentSetting.getInstance(requireContext()).fragmentSettingSwitchWidgetSettingPremissonisChecked
            text =
                PrefFragmentSetting.getInstance(requireContext()).fragmentSettingSwitchWidgetSettingPremissonText
        }

        switchWidgetSettingImageControl.apply {
            isChecked =
                PrefFragmentSetting.getInstance(requireContext()).fragmentSettingSwitchWidgetSettingImageControlisChecked
            text =
                PrefFragmentSetting.getInstance(requireContext()).fragmentSettingSwitchWidgetSettingImageControlText
        }

        val textON = "ON\t"
        val textOFF = "OFF\t"
        //TASK 조절 스위치

        var clickCount = 0
        switchWidgetSettingPremisson.setOnCheckedChangeListener { _, isChecked ->
            clickCount++
            /**
            if문을 when으로 변경완료
             */
            when (isChecked) {
                true -> {
                    PrefFragmentSetting.getInstance(requireContext()).fragmentSettingSwitchWidgetSettingPremissonisChecked =
                        isChecked

                    PrefRequestPremisson.getInstance(requireContext()).requestScore =
                        UsageMark.REQUEST_POSITIVE

                    switchWidgetSettingPremisson.text = textON
                    PrefFragmentSetting.getInstance(requireContext()).fragmentSettingSwitchWidgetSettingPremissonText =
                        textON

                    if (clickCount <= 4)
                        toastShort(
                            context,
                            R.string.FragmentSetting_switchWidgetSettingPremisson_On
                        )
                }

                false -> {
                    PrefFragmentSetting.getInstance(requireContext()).fragmentSettingSwitchWidgetSettingPremissonisChecked =
                        isChecked

                    PrefRequestPremisson.getInstance(requireContext()).requestScore =
                        UsageMark.REQUEST_NEGATIVE


                    switchWidgetSettingPremisson.text = textOFF
                    PrefFragmentSetting.getInstance(requireContext()).fragmentSettingSwitchWidgetSettingPremissonText =
                        textOFF

                    if (clickCount <= 4)
                        toastShort(
                            context,
                            R.string.FragmentSetting_switchWidgetSettingPremisson_Off
                        )
                }
            }
        }

        // 사진 고정기능 조절 스위치

        switchWidgetSettingImageControl.setOnCheckedChangeListener { _, isChecked ->
            clickCount++

            when (isChecked) {
                true -> {
                    PrefFragmentSetting.getInstance(requireContext()).fragmentSettingSwitchWidgetSettingImageControlisChecked =
                        isChecked
                    switchWidgetSettingImageControl.text = textON
                    PrefFragmentSetting.getInstance(requireContext()).fragmentSettingSwitchWidgetSettingImageControlText =
                        textON
                    PrefVisibility.getInstance(requireContext()).textViewImageModeCheckVisibility =
                        View.INVISIBLE

                    if (clickCount <= 4)
                        toastShort(
                            context,
                            R.string.Fragment_Setting_switchWidgetSettingImageControl_On
                        )
                }
                false -> {
                    PrefFragmentSetting.getInstance(requireContext()).fragmentSettingSwitchWidgetSettingImageControlisChecked =
                        isChecked

                    switchWidgetSettingImageControl.text = textOFF
                    PrefFragmentSetting.getInstance(requireContext()).fragmentSettingSwitchWidgetSettingImageControlText =
                        textOFF

                    PrefVisibility.getInstance(requireContext()).textViewImageModeCheckVisibility =
                        View.VISIBLE

                    if (clickCount <= 4)

                        toastShort(
                            context,
                            R.string.Fragment_Setting_switchWidgetSettingImageControl_Off
                        )
                }
            }
        }

        buttonAllList.setOnClickListener {

            val transactionRecyclerView = fragmentManager!!.beginTransaction()
            transactionRecyclerView.replace(
                R.id.constraintLayout_AllList,
                FragmentAllList()
            )
                .addToBackStack(null)
                .commit()

            root.visibility = View.GONE
            // 콜백함수를 알아보자

        }
    }

}


