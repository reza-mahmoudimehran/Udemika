package ir.reza_mahmoudi.udemika.data.remote

import ir.reza_mahmoudi.udemika.model.KotlinCourses
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val coursesApi: CoursesApi
) {
    suspend fun getCourses(): Response<KotlinCourses> {
        return coursesApi.getCourses()
    }
}