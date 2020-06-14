package com.example.wisesaying.view.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
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
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wisesaying.R
import com.example.wisesaying.databinding.FragmentSelfStoryImageSelectBinding
import com.example.wisesaying.db.PigmeDatabase
import com.example.wisesaying.preference.PrefSingleton
import com.example.wisesaying.view.activity.keyboardShow_Hide
import com.example.wisesaying.view.adapter.RecyclerViewImageSelectAdapter
import com.example.wisesaying.view.adapter.RecyclerviewImageSelectClcikEvent
import com.example.wisesaying.view.dialog.ImagePremissonRequestDialog
import com.example.wisesaying.view.dialog.PremissonRequestDialogInterface
import com.example.wisesaying.viewmodel.PigmeViewModel
import com.example.wisesaying.viewmodel.PigmeViewModelFactory
import kotlinx.android.synthetic.main.dialog_self_story_image_select_buttonevent.*
import kotlinx.android.synthetic.main.fragment_self_story_image_select.*
import kotlinx.coroutines.*


class FragmentSelfStoryImageSelect : Fragment(), RecyclerviewImageSelectClcikEvent {

    private val viewModel: PigmeViewModel by lazy {
        val pigmedatabase = PigmeDatabase.getInstance(requireContext())
        val factory = PigmeViewModelFactory(pigmedatabase.pigmeDao)
        ViewModelProvider(this, factory).get(PigmeViewModel::class.java)

    }

    private val animation_buttonGallery by lazy {
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
    ): View? = DataBindingUtil.inflate<FragmentSelfStoryImageSelectBinding>(
        inflater, R.layout.fragment_self_story_image_select, container,
        false
    ).run {
        val viewModelJob = Job()
        val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

        pigmeViewModel = viewModel
        story = (arguments?.getString("selfStory"))
        textViewGalleryguide.startAnimation(animation_buttonGallery)

        val image = List<String>(5) { "" }.toMutableList()
        for (i in image.indices) {
            image[i] += resources.getIdentifier(
                "a" + (i + 1),
                "drawable",
                "com.example.wisesaying"
            ).toString()
        }

        recyclerViewImageSelect.adapter =
            RecyclerViewImageSelectAdapter(image, this@FragmentSelfStoryImageSelect)

        recyclerViewImageSelect.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        buttonNewSelfStoryADD.setOnClickListener {
            /**
             * TODO:Entity Bitmap으로 변경후  imageView_backgroundimage !is BitmapDrawable 으로 바꿀 예정
             */
            if (textViewImageBackgroundResIdCheck.text == "" && imageViewBackgroundImage.background == imageView_backgroundImage) {
                Toast.makeText(requireActivity(), "사진을 선택해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //키보드 가리기
            keyboardShow_Hide(requireContext(), editTextImageSelectSelfStory)

            //다이얼로그 부르기
            val dialogImageSelectMode =
                PremissonRequestDialogInterface(requireActivity() as AppCompatActivity)
            dialogImageSelectMode.dialogImageSelectBuilderSetting(dialogImageSelectMode)
            dialogImageSelectMode.dialogImageSelect.show()


            dialogImageSelectMode.dialogImageSelect.button_newSelfStoryaddFinish.setOnClickListener {

                when (dialogImageSelectMode.dialogImageSelect.radioGruop_imageSelect_Mode.checkedRadioButtonId) {
                    R.id.radiobutton_option1_ResetAfterNewList -> {

                    }
                    R.id.radiobutton_option2_default -> {

                            pigmeViewModel!!.insert(
                                editTextImageSelectSelfStory.text.toString(),
                                textViewImageBackgroundResIdCheck.text.toString()
                            )


                        Toast.makeText(
                            requireActivity(),
                            "작성하신 글귀가 새롭게 추가 되었습니다.",
                            Toast.LENGTH_SHORT
                        ).show()


                        /**
                         *  버튼 눌렀을 때만 selfMakingCount를 1로 설정
                        메인 프레그먼트에서 updatedList.shuffled()가 적용되는 순간 사진 고정모드 자체도 먹히지 않음으로 이 순간만 1로 설정
                        메인 프레그먼트 온크레이트 뷰에서 기본 0으로 설정
                         */

                        PrefSingleton.getInstance(requireContext()).selfStoryUsageMark =
                            1
                        PrefSingleton.getInstance(requireContext()).RecyclerViewAadapterChangeScore =
                            1
                        fragmentManager!!.popBackStack("main", 1)

                    }


                    R.id.radiobutton_option3_deleteAfterNewList -> {

                    }
                }

                dialogImageSelectMode.dialogImageSelect.dismiss()
            }
            /**
             * TODO: 코루틴 더 연구해서 클릭할때마다 변화게끔 만들어보기
            uiScope.launch {
            dialog_imageSelectMode.dialogImageSelect.findViewById<RadioButton>(
            dialog_imageSelectMode.dialogImageSelect.radioGruop_imageSelect_Mode.checkedRadioButtonId
            ).run {
            setTypeface(null, Typeface.BOLD)
            }

            }
             */

        }

        floatingActionButtonGalleryImageSelect.setOnClickListener {


            /**
             * FIXME: 권한 허용 나면 바로 갤러리로 이용하려고 했지만, 코루틴 이용실패 좀더 연구해보기
             */
            uiScope.launch {
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                    ) {
                        ImagePremissonRequestDialog.show(requireContext(), "사진", "필수", "예",
                            {
                                ActivityCompat.requestPermissions(
                                    requireActivity(),
                                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                                    REQUEST_EXTERNAL_STORAGE_PREMISSON
                                )
                            }, "아니요", {})
                    } else {
                        ActivityCompat.requestPermissions(
                            requireActivity(),
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            REQUEST_EXTERNAL_STORAGE_PREMISSON
                        )
                    }
                } else {
                    val intent =
                        Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        )
                    startActivityForResult(intent, REQUEST_IMAGE_CODE)
                }
            }

        }

        root
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CODE) {
            val image = data?.data
            image?.let {
                if (Build.VERSION.SDK_INT < 28) {
                    imageView_backgroundImage.setImageURI(image)
                    //   val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver,
                    // image)
                    //  imageView_backgroundImage.setImageBitmap(bitmap)
                    textView_imageBackgroundResIdCheck.text = image.toString()

                } else {
                    val source =
                        ImageDecoder.createSource(requireActivity().contentResolver, image)
                    val bitmap = ImageDecoder.decodeBitmap(source)
                    imageView_backgroundImage.setImageBitmap(bitmap)
                }
            }
        }
    }

    override fun onclickEvent(position: String) {
        imageView_backgroundImage.setImageURI(Uri.parse("android.resource://com.example.wisesaying/$position"))
        textView_imageBackgroundResIdCheck.text = position
    }

}

