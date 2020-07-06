package com.example.wisesaying.view.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.wisesaying.R
import com.example.wisesaying.databinding.FragmentSelfStoryImageSelectBinding
import com.example.wisesaying.db.PigmeDatabase
import com.example.wisesaying.db.entity.GalleyImage
import com.example.wisesaying.preference.PrefUsageMark
import com.example.wisesaying.view.adapter.RecyclerViewDialogInDialogAdapter
import com.example.wisesaying.view.adapter.RecyclerViewImageSelectAdapter
import com.example.wisesaying.view.constscore.UsageMark
import com.example.wisesaying.view.dialog.DialogInLayoutCreateMode
import com.example.wisesaying.view.dialog.DialogSimple
import com.example.wisesaying.view.toast.toastShort
import com.example.wisesaying.viewmodel.PigmeViewModel
import com.example.wisesaying.viewmodel.PigmeViewModelFactory
import kotlinx.android.synthetic.main.dialog_dialogindialog_deletelist.*
import kotlinx.android.synthetic.main.dialog_self_story_image_select_buttonevent.*
import kotlinx.android.synthetic.main.dialog_self_story_image_select_buttonevent.radiobutton_option1
import kotlinx.android.synthetic.main.dialog_self_story_image_select_buttonevent.view.*
import kotlinx.android.synthetic.main.fragment_self_story_image_select.*

class FragmentSelfStoryImageSelect : Fragment() {

    private val viewModel: PigmeViewModel by lazy {
        val pigmeDataBase = PigmeDatabase.getInstance(requireContext())
        val factory = PigmeViewModelFactory(pigmeDataBase.pigmeDao)
        ViewModelProvider(this, factory).get(PigmeViewModel::class.java)
    }

    private val animationButtonGallery by lazy {
        AnimationUtils.loadAnimation(
            requireActivity(),
            R.anim.animation_fragmentselfstoryimageselect_button_gallery
        )
    }

    private val dialogImageSelectMode by lazy {
        DialogInLayoutCreateMode(requireActivity() as AppCompatActivity)
    }

