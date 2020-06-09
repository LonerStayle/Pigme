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
import com.example.wisesaying.preference.Preference_View
import com.example.wisesaying.preference.PreferenceinPermissonRequest


class FragmentSetting : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<FragmentSettingsBinding>(
            inflater,
            R.layout.fragment_settings,
            container,
            false
        ).run {

            switchWidgetSettingPremisson.isChecked =
                Preference_View.get_FragmentSetting_switchWidgetSettingPremissonisChecked(
                    requireContext()
                )
            switchWidgetSettingPremisson.text =
                Preference_View.get_FragmentSetting_switchWidgetSettingPremissonisText(
                    requireContext()
                )
            switchWidgetSettingImageControl.isChecked =
                Preference_View.get_FragmentSetting_switchWidgetSettingimageControlisChecked(
                    requireContext()
                )
            switchWidgetSettingImageControl.text =
                Preference_View.get_FragmentSetting_switchWidgetSettingimageControlisText(
                    requireContext()
                )

//TASK 조절 스위치
            var imageclickCount = 0
            var premissonclickcount = 0
            switchWidgetSettingPremisson.setOnCheckedChangeListener { buttonView, isChecked ->
                imageclickCount++
                val textON = "ON\t"
                val textOFF = "OFF\t"
                if (isChecked) {
                    Preference_View.set_FragmentSetting_switchWidgetSettingPremissonisChecked(
                        isChecked, requireContext()
                    )

                    MainFragment.requestPermissionScore = 1

                    PreferenceinPermissonRequest.set_PermissionRequestScore(1, requireContext())

                    switchWidgetSettingPremisson.setText(textON)
                    Preference_View.set_FragmentSetting_switchWidgetSettingPremissonisText(
                        textON, requireContext()
                    )


                    if (imageclickCount <= 4)
                        Toast.makeText(
                            requireContext(),
                            R.string.FragmentSetting_switchWidgetSettingPremisson_On,
                            Toast.LENGTH_SHORT
                        ).show()

                } else {
                    Preference_View.set_FragmentSetting_switchWidgetSettingPremissonisChecked(
                        isChecked, requireContext()
                    )
                    MainFragment.requestPermissionScore = 2
                    PreferenceinPermissonRequest.set_PermissionRequestScore(2, requireContext())


                    switchWidgetSettingPremisson.setText(textOFF)
                    Preference_View.set_FragmentSetting_switchWidgetSettingPremissonisText(
                        textOFF, requireContext()
                    )

                    if (imageclickCount <= 4)
                        Toast.makeText(
                            context,
                            R.string.FragmentSetting_switchWidgetSettingPremisson_Off,
                            Toast.LENGTH_SHORT
                        ).show()
                }
            }

            // 사진 고정기능 조절 스위치
            switchWidgetSettingImageControl.setOnCheckedChangeListener { buttonView, isChecked ->
                premissonclickcount++
                val textON = "ON\t"
                val textOFF = "OFF\t"
                if (isChecked) {
                    Preference_View.set_FragmentSetting_switchWidgetSettingimageControlisChecked(
                        isChecked,
                        requireContext()
                    )
                    switchWidgetSettingImageControl.text = textON
                    Preference_View.set_FragmentSetting_switchWidgetSettingimageControlisText(
                        textON,
                        requireContext()
                    )

                    Preference_View.set_frameLayoutImageModeCheck_visibility(0x00000008, context!!)
                    if (premissonclickcount <= 4)

                        Toast.makeText(
                            context,
                            R.string.Fragment_Setting_switchWidgetSettingImageControl_On,
                            Toast.LENGTH_SHORT
                        ).show()
                } else {
                    Preference_View.set_FragmentSetting_switchWidgetSettingimageControlisChecked(
                        isChecked,
                        requireContext()
                    )
                    switchWidgetSettingImageControl.text = textOFF
                    Preference_View.set_FragmentSetting_switchWidgetSettingimageControlisText(
                        textOFF,
                        requireContext()
                    )

                    Preference_View.set_frameLayoutImageModeCheck_visibility(0x00000000, context!!)
                    if (premissonclickcount <= 4)

                        Toast.makeText(
                            context,
                            R.string.Fragment_Setting_switchWidgetSettingImageControl_Off,
                            Toast.LENGTH_SHORT
                        ).show()
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
}

