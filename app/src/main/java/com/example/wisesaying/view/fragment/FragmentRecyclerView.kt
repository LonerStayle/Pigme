package com.example.wisesaying.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.wisesaying.R
import com.example.wisesaying.databinding.FragmentRecyclerViewBinding
import com.example.wisesaying.preference.PrefAllListAdapter
import com.example.wisesaying.preference.PrefModelList
import com.example.wisesaying.preference.PrefRequestPremisson
import com.example.wisesaying.view.adapter.RecyclerViewAllListAdapter

class FragmentRecyclerView : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = DataBindingUtil.inflate<FragmentRecyclerViewBinding>(
        inflater,
        R.layout.fragment_recycler_view, container, false
    ).run {

          recyclerview.adapter = RecyclerViewAllListAdapter(PrefModelList.getInstance(requireContext()).modelListPref)

        root
        }

    }


