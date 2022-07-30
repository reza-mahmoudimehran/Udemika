package ir.reza_mahmoudi.udemika.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ir.reza_mahmoudi.udemika.databinding.ItemCourseBinding
import ir.reza_mahmoudi.udemika.model.Course
import ir.reza_mahmoudi.udemika.utils.DiffUtilCallback

class CoursesAdapter(private val goToComments: () -> Unit,
                     private val changeCourseIsLiked: (isLiked:Boolean, courseId: Long) -> Unit) : RecyclerView.Adapter<CoursesAdapter.ViewHolder>() {

    private var courses = emptyList<Course>()

    class ViewHolder(private val binding: ItemCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(course: Course,
                 goToComments: () -> Unit,
                 changeCourseIsLiked: (isLiked:Boolean, courseId: Long) -> Unit
                 ,isLiked:Boolean, courseId: Long) {
            // Data Binding
            binding.course = course
            binding.commentCount.setOnClickListener {
                goToComments()
            }
            binding.commentIcon.setOnClickListener {
                goToComments()
            }
            binding.likeIcon.setOnClickListener {
                changeCourseIsLiked(isLiked,courseId)
            }
            binding.executePendingBindings()
        }

        companion object {
            // Use this function inside onCreateViewHolder
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCourseBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCourse = courses[position]
        val courseId= courses[position].id
        val isLiked= courses[position].isLiked
        holder.bind(currentCourse,goToComments,changeCourseIsLiked,isLiked!!,courseId)
    }

    override fun getItemCount(): Int {
        return courses.size
    }

    fun updateCourses(newData: List<Course>){
        val recipesDiffUtil =
            DiffUtilCallback(courses, newData)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        courses = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }
}