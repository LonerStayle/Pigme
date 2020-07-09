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
import androidx.preference.Preference
import com.example.wisesaying.databinding.FragmentAllListBinding
import com.example.wisesaying.db.entity.Pigme
import com.example.wisesaying.preference.PrefUsageMark
import com.example.wisesaying.view.constscore.UsageMark
import com.example.wisesaying.view.toast.toastShort

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



        buttonListDelete.setOnClickListener {


            delete(modelList)

        }
        buttonIndexToMove.setOnClickListener {

            if (PrefUsageMark.getInstance(requireContext()).deleteModelListOfIndex.size > 1)
                toastShort(requireContext(), R.string.toast_indexToMoveNegative)
            else
                toMove()
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

    private fun toMove() {
        /**
         * TODO: 아래구문 실패 팝백스택 미리 안정해져서 안먹힘 무브 기능 해결할 것
         */
        fragmentManager!!.popBackStack("main", 1)
    }

    private fun reset(modelList: List<Pigme>) {

        viewModel.listdelete(modelList)
        observerControl()
    }

    private fun delete(modelList: List<Pigme>) {
        val deleteIndex = PrefUsageMark.getInstance(requireContext()).deleteModelListOfIndex

        for (i in deleteIndex.indices) {
            viewModel.delete(modelList[deleteIndex[i]])
        }
        observerControl()
    }

    private fun restore() {
        val textings = resources.getStringArray(R.array.wise_Saying)
        val image = Array(100) { "" }
        for (i in textings.indices) {
            image[i] += (resources.getIdentifier(
                "a" + (1 + i),
                "drawable",
                activity!!.packageName
            ).toString())

            val pigmeList = listOf<Pigme>(Pigme(textings[i], image[i]))

            viewModel.listInsert(pigmeList)
            observerControl()
        }
    }

    private fun observerControl() {
        PrefUsageMark.getInstance(requireContext()).selfStoryUsageMark =
            UsageMark.ALL_LIST_USAGE_MARK

        PrefUsageMark.getInstance(requireContext()).deleteModelListOfIndex.clear()
    }

}


