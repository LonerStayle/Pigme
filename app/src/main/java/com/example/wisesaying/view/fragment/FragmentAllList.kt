package com.example.wisesaying.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.wisesaying.R
import com.example.wisesaying.db.PigmeDatabase
import com.example.wisesaying.view.adapter.RecyclerViewAllListAdapter
import com.example.wisesaying.viewmodel.PigmeViewModel
import com.example.wisesaying.viewmodel.PigmeViewModelFactory
import androidx.lifecycle.ViewModelProvider
import com.example.wisesaying.databinding.FragmentAllListBinding
import com.example.wisesaying.db.entity.Pigme
import com.example.wisesaying.preference.PrefUsageMark
import com.example.wisesaying.preference.PrefViewPagerItem
import com.example.wisesaying.preference.PrefVisibility
import com.example.wisesaying.view.constscore.UsageMark
import com.example.wisesaying.view.toast.toastShort
import kotlinx.android.synthetic.main.fragment_main.*

class FragmentAllList : Fragment() {
    private val viewModel: PigmeViewModel by lazy {
        val database = PigmeDatabase.getInstance(requireContext())
        val factory = PigmeViewModelFactory(database.pigmeDao)
        ViewModelProvider(this, factory).get(PigmeViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = DataBindingUtil.inflate<FragmentAllListBinding>(
        inflater,
        R.layout.fragment_all_list, container, false
    ).run {

        viewModel.pigmeList.observe(viewLifecycleOwner, Observer {
            recyclerview.adapter = RecyclerViewAllListAdapter(it, requireContext())
            (recyclerview.adapter as RecyclerViewAllListAdapter).notifyDataSetChanged()
        })


        val modelList by lazy { (recyclerview.adapter as RecyclerViewAllListAdapter).modelList }


        buttonIndexToMove.setOnClickListener {

            if (PrefUsageMark.getInstance(requireContext()).deleteModelListOfIndex.size > 1)
                toastShort(requireContext(), R.string.toast_indexToMoveNegative)
            else
                toMove()
        }

        buttonListDelete.setOnClickListener {


            delete(modelList)

        }


        buttonListAdd.setOnClickListener {

        }


        buttonListRestore.setOnClickListener {

            viewModel.listdelete(modelList)
            restore()

        }
        buttonListReset.setOnClickListener {

            reset(modelList)
        }

        root

    }

    private val deleteIndex by lazy {
        PrefUsageMark.getInstance(requireContext()).deleteModelListOfIndex
    }

    private fun toMove() {

        PrefViewPagerItem.getInstance(requireContext()).currentViewpager = deleteIndex.last()

        val transaction = fragmentManager!!.beginTransaction()
        transaction.replace(
            R.id.constraintLayout,
            MainFragment()
        )
            .commit()

        observerControl(UsageMark.ALL_LIST_INDEX_POSITION_TO_MOVE)
        selectIndexClear()
    }

    private fun reset(modelList: List<Pigme>) {

        viewModel.listdelete(modelList)
        observerControl(UsageMark.ALL_LIST_USAGE_MARK)
        selectIndexClear()
    }

    private fun delete(modelList: List<Pigme>) {

        for (i in deleteIndex.indices) {
            viewModel.delete(modelList[deleteIndex[i]])
        }
        observerControl(UsageMark.ALL_LIST_USAGE_MARK)
        selectIndexClear()
    }

    private fun restore() {
        val textBox = resources.getStringArray(R.array.wise_Saying)
        val image = Array(100) { "" }
        for (i in textBox.indices) {
            image[i] += (resources.getIdentifier(
                "a" + (1 + i),
                "drawable",
                activity!!.packageName
            ).toString())

            val pigmeList = listOf(Pigme(textBox[i], image[i]))

            viewModel.listInsert(pigmeList)
            observerControl(UsageMark.ALL_LIST_USAGE_MARK)
            selectIndexClear()
        }
    }

    private fun observerControl(control:Int) {
        PrefUsageMark.getInstance(requireContext()).selfStoryUsageMark =
            control

    }

    private fun selectIndexClear() {
        PrefUsageMark.getInstance(requireContext()).deleteModelListOfIndex.clear()
    }
}


