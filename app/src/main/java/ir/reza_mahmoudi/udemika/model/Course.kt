package ir.reza_mahmoudi.udemika.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import ir.reza_mahmoudi.udemika.utils.Constants

@Entity(
    tableName = Constants.COURSE_TABLE
)
data class Course (
    @PrimaryKey()
    @SerializedName("id")
    var id: Long,
    @SerializedName("category_id")
    var categoryId: Long,
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("image_480x270")
    val imageUrl: String?,
    @SerializedName("headline")
    val summary: String?,
    @SerializedName("likeCount")
    val likeCount: Int?,
    @SerializedName("isLiked")
    var isLiked: Boolean?,
    @Ignore
    @SerializedName("comments")
    val comments: List<Comment>?,
    @SerializedName("commentCount")
    val commentCount: Int?
){
    //Secondary Constructor to Ignore Comments List as a Column of course_table Table
    constructor(id: Long,categoryId: Long,title: String?,
                url: String?,imageUrl: String?,summary: String?,
                likeCount: Int?,isLiked: Boolean?,commentCount: Int?) :
            this(id,categoryId,title,url,imageUrl,summary,likeCount,isLiked,null,commentCount)
}