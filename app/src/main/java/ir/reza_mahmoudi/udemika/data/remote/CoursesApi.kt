package ir.reza_mahmoudi.udemika.data.remote

import ir.reza_mahmoudi.udemika.model.UdemyResponse
import retrofit2.Response
import retrofit2.http.GET

interface CoursesApi {
    @GET("v3/ff57a2bf-1bad-4229-9ca1-62332e8c9f85")
    suspend fun getCourses() : Response<UdemyResponse>
}