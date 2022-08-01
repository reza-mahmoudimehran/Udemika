package ir.reza_mahmoudi.udemika.data.repository

import dagger.hilt.android.scopes.ViewModelScoped
import ir.reza_mahmoudi.udemika.data.local.CoursesDao
import ir.reza_mahmoudi.udemika.data.remote.CoursesApiHelper
import ir.reza_mahmoudi.udemika.model.Comment
import ir.reza_mahmoudi.udemika.model.Course
import ir.reza_mahmoudi.udemika.model.UdemyResponse
import ir.reza_mahmoudi.udemika.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class Repository @Inject constructor(
    private val remoteDataSource: CoursesApiHelper,
    private val localDataSource: CoursesDao
):IRepository{

    // Web Service
    override suspend fun getUdemy(): NetworkResult<UdemyResponse> {
        return try {
            val response = remoteDataSource.getUdemy()
            val result = response.body()
            if (response.isSuccessful && result != null) {
                NetworkResult.Success(result)
            } else {
                NetworkResult.Error("An error occurred")
            }
        } catch (e: Exception) {
            NetworkResult.Error("Error occurred ${e.localizedMessage}")
        }
    }

    // Local Database
    override suspend fun insertUdemyResponse(udemyResponse: UdemyResponse): Long{
        return localDataSource.insertUdemyResponse(udemyResponse)
    }
    override fun getUdemyResponse(): Flow<List<UdemyResponse>> {
        return localDataSource.getUdemyResponse()
    }
    override suspend fun insertCourses(courses: List<Course>){
        localDataSource.insertCourses(courses)
    }
    override fun getCoursesList(): Flow<List<Course>> {
        return localDataSource.getCoursesList()
    }
    override suspend fun getCoursesListPage(limit: Int, offset: Int): List<Course>{
        return localDataSource.getCoursesListPage(limit,offset)
    }

    override fun getCourse(courseId:Long): Flow<Course> {
        return localDataSource.getCourse(courseId)
    }

    override suspend fun insertCommentsList(comments: List<Comment>){
        localDataSource.insertCommentsList(comments)
    }

    override suspend fun insertComment(comment: Comment){
        localDataSource.insertComment(comment)
    }
    override suspend fun getComments(courseId: Long,limit: Int, offset: Int): List<Comment>{
        return localDataSource.getComments(courseId,limit, offset)
    }
    override suspend fun changeCourseIsLiked(isLiked:Boolean, courseId: Long){
        localDataSource.changeCourseIsLiked(isLiked,courseId)
    }
}