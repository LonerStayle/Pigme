package com.example.wisesaying.view.fragment


import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.wisesaying.R
import com.example.wisesaying.databinding.FragmentSelfStoryBinding
import com.example.wisesaying.viewmodel.PigmeViewModel
import com.example.wisesaying.viewmodel.PigmeViewModelFactory
import com.example.wisesaying.db.PigmeDatabase
import com.example.wisesaying.preference.Preference_View

class FragmentSelfStory : Fragment() {

    private val viewModel: PigmeViewModel by lazy {
        val pigmedatabase = PigmeDatabase.getInstance(requireContext())
        val factory = PigmeViewModelFactory(pigmedatabase.pigmeDao)
    ViewModelProvider(this,factory).get(PigmeViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = DataBindingUtil.inflate<FragmentSelfStoryBinding>(
        inflater, R.layout.fragment_self_story, container,
        false
    ).run {


        vm = viewModel
        buttonFragementSelfStory.setOnClickListener {

            if (TextUtils.isEmpty(editTextBody.text)) {
                Toast.makeText(context, "새로운 글을 입력해주세요", Toast.LENGTH_SHORT)
                    .show()

            } else {
                
                // 버튼 눌렀을 때만 selfMakingCount를 1로 설정
                // 메인 프레그먼트에서 updatedList.shuffled()가 적용되는 순간 사진 고정모드 자체도 먹히지 않음으로 이 순간만 1로 설정
                // 메인 프레그먼트 온크레이트 뷰에서 기본 0으로 설정
                 MainFragment.selfStoryMakingCount = 1

                vm!!.insert(story!!)
                Preference_View.set_RecyclerViewAadapterChangeScore(1,context!!)
                MainFragment.recyclerViewAdapterChange = 1
                Toast.makeText(context, "작성하신 글이 새롭게 추가 되었습니다. \n 셀프메이킹카운트: ${MainFragment.selfStoryMakingCount}", Toast.LENGTH_SHORT)
                    .show()
                story =""

            }
        }

        root
    }

}

