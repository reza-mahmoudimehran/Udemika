package ir.reza_mahmoudi.udemika.view.fragment.comments

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.reza_mahmoudi.udemika.data.repository.IRepository
import ir.reza_mahmoudi.udemika.model.Comment
import ir.reza_mahmoudi.udemika.model.Course
import ir.reza_mahmoudi.udemika.view.paging.MainPagingSource
import ir.reza_mahmoudi.udemika.utils.getCurrentTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val repository: IRepository,
    private val pagingConfig: PagingConfig
) : ViewModel() {
    var courseData: LiveData<Course> = MutableLiveData()

    fun data(courseId: Long) = Pager(pagingConfig) { MainPagingSource(repository,courseId) }.flow.cachedIn(viewModelScope)

    fun getCourseDataFromLocal(courseId: Long) = viewModelScope.launch {
        courseData = repository.getCourse(courseId).asLiveData()
    }
    fun addComments(text:String, courseId:Long) = viewModelScope.launch {
        val time= getCurrentTime()
        val comment=Comment(null,text,courseId,time)
        repository.insertComment(comment)
    }
    fun changeIsLiked(isLiked:Boolean, courseId: Long)=viewModelScope.launch(Dispatchers.IO) {
        repository.changeCourseIsLiked(!isLiked, courseId)
    }
}