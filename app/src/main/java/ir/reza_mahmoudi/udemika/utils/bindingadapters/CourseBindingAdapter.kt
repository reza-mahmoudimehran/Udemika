package ir.reza_mahmoudi.udemika.utils.bindingadapters

import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import ir.reza_mahmoudi.udemika.R
import ir.reza_mahmoudi.udemika.utils.getProgressDrawable
import ir.reza_mahmoudi.udemika.utils.loadImage

class CourseBindingAdapter {
    companion object {
        @BindingAdapter("loadImage")
        @JvmStatic
        fun loadImage(imageView: ImageView, imageUrl: String) {
            val progressDrawable= getProgressDrawable(imageView.context)
            imageView.loadImage(imageUrl,progressDrawable)
        }

        @BindingAdapter("setLikeCount")
        @JvmStatic
        fun setLikeCount(textView: TextView, likeCount: Int) {
            textView.text= "Liked by $likeCount People"
        }

        @BindingAdapter("setCommentCount")
        @JvmStatic
        fun setCommentCount(textView: TextView, commentCount: Int) {
            textView.text= "View all $commentCount Comments"
        }

        @BindingAdapter("setLikeColor")
        @JvmStatic
        fun setLikeColor(view: ImageButton, isLiked: Boolean) {
            if (isLiked) {
                view.setColorFilter(
                    ContextCompat.getColor(
                        view.context,
                        R.color.red_600
                    )
                )
            }
        }
    }
}