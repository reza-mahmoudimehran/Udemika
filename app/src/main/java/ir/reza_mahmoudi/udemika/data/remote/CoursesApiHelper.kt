package ir.reza_mahmoudi.udemika.data.remote

import ir.reza_mahmoudi.udemika.model.UdemyResponse
import retrofit2.Response

interface CoursesApiHelper {
    suspend fun getUdemy() : Response<UdemyResponse>
}