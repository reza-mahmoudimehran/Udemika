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
    fun getCourses(): Flow<List<Course>>

    @Transaction
    @Insert()
    suspend fun insertComments(comments: List<Comment>)

    //@Query("SELECT * FROM comments_table where courseId= :courseId")
    @Query("SELECT * FROM comments_table where courseId= :courseId ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getComments(courseId:Long,limit: Int, offset: Int): List<Comment>

    @Query("UPDATE course_table SET isLiked = :isLiked WHERE id = :courseId")
    suspend fun changeCourseIsLiked(isLiked:Boolean, courseId: Long)
}