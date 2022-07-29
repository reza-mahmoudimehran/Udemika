package ir.reza_mahmoudi.udemika.model

import com.google.gson.annotations.SerializedName

data class Course (
    @SerializedName("id")
    var id: Long,
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
    val isLiked: Boolean?,
    @SerializedName("comments")
    val comments: List<String>?
)