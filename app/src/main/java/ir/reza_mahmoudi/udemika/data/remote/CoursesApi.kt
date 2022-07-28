package ir.reza_mahmoudi.udemika.data.remote

import ir.reza_mahmoudi.udemika.model.KotlinCourses
import retrofit2.Response
import retrofit2.http.GET

interface CoursesApi {
    @GET("/d1997e22-b6e8-428c-9d7a-f3fa1101b451")
    suspend fun getCourses() : Response<KotlinCourses>
}