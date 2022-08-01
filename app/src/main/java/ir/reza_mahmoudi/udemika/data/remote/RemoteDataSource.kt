package ir.reza_mahmoudi.udemika.data.remote

import ir.reza_mahmoudi.udemika.model.UdemyResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val coursesApi: CoursesApi
) : CoursesApiHelper{
    override suspend fun getUdemy(): Response<UdemyResponse> {
        return coursesApi.getUdemy()
    }
}