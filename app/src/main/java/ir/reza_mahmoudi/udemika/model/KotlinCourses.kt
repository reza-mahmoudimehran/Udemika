package ir.reza_mahmoudi.udemika.model

import com.google.gson.annotations.SerializedName

data class KotlinCourses (
    @SerializedName("title")
    var title: String,
    @SerializedName("items")
    val coursesList: List<Course>?
)