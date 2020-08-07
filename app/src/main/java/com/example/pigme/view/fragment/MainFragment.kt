package com.example.pigme.view.fragment

import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.pigme.R
import com.example.pigme.databinding.FragmentMainBinding
import com.example.pigme.db.entity.Pigme
import com.example.pigme.preference.PrefUsageMark
import com.example.pigme.preference.PrefViewPagerItem
import com.example.pigme.preference.PrefVisibility
import com.example.pigme.view.adapter.ViewPagerAdapter
import com.example.pigme.view.constscore.UsageMark
import com.example.pigme.view.viewbase.BaseFragment
import kotlinx.coroutines.*


class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {
    private var image: Array<String>? = null
    private val textings by lazy { resources.getStringArray(R.array.wise_Saying) }

    override fun FragmentMainBinding.setEventListener() {
        PrefUsageMark.getInstance(requireContext()).selfStoryUsageMark =
            (arguments?.getInt("positionToMove")) ?: UsageMark.STANDARD_OBSERVER_PATTERN

        // 앱 새로시작할때 마다 이미지 순서 랜덤인지 아닌지 확인 on일시 GONE
        textViewImageModeCheck.visibility =
            PrefVisibility.getInstance(requireContext()).textViewImageModeCheckVisibility

        fragmentLauncher(null, R.id.fregment_SettingLayout, FragmentSetting())

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

            val modelListShuffled = modelList.shuffled()
            viewPager.adapter = ViewPagerAdapter(modelListShuffled)
            viewModel.listInsert((viewPager.adapter as ViewPagerAdapter).modelList)

        }


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


            if (!fragmentSettingClick)
                fregmentSettingLayout.visibility = View.GONE
            else
                fregmentSettingLayout.visibility = View.VISIBLE

        }

        //플로팅 온오프 표시
        var floatingActionButtonSelfStoryToMoveOnoff = false
        imageButtonSelfStoryToMove.setOnClickListener {
            floatingActionButtonSelfStoryToMoveOnoff = !floatingActionButtonSelfStoryToMoveOnoff


            if (floatingActionButtonSelfStoryToMoveOnoff) {
                fregmentSettingLayout.visibility = View.GONE
                // 플로팅 버튼 킬때
                fragmentLauncher(
                    "main",
                    R.id.frameLayout_selfStoryFragment,
                    FragmentSelfStory()
                )
            } else
                fragmentManager!!.popBackStack("main", 1)

        }
    }

    override fun FragmentMainBinding.setViewModelInObserver() {
        viewModel.pigmeList.observe(viewLifecycleOwner, Observer { updatedList ->

            when (PrefUsageMark.getInstance(requireContext()).selfStoryUsageMark) {
                UsageMark.STANDARD_OBSERVER_PATTERN -> {

                    if (textViewImageModeCheck.visibility == View.VISIBLE) {

                        if (PrefUsageMark.getInstance(requireContext()).liveDataFirstUseTrace) {
                            PrefUsageMark.getInstance(requireContext()).liveDataFirstUseTrace =
                                false
                            viewPager.visibility = View.VISIBLE
                        } else {
                            viewPager.adapter = ViewPagerAdapter(updatedList.shuffled())


                            PrefUsageMark.getInstance(requireContext()).selfStoryUsageMark =
                                UsageMark.OBSERVER_IN_VIEW_MODEL_FINAL_WORK

                            viewModel.listInsert((viewPager.adapter as ViewPagerAdapter).modelList)
                        }
                    } else {

                        viewPager.adapter = ViewPagerAdapter(updatedList)

                        CoroutineScope(Dispatchers.Main).launch {
                            viewPager.currentItem =
                                PrefViewPagerItem.getInstance(requireContext()).currentViewpager
                            delay(200)
                            viewPager.visibility = View.VISIBLE
                        }

                    }
                }


                UsageMark.SELF_STORY_USAGE_MARK_INSERT -> {

                    (viewPager.adapter as ViewPagerAdapter).run {

                        (this.modelList as MutableList<Pigme>).add(
                            viewPager.currentItem,
                            updatedList.last()
                        )

                        notifyDataSetChanged()
                        PrefUsageMark.getInstance(requireContext()).selfStoryUsageMark =
                            UsageMark.OBSERVER_IN_VIEW_MODEL_FINAL_WORK
                        viewModel.listInsert(this.modelList)
                    }

                }
                UsageMark.SELF_STORY_USAGE_MARK_RESET_AFTER_INSERT -> {
                    (viewPager.adapter as ViewPagerAdapter).apply {

                        this.modelList = listOf(updatedList.last())

                        notifyDataSetChanged()

                        PrefUsageMark.getInstance(requireContext()).selfStoryUsageMark =
                            UsageMark.OBSERVER_IN_VIEW_MODEL_FINAL_WORK
                        val deleteList = updatedList as MutableList<Pigme>
                        deleteList.removeAt(updatedList.lastIndex)
                        viewModel.listdelete(deleteList)
                    }

                }
                UsageMark.SELF_STORY_USAGE_MARK_DELETE -> {
                    (viewPager.adapter as ViewPagerAdapter).apply {
                        this.modelList = updatedList
                        notifyDataSetChanged()

                        PrefUsageMark.getInstance(requireContext()).selfStoryUsageMark =
                            UsageMark.SELF_STORY_USAGE_MARK_INSERT

                        viewModel.insert(
                            PrefUsageMark.getInstance(requireContext())
                                .selfStoryDeleteAfterInsertDataText,
                            PrefUsageMark.getInstance(requireContext())
                                .selfStoryDeleteAfterInsertDataImage
                        )
                    }
                }

                UsageMark.ALL_LIST_USAGE_MARK -> {
                    (viewPager.adapter as ViewPagerAdapter).apply {
                        modelList = updatedList
                        notifyDataSetChanged()
                    }

                }
                UsageMark.ALL_LIST_INDEX_POSITION_TO_MOVE -> {
                    viewPager.adapter = ViewPagerAdapter(updatedList)

                    viewPager.currentItem =
                        PrefViewPagerItem.getInstance(requireContext()).currentViewpager

                    viewPager.visibility = View.VISIBLE
                    arguments?.remove("positionToMove")
                }
                UsageMark.OBSERVER_IN_VIEW_MODEL_FINAL_WORK -> {
                    viewPager.visibility = View.VISIBLE
                    return@Observer

                }
            }
        })
    }

    private fun fragmentLauncher(name: String?, layoutId: Int, fragment: Fragment) {

        // 플로팅 버튼 킬때
        val transactionReCreate = fragmentManager!!.beginTransaction()
        transactionReCreate.replace(
            layoutId,
            fragment
        )
            .addToBackStack(name)
            .commit()
    }

}



