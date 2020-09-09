package com.example.pigme.view.fragment

import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.example.pigme.R
import com.example.pigme.view.adapter.RecyclerViewAllListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pigme.databinding.FragmentAllListBinding
import com.example.pigme.db.entity.Pigme
import com.example.pigme.preference.PrefUsageMark
import com.example.pigme.preference.PrefViewPagerItem
import com.example.pigme.view.constscore.UsageMark
import com.example.pigme.view.toast.toastShort
import com.example.pigme.view.viewbase.BaseFragment


class FragmentAllList : BaseFragment<FragmentAllListBinding>(R.layout.fragment_all_list) {


    override fun FragmentAllListBinding.setEventListener() {
        setButtonAllEvent()
    }

    private fun FragmentAllListBinding.setButtonAllEvent() {
        buttonIndexToMove.setOnClickListener {
            if (PrefUsageMark.getInstance(requireContext()).deleteModelListOfIndex.size > 1)
                context?.toastShort(R.string.toast_indexToMoveNegative)
            else
                toMove()
        }

        buttonListDelete.setOnClickListener {
            delete((recyclerview.adapter as RecyclerViewAllListAdapter).modelList, recyclerview)
        }


        buttonImageSave.setOnClickListener {
            saveImageInGallery()
        }

        buttonListRestore.setOnClickListener {
            restore()
        }
        buttonListReset.setOnClickListener {
            reset((recyclerview.adapter as RecyclerViewAllListAdapter).modelList)
        }
    }

    override fun FragmentAllListBinding.setViewModelInObserver() {
        viewModel.pigmeList.observe(viewLifecycleOwner, Observer {
            recyclerview.adapter = RecyclerViewAllListAdapter(context = requireContext())
            (recyclerview.adapter as RecyclerViewAllListAdapter).run {
                modelList = it
                notifyDataSetChanged()
            }
        })
    }





    private fun toMove() {

       if(PrefUsageMark.getInstance(requireContext()).deleteModelListOfIndex.isEmpty()) {
           context?.toastShort(R.string.allList_toMoveButtonFailText)
           return
       }
        PrefViewPagerItem.getInstance(requireContext()).currentViewpager =
            PrefUsageMark.getInstance(requireContext()).deleteModelListOfIndex.last()
        val mainFragment = MainFragment()
        mainFragment.arguments =
            bundleOf("positionToMove" to UsageMark.ALL_LIST_INDEX_POSITION_TO_MOVE)


        fragmentManager!!.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        val transaction = fragmentManager!!.beginTransaction()
            .remove(FragmentSetting())
            .replace(
                R.id.constraintLayout,
                mainFragment
            )
            .commit()

        selectIndexClear()
    }

    private fun reset(modelList: List<Pigme>) {

        viewModel.listdelete(modelList)
        observerControl()
        selectIndexClear()
    }

    private fun delete(modelList: List<Pigme>, recyclerview: RecyclerView) {


        for (i in PrefUsageMark.getInstance(requireContext()).deleteModelListOfIndex.indices) {
            viewModel.delete(modelList[PrefUsageMark.getInstance(requireContext()).deleteModelListOfIndex[i]])
        }
        (recyclerview.adapter as RecyclerViewAllListAdapter).run {
            saveListElement.clear()
        }
        observerControl()
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
            observerControl()
            selectIndexClear()
        }
    }

    private fun saveImageInGallery() {
        context?.toastShort(R.string.waitingText)
    }

    private fun observerControl() {
        PrefUsageMark.getInstance(requireContext()).selfStoryUsageMark =
            UsageMark.ALL_LIST_USAGE_MARK

    }

    private fun selectIndexClear() {
        PrefUsageMark.getInstance(requireContext()).deleteModelListOfIndex.clear()
    }

}


