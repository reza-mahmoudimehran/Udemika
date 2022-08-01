package ir.reza_mahmoudi.udemika.data.repository

import ir.reza_mahmoudi.udemika.model.Comment
import ir.reza_mahmoudi.udemika.model.Course
import ir.reza_mahmoudi.udemika.model.UdemyResponse
import ir.reza_mahmoudi.udemika.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface IRepository {
    // Web Service
    suspend fun getUdemy(): NetworkResult<UdemyResponse>


    //Local Database
    suspend fun insertUdemyResponse(udemyResponse: UdemyResponse): Long

    fun getUdemyResponse(): Flow<List<UdemyResponse>>

    suspend fun insertCourses(courses: List<Course>)

    fun getCoursesList(): Flow<List<Course>>

    suspend fun getCoursesListPage(limit: Int, offset: Int): List<Course>

    fun getCourse(courseId:Long): Flow<Course>

    suspend fun insertCommentsList(comments: List<Comment>)

    suspend fun insertComment(comment: Comment)

    suspend fun getComments(courseId: Long,limit: Int, offset: Int): List<Comment>

    suspend fun changeCourseIsLiked(isLiked:Boolean, courseId: Long)

}