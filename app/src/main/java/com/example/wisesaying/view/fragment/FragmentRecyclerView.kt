package com.example.wisesaying.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.wisesaying.Preference
import com.example.wisesaying.R
import com.example.wisesaying.databinding.FragmentRecyclerViewBinding
import com.example.wisesaying.db.PigmeDatabase
import com.example.wisesaying.view.adapter.RecyclerViewAdapter
import com.example.wisesaying.viewmodel.PigmeViewModel
import com.example.wisesaying.viewmodel.PigmeViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
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

        val preference = Preference()
        when(MainFragment.recyclerViewAdapterChange) {
            0-> recyclerview.adapter = RecyclerViewAdapter(preference.get_ModelListPref(context!!) )
            1-> recyclerview.adapter = RecyclerViewAdapter(preference.get_ModelListPrefSelfStory(context!!))
        }

        recyclerview.layoutManager = GridLayoutManager(requireContext(),2)
        root
    }
}

