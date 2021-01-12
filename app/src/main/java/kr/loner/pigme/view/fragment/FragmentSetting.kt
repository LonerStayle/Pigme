package kr.loner.pigme.view.fragment

import android.view.View

import kr.loner.pigme.R
import kr.loner.pigme.databinding.FragmentSettingsBinding
import kr.loner.pigme.preference.PrefFragmentSetting
import kr.loner.pigme.preference.PrefRequestPremisson
import kr.loner.pigme.view.constscore.UsageMark
import kr.loner.pigme.view.toast.toastShort
import kr.loner.pigme.view.viewbase.BaseFragment


class FragmentSetting() : BaseFragment<FragmentSettingsBinding>(R.layout.fragment_settings) {

    override fun FragmentSettingsBinding.setOnCreateView() {
        setSwitchWidgetSettingPremisson()
        setSwitchWidgetSettingImageControl()
    }

    override fun FragmentSettingsBinding.setEventListener() {

        val triple = setSwitchWidgetSettingPremissonSelectEvent()
        val textON = triple.first
        val textOFF = triple.second
        val clickCount = triple.third

        // 사진 고정기능 조절 스위치
        setSwitchWidgetSettingImageControlSelectEvent(clickCount, textON, textOFF)
        setButtonAllListClickListener()
    }

    private fun FragmentSettingsBinding.setButtonAllListClickListener() {
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

    private fun FragmentSettingsBinding.setSwitchWidgetSettingImageControlSelectEvent(
        clickCount: Int,
        textON: String,
        textOFF: String
    ) {
        var clickCount1 = clickCount
        switchWidgetSettingImageControl.setOnCheckedChangeListener { _, isChecked ->
            clickCount1++

            when (isChecked) {
                true -> {
                    PrefFragmentSetting.getInstance(requireContext()).fragmentSettingSwitchWidgetSettingImageControlisChecked =
                        isChecked
                    switchWidgetSettingImageControl.text = textON
                    PrefFragmentSetting.getInstance(requireContext()).fragmentSettingSwitchWidgetSettingImageControlText =
                        textON
                    sharedViewModel.visibleChange( View.INVISIBLE)


                    if (clickCount1 <= 4)
                        context?.toastShort(
                            R.string.Fragment_Setting_switchWidgetSettingImageControl_On
                        )
                }
                false -> {
                    PrefFragmentSetting.getInstance(requireContext()).fragmentSettingSwitchWidgetSettingImageControlisChecked =
                        isChecked

                    switchWidgetSettingImageControl.text = textOFF
                    PrefFragmentSetting.getInstance(requireContext()).fragmentSettingSwitchWidgetSettingImageControlText =
                        textOFF
                    sharedViewModel.visibleChange(View.VISIBLE)

                    if (clickCount1 <= 4)

                        context?.toastShort(
                            R.string.Fragment_Setting_switchWidgetSettingImageControl_Off
                        )
                }
            }
        }
    }

    private fun FragmentSettingsBinding.setSwitchWidgetSettingPremissonSelectEvent(): Triple<String, String, Int> {
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
                        context?.toastShort(
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
                        context?.toastShort(
                            R.string.FragmentSetting_switchWidgetSettingPremisson_Off
                        )
                }
            }
        }
        return Triple(textON, textOFF, clickCount)
    }

    private fun FragmentSettingsBinding.setSwitchWidgetSettingImageControl() {
        switchWidgetSettingImageControl.apply {
            isChecked =
                PrefFragmentSetting.getInstance(requireContext()).fragmentSettingSwitchWidgetSettingImageControlisChecked
            text =
                PrefFragmentSetting.getInstance(requireContext()).fragmentSettingSwitchWidgetSettingImageControlText
        }
    }

    private fun FragmentSettingsBinding.setSwitchWidgetSettingPremisson() {
        switchWidgetSettingPremisson.apply {
            isChecked =
                PrefFragmentSetting.getInstance(requireContext()).fragmentSettingSwitchWidgetSettingPremissonisChecked
            text =
                PrefFragmentSetting.getInstance(requireContext()).fragmentSettingSwitchWidgetSettingPremissonText
        }
    }

}


