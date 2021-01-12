package kr.loner.pigme.view.viewbase

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
import kr.loner.pigme.db.PigmeDatabase
import kr.loner.pigme.viewmodel.PigmeViewModel
import kr.loner.pigme.viewmodel.PigmeViewModelFactory
import kr.loner.pigme.viewmodel.SharedViewModel


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