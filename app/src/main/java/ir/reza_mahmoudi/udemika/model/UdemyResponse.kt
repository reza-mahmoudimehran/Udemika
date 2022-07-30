package ir.reza_mahmoudi.udemika.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import ir.reza_mahmoudi.udemika.utils.Constants

@Entity(
    tableName = Constants.UDEMY_TABLE
)
data class UdemyResponse (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @SerializedName("title")
    var title: String,
    @Ignore
    @SerializedName("items")
    val coursesList: List<Course>?
){
    constructor(id: Long,title: String) :
            this(id,title,null)
}