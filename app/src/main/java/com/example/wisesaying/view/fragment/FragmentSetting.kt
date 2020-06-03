package com.example.wisesaying.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment

import com.example.wisesaying.Preference
import com.example.wisesaying.R

import com.example.wisesaying.databinding.FragmentSettingsBinding



class FragmentSetting : Fragment() {

    val preference = Preference()

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
            MainFragment.requestPermissionScore =
                preference.get_FragmentSetting_switchWidgetSettingPremisson(root, context!!)
            preference.get_FragmentSetting_switchWidgetSettingimageControl(root, context!!)


//TASK 조절 스위치
            var imageclickCount = 0
            var premissonclickcount = 0
            switchWidgetSettingPremisson.setOnCheckedChangeListener { buttonView, isChecked ->
imageclickCount++
                if (isChecked) {
                    switchWidgetSettingPremisson.setText("ON\t")
                    MainFragment.requestPermissionScore = 1
                    val on = "ON\t"
                    preference.set_FragmentSetting_switchWidgetSettingPremisson(
                        isChecked,
                        on,
                        1,
                        activity!!.applicationContext
                    )
                    preference.set_Permission(1,context!!)
                    if(imageclickCount <= 4)
                    Toast.makeText(
                        activity!!.applicationContext,
                        "스마트폰 잠금화면 해제 시 자동적으로 앱이 실행됩니다.",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    switchWidgetSettingPremisson.setText("OFF\t")
                    MainFragment.requestPermissionScore = 2
                    val off = "OFF\t"
                    preference.set_FragmentSetting_switchWidgetSettingPremisson(
                        isChecked,
                        off,
                        2,
                        context!!
                    )
                    preference.set_Permission(2,context!!)
                    if(imageclickCount <= 4)
                    Toast.makeText(
                        context,
                        "스마트폰 잠금화면 해제 시 자동적으로 앱이 실행되지 않습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            // 사진 고정기능 조절 스위치
            switchWidgetSettingImageControl.setOnCheckedChangeListener { buttonView, isChecked ->
               premissonclickcount++
                if (isChecked) {
                    switchWidgetSettingImageControl.text = "ON\t"

                    preference.set_FragmentSetting_switchWidgetSettingimageControl(
                        isChecked,
                        "ON\t",
                        1,
                        activity!!.applicationContext
                    )
                    if(premissonclickcount <= 4)
                    preference.set_frameLayoutImageModeCheck(0x00000008, context!!)
                    Toast.makeText(
                        activity!!.applicationContext,
                        "앱 재실행 시 마지막으로 본 사진을 불러옵니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    switchWidgetSettingImageControl.text = "OFF\t"

                    preference.set_FragmentSetting_switchWidgetSettingimageControl(
                        isChecked,
                        "OFF\t",
                        2,
                        context!!
                    )
                    if(premissonclickcount <= 4)
                    preference.set_frameLayoutImageModeCheck(0x00000000, context!!)
                    Toast.makeText(
                        context,
                        "앱 재실행 시 사진 순서들이 무작위로 바뀝니다.",
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

