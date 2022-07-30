package ir.reza_mahmoudi.udemika.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import ir.reza_mahmoudi.udemika.utils.Constants

@Entity(
    tableName = Constants.COMMENT_TABLE
)
data class Comment(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @SerializedName("text")
    val text: String?,
    @SerializedName("course_id")
    val courseId: Long?,
    @SerializedName("time")
    val time: String?
)
