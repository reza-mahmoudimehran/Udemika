package ir.reza_mahmoudi.udemika.view.activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.reza_mahmoudi.udemika.data.repository.Repository
import ir.reza_mahmoudi.udemika.model.KotlinCourses
import ir.reza_mahmoudi.udemika.utils.NetworkHelper
import ir.reza_mahmoudi.udemika.utils.NetworkResult
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
   private val repository: Repository,
     private val networkHelper: NetworkHelper
) : ViewModel() {

    var coursesResponse: MutableLiveData<NetworkResult<KotlinCourses>> = MutableLiveData()


    fun getCourses() = viewModelScope.launch {
        getCoursesSafeCall()
    }
    private suspend fun getCoursesSafeCall() {
        coursesResponse.value = NetworkResult.Loading()
        if (networkHelper.isNetworkConnected()) {
            try {
                val response = repository.remote.getCourses()
                coursesResponse.value = handleCoursesResponse(response)

            } catch (e: Exception) {
                coursesResponse.value = NetworkResult.Error(e.toString()+"Courses not found.")
            }
        } else {
            coursesResponse.value = NetworkResult.Error("Please Check Your Internet Connection.")
        }
    }
    private fun handleCoursesResponse(response: Response<KotlinCourses>): NetworkResult<KotlinCourses>? {
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