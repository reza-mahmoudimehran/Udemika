package ir.reza_mahmoudi.udemika.view.activity

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.reza_mahmoudi.udemika.data.repository.Repository
import ir.reza_mahmoudi.udemika.model.Course
import ir.reza_mahmoudi.udemika.model.UdemyResponse
import kotlinx.coroutines.flow.Flow
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

    val localUdemyResponse: LiveData<UdemyResponse> = repository.local.getUdemyResponse().asLiveData()

    var coursesResponse: MutableLiveData<NetworkResult<UdemyResponse>> = MutableLiveData()

    private fun insertRecipes(udemyResponse: UdemyResponse) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertUdemyCourses(udemyResponse)
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
                    insertRecipes(udemyResponse)
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