package ir.reza_mahmoudi.udemika.data.local

import androidx.room.*
import ir.reza_mahmoudi.udemika.model.Comment
import ir.reza_mahmoudi.udemika.model.Course
import ir.reza_mahmoudi.udemika.model.UdemyResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface CoursesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUdemyResponse(udemyCourses: UdemyResponse): Long

    @Query("SELECT * FROM udemy_table ORDER BY categoryId ASC")
    fun getUdemyResponse(): Flow<List<UdemyResponse>>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourses(courses: List<Course>)

    @Query("SELECT * FROM course_table")
    fun getCoursesList(): Flow<List<Course>>

    @Query("SELECT * FROM course_table LIMIT :limit OFFSET :offset")
    suspend fun getCoursesListPage(limit: Int, offset: Int): List<Course>

    @Query("SELECT * FROM course_table where id= :courseId")
    fun getCourse(courseId:Long): Flow<Course>

    @Transaction
    @Insert()
    suspend fun insertCommentsList(comments: List<Comment>)

    @Insert()
    suspend fun insertComment(comment:Comment)

    @Query("SELECT * FROM comments_table where courseId= :courseId ORDER BY id DESC LIMIT :limit OFFSET :offset")
    suspend fun getComments(courseId:Long,limit: Int, offset: Int): List<Comment>

    @Query("UPDATE course_table SET isLiked = :isLiked WHERE id = :courseId")
    suspend fun changeCourseIsLiked(isLiked:Boolean, courseId: Long)
}