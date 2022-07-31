package ir.reza_mahmoudi.udemika.view.fragment.comments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import ir.reza_mahmoudi.udemika.R
import ir.reza_mahmoudi.udemika.databinding.FragmentCommentsBinding
import ir.reza_mahmoudi.udemika.model.Course
import ir.reza_mahmoudi.udemika.utils.MainLoadStateAdapter
import ir.reza_mahmoudi.udemika.view.activity.MainViewModel
import ir.reza_mahmoudi.udemika.view.adapter.CommentsAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CommentsFragment : Fragment() {
    private lateinit var commentsViewModel: CommentsViewModel
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: FragmentCommentsBinding
    private var courseId: Long=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        courseId= arguments?.getLong("courseId")!!
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        commentsViewModel = ViewModelProvider(requireActivity())[CommentsViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommentsBinding.inflate(layoutInflater)
        initViews()
        return binding.root
    }
    private fun initViews() {
        var courseData:Course?=null
        val adapter = CommentsAdapter()
        binding.commentsList.adapter = adapter.withLoadStateFooter(
            MainLoadStateAdapter()
        )

        lifecycleScope.launch {
            commentsViewModel.data(courseId).collectLatest {
                adapter.submitData(it)
            }
        }
        commentsViewModel.getCourseDataFromLocal(courseId)
        commentsViewModel.courseData.observe(viewLifecycleOwner){
            binding.course=it
            courseData=it
        }
        binding.send.setOnClickListener{
            commentsViewModel.addComments(binding.commentText.text.toString(),courseId)
            binding.commentText.text=null
            lifecycleScope.launch {
                commentsViewModel.data(courseId).collectLatest {
                    adapter.submitData(it)
                }
            }
            binding.commentsList.smoothScrollToPosition(0)
        }
        binding.likeIcon.setOnClickListener{
            courseData?.isLiked?.let {
                    isLiked -> mainViewModel.changeIsLiked(isLiked,courseId)
                val likeColor=if (isLiked){
                    R.color.grey_600
                }else{ R.color.red_600}
                if(it is ImageView){
                    it.setColorFilter(
                        ContextCompat.getColor(it.context, likeColor)
                    )
                }
            }
        }
    }
}