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
import com.example.wisesaying.preference.PrefSingleton


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
                    PrefSingleton.getInstance(requireContext()).fragmentSettingSwitchWidgetSettingPremissonisChecked
                text =
                    PrefSingleton.getInstance(requireContext()).fragmentSettingSwitchWidgetSettingPremissonText
            }

            switchWidgetSettingImageControl.apply {
                isChecked =
                    PrefSingleton.getInstance(requireContext()).fragmentsettingSwitchwidgetSettingImageControlisChecked
                text =
                    PrefSingleton.getInstance(requireContext()).fragmentsettingSwitchwidgetSettingImageControlText
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
                        PrefSingleton.getInstance(requireContext()).fragmentSettingSwitchWidgetSettingPremissonisChecked =
                            isChecked

                        PrefSingleton.getInstance(requireContext()).requestScore = 1

                        switchWidgetSettingPremisson.text = textON
                        PrefSingleton.getInstance(requireContext()).fragmentSettingSwitchWidgetSettingPremissonText =
                            textON

                        if (clickCount <= 4)
                            Toast.makeText(
                                requireContext(),
                                R.string.FragmentSetting_switchWidgetSettingPremisson_On,
                                Toast.LENGTH_SHORT
                            ).show()
                    }

                    false -> {
                        PrefSingleton.getInstance(requireContext()).fragmentSettingSwitchWidgetSettingPremissonisChecked =
                            isChecked

                        PrefSingleton.getInstance(requireContext()).requestScore = 2


                        switchWidgetSettingPremisson.text = textOFF
                        PrefSingleton.getInstance(requireContext()).fragmentSettingSwitchWidgetSettingPremissonText =
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
                        PrefSingleton.getInstance(requireContext()).fragmentsettingSwitchwidgetSettingImageControlisChecked =
                            isChecked
                        switchWidgetSettingImageControl.text = textON
                        PrefSingleton.getInstance(requireContext()).fragmentsettingSwitchwidgetSettingImageControlText =
                            textON
                        PrefSingleton.getInstance(requireContext()).frameLayoutImageModeCheckVisibility =
                            0x00000008

                        if (clickCount <= 4)

                            Toast.makeText(
                                context,
                                R.string.Fragment_Setting_switchWidgetSettingImageControl_On,
                                Toast.LENGTH_SHORT
                            ).show()
                    }
                    false -> {
                            PrefSingleton.getInstance(requireContext()).fragmentsettingSwitchwidgetSettingImageControlisChecked =
                                isChecked

                            switchWidgetSettingImageControl.text = textOFF
                            PrefSingleton.getInstance(requireContext()).fragmentsettingSwitchwidgetSettingImageControlText =
                                textOFF

                            PrefSingleton.getInstance(requireContext()).frameLayoutImageModeCheckVisibility =
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
                    FragmentRecyclerView()
                )
                transactionRecyclerView.addToBackStack(null)
                transactionRecyclerView.commit()

                root.visibility = View.GONE
                //메인 프레그먼트에서 뷰페이저 비지블 조절해보기
                // 콜백함수를 알아보자
            }


            root
        }

}


