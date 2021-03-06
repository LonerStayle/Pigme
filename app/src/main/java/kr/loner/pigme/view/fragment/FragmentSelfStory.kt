package kr.loner.pigme.view.fragment

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import kr.loner.pigme.R
import kr.loner.pigme.databinding.FragmentSelfStoryBinding
import kr.loner.pigme.view.toast.toastShort
import kr.loner.pigme.view.viewbase.BaseFragment


class FragmentSelfStory : BaseFragment<FragmentSelfStoryBinding>(R.layout.fragment_self_story) {


    override fun FragmentSelfStoryBinding.setEventListener() {
        buttonClickListener()
    }

    private fun FragmentSelfStoryBinding.buttonClickListener() {
        buttonFragementSelfStory.setOnClickListener {
            keyBoardShowHiding(requireContext(), editTextSelfStory)

            if (TextUtils.isEmpty(story)) {
                context?.toastShort(R.string.toast_newWrite)
                return@setOnClickListener
            }

            val fragmentSelfStoryImageSelect = FragmentSelfStoryImageSelect()

            fragmentSelfStoryImageSelect.arguments = bundleOf(
                "selfStory" to story
                //,"selfstory2" to ""
            )

            val fragmentBegin = fragmentManager!!.beginTransaction()
                .replace(R.id.frameLayout_selfStoryFragment, fragmentSelfStoryImageSelect)
                .addToBackStack("selfStory")
                .commit()
        }
    }


    private fun keyBoardShowHiding(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}


