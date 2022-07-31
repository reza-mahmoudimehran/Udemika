package ir.reza_mahmoudi.udemika.view.fragment.comments

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.reza_mahmoudi.udemika.data.repository.Repository
import ir.reza_mahmoudi.udemika.model.Comment
import ir.reza_mahmoudi.udemika.model.Course
import ir.reza_mahmoudi.udemika.utils.MainPagingSource
import ir.reza_mahmoudi.udemika.utils.getCurrentTime
import ir.reza_mahmoudi.udemika.utils.showLog
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val repository: Repository,
    private val pagingConfig: PagingConfig
) : ViewModel() {
    var courseData: LiveData<Course> = MutableLiveData()

    fun data(courseId: Long) = Pager(pagingConfig) {MainPagingSource(repository,courseId) }.flow.cachedIn(viewModelScope)

    fun getCourseDataFromLocal(courseId: Long) = viewModelScope.launch {
        courseData = repository.local.getCourse(courseId).asLiveData()
    }
    fun addComments(text:String, courseId:Long) = viewModelScope.launch {
        val time= getCurrentTime()
        val comment=Comment(null,text,courseId,time)
        repository.local.insertComment(comment)
    }
}