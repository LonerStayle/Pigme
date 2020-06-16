package com.example.wisesaying.view.fragment

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import com.example.wisesaying.*
import com.example.wisesaying.databinding.FragmentMainBinding
import com.example.wisesaying.db.PigmeDatabase
import com.example.wisesaying.db.entity.Pigme
import com.example.wisesaying.preference.PrefSingleton

import com.example.wisesaying.view.adapter.ViewPagerAdapter
import com.example.wisesaying.viewmodel.PigmeViewModel
import com.example.wisesaying.viewmodel.PigmeViewModelFactory
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainFragment : Fragment() {
    private var image: Array<String>? = null
    private val textings by lazy { resources.getStringArray(R.array.wise_Saying) }

    /** val stack = Stack<String>() */


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
        PrefSingleton.getInstance(requireContext()).selfStoryUsageMark = 0

        // 명언 추가 기능 사용 여부 on일시 GONE
        frameLayoutSelfstotyUsagemarks.visibility =
            PrefSingleton.getInstance(requireContext()).fremeLayoutSelfstoryUsagemarksVisibility


        // 앱 새로시작할때 마다 이미지 순서 랜덤인지 아닌지 확인 on일시 GONE
        frameLayoutImageModeCheck.visibility =
            PrefSingleton.getInstance(requireContext()).frameLayoutImageModeCheckVisibility

        val transaction = fragmentManager!!.beginTransaction()
        transaction.replace(
            R.id.fregment_SettingLayout,
            FragmentSetting()
        )
            .addToBackStack(null)
            .commit()


        // 이미지 사진들 배열에 담기
        image = Array(100) { "" }
        for (i in image!!.indices) {
            image!![i] += resources.getIdentifier("a" + (1 + i), "drawable", activity!!.packageName)
                .toString()
        }

        Toast.makeText(context, image!![0], Toast.LENGTH_SHORT).show()


        //viewPagerModel에 배열에 사진과 명언 글귀 모두 담기
        val modelList = mutableListOf<Pigme>()
        for (i in textings.indices) {
            modelList.add(
                Pigme(
                    textings[i],
                    image!![i]
                )
            )
        }



        when {
            /**
             * frameLayoutImageModeCheck ->VISIBLE  사진 랜덤으로 생성 ,
             * frameLayoutSelfstotyUsagemarks->VISIBLE 새로 추가된 명언 글귀가 한개라도 없는 상태
             * frameLayoutImageModeCheck ->GONE   사진 고정생성 모드,
             * frameLayoutSelfstotyUsagemarks-> GONE 새롭게 추가된 글귀가 하나라도 존재할 때
             */
            frameLayoutImageModeCheck.visibility == View.VISIBLE && frameLayoutSelfstotyUsagemarks.visibility == View.VISIBLE -> {

                viewPager.adapter =
                    ViewPagerAdapter(
                        modelList.shuffled()
                    )

                PrefSingleton.getInstance(requireContext()).RecyclerViewAadapterChangeScore = 0

                PrefSingleton.getInstance(requireContext()).modelListPref =
                    (viewPager.adapter as ViewPagerAdapter).modelList
            }

            frameLayoutImageModeCheck.visibility == View.VISIBLE && frameLayoutSelfstotyUsagemarks.visibility == View.GONE -> {

                viewPager.adapter =
                    ViewPagerAdapter(
                        PrefSingleton.getInstance(requireContext()).modelListPrefSelfStory.shuffled()
                    )


                PrefSingleton.getInstance(requireContext()).modelListPrefSelfStory =
                    (viewPager.adapter as ViewPagerAdapter).modelList
            }

            frameLayoutImageModeCheck.visibility == View.GONE && frameLayoutSelfstotyUsagemarks.visibility == View.VISIBLE -> {
                PrefSingleton.getInstance(requireContext()).RecyclerViewAadapterChangeScore = 0

                viewPager.adapter =
                    ViewPagerAdapter(
                        PrefSingleton.getInstance(requireContext()).modelListPref
                    )

                PrefSingleton.getInstance(requireContext()).modelListPref =
                    (viewPager.adapter as ViewPagerAdapter).modelList

                /**
                 * TODO:매직넘버 - > 콜백함수로 바꾸는법 연구 좀더 공부가 필요함..
                 */
                CoroutineScope(Dispatchers.Main).launch {
                    viewPager.visibility = View.GONE
                    viewPager.currentItem =
                        PrefSingleton.getInstance(requireContext()).currentViewpager
                    delay(100)
                    viewPager.visibility = View.VISIBLE
                }
            }

            frameLayoutImageModeCheck.visibility == View.GONE && frameLayoutSelfstotyUsagemarks.visibility == View.GONE -> {

                viewPager.adapter =
                    ViewPagerAdapter(
                        PrefSingleton.getInstance(requireContext()).modelListPrefSelfStory
                    )

                PrefSingleton.getInstance(requireContext()).modelListPrefSelfStory =
                    (viewPager.adapter as ViewPagerAdapter).modelList



                CoroutineScope(Dispatchers.Main).launch {
                    viewPager.visibility = View.GONE
                    viewPager.currentItem =
                        PrefSingleton.getInstance(requireContext()).currentViewpager
                    delay(100)
                    viewPager.visibility = View.VISIBLE
                }

            }

        }


        viewModel.pigmeList.observe(viewLifecycleOwner, Observer { updatedList ->
            (viewPager.adapter as ViewPagerAdapter).apply {

                when {
                    PrefSingleton.getInstance(requireContext()).selfStoryUsageMark == 1 && frameLayoutSelfstotyUsagemarks.visibility == View.VISIBLE -> {
                        (this.modelList as MutableList<Pigme>).addAll(
                            viewPager.currentItem,
                            updatedList
                        )
                        frameLayoutSelfstotyUsagemarks.visibility = View.GONE
                        PrefSingleton.getInstance(requireContext()).fremeLayoutSelfstoryUsagemarksVisibility =
                            0x00000008
                    }

                    PrefSingleton.getInstance(requireContext()).selfStoryUsageMark == 1 && frameLayoutSelfstotyUsagemarks.visibility == View.GONE -> {
                        (this.modelList as MutableList<Pigme>).add(
                            viewPager.currentItem,
                            updatedList.last()
                        )
                    }
                    PrefSingleton.getInstance(requireContext()).selfStoryUsageMark == 2 -> {
                        this.modelList = listOf(updatedList.last())
                        (this.modelList as MutableList<Pigme>).reverse()
                        frameLayoutSelfstotyUsagemarks.visibility = View.GONE
                        PrefSingleton.getInstance(requireContext()).fremeLayoutSelfstoryUsagemarksVisibility =
                            0x00000008
                    }
                }
                PrefSingleton.getInstance(requireContext()).modelListPrefSelfStory = this.modelList
                notifyDataSetChanged()
                /**
                 *  랜덤으로 돌리고자 한다면 .
                val randomIndex = Random().nextInt(this.modelList.lastIndex+1)
                (this.modelList as MutableList<Pigme>).add(randomIndex,updatedList.last())
                마지막 인덱스에서 생성은
                (this.modelList as MutableList<Pigme>).add(updatedList.last())
                첫번째 인덱스에서 생성은
                (this.modelList as MutableList<Pigme>).add(0,updatedList.last())
                마지막으로 본화면에서 생성은
                (this.modelList as MutableList<Pigme>).add(viewPager.currentItem,updatedList.last())
                 */

            }
        })


        //   Toast.makeText( context, "현재페이지:${viewPager.currentItem},Self:${PrefSingleton.getInstance(requireContext()).selfStoryUsageMark}", Toast.LENGTH_SHORT ).show()

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

}


//이전 기능들
/**텍스트 뷰 리소 랜덤 뽑기
display(textings[Random().nextInt(textings.size)])
imageandtext()*/

/**맨 첫화면 스택에 넣기
stack.push(textView_story.text.toString())*/


/**   //이전 버튼 or 스택에서 꺼내기
actionPrevButton.setOnClickListener {
if (stack.isEmpty()) {
Toast.makeText(applicationContext, R.string.FinalStack, Toast.LENGTH_SHORT).show()
} else {
display(stack.pop())
imageandtext()
}
}
//다음 버튼 or 스택에 넣기
actionNextButton.setOnClickListener {
stack.push(textView_story.text.toString())
display(textings[Random().nextInt(textings.size)])
imageandtext()
}

fun imageandeText() {
for (i in image!!.indices) {
if(textView_story.text == textings[i])
imageView.setImageResource(image!![i])
}
}
fun display(textings:String) {
textView_story.text = textings
}
 */

