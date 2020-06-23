package com.example.wisesaying.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment

import com.example.wisesaying.R

import com.example.wisesaying.databinding.FragmentSettingsBinding
import com.example.wisesaying.preference.PrefFragmentSetting
import com.example.wisesaying.preference.PrefRequestPremisson
import com.example.wisesaying.preference.PrefVisibility


class FragmentSetting : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = DataBindingUtil.inflate<FragmentSettingsBinding>(
            inflater,
            R.layout.fragment_settings,
            container,
            false
        ).run {

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

                        PrefRequestPremisson.getInstance(requireContext()).requestScore = 1

                        switchWidgetSettingPremisson.text = textON
                        PrefFragmentSetting.getInstance(requireContext()).fragmentSettingSwitchWidgetSettingPremissonText =
                           textON

                        if (clickCount <= 4)
                            Toast.makeText(
                                requireContext(),
                                R.string.FragmentSetting_switchWidgetSettingPremisson_On,
                                Toast.LENGTH_SHORT
                            ).show()
                    }

                    false -> {
                        PrefFragmentSetting.getInstance(requireContext()).fragmentSettingSwitchWidgetSettingPremissonisChecked =
                            isChecked

                        PrefRequestPremisson.getInstance(requireContext()).requestScore = 2


                        switchWidgetSettingPremisson.text = textOFF
                        PrefFragmentSetting.getInstance(requireContext()).fragmentSettingSwitchWidgetSettingPremissonText =
                            textOFF

                        if (clickCount <= 4)
                            Toast.makeText(
                                context,
                                R.string.FragmentSetting_switchWidgetSettingPremisson_Off,
                                Toast.LENGTH_SHORT
                            ).show()
                    }
                }
            }

            // 사진 고정기능 조절 스위치

            switchWidgetSettingImageControl.setOnCheckedChangeListener { _, isChecked ->
                clickCount++

                when(isChecked) {
                    true -> {
                        PrefFragmentSetting.getInstance(requireContext()).fragmentSettingSwitchWidgetSettingImageControlisChecked =
                            isChecked
                        switchWidgetSettingImageControl.text = textON
                        PrefFragmentSetting.getInstance(requireContext()).fragmentSettingSwitchWidgetSettingImageControlText =
                            textON
                        PrefVisibility.getInstance(requireContext()).frameLayoutImageModeCheckVisibility =
                            0x00000008

                        if (clickCount <= 4)
                            Toast.makeText(context,
                                R.string.Fragment_Setting_switchWidgetSettingImageControl_On,
                                Toast.LENGTH_SHORT
                            ).show()
                    }
                    false -> {
                        PrefFragmentSetting.getInstance(requireContext()).fragmentSettingSwitchWidgetSettingImageControlisChecked =
                                isChecked

                            switchWidgetSettingImageControl.text = textOFF
                        PrefFragmentSetting.getInstance(requireContext()).fragmentSettingSwitchWidgetSettingImageControlText =
                                textOFF

                            PrefVisibility.getInstance(requireContext()).frameLayoutImageModeCheckVisibility =
                                0x00000000

                            if (clickCount <= 4)

                                Toast.makeText(
                                    context,
                                    R.string.Fragment_Setting_switchWidgetSettingImageControl_Off,
                                    Toast.LENGTH_SHORT
                                ).show()
                    }
                }
            }

            buttonAllList.setOnClickListener {

                val transactionRecyclerView = fragmentManager!!.beginTransaction()
                transactionRecyclerView.replace(
                    R.id.frameLayout_RecyclerView,
                    FragmentAllList()
                )
                transactionRecyclerView.addToBackStack(null)
                transactionRecyclerView.commit()

                root.visibility = View.GONE
                // 콜백함수를 알아보자
            }


            root
        }

}


