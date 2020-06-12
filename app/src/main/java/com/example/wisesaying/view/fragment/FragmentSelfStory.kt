package com.example.wisesaying.view.fragment




import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.wisesaying.R
import com.example.wisesaying.databinding.FragmentSelfStoryBinding
import com.example.wisesaying.view.activity.keyboardShow_Hide


class FragmentSelfStory : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = DataBindingUtil.inflate<FragmentSelfStoryBinding>(
        inflater, R.layout.fragment_self_story, container,
        false
    ).run {
        buttonFragementSelfStory.setOnClickListener {

            if (TextUtils.isEmpty(story)) {
                Toast.makeText(context, "새로운 글을 입력해주세요", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val fragmentSelfStoryImageSelect = FragmentSelfStoryImageSelect()
            fragmentSelfStoryImageSelect.arguments = bundleOf(
                "selfStory" to story
                //,"selfstory2" to ""
            )
            val fragment = fragmentManager!!.beginTransaction()
            fragment.replace(R.id.frameLayout_selfStoryFragment, fragmentSelfStoryImageSelect)
                .addToBackStack("selfStory")
                .commit()

            keyboardShow_Hide(requireContext(), editTextSelfStory)

        }


        root
    }


}


