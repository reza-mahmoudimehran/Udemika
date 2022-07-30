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
    @PrimaryKey()
    @SerializedName("category_id")
    val categoryId: Long,
    @SerializedName("title")
    var title: String,
    @SerializedName("item_type")
    var itemType: String,
    @Ignore
    @SerializedName("items")
    val coursesList: List<Course>?
){
    constructor(categoryId: Long,title: String,itemType: String) :
            this(categoryId,title,itemType,null)
}