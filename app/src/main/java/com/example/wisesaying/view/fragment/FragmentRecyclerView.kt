package com.example.wisesaying.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wisesaying.R
import com.example.wisesaying.databinding.FragmentRecyclerViewBinding
import com.example.wisesaying.db.PigmeDatabase
import com.example.wisesaying.preference.Preference_dataModel
import com.example.wisesaying.view.adapter.RecyclerViewAdapter
import com.example.wisesaying.viewmodel.PigmeViewModel
import com.example.wisesaying.viewmodel.PigmeViewModelFactory

class FragmentRecyclerView : Fragment() {
    private val viewModel: PigmeViewModel by lazy {
        val pigmedatabase = PigmeDatabase.getInstance(requireContext())
        val factory = PigmeViewModelFactory(pigmedatabase.pigmeDao)
        ViewModelProvider(this,factory).get(PigmeViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = DataBindingUtil.inflate<FragmentRecyclerViewBinding>(
        inflater,
        R.layout.fragment_recycler_view, container, false
    ).run {


        when(MainFragment.recyclerViewAdapterChange) {
            0-> recyclerview.adapter = RecyclerViewAdapter(Preference_dataModel.get_ModelListPref(context!!) )
            1-> recyclerview.adapter = RecyclerViewAdapter(Preference_dataModel.get_ModelListPrefSelfStory(context!!))
        }

        recyclerview.layoutManager = GridLayoutManager(requireContext(),2)
        root
    }
}

