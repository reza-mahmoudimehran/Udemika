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

    @Query("SELECT * FROM comments_table where courseId= :courseId")
    fun getComments(courseId:Long): Flow<List<Comment>>
}