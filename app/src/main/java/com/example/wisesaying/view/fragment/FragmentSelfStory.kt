package com.example.wisesaying.view.fragment

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.wisesaying.R
import com.example.wisesaying.databinding.FragmentSelfStoryBinding


class FragmentSelfStory : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = DataBindingUtil.inflate<FragmentSelfStoryBinding>(
        inflater, R.layout.fragment_self_story, container,
        false
    ).run {
        buttonFragementSelfStory.setOnClickListener {
            keyBoardShowHiding(requireContext(), editTextSelfStory)

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
        }
        root
    }

    private fun keyBoardShowHiding(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}


