package ir.reza_mahmoudi.udemika.view.activity

import android.content.SharedPreferences
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.reza_mahmoudi.udemika.data.repository.Repository
import ir.reza_mahmoudi.udemika.model.Comment
import ir.reza_mahmoudi.udemika.model.Course
import ir.reza_mahmoudi.udemika.model.UdemyResponse
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
    val localCourseComments: LiveData<List<Comment>> =
        repository.local.getComments(2642574).asLiveData()

    var coursesListFromApi: MutableLiveData<NetworkResult<UdemyResponse>> = MutableLiveData()

    private fun insertUdemyResponse(udemyResponse: UdemyResponse) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertUdemyResponse(udemyResponse)
            udemyResponse.coursesList?.let {
                repository.local.insertCourses(it.reversed())
                //showLog(" ",it.joinToString())
                for (course in udemyResponse.coursesList) {
                    course.comments?.let { comments -> repository.local.insertComments(comments) }
                }
            }
            sharedPreferences.edit().putBoolean("firstLogin", false).apply()
        }

    fun changeIsLiked(isLiked:Boolean, courseId: Long)=viewModelScope.launch(Dispatchers.IO) {
        repository.local.changeCourseIsLiked(!isLiked, courseId)
    }
    fun getCourses() = viewModelScope.launch {
        if (sharedPreferences.getBoolean("firstLogin", true)) {
            getCoursesFromApi()
            showLog("getCourses ", "api")
        } else {
            localCourses = repository.local.getCourses().asLiveData()
            showLog("getCourses ", "local")
        }
    }

    private suspend fun getCoursesFromApi() {
        coursesListFromApi.value = NetworkResult.Loading()
        if (networkHelper.isNetworkConnected()) {
            try {
                val response = repository.remote.getCourses()
                coursesListFromApi.value = handleCoursesResponse(response)

                val udemyResponse = coursesListFromApi.value!!.data
                if (udemyResponse != null) {
                    insertUdemyResponse(udemyResponse)
                }
            } catch (e: Exception) {
                coursesListFromApi.value = NetworkResult.Error("Courses not found.")
            }
        } else {
            coursesListFromApi.value = NetworkResult.Error("Please Check Your Internet Connection.")
        }
    }

    private fun handleCoursesResponse(response: Response<UdemyResponse>): NetworkResult<UdemyResponse> {
        return when {
            response.isSuccessful -> {
                val udemyResponse = response.body()
                NetworkResult.Success(udemyResponse!!)
            }
            else -> {
                NetworkResult.Error(response.message())
            }
        }
    }
}