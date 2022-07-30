package ir.reza_mahmoudi.udemika.view.activity

import android.content.SharedPreferences
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.reza_mahmoudi.udemika.data.repository.Repository
import ir.reza_mahmoudi.udemika.model.Comment
import ir.reza_mahmoudi.udemika.model.Course
import ir.reza_mahmoudi.udemika.model.UdemyResponse
import ir.reza_mahmoudi.udemika.utils.MainPagingSource
import ir.reza_mahmoudi.udemika.utils.NetworkHelper
import ir.reza_mahmoudi.udemika.utils.NetworkResult
import ir.reza_mahmoudi.udemika.utils.showLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    private val networkHelper: NetworkHelper,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    val localUdemyResponse: LiveData<List<UdemyResponse>> =
        repository.local.getUdemyResponse().asLiveData()
    var localCourses: LiveData<List<Course>> = MutableLiveData()
//    val localCourseComments: LiveData<List<Comment>> =
//        repository.local.getComments(2642574,2,1).asLiveData()
val data = Pager(
    PagingConfig(
        pageSize = 2,
        enablePlaceholders = false,
        initialLoadSize = 2
    ),
) {
    MainPagingSource(repository)
}.flow.cachedIn(viewModelScope)

    var coursesListFromApi: MutableLiveData<NetworkResult> = MutableLiveData()

    private fun insertUdemyResponse(udemyResponse: UdemyResponse) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertUdemyResponse(udemyResponse)
            udemyResponse.coursesList?.let {
                repository.local.insertCourses(it.reversed())
                for (course in udemyResponse.coursesList) {
                    course.comments?.let { comments -> repository.local.insertComments(comments) }
                }
            }
            sharedPreferences.edit().putBoolean("firstLogin", false).apply()
        }

    fun changeIsLiked(isLiked:Boolean, courseId: Long)=viewModelScope.launch(Dispatchers.IO) {
        repository.local.changeCourseIsLiked(!isLiked, courseId)
    }

    fun getCoursesFromLocal() = viewModelScope.launch {
        getCoursesFromLocalSafeCall()
    }
    private fun getCoursesFromLocalSafeCall() {
        localCourses = repository.local.getCourses().asLiveData()
    }

    fun getCoursesFromApi() = viewModelScope.launch {
        if (sharedPreferences.getBoolean("firstLogin", true)) {
            getCoursesFromApiSafeCall()
        } else {
            coursesListFromApi.value =NetworkResult.Success()
        }
    }
    private suspend fun getCoursesFromApiSafeCall() {
        if (networkHelper.isNetworkConnected()) {
            try {
                val response = repository.remote.getCourses()
                coursesListFromApi.value = handleCoursesResponse(response)
            } catch (e: Exception) {
                coursesListFromApi.value = NetworkResult.Error("Courses not found.")
            }
        } else {
            coursesListFromApi.value = NetworkResult.Error("Please Check Your Internet Connection.")
        }
    }

    private fun handleCoursesResponse(response: Response<UdemyResponse>): NetworkResult {
        return when {
            response.isSuccessful -> {
                val udemyResponse = response.body()
                if (udemyResponse != null) {
                    insertUdemyResponse(udemyResponse)
                }
                NetworkResult.Success()
            }
            else -> {
                NetworkResult.Error(response.message())
            }
        }
    }
}