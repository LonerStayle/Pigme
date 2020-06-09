package com.example.wisesaying.view.fragment

import android.content.Intent

import android.os.Bundle
import android.os.Handler
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
import com.example.wisesaying.preference.Preference_View
import com.example.wisesaying.preference.Preference_dataModel
import com.example.wisesaying.view.adapter.ViewPagerAdapter
import com.example.wisesaying.viewmodel.PigmeViewModel
import com.example.wisesaying.viewmodel.PigmeViewModelFactory
import kotlinx.android.synthetic.main.fragment_main.*



class MainFragment : Fragment() {
   private var image: Array<Int>?= null
    private val textings by lazy { resources.getStringArray(R.array.wise_Saying) }
    /** val stack = Stack<String>() */
    private val transaction by lazy { fragmentManager!!.beginTransaction() }

        private val viewModel: PigmeViewModel by lazy {
            val pigmedatabase = PigmeDatabase.getInstance(requireContext())
            val factory = PigmeViewModelFactory(pigmedatabase.pigmeDao)
            ViewModelProvider(this,factory).get(PigmeViewModel::class.java)

        }

     companion object  {
        //태스크 변경 권한 번호
        var requestPermissionScore= 0
        //셋팅 창 키는버튼 카운트
        var fragmentSettingClickCount = 0
        //셀프 명언 사용 했을때
        var selfStoryMakingCount = 0
      // 전체리스트 (리싸이클러뷰) 명언 추가 여부에 따른 화면 변경
      var recyclerViewAdapterChange = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = DataBindingUtil.inflate<FragmentMainBinding>(inflater, R.layout.fragment_main, container, false).run {

       // 온크레이트 뷰가 다시 일어나면 0 으로
        selfStoryMakingCount = 0

        //명언 하나라도 추가했을 때 set을 시켜놓았었음 get으로 다시 불러와 0,1에 따라 전체리스트 어댑터 변경
        recyclerViewAdapterChange = Preference_View.get_RecyclerViewAadapterChangeScore(requireContext())

        // 명언 추가 기능 사용 여부 on일시 GONE
        frameLayoutSelfstotyUsagemarks.visibility = Preference_View.get_fremeLayout_selfstory_Usagemarks_visibility(requireContext())


        // 앱 새로시작할때 마다 이미지 순서 랜덤인지 아닌지 확인 on일시 GONE
        frameLayoutImageModeCheck.visibility = Preference_View.get_frameLayoutImageModeCheck_visibility(requireContext())

        transaction.replace(

            R.id.fregment_SettingLayout,
            FragmentSetting()
        )
        transaction.addToBackStack(null)
        transaction.commit()


        // 이미지 사진들 배열에 담기
        image = Array(100,{0})
        for (i in image!!.indices) {
            image!![i] += resources.getIdentifier(
                "a" + (i + 1),
                "drawable",
                "com.example.wisesaying"
            )
        }

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

        // 사진과 문자 랜덤으로 출력하는 모드
         val modelListShuffledMode = modelList.shuffled()
        //사진 랜덤 출력 && 셀프명언 없을 때 **어플 최초의 실행시 뷰페이저의 기본 어댑터상태 **
        if (frameLayoutImageModeCheck.visibility == View.VISIBLE && frameLayoutSelfstotyUsagemarks.visibility == View.VISIBLE) {


            viewPager.adapter =
                ViewPagerAdapter(
                    modelListShuffledMode)

            recyclerViewAdapterChange = 0
                    Preference_dataModel.set_ModelListPref(
                context!!,
                (viewPager.adapter as ViewPagerAdapter).modelList
            )
        }
        //사진 랜덤 출력  && 셀프명언이 하나라도 존재 할때
        else if (frameLayoutImageModeCheck.visibility == View.VISIBLE && frameLayoutSelfstotyUsagemarks.visibility == View.GONE) {

            viewPager.adapter =
                ViewPagerAdapter(
                    Preference_dataModel.get_ModelListPrefSelfStory(
                        context!!
                    ).shuffled()
                )

            Preference_dataModel.set_ModelListPrefSelfStory(context!!,
                (viewPager.adapter as ViewPagerAdapter).modelList)
        }
        //사진 고정모드 && 셀프명언 없을때
        else if (frameLayoutImageModeCheck.visibility == View.GONE && frameLayoutSelfstotyUsagemarks.visibility == View.VISIBLE) {
            recyclerViewAdapterChange = 0
            viewPager.adapter =
                ViewPagerAdapter(
                    Preference_dataModel.get_ModelListPref(
                        context!!)
                )

            Preference_dataModel.set_ModelListPref( context!!,
                        (viewPager.adapter as ViewPagerAdapter).modelList)


            // 매직넘버 - > 콜백함수로 바꿀것
            viewPager.currentItem =  Preference_View.get_CurrentViewpager(context!!)
            Handler().postDelayed({
                viewPager.visibility = View.VISIBLE
            }, 180) }

        //사진 고정모드 && 셀프명언이 하나라도 존재 할때
        else if (frameLayoutImageModeCheck.visibility == View.GONE && frameLayoutSelfstotyUsagemarks.visibility == View.GONE) {
            viewPager.adapter =
                ViewPagerAdapter(
                    Preference_dataModel.get_ModelListPrefSelfStory(
                        context!!
                    )
                )
            Preference_dataModel.set_ModelListPrefSelfStory(
                context!!,
                (viewPager.adapter as ViewPagerAdapter).modelList)

            /**
             * 매직넘버 수정하기
             */

          viewPager.currentItem =  Preference_View.get_CurrentViewpager(context!!)
            Handler().postDelayed({
                viewPager.visibility = View.VISIBLE
            },180)
        }


        viewModel.pigmeList.observe(viewLifecycleOwner, Observer { updatedList  ->
            (viewPager.adapter as ViewPagerAdapter).apply {

              if (selfStoryMakingCount == 1&& frameLayoutSelfstotyUsagemarks.visibility == View.VISIBLE ) {
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
                  (this.modelList as MutableList<Pigme>).addAll(updatedList)
                 (viewPager.adapter as ViewPagerAdapter).modelList.shuffled()
                  Preference_dataModel.set_ModelListPrefSelfStory(context!!, this.modelList)
                frameLayoutSelfstotyUsagemarks.visibility = View.GONE
                  Preference_View.set_fremeLayout_selfstory_Usagemarks_visibility(context!!,0x00000008)
              }
                else if(selfStoryMakingCount == 1 && frameLayoutSelfstotyUsagemarks.visibility == View.GONE){
                  (this.modelList as MutableList<Pigme>).add(viewPager.currentItem,updatedList.last())
                  Preference_dataModel.set_ModelListPrefSelfStory(context!!, this.modelList)
              }

                notifyDataSetChanged()

            }
        })



        Toast.makeText(context,"현재페이지:${viewPager.currentItem},Self:$selfStoryMakingCount",Toast.LENGTH_SHORT).show()

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

        // fragmentSettingClickCount == 1 이 ON  == 2 는 OFF

        btnSetting.setOnClickListener {
            fragmentSettingClickCount++

            if (fragmentSettingClickCount >= 2) {
                fregment_SettingLayout.visibility = View.GONE
                fragmentSettingClickCount = 0

            } else if (fragmentSettingClickCount == 1) {
                fregment_SettingLayout.visibility = View.VISIBLE

            }
        }

        //플로팅 온오프 표시
        var floatingActionButtonOnoff = 0
        floatingActionButton.setOnClickListener {

            floatingActionButtonOnoff++

            val transaction3 = fragmentManager!!.beginTransaction()
            // 플로팅 버튼 킬때
            if (floatingActionButtonOnoff == 1) {


                transaction3.replace(
                    R.id.frameLayout_selfStoryFragment,
                    FragmentSelfStory()
                )
                transaction3.addToBackStack(null)


            } else if (floatingActionButtonOnoff == 2) {
                fragmentManager!!.popBackStack()
                floatingActionButtonOnoff = 0
            }
            transaction3.commit()
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

