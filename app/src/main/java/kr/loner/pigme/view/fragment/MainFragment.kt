package kr.loner.pigme.view.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.*
import kr.loner.pigme.R
import kr.loner.pigme.databinding.FragmentMainBinding
import kr.loner.pigme.db.entity.Pigme
import kr.loner.pigme.preference.PrefUsageMark
import kr.loner.pigme.preference.PrefViewPagerItem
import kr.loner.pigme.view.adapter.ViewPagerAdapter
import kr.loner.pigme.view.constscore.UsageMark
import kr.loner.pigme.view.viewbase.BaseFragment


class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {
    private var image: Array<String>? = null
    private val textings by lazy { resources.getStringArray(R.array.wise_Saying) }

    override fun FragmentMainBinding.setOnCreateView() {
        setObserverSetting()
        setImageRandomCheck()
        setUpFragmentSetting()
        setUpFirstRunAdapter()
        setUpAdView()
    }

    private fun FragmentMainBinding.setUpAdView() {
        MobileAds.initialize(requireContext(), getString(R.string.adMob_app_id))
        adView.loadAd(AdRequest.Builder().build())
    }

    override fun FragmentMainBinding.setEventListener() {
        setImageButtonClickListener()
        setButtonSettingClickListener()
        setButtonSelfStroryToMoveClickListener()
    }

    private fun FragmentMainBinding.setButtonSelfStroryToMoveClickListener() {
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

    @SuppressLint("QueryPermissionsNeeded")
    private fun FragmentMainBinding.setImageButtonClickListener() {
        imageButtonShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.apply {
                type = "text/plain"
                putExtra(
                    Intent.EXTRA_TEXT,
                    "다른 다이어터에게도 앱을 추천해보세요!!\n"+"https://play.google.com/store/apps/details?id=kr.loner.pigme"
                )
                val chooser = Intent.createChooser(intent, null)
                if (intent.resolveActivity(activity!!.packageManager) != null) {
                    startActivity(chooser)
                }
            }
        }
    }

    private fun FragmentMainBinding.setButtonSettingClickListener() {
        //오른쪽 하단 설정창 보이기 설정
        var fragmentSettingClick = false
        buttonSetting.setOnClickListener {
            fragmentSettingClick = !fragmentSettingClick


            if (!fragmentSettingClick)
                fregmentSettingLayout.visibility = View.GONE
            else
                fregmentSettingLayout.visibility = View.VISIBLE

        }
    }

    private fun FragmentMainBinding.setImageRandomCheck() {
        sharedViewModel.visibleSetting.observe(requireActivity(), Observer {
            textViewImageModeCheck.visibility = it
        })
    }

    private fun setObserverSetting() {
        PrefUsageMark.getInstance(requireContext()).selfStoryUsageMark =
            (arguments?.getInt("positionToMove")) ?: UsageMark.STANDARD_OBSERVER_PATTERN
    }

    private fun setUpFragmentSetting() {
        fragmentLauncher(null, R.id.fregment_SettingLayout, FragmentSetting())
    }

    private fun FragmentMainBinding.setUpFirstRunAdapter() {
        if (PrefUsageMark.getInstance(requireContext()).liveDataFirstUseTrace) {

            image = Array(98) { "" }

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



