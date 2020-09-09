package com.example.pigme.view.viewbase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.pigme.db.PigmeDatabase
import com.example.pigme.viewmodel.PigmeViewModel
import com.example.pigme.viewmodel.PigmeViewModelFactory
import com.example.pigme.viewmodel.SharedViewModel
import androidx.lifecycle.ViewModelProvider


abstract class BaseFragment<VDB : ViewDataBinding>(@LayoutRes private val layoutResId: Int) :
    Fragment() {

    protected val viewModel by viewModels<PigmeViewModel> {
        val pigmeDatabase = PigmeDatabase.getInstance(requireContext())
        val factory = PigmeViewModelFactory(pigmeDatabase.pigmeDao)
        factory
    }

    protected val sharedViewModel by lazy{
        ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

    }

    protected lateinit var binding: VDB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = DataBindingUtil.inflate<VDB>(inflater, layoutResId, container, false).run {

        lifecycleOwner = this@BaseFragment
        binding = this
        setOnCreateView()
        setEventListener()
        setViewModelInObserver()

        root
    }

    open fun VDB.setOnCreateView() = Unit
    abstract fun VDB.setEventListener()
    open fun VDB.setViewModelInObserver() = Unit
}