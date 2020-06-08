package com.example.wisesaying.viewmodel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.wisesaying.R
import com.example.wisesaying.db.dao.PigmeDao
import com.example.wisesaying.db.entity.Pigme
import kotlinx.coroutines.*


class PigmeViewModel(private val pigmeSource: PigmeDao) : ViewModel() {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main+viewModelJob)

    val pigmeList:LiveData<List<Pigme>>
    get() = pigmeSource.getAllPigmeList()

    fun insert(story:String){
        uiScope.launch {
            withContext(Dispatchers.IO){
                pigmeSource.insert(
                    Pigme(story,R.drawable.testimage)
                )
            }
        }
    }
    fun delete(pigme: Pigme){
        uiScope.launch {
            withContext(Dispatchers.IO){
                pigmeSource.insert(pigme)
            }
        }
    }
}

/**
 * 프레그먼트 간의 통신 뷰모델로 연구해봄
val _selfStorybinding = MutableLiveData<String>()
val selfStorybinding:LiveData<String>
get() = _selfStorybinding

fun get_selfStroyBind():LiveData<String>{
return selfStorybinding
}
fun set_selfStorybind(story: String) {
_selfStorybinding.value = story
}
 */