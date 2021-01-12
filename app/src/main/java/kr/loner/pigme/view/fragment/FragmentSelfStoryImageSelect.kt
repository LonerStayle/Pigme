package kr.loner.pigme.view.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.provider.MediaStore
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.RadioButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.dialog_dialogindialog_deletelist.*
import kotlinx.android.synthetic.main.dialog_self_story_image_select_buttonevent.*
import kotlinx.android.synthetic.main.fragment_self_story_image_select.*
import kr.loner.pigme.R
import kr.loner.pigme.databinding.FragmentSelfStoryImageSelectBinding
import kr.loner.pigme.db.entity.GalleyImage
import kr.loner.pigme.preference.PrefUsageMark
import kr.loner.pigme.view.adapter.RecyclerViewDialogInDialogAdapter
import kr.loner.pigme.view.adapter.RecyclerViewImageSelectAdapter
import kr.loner.pigme.view.constscore.UsageMark
import kr.loner.pigme.view.dialog.DialogInLayoutCreateMode
import kr.loner.pigme.view.dialog.DialogSimple
import kr.loner.pigme.view.imageurl.imageUrl
import kr.loner.pigme.view.toast.toastShort
import kr.loner.pigme.view.viewbase.BaseFragment

class FragmentSelfStoryImageSelect :
    BaseFragment<FragmentSelfStoryImageSelectBinding>(R.layout.fragment_self_story_image_select) {

    private val animationButtonGallery by lazy {
        AnimationUtils.loadAnimation(
            requireActivity(),
            R.anim.animation_fragmentselfstoryimageselect_button_gallery
        )
    }

    private val REQUEST_EXTERNAL_STORAGE_PREMISSON = 1002
    private val REQUEST_IMAGE_CODE = 1001

    private val dialogImageSelectMode by lazy { DialogInLayoutCreateMode(requireContext()) }
    private val dialogDeleteMode by lazy { dialogImageSelectMode.dialogInImageDeleteDialog }


    override fun FragmentSelfStoryImageSelectBinding.setOnCreateView() {
        val image = setData()
        setRecyclerViewAdapter(image)
        setExampleImageSetting(image)
    }
    override fun FragmentSelfStoryImageSelectBinding.setEventListener() {
        setButtonNewImageSelect()
        setFabActionButtonClickListener()
    }

    private fun FragmentSelfStoryImageSelectBinding.setData(): MutableList<GalleyImage> {
        story = (arguments?.getString("selfStory"))
        textViewGalleryguide.startAnimation(animationButtonGallery)

        val image = List<GalleyImage>(5) { GalleyImage("") }
            .toMutableList()
        for (i in image.indices) {
            image[i].galleryImage += resources.getIdentifier(
                "a" + (i + 1),
                "drawable",
                "com.example.pigme"
            ).toString()
        }
        return image
    }

    private fun FragmentSelfStoryImageSelectBinding.setExampleImageSetting(
        image: MutableList<GalleyImage>
    ) {
        viewModel.pigmeImageSelectVersion.observe(viewLifecycleOwner, Observer {
            (recyclerViewImageSelectInExampleImage.adapter as RecyclerViewImageSelectAdapter).run {
                if (it.isEmpty()) {
                    for (i in image.indices)
                        viewModel.galleyNewImageinsert(image[i].galleryImage)
                }
                imageSampleList = it as MutableList<GalleyImage>
                imageSampleList.reverse()
                notifyDataSetChanged()
            }
        })
    }

    private fun FragmentSelfStoryImageSelectBinding.setRecyclerViewAdapter(
        image: MutableList<GalleyImage>
    ) {
        recyclerViewImageSelectInExampleImage.adapter =
            RecyclerViewImageSelectAdapter(image) {
                Glide.with(imageViewBackgroundImage.context).load(imageUrl(it!!))
                    .into(imageViewBackgroundImage)
                textViewImageBackgroundResIdCheck.text = imageUrl(it)
                textViewGalleryguide.clearAnimation()
                textViewGalleryguide.visibility = View.GONE
            }
        recyclerViewImageSelectInExampleImage.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun FragmentSelfStoryImageSelectBinding.setButtonNewImageSelect() {
        buttonNewSelefStroyImageSelect.setOnClickListener {

            if (textViewImageBackgroundResIdCheck.text == "") {
                context?.toastShort(
                    R.string.toast_selfStroyNoImageSelectText
                )
                return@setOnClickListener
            }
            dialogImageSelectMode.dialogImageSelectBuilderSetting(dialogImageSelectMode)
            val dialog = dialogImageSelectMode.dialogImageSelect
            dialog.show()

            //다이얼로그 창 안에서 3가지 버튼 클릭 상태에 따른 폰트 변화

            dialog.radioGruop_imageSelect_Mode.setOnCheckedChangeListener { _, checkedId ->

                dialog.findViewById<RadioButton>(checkedId).run {

                    dialog.radiobutton_option1.setTypeface(null, Typeface.NORMAL)
                    dialog.radiobutton_option2.setTypeface(null, Typeface.NORMAL)
                    dialog.radiobutton_option3.setTypeface(null, Typeface.NORMAL)

                    setTypeface(null, Typeface.BOLD)
                }

            }

            dialog.button_newSelfStoryaddFinish.setOnClickListener {

                when (dialog.radioGruop_imageSelect_Mode.checkedRadioButtonId) {
                    R.id.radiobutton_option1 -> {
                        DialogSimple.show(
                            requireContext(),
                            R.string.dialogResetAfterImageSelectInTitle,
                            R.string.dialogResetAfterImageSelectInMessages,
                            R.string.dialogResetAfterImageSelectInPositiveText,
                            {
                                viewModel.insert(
                                    editTextImageSelectSelfStoryInText.text.toString(),
                                    textViewImageBackgroundResIdCheck.text.toString()
                                )

                                selfStoryObserverControl(UsageMark.SELF_STORY_USAGE_MARK_RESET_AFTER_INSERT)

                                fragmentManager!!.popBackStack("main", 1)
                                context?.toastShort(R.string.toast_resetAfterInsert)
                            },
                            R.string.dialogResetAfterImageSelectInNegativeText,
                            { return@show }
                        )
                    }
                    R.id.radiobutton_option2 -> {

                        viewModel.insert(
                            editTextImageSelectSelfStoryInText.text.toString(),
                            textViewImageBackgroundResIdCheck.text.toString()
                        )

                        context?.toastShort(R.string.toast_newSelfStory)
                        selfStoryObserverControl(UsageMark.SELF_STORY_USAGE_MARK_INSERT)
                        fragmentManager!!.popBackStack("main", 1)
                    }
                    R.id.radiobutton_option3 -> {
                        dialogInDialogViewModelInObserver()
                        dialogDeleteMode.show()
                        dialogInToButtonListOfDeleteClickListener()

                    }

                }

                dialog.dismiss()
            }

        }
    }

    private fun FragmentSelfStoryImageSelectBinding.dialogInToButtonListOfDeleteClickListener() {
        dialogDeleteMode.button_listOfIndexDelete.setOnClickListener {

            PrefUsageMark.getInstance(requireContext())
                .selfStoryDeleteAfterInsertDataText =
                editTextImageSelectSelfStoryInText.text.toString()
            PrefUsageMark.getInstance(requireContext())
                .selfStoryDeleteAfterInsertDataImage =
                textViewImageBackgroundResIdCheck.text.toString()


            if (PrefUsageMark.getInstance(requireContext())
                    .deleteModelListOfIndex.isEmpty()
            )
                context?.toastShort(R.string.toast_deleteElementSelect)
            else {

                val deleteListOfIndex =
                    PrefUsageMark.getInstance(requireContext()).deleteModelListOfIndex

                val deleteList =
                    (dialogDeleteMode
                        .recyclerView_DialogInDialogDeleteList.adapter
                            as RecyclerViewDialogInDialogAdapter).modellist


                for (i in deleteListOfIndex.indices) {
                    viewModel.delete(deleteList[deleteListOfIndex[i]])
                }

                PrefUsageMark.getInstance(requireContext())
                    .deleteModelListOfIndex.clear()
                dialogDeleteMode.dismiss()

                selfStoryObserverControl(UsageMark.SELF_STORY_USAGE_MARK_DELETE)
                fragmentManager!!.popBackStack("main", 1)

                context?.toastShort(R.string.toast_deleteAfterInsert)
            }
        }
    }

    private fun FragmentSelfStoryImageSelectBinding.setFabActionButtonClickListener() {
        floatingActionButtonGalleryImageSelect.setOnClickListener {


            /**
             * FIXME: 권한 허용 나면 바로 갤러리로 이용하려고 했지만, 코루틴 이용실패 좀더 연구해보기
             */

            when {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED -> {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                    ) {
                        DialogSimple.show(requireContext(),
                            R.string.dialogGalleryRequestPremissonInTitle,
                            R.string.dialogGalleryRequestPremissonInMessage,
                            R.string.dialogGalleryRequestPremissonInPositiveText,
                            {
                                ActivityCompat.requestPermissions(
                                    requireActivity(),
                                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                                    REQUEST_EXTERNAL_STORAGE_PREMISSON
                                )
                            },
                            R.string.dialogGalleryRequestPremissonInNegativeText,
                            { return@show })
                    } else {
                        ActivityCompat.requestPermissions(
                            requireActivity(),
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            REQUEST_EXTERNAL_STORAGE_PREMISSON
                        )
                    }
                }
                else -> {
                    val intent =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(intent, REQUEST_IMAGE_CODE)
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val galleyImageUri = data?.data

        galleyImageUri?.let {

            Glide.with(this).load(it).into(imageView_backgroundImage)
            textView_imageBackgroundResIdCheck.text = it.toString()
            viewModel.galleyNewImageinsert(it.toString())
            context?.toastShort( R.string.toast_galleyImageUriAddAlarm)

        }

        if (textView_imageBackgroundResIdCheck.text.toString().isNotEmpty()) {
            textView_galleryguide.clearAnimation()
            textView_galleryguide.visibility = View.GONE
        }
        recyclerView_imageSelectInExampleImage.scrollToPosition(0)
    }

    private fun dialogInDialogViewModelInObserver() {

        viewModel.pigmeList.observe(viewLifecycleOwner, Observer {
            dialogDeleteMode.recyclerView_DialogInDialogDeleteList.adapter =
                RecyclerViewDialogInDialogAdapter(it, requireContext())

            (dialogDeleteMode.recyclerView_DialogInDialogDeleteList.adapter
                    as RecyclerViewDialogInDialogAdapter).notifyDataSetChanged()
        })
    }

    private fun selfStoryObserverControl(control: Int) {
        PrefUsageMark.getInstance(requireContext()).selfStoryUsageMark = control
    }


}
