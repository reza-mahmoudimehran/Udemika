package ir.reza_mahmoudi.udemika.data.local

import ir.reza_mahmoudi.udemika.model.UdemyResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val coursesDao:CoursesDao
){
    suspend fun insertUdemyCourses(udemyResponse: UdemyResponse){
        coursesDao.insertUdemyCourses(udemyResponse)
    }
    fun getUdemyResponse(): Flow<UdemyResponse> {
        return coursesDao.getUdemyResponse()
    }
}