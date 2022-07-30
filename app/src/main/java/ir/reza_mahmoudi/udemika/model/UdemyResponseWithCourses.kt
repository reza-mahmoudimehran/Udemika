package ir.reza_mahmoudi.udemika.model

import androidx.room.Embedded
import androidx.room.Relation

data class UdemyResponseWithCourses(
    @Embedded val response: UdemyResponse,
    @Relation(
          parentColumn = "id",
          entityColumn = "udemyResponseId"
    )
    val courses: List<Course>
)