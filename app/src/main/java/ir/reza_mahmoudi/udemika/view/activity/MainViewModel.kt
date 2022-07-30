package ir.reza_mahmoudi.udemika.view.activity

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
     private val networkHelper: NetworkHelper
) : ViewModel() {

    val localUdemyResponse: LiveData<List<UdemyResponse>> = repository.local.getUdemyResponse().asLiveData()
    val localCourses: LiveData<List<Course>> = repository.local.getCourses().asLiveData()
    val localCourseComments: LiveData<List<Comment>> = repository.local.getComments(2642574).asLiveData()

    var coursesResponse: MutableLiveData<NetworkResult<UdemyResponse>> = MutableLiveData()

    private fun insertUdemyResponse(udemyResponse: UdemyResponse) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertUdemyResponse(udemyResponse)
            udemyResponse.coursesList?.let {
                repository.local.insertCourses(it)
                for (course in udemyResponse.coursesList){
                    course.comments?.let { comments -> repository.local.insertComments(comments) }
                }
            }
        }

    fun getCourses() = viewModelScope.launch {
        getCoursesSafeCall()
    }
    private suspend fun getCoursesSafeCall() {
        coursesResponse.value = NetworkResult.Loading()
        if (networkHelper.isNetworkConnected()) {
            try {
                val response = repository.remote.getCourses()
                coursesResponse.value = handleCoursesResponse(response)

                val udemyResponse = coursesResponse.value!!.data
                if(udemyResponse != null) {
                    insertUdemyResponse(udemyResponse)
                }
            } catch (e: Exception) {
                coursesResponse.value = NetworkResult.Error(e.toString()+"Courses not found.")
            }
        } else {
            coursesResponse.value = NetworkResult.Error("Please Check Your Internet Connection.")
        }
    }
    private fun handleCoursesResponse(response: Response<UdemyResponse>): NetworkResult<UdemyResponse>? {
        return when {
            response.isSuccessful -> {
                val foodRecipes = response.body()
                NetworkResult.Success(foodRecipes!!)
            }
            else -> {
                NetworkResult.Error(response.message())
            }
        }
    }
}