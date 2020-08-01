package com.example.wisesaying.view.viewbase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.wisesaying.db.PigmeDatabase
import com.example.wisesaying.viewmodel.PigmeViewModel
import com.example.wisesaying.viewmodel.PigmeViewModelFactory


abstract class BaseFragment<VDB : ViewDataBinding>(@LayoutRes private val layoutResId: Int) :
    Fragment() {

    protected val viewModel by viewModels<PigmeViewModel> {
        val pigmeDatabase = PigmeDatabase.getInstance(requireContext())
        val factory = PigmeViewModelFactory(pigmeDatabase.pigmeDao)
        factory}

    protected lateinit var binding: VDB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = DataBindingUtil.inflate<VDB>(inflater, layoutResId, container, false).run {

        lifecycleOwner = this@BaseFragment

        binding = this
        setOnCreateView()

        root
    }

    abstract fun VDB.setOnCreateView()
}