package ir.reza_mahmoudi.udemika.data.local

import androidx.room.*
import ir.reza_mahmoudi.udemika.model.UdemyResponse
import kotlinx.coroutines.flow.Flow


@Dao
interface CoursesDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUdemyCourses(udemyCourses: UdemyResponse)

    @Query("SELECT * FROM udemy_table ORDER BY id ASC")
    fun getUdemyResponse(): Flow<UdemyResponse>
}