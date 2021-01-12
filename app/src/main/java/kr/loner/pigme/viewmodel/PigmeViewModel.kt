package kr.loner.pigme.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

import kotlinx.coroutines.*
import kr.loner.pigme.db.dao.PigmeDao
import kr.loner.pigme.db.entity.GalleyImage
import kr.loner.pigme.db.entity.Pigme


class PigmeViewModel(private val pigmeSource: PigmeDao) : ViewModel() {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val pigmeList: LiveData<List<Pigme>>
        get() = pigmeSource.getAllPigmeList()


    val pigmeImageSelectVersion: LiveData<List<GalleyImage>>
        get() = pigmeSource.getGalleyNewImageList()


    fun galleyNewImageinsert(image: String) {

        uiScope.launch {
            withContext(Dispatchers.IO) {
                pigmeSource.galleyNewImageinsert(
                    GalleyImage(image)
                )
            }
        }
    }


    fun listInsert(pigme: List<Pigme>) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                pigmeSource.allInsert(pigme)
            }
        }
    }

    fun listdelete(pigme: List<Pigme>) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                pigmeSource.listDelete(pigme)
            }
        }
    }

    fun insert(story: String, image: String) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                pigmeSource.insert(
                    Pigme(story, image)
                )
            }
        }
    }

    fun delete(pigme: Pigme) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                pigmeSource.delete(pigme)
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