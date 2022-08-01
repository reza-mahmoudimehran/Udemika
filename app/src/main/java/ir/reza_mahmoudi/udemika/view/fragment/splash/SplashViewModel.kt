package ir.reza_mahmoudi.udemika.view.fragment.splash

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.reza_mahmoudi.udemika.data.repository.IRepository
import ir.reza_mahmoudi.udemika.model.UdemyResponse
import ir.reza_mahmoudi.udemika.utils.NetworkHelper
import ir.reza_mahmoudi.udemika.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: IRepository,
    private val networkHelper: NetworkHelper,
    private val sharedPreferences: SharedPreferences
    ) : ViewModel() {
    var coursesListFromApi: MutableLiveData<NetworkResult<UdemyResponse>> = MutableLiveData()

    private fun insertUdemyResponse(udemyResponse: UdemyResponse) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertUdemyResponse(udemyResponse)
            udemyResponse.coursesList?.let {
                repository.insertCourses(it)
                for (course in udemyResponse.coursesList) {
                    course.comments?.let { comments -> repository.insertCommentsList(comments) }
                }
            }
            sharedPreferences.edit().putBoolean("firstLogin", false).apply()
        }

    fun getCoursesFromApi() = viewModelScope.launch {
        coursesListFromApi.value = NetworkResult.Loading()
        if (sharedPreferences.getBoolean("firstLogin", true)) {
            getCoursesFromApiSafeCall()
        } else {
            coursesListFromApi.value = NetworkResult.Empty()
        }
    }
    private suspend fun getCoursesFromApiSafeCall() {
        if (networkHelper.isNetworkConnected()) {
            try {
                when (val response = repository.getUdemy()) {
                    is NetworkResult.Success -> {
                        coursesListFromApi.value = handleCoursesResponse(response)
                    }
                    is NetworkResult.Error -> {
                        coursesListFromApi.value = NetworkResult.Error(response.message ?: "Error")
                    }
                    else -> {}
                }
                val response = repository.getUdemy()
                coursesListFromApi.value = handleCoursesResponse(response)
            } catch (e: Exception) {
                coursesListFromApi.value = NetworkResult.Error("Courses not found.")
            }
        } else {
            coursesListFromApi.value = NetworkResult.Error("Please Check Your Internet Connection.")
        }
    }
    private fun handleCoursesResponse(response: NetworkResult<UdemyResponse>): NetworkResult<UdemyResponse> {
        response.data?.let {
            val udemyResponse = response.data
            insertUdemyResponse(udemyResponse)
            return NetworkResult.Success(udemyResponse)
        }
        return NetworkResult.Error("No data found")
    }
}