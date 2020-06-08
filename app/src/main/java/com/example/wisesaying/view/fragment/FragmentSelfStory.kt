package com.example.wisesaying.view.fragment

import android.content.Context


import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.wisesaying.R
import com.example.wisesaying.databinding.FragmentSelfStoryBinding
import com.example.wisesaying.db.PigmeDatabase
import com.example.wisesaying.view.activity.keyboardShow_Hide
import com.example.wisesaying.viewmodel.PigmeViewModel
import com.example.wisesaying.viewmodel.PigmeViewModelFactory
import kotlinx.android.synthetic.main.fragment_self_story.*
import kotlinx.android.synthetic.main.fragment_self_story_image_select.*

class FragmentSelfStory : Fragment() {

    private val viewModel: PigmeViewModel by lazy {
        val pigmedatabase = PigmeDatabase.getInstance(requireContext())
        val factory = PigmeViewModelFactory(pigmedatabase.pigmeDao)
        ViewModelProvider(this, factory).get(PigmeViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = DataBindingUtil.inflate<FragmentSelfStoryBinding>(
        inflater, R.layout.fragment_self_story, container,
        false
    ).run {
        buttonFragementSelfStory.setOnClickListener {

            if (TextUtils.isEmpty(editTextSelfStory.text)) {
                Toast.makeText(context, "새로운 글을 입력해주세요", Toast.LENGTH_SHORT)
                    .show()

            } else {
                val bundle = Bundle()
                bundle.putString("selfStory", editTextSelfStory.text.toString())
                val fragmentSelfStoryImageSelect = FragmentSelfStoryImageSelect()
                fragmentSelfStoryImageSelect.arguments = bundle
                val fragment = fragmentManager!!.beginTransaction()
                fragment.replace(R.id.frameLayout_selfStoryFragment, fragmentSelfStoryImageSelect)
                    .addToBackStack(null)
                    .commit()

                keyboardShow_Hide(requireContext(), editTextSelfStory)
            }
        }


        root
    }


}


