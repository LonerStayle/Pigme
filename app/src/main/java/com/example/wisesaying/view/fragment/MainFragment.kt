package com.example.wisesaying.view.fragment

import android.content.Intent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import com.example.wisesaying.*
import com.example.wisesaying.databinding.FragmentMainBinding
import com.example.wisesaying.db.PigmeDatabase
import com.example.wisesaying.db.entity.Pigme
import com.example.wisesaying.preference.*

import com.example.wisesaying.view.adapter.ViewPagerAdapter
import com.example.wisesaying.viewmodel.PigmeViewModel
import com.example.wisesaying.viewmodel.PigmeViewModelFactory
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import java.util.Collections.*


class MainFragment : Fragment() {
    private var image: Array<String>? = null
    private val textings by lazy { resources.getStringArray(R.array.wise_Saying) }

    private val viewModel: PigmeViewModel by lazy {
        val pigmeDatabase = PigmeDatabase.getInstance(requireContext())
        val factory = PigmeViewModelFactory(pigmeDatabase.pigmeDao)
        ViewModelProvider(this, factory).get(PigmeViewModel::class.java)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = DataBindingUtil.inflate<FragmentMainBinding>(
        inflater,
        R.layout.fragment_main,
        container,
        false
    ).run {
        // 온크레이트 뷰가 다시 일어나면 0 으로
        PrefUsageMark.getInstance(requireContext()).selfStoryUsageMark = 0


        // 명언 추가 기능 사용 여부 on일시 GONE
        frameLayoutSelfstotyUsagemarks.visibility =
            PrefVisibility.getInstance(requireContext()).fremeLayoutSelfstoryUsagemarksVisibility

        // 앱 새로시작할때 마다 이미지 순서 랜덤인지 아닌지 확인 on일시 GONE
        frameLayoutImageModeCheck.visibility =
            PrefVisibility.getInstance(requireContext()).frameLayoutImageModeCheckVisibility

        val transaction = fragmentManager!!.beginTransaction()
        transaction.replace(
            R.id.fregment_SettingLayout,
            FragmentSetting()
        )
            .addToBackStack(null)
            .commit()

        if (PrefUsageMark.getInstance(requireContext()).liveDataFirstUseTrace) {
            image = Array(100) { "" }
            val modelList = mutableListOf<Pigme>()
            for (i in textings.indices) {
                image!![i] += (resources.getIdentifier(
                    "a" + (1 + i),
                    "drawable",
                    activity!!.packageName

                ).toString())
                modelList.add(Pigme(textings[i], image!![i]))
            }
            viewPager.visibility = View.GONE
            viewPager.adapter = ViewPagerAdapter(modelList.shuffled())
            viewModel.listInsert((viewPager.adapter as ViewPagerAdapter).modelList)
        }



        viewModel.pigmeList.observe(viewLifecycleOwner, Observer { updatedList ->
            if (PrefUsageMark.getInstance(requireContext()).selfStoryUsageMark == -1) {
                return@Observer

            } else if (PrefUsageMark.getInstance(requireContext()).selfStoryUsageMark == 0) {

                when {

                    frameLayoutImageModeCheck.visibility == View.VISIBLE &&
                            frameLayoutSelfstotyUsagemarks.visibility == View.VISIBLE -> {

                        if (PrefUsageMark.getInstance(requireContext()).liveDataFirstUseTrace) {
                            PrefUsageMark.getInstance(requireContext()).liveDataFirstUseTrace =
                                false
                            viewPager.visibility = View.VISIBLE
                        } else {
                            viewPager.adapter = ViewPagerAdapter(updatedList.shuffled())
                            PrefUsageMark.getInstance(requireContext()).selfStoryUsageMark = -1
                            viewModel.listInsert((viewPager.adapter as ViewPagerAdapter).modelList)
                        }
                    }

                    frameLayoutImageModeCheck.visibility == View.VISIBLE &&
                            frameLayoutSelfstotyUsagemarks.visibility == View.GONE -> {
                        viewPager.adapter = ViewPagerAdapter(updatedList.shuffled())

                        PrefUsageMark.getInstance(requireContext()).selfStoryUsageMark = -1
                        viewModel.listInsert((viewPager.adapter as ViewPagerAdapter).modelList)
                    }

                    frameLayoutImageModeCheck.visibility == View.GONE &&
                            frameLayoutSelfstotyUsagemarks.visibility == View.VISIBLE ||
                            frameLayoutImageModeCheck.visibility == View.GONE &&
                            frameLayoutSelfstotyUsagemarks.visibility == View.GONE -> {

                        viewPager.adapter = ViewPagerAdapter(updatedList)

                        /**
                         * TODO:매직넘버 - > 콜백함수로 바꾸는법 연구 좀더 공부가 필요함..
                         */
                        CoroutineScope(Dispatchers.Main).launch {
                            viewPager.visibility = View.GONE
                            viewPager.currentItem =
                                PrefViewPagerItem.getInstance(requireContext()).currentViewpager
                            delay(100)
                            viewPager.visibility = View.VISIBLE
                        }

                        PrefUsageMark.getInstance(requireContext()).selfStoryUsageMark = -1
                        viewModel.listInsert((viewPager.adapter as ViewPagerAdapter).modelList)
                    }
                }


                //명언 추가 했을때
            } else if (PrefUsageMark.getInstance(requireContext()).selfStoryUsageMark >= 1) {

                when (PrefUsageMark.getInstance(requireContext()).selfStoryUsageMark) {
                    1 -> {

                        (viewPager.adapter as ViewPagerAdapter).run {

                            (this.modelList as MutableList<Pigme>).add(
                                viewPager.currentItem,
                                updatedList.last()
                            )

                            visibilityGoneModeUse(frameLayoutSelfstotyUsagemarks)
                            notifyDataSetChanged()
                            PrefUsageMark.getInstance(requireContext()).selfStoryUsageMark = -1
                            viewModel.listInsert(this.modelList)
                        }
                        //명언 모두 초기화 후 하나만 생성했을 때
                    }
                    2 -> {
                        (viewPager.adapter as ViewPagerAdapter).apply {

                            this.modelList = listOf(updatedList.last())

                            visibilityGoneModeUse(frameLayoutSelfstotyUsagemarks)
                            notifyDataSetChanged()

                            PrefUsageMark.getInstance(requireContext()).selfStoryUsageMark = -1
                            val deleteList = updatedList as MutableList<Pigme>
                            deleteList.removeAt(updatedList.lastIndex)
                            viewModel.listdelete(deleteList)
                        }
                        //명언 부분 삭제후 하나만 생성했을 때
                    }
                    3 -> {
                        (viewPager.adapter as ViewPagerAdapter).apply {
                            this.modelList = updatedList
                            notifyDataSetChanged()

                            PrefUsageMark.getInstance(requireContext()).selfStoryUsageMark = 1
                            viewModel.insert(
                                PrefUsageMark.getInstance(requireContext()).selfStoryDeleteModeToInsertDataPassingEditTextImageSelectSelfStoryInText,
                                PrefUsageMark.getInstance(requireContext()).selfStoryDeleteModeToInsertDataPassingTextViewImageBackgroundResIdCheck
                            )
                        }
                    }
                }
            }
        })


        //공유 기능
        imageButtonShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "보낼 앱다운로드 페이지")
                val chooser = Intent.createChooser(intent, null)
                if (intent.resolveActivity(activity!!.packageManager) != null) {
                    startActivity(chooser)
                }
            }
        }

        //오른쪽 하단 설정창 보이기 설정
        var fragmentSettingClick = false
        buttonSetting.setOnClickListener {
            fragmentSettingClick = !fragmentSettingClick

            when {
                !fragmentSettingClick -> {
                    fregment_SettingLayout.visibility = View.GONE
                }
                fragmentSettingClick ->
                    fregment_SettingLayout.visibility = View.VISIBLE
            }
        }

        //플로팅 온오프 표시
        var floatingActionButtonSelfStoryToMoveOnoff = false
        floatingActionButtonSelfStoryToMove.setOnClickListener {
            floatingActionButtonSelfStoryToMoveOnoff = !floatingActionButtonSelfStoryToMoveOnoff

            when {
                floatingActionButtonSelfStoryToMoveOnoff -> {
                    fregmentSettingLayout.visibility = View.GONE
                    // 플로팅 버튼 킬때
                    val transactionReCreate = fragmentManager!!.beginTransaction()
                    transactionReCreate.replace(
                        R.id.frameLayout_selfStoryFragment,
                        FragmentSelfStory()
                    )
                        .addToBackStack("main")
                        .commit()
                }
                !floatingActionButtonSelfStoryToMoveOnoff -> {
                    fragmentManager!!.popBackStack("main", 1)

                }
            }
        }


        root
    }

    fun visibilityGoneModeUse(frameLayoutSelfstotyUsagemarks: FrameLayout) {
        frameLayoutSelfstotyUsagemarks.visibility = View.GONE
        PrefVisibility.getInstance(requireContext()).fremeLayoutSelfstoryUsagemarksVisibility =
            0x00000008
    }


}
