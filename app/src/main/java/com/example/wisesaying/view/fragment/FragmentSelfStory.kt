package com.example.wisesaying.view.fragment

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.wisesaying.R
import com.example.wisesaying.databinding.FragmentSelfStoryBinding
import com.example.wisesaying.view.toast.toastShort
import com.example.wisesaying.view.viewbase.BaseFragment


class FragmentSelfStory : BaseFragment<FragmentSelfStoryBinding>(R.layout.fragment_self_story) {


    override fun FragmentSelfStoryBinding.setOnCreateView() {

        buttonFragementSelfStory.setOnClickListener {
            keyBoardShowHiding(requireContext(), editTextSelfStory)

            if (TextUtils.isEmpty(story)) {
                toastShort(context, R.string.toast_newWrite)
                return@setOnClickListener
            }

            val fragmentSelfStoryImageSelect = FragmentSelfStoryImageSelect()
            fragmentSelfStoryImageSelect.arguments = bundleOf(
                "selfStory" to story
                //,"selfstory2" to ""
            )

            val fragmentBegin = fragmentManager!!.beginTransaction()
                .replace(R.id.frameLayout_selfStoryFragment, fragmentSelfStoryImageSelect)
                .addToBackStack("selfStory")
                .commit()
        }
    }

    private fun keyBoardShowHiding(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


}


