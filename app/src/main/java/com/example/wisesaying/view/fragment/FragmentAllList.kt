package com.example.wisesaying.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.wisesaying.R
import com.example.wisesaying.db.PigmeDatabase
import com.example.wisesaying.view.adapter.RecyclerViewAllListAdapter
import com.example.wisesaying.viewmodel.PigmeViewModel
import com.example.wisesaying.viewmodel.PigmeViewModelFactory
import androidx.lifecycle.ViewModelProvider
import com.example.wisesaying.databinding.FragmentAllListBinding

class FragmentAllList : Fragment() {
private val viewModel:PigmeViewModel by lazy{
    val database = PigmeDatabase.getInstance(requireContext())
    val factory=PigmeViewModelFactory(database.pigmeDao)
    ViewModelProvider(this,factory).get(PigmeViewModel::class.java)
}



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = DataBindingUtil.inflate<FragmentAllListBinding>(
        inflater,
        R.layout.fragment_all_list, container, false
    ).run {

        viewModel.pigmeList.observe(viewLifecycleOwner, Observer {
            recyclerview.adapter = RecyclerViewAllListAdapter(it)
            (recyclerview.adapter as RecyclerViewAllListAdapter).notifyDataSetChanged()
        })
        /**
         * TODO: 각각 클릭리스너 적용 시킬것
         */
        buttonListAdd.setOnClickListener {

        }

        buttonListDelete.setOnClickListener {

        }

        buttonListReset.setOnClickListener {

        }

        buttonPickAndDelete.setOnClickListener {

        }
        buttonListRestore.setOnClickListener {

        }

        root

        }

    }


