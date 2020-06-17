package com.example.wisesaying.view.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import androidx.databinding.DataBindingUtil.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wisesaying.R
import com.example.wisesaying.databinding.FragmentSelfStoryImageSelectBinding
import com.example.wisesaying.db.PigmeDatabase
import com.example.wisesaying.db.entity.GalleyImage
import com.example.wisesaying.preference.PrefSingleton
import com.example.wisesaying.view.adapter.RecyclerViewDialogInDialogAdapter
import com.example.wisesaying.view.adapter.RecyclerViewImageSelectAdapter
import com.example.wisesaying.view.dialog.DialogInLayoutCreateMode
import com.example.wisesaying.view.dialog.DialogSimple
import com.example.wisesaying.viewmodel.PigmeViewModel
import com.example.wisesaying.viewmodel.PigmeViewModelFactory
import kotlinx.android.synthetic.main.dialog_image_select_mode_recycler_view_.*
import kotlinx.android.synthetic.main.dialog_self_story_image_select_buttonevent.*
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

        val image = List<GalleyImage>(5) { GalleyImage("") }.toMutableList()
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
                textViewImageBackgroundResIdCheck,
                textViewGalleryguide
            )
        recyclerViewImageSelectInExampleImage.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)



        viewModel.pigmeImageSelectVersion.observe(viewLifecycleOwner, Observer {
            (recyclerViewImageSelectInExampleImage.adapter as RecyclerViewImageSelectAdapter).run {
                if (it.isEmpty()) {
                    for (i in image.indices)
                        viewModel.galleyNewImageinsert(image = image[i].galleryImage)
                }
                imageSampleList = it as MutableList<GalleyImage>
                imageSampleList.reverse()
                notifyDataSetChanged()

            }
        })



        buttonNewSelefStroyImageSelect.setOnClickListener {

            if (textViewImageBackgroundResIdCheck.text == "") {
                Toast.makeText(requireActivity(), R.string.toast_selfStroyNoImageSelectText, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            //다이얼로그 부르기
            val dialogImageSelectMode =
                DialogInLayoutCreateMode(requireActivity() as AppCompatActivity)
            dialogImageSelectMode.dialogImageSelectBuilderSetting(dialogImageSelectMode)
            dialogImageSelectMode.dialogImageSelect.show()

            //다이얼로그 창 안에서 3가지 버튼 클릭 상태에 따른 폰트 변화
            dialogImageSelectMode.dialogImageSelect.radioGruop_imageSelect_Mode.setOnCheckedChangeListener { _, checkedId ->

                when (checkedId) {
                    R.id.radiobutton_option1 -> {
                        dialogImageSelectMode.dialogImageSelect.radiobutton_option1.setTypeface(
                            null,
                            Typeface.BOLD
                        )
                        dialogImageSelectMode.dialogImageSelect.radiobutton_option2.setTypeface(
                            null,
                            Typeface.NORMAL
                        )
                        dialogImageSelectMode.dialogImageSelect.radiobutton_option3.setTypeface(
                            null,
                            Typeface.NORMAL
                        )
                    }
                    R.id.radiobutton_option2 -> {
                        dialogImageSelectMode.dialogImageSelect.radiobutton_option1.setTypeface(
                            null,
                            Typeface.NORMAL
                        )
                        dialogImageSelectMode.dialogImageSelect.radiobutton_option2.setTypeface(
                            null,
                            Typeface.BOLD
                        )
                        dialogImageSelectMode.dialogImageSelect.radiobutton_option3.setTypeface(
                            null,
                            Typeface.NORMAL
                        )
                    }
                    R.id.radiobutton_option3 -> {
                        dialogImageSelectMode.dialogImageSelect.radiobutton_option1.setTypeface(
                            null,
                            Typeface.NORMAL
                        )
                        dialogImageSelectMode.dialogImageSelect.radiobutton_option2.setTypeface(
                            null,
                            Typeface.NORMAL
                        )
                        dialogImageSelectMode.dialogImageSelect.radiobutton_option3.setTypeface(
                            null,
                            Typeface.BOLD
                        )
                    }
                }
            }

            dialogImageSelectMode.dialogImageSelect.button_newSelfStoryaddFinish.setOnClickListener {

                when (dialogImageSelectMode.dialogImageSelect.radioGruop_imageSelect_Mode.checkedRadioButtonId) {
                    R.id.radiobutton_option1 -> {
                        //why?? 선생님꼐 여쭤보자
                        DialogSimple.show(
                            requireContext(), "경고 메세지", "어떤 사진, 글이던 모두 초기화 되고 \n 현재 추가하신" +
                                    " 글과 사진만 남게됩니다. 괜찮으시겠습니까? ", "네 괜찮습니다", {
                                viewModel.insert(
                                    editTextImageSelectSelfStoryInText.text.toString(),
                                    textViewImageBackgroundResIdCheck.text.toString()
                                )

                                PrefSingleton.getInstance(requireContext()).selfStoryUsageMark = 2
                                PrefSingleton.getInstance(requireContext()).RecyclerViewAadapterChangeScore =
                                    1
                                fragmentManager!!.popBackStack("main", 1)
                            }, "아니요 다시 선택하겠습니다", { return@show }
                        )

                    }
                    R.id.radiobutton_option2 -> {

                        viewModel.insert(
                            editTextImageSelectSelfStoryInText.text.toString(),
                            textViewImageBackgroundResIdCheck.text.toString()
                        )

                        Toast.makeText(
                            requireActivity(),
                            "작성하신 사진 글이 새롭게 추가 되었습니다.",
                            Toast.LENGTH_SHORT
                        ).show()

                        /**
                         *  버튼 눌렀을 때만 selfMakingCount를 1로 설정
                        메인 프레그먼트에서 updatedList.shuffled()가 적용되는 순간 사진 고정모드 자체도 먹히지 않음으로 이 순간만 1로 설정
                        메인 프레그먼트 온크레이트 뷰에서 기본 0으로 설정
                         */
                        PrefSingleton.getInstance(requireContext()).selfStoryUsageMark = 1
                        PrefSingleton.getInstance(requireContext()).RecyclerViewAadapterChangeScore =
                            1
                        fragmentManager!!.popBackStack("main", 1)
                    }
                    R.id.radiobutton_option3 -> {
                        dialogImageSelectMode.dialogInImageDeleteDialog.show()
                        dialogImageSelectMode.dialogInImageDeleteDialog.recyclerView_DialogInDialog.adapter =
                            RecyclerViewDialogInDialogAdapter(
                                PrefSingleton.getInstance(
                                    requireContext()
                                ).modelListPrefSelfStory
                            )
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
                            "갤러리 사진 허용권한",
                            "사진을 불러오려면 사용자의 권한이 필요합니다. \\n 권한을 허용하시겠습니까?",
                            "허용합니다.",
                            {
                                ActivityCompat.requestPermissions(
                                    requireActivity(),
                                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                                    REQUEST_EXTERNAL_STORAGE_PREMISSON
                                )
                            },
                            "거절합니다.",
                            {return@show})
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
            imageView_backgroundImage.setImageURI(it)
            textView_imageBackgroundResIdCheck.text = it.toString()
            viewModel.galleyNewImageinsert(it.toString())
            Toast.makeText(context, R.string.toast_galleyImageUriAddAlarm, Toast.LENGTH_SHORT).show()

        }

        if (textView_imageBackgroundResIdCheck.text.toString().length > 2) {
            textView_galleryguide.clearAnimation()
            textView_galleryguide.visibility = View.GONE
        }
        recyclerView_imageSelectInExampleImage.scrollToPosition(0)
    }


}

//   val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver,
// image)
//  imageView_backgroundImage.setImageBitmap(bitmap)

// val source =
//    ImageDecoder.createSource(requireActivity().contentResolver, image)
//  val bitmap = ImageDecoder.decodeBitmap(source)
//  imageView_backgroundImage.setImageBitmap(bitmap)
