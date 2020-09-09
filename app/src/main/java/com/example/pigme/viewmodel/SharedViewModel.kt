package com.example.pigme.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SharedViewModel:ViewModel() {
    private val _visibleSetting = MutableLiveData<Int>()
    val visibleSetting : LiveData<Int>
    get() = _visibleSetting

    fun visibleChange(int: Int){
        _visibleSetting.postValue(int)
    }
}