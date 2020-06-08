package com.example.wisesaying.view.fragment

import android.content.Intent.getIntent
import android.content.Intent.getIntentOld
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.wisesaying.R
import com.example.wisesaying.databinding.FragmentSelfStoryImageSelectBinding
import com.example.wisesaying.db.PigmeDatabase
import com.example.wisesaying.preference.Preference_View
import com.example.wisesaying.view.activity.keyboardShow_Hide
import com.example.wisesaying.view.adapter.RecyclerView_ImageSelectAdapter
import com.example.wisesaying.viewmodel.PigmeViewModel
import com.example.wisesaying.viewmodel.PigmeViewModelFactory
import kotlinx.android.synthetic.main.fragment_self_story_image_select.*


class FragmentSelfStoryImageSelect : Fragment() {

    private val viewModel: PigmeViewModel by lazy {
        val pigmedatabase = PigmeDatabase.getInstance(requireContext())
        val factory = PigmeViewModelFactory(pigmedatabase.pigmeDao)
        ViewModelProvider(this, factory).get(PigmeViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return DataBindingUtil.inflate<FragmentSelfStoryImageSelectBinding>(
            inflater, R.layout.fragment_self_story_image_select, container,
            false
        ).run {

            pigmeViewModel = viewModel
     editTextImageSelectSelfStory.setText(arguments?.getString("selfStory"))

            val image = List<Int>(5) { 0 }.toMutableList()
            for (i in image.indices) {
                image[i] += resources.getIdentifier(
                    "a" + (i + 1),
                    "drawable",
                    "com.example.wisesaying"
                )
            }


            recyclerViewImageSelect.adapter = RecyclerView_ImageSelectAdapter(image)

            recyclerViewImageSelect.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            buttonNewSelfStoryaddFinish.setOnClickListener {
                keyboardShow_Hide(requireContext(), editTextImageSelectSelfStory)

                // 버튼 눌렀을 때만 selfMakingCount를 1로 설정
                // 메인 프레그먼트에서 updatedList.shuffled()가 적용되는 순간 사진 고정모드 자체도 먹히지 않음으로 이 순간만 1로 설정
                // 메인 프레그먼트 온크레이트 뷰에서 기본 0으로 설정
                MainFragment.selfStoryMakingCount = 1
                MainFragment.recyclerViewAdapterChange = 1
                pigmeViewModel!!.insert(editTextImageSelectSelfStory.text.toString())
                Preference_View.set_RecyclerViewAadapterChangeScore(1, context!!)

                Toast.makeText(
                    context,
                    "작성하신 글귀가 새롭게 추가 되었습니다. \n 셀프메이킹카운트: ${MainFragment.selfStoryMakingCount}",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

            root
        }
    }



}
