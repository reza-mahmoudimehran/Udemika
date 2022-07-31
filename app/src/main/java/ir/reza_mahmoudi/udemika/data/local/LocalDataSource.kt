package ir.reza_mahmoudi.udemika.data.local

import ir.reza_mahmoudi.udemika.model.Comment
import ir.reza_mahmoudi.udemika.model.Course
import ir.reza_mahmoudi.udemika.model.UdemyResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val coursesDao:CoursesDao
){
    suspend fun insertUdemyResponse(udemyResponse: UdemyResponse): Long{
        return coursesDao.insertUdemyResponse(udemyResponse)
    }
    fun getUdemyResponse(): Flow<List<UdemyResponse>> {
        return coursesDao.getUdemyResponse()
    }
    suspend fun insertCourses(courses: List<Course>){
        coursesDao.insertCourses(courses)
    }
    fun getCoursesList(): Flow<List<Course>>{
        return coursesDao.getCoursesList()
    }

    fun getCourse(courseId:Long): Flow<Course>{
        return coursesDao.getCourse(courseId)
    }

    suspend fun insertCommentsList(comments: List<Comment>){
        coursesDao.insertCommentsList(comments)
    }

    suspend fun insertComment(comment: Comment){
        coursesDao.insertComment(comment)
    }
    suspend fun getComments(courseId: Long,limit: Int, offset: Int): List<Comment>{
        return coursesDao.getComments(courseId,limit, offset)
    }
    suspend fun changeCourseIsLiked(isLiked:Boolean, courseId: Long){
        coursesDao.changeCourseIsLiked(isLiked,courseId)
    }

}