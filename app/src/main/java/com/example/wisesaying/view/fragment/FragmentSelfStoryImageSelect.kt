package com.example.wisesaying.view.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wisesaying.R
import com.example.wisesaying.databinding.FragmentSelfStoryImageSelectBinding
import com.example.wisesaying.db.PigmeDatabase
import com.example.wisesaying.preference.Preference_View
import com.example.wisesaying.view.activity.keyboardShow_Hide
import com.example.wisesaying.view.adapter.RecyclerView_ImageSelectAdapter
import com.example.wisesaying.view.adapter.Recyclerview_Image_Select_clcikEvent
import com.example.wisesaying.view.dialog.PremissonRequestDialog
import com.example.wisesaying.viewmodel.PigmeViewModel
import com.example.wisesaying.viewmodel.PigmeViewModelFactory
import kotlinx.android.synthetic.main.fragment_self_story_image_select.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException


class FragmentSelfStoryImageSelect : Fragment(), Recyclerview_Image_Select_clcikEvent {

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

    val REQUEST_EXTERNAL_STORAGE_PREMISSON = 1002
    val REQUEST_IMAGE_CODE = 1001
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return DataBindingUtil.inflate<FragmentSelfStoryImageSelectBinding>(
            inflater, R.layout.fragment_self_story_image_select, container,
            false
        ).run {

            pigmeViewModel = viewModel
            editTextImageSelectSelfStory.setText(arguments?.getString("selfStory"))
            textViewGalleryguide.startAnimation(animation_buttonGallery)

            val image = List<Int>(5) { 0 }.toMutableList()
            for (i in image.indices) {
                image[i] += resources.getIdentifier(
                    "a" + (i + 1),
                    "drawable",
                    "com.example.wisesaying"
                )
            }


            recyclerViewImageSelect.adapter =
                RecyclerView_ImageSelectAdapter(image, this@FragmentSelfStoryImageSelect)

            recyclerViewImageSelect.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            buttonNewSelfStoryaddFinish.setOnClickListener {
                keyboardShow_Hide(requireContext(), editTextImageSelectSelfStory)

                // 버튼 눌렀을 때만 selfMakingCount를 1로 설정
                // 메인 프레그먼트에서 updatedList.shuffled()가 적용되는 순간 사진 고정모드 자체도 먹히지 않음으로 이 순간만 1로 설정
                // 메인 프레그먼트 온크레이트 뷰에서 기본 0으로 설정
                MainFragment.selfStoryMakingCount = 1
                MainFragment.recyclerViewAdapterChange = 1


                val a = recyclerViewImageSelect.adapter as RecyclerView_ImageSelectAdapter
                pigmeViewModel!!.insert(
                    editTextImageSelectSelfStory.text.toString(),
                    textViewImageBackgroundResIdCheck.text.toString().toInt()
                )
                Preference_View.set_RecyclerViewAadapterChangeScore(1, requireActivity()!!)

                Toast.makeText(
                    requireActivity(),
                    "작성하신 글귀가 새롭게 추가 되었습니다. \n 셀프메이킹카운트: ${MainFragment.selfStoryMakingCount}",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            floatingActionButtonGalleryImageSelect.setOnClickListener {
                val viewModelJob = Job()
                val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

                if (ContextCompat.checkSelfPermission(
                        activity!!.applicationContext,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            activity!!,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                    ) {
//                        PremissonRequestDialog.show(requireContext(),"사진","필수","예",
//                            ActivityCompat.requestPermissions(
//                                this@MainActivity,
//                                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
//                                EQUEST_EXTERNAL_STORAGE_PREMISSON
//                            ))
                    } else {
                        ActivityCompat.requestPermissions(
                            activity!!,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            REQUEST_EXTERNAL_STORAGE_PREMISSON
                        )
                    }
                } else {
                    val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(intent,REQUEST_IMAGE_CODE)
                }
//                uiScope.launch {
//                    if (ContextCompat.checkSelfPermission(
//                            requireContext(),
//                            Manifest.permission.READ_EXTERNAL_STORAGE
//                        ) == PackageManager.PERMISSION_GRANTED) {
//                        val intent =
//                            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//                        startActivityForResult(intent, REQUEST_IMAGE_CODE)
//                    }
//                }
            }

            root
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//
        if(requestCode == REQUEST_IMAGE_CODE) {
            val image = data?.data
            image.let {
                if(Build.VERSION.SDK_INT < 28) {
                    val bitmap = MediaStore.Images.Media.getBitmap(
                        activity!!.contentResolver,
                        image)
                    imageView_backgroundImage.setImageBitmap(bitmap)
                } else {
                    val source = ImageDecoder.createSource(activity!!.contentResolver, image!!)
                    val bitmap = ImageDecoder.decodeBitmap(source)
                    imageView_backgroundImage.setImageBitmap(bitmap)
                }
            }
        }
    }
    override fun onclickEvent(position: Int) {
        imageView_backgroundImage.setBackgroundResource(position)
        textView_imageBackgroundResIdCheck.text = position.toString()
    }


}