    private val REQUEST_EXTERNAL_STORAGE_PREMISSON = 1002
    private val REQUEST_IMAGE_CODE = 1001

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflate<FragmentSelfStoryImageSelectBinding>(
        inflater, R.layout.fragment_self_story_image_select, container,
        false
    ).run {

        story = (arguments?.getString("selfStory"))
        textViewGalleryguide.startAnimation(animationButtonGallery)

        val image = List<GalleyImage>(5) { GalleyImage("") }
            .toMutableList()
        for (i in image.indices) {
            image[i].galleryImage += resources.getIdentifier(
                "a" + (i + 1),
                "drawable",
                "com.example.wisesaying"
            ).toString()
        }


        recyclerViewImageSelectInExampleImage.adapter =
            RecyclerViewImageSelectAdapter(
                image,
                imageViewBackgroundImage,
                textViewGalleryguide,
                textViewImageBackgroundResIdCheck
            )

        recyclerViewImageSelectInExampleImage.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

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


        buttonNewSelefStroyImageSelect.setOnClickListener {


            if (textViewImageBackgroundResIdCheck.text == "") {
                toastShort(
                    context, R.string.toast_selfStroyNoImageSelectText
                )
                return@setOnClickListener
            }
            dialogImageSelectMode.dialogImageSelectBuilderSetting(dialogImageSelectMode)
            dialogImageSelectMode.dialogImageSelect.show()

            //다이얼로그 창 안에서 3가지 버튼 클릭 상태에 따른 폰트 변화

            dialogImageSelectMode.dialogImageSelect.radioGruop_imageSelect_Mode.setOnCheckedChangeListener { _, checkedId ->

                dialogImageSelectMode.dialogImageSelect.findViewById<RadioButton>(checkedId).run {
                    val dialog = dialogImageSelectMode.dialogImageSelect
                    dialog.radiobutton_option1.setTypeface(null, Typeface.NORMAL)
                    dialog.radiobutton_option2.setTypeface(null, Typeface.NORMAL)
                    dialog.radiobutton_option3.setTypeface(null, Typeface.NORMAL)

                    setTypeface(null, Typeface.BOLD)
                }

            }

            dialogImageSelectMode.dialogImageSelect.button_newSelfStoryaddFinish.setOnClickListener {

                when (dialogImageSelectMode.dialogImageSelect.radioGruop_imageSelect_Mode.checkedRadioButtonId) {
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

                                PrefUsageMark.getInstance(requireContext()).selfStoryUsageMark =
                                    UsageMark.SELF_STORY_USAGE_MARK_RESET_AFTER_INSERT
                                fragmentManager!!.popBackStack("main", 1)
                            },
                            R.string.dialogResetAfterImageSelectInNegativeText,
                            { return@show }
                        )
                        toastShort(context, R.string.toast_resetAfterInsert)
                    }
                    R.id.radiobutton_option2 -> {

                        viewModel.insert(
                            editTextImageSelectSelfStoryInText.text.toString(),
                            textViewImageBackgroundResIdCheck.text.toString()
                        )

                        toastShort(
                            context,
                            R.string.toast_newSelfStory
                        )

                        PrefUsageMark.getInstance(requireContext()).selfStoryUsageMark =
                            UsageMark.SELF_STORY_USAGE_MARK_INSERT
                        fragmentManager!!.popBackStack("main", 1)
                    }
                    R.id.radiobutton_option3 -> {
                        viewModel.pigmeList.observe(viewLifecycleOwner, Observer {
                            dialogImageSelectMode.dialogInImageDeleteDialog
                                .recyclerView_DialogInDialogDeleteList.adapter =
                                RecyclerViewDialogInDialogAdapter(it, requireContext())

                            (dialogImageSelectMode.dialogInImageDeleteDialog
                                .recyclerView_DialogInDialogDeleteList.adapter
                                    as RecyclerViewDialogInDialogAdapter).notifyDataSetChanged()
                        })

                        dialogImageSelectMode.dialogInImageDeleteDialog.show()

                        dialogImageSelectMode.dialogInImageDeleteDialog
                            .button_listOfIndexDelete.setOnClickListener {

                                PrefUsageMark.getInstance(requireContext())
                                    .selfStoryDeleteAfterInsertDataText =
                                    editTextImageSelectSelfStoryInText.text.toString()
                                PrefUsageMark.getInstance(requireContext())
                                    .selfStoryDeleteAfterInsertDataImage =
                                    textViewImageBackgroundResIdCheck.text.toString()


                                if (PrefUsageMark.getInstance(requireContext()).deleteModelListOfIndex.isEmpty())
                                    toastShort(
                                        requireContext(),
                                        R.string.toast_deleteElementSelect
                                    )
                                else {

                                    val deleteListOfIndex =
                                        PrefUsageMark.getInstance(requireContext()).deleteModelListOfIndex

                                    val deleteList =
                                        (dialogImageSelectMode.dialogInImageDeleteDialog
                                            .recyclerView_DialogInDialogDeleteList.adapter
                                                as RecyclerViewDialogInDialogAdapter).modellist


                                    for (i in deleteListOfIndex.indices) {
                                        viewModel.delete(deleteList[deleteListOfIndex[i]])
                                    }

                                    PrefUsageMark.getInstance(requireContext()).deleteModelListOfIndex.clear()
                                    dialogImageSelectMode.dialogInImageDeleteDialog.dismiss()

                                    PrefUsageMark.getInstance(requireContext()).selfStoryUsageMark =
                                        UsageMark.SELF_STORY_USAGE_MARK_DELETE
                                    fragmentManager!!.popBackStack("main", 1)

                                    toastShort(context, R.string.toast_deleteAfterInsert)
                                }
                            }

                    }

                }

                dialogImageSelectMode.dialogImageSelect.dismiss()
            }

        }

        floatingActionButtonGalleryImageSelect.setOnClickListener {


            /**
             * FIXME: 권한 허용 나면 바로 갤러리로 이용하려고 했지만, 코루틴 이용실패 좀더 연구해보기
             */

            when {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                        != PackageManager.PERMISSION_GRANTED -> {

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
        root
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val galleyImageUri = data?.data

        galleyImageUri?.let {
            Glide.with(this).load(it).into(imageView_backgroundImage)
            textView_imageBackgroundResIdCheck.text = it.toString()
            viewModel.galleyNewImageinsert(it.toString())
            toastShort(context, R.string.toast_galleyImageUriAddAlarm)

        }

        if (textView_imageBackgroundResIdCheck.text.toString().isNotEmpty()) {
            textView_galleryguide.clearAnimation()
            textView_galleryguide.visibility = View.GONE
        }
        recyclerView_imageSelectInExampleImage.scrollToPosition(0)
    }


}
