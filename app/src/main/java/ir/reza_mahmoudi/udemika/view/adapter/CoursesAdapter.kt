package ir.reza_mahmoudi.udemika.view.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ir.reza_mahmoudi.udemika.R
import ir.reza_mahmoudi.udemika.databinding.ItemCourseBinding
import ir.reza_mahmoudi.udemika.model.Course

class CoursesAdapter(private val goToComments: (courseId: Long) -> Unit,
                      private val changeCourseIsLiked: (isLiked:Boolean, courseId: Long) -> Unit)
    : PagingDataAdapter<Course, CoursesAdapter.MainViewHolder>(DIFF_CALLBACK) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Course>() {
            override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean =
                oldItem == newItem
        }
    }

    inner class MainViewHolder(val binding: ItemCourseBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = getItem(position)

        holder.binding.apply {
            course=item
            if (item != null) {
                commentIcon.setOnClickListener {goToComments(item.id)}
                commentCount.setOnClickListener {goToComments(item.id)}
                likeIcon.setOnClickListener {
                    changeCourseIsLiked(item.isLiked!!, item.id)
                    item.isLiked?.let { isLiked ->
                        val likeColor=if (isLiked){ R.color.grey_600 }else{ R.color.red_600}
                        likeIcon.setColorFilter(ContextCompat.getColor(it.context, likeColor))
                    }
                }
            }

        }
    }
}