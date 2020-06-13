package com.example.wisesaying.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.wisesaying.R
import com.example.wisesaying.databinding.FragmentRecyclerViewBinding
import com.example.wisesaying.preference.PrefSingleton
import com.example.wisesaying.view.adapter.RecyclerViewSelfStoryAdapter

class FragmentRecyclerView : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = DataBindingUtil.inflate<FragmentRecyclerViewBinding>(
        inflater,
        R.layout.fragment_recycler_view, container, false
    ).run {


        when(PrefSingleton.getInstance(requireContext()).RecyclerViewAadapterChangeScore) {
            0-> recyclerview.adapter = RecyclerViewSelfStoryAdapter(PrefSingleton.getInstance(requireContext()).modelListPref)
            1-> recyclerview.adapter = RecyclerViewSelfStoryAdapter(PrefSingleton.getInstance(requireContext()).modelListPrefSelfStory)
        }
        root
    }
}

