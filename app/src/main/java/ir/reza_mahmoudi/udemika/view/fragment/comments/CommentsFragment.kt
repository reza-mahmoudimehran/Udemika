package ir.reza_mahmoudi.udemika.view.fragment.comments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import ir.reza_mahmoudi.udemika.R
import ir.reza_mahmoudi.udemika.data.local.CoursesDao
import ir.reza_mahmoudi.udemika.databinding.FragmentCommentsBinding
import ir.reza_mahmoudi.udemika.utils.MainLoadStateAdapter
import ir.reza_mahmoudi.udemika.utils.showLog
import ir.reza_mahmoudi.udemika.view.activity.MainViewModel
import ir.reza_mahmoudi.udemika.view.adapter.CommentsAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CommentsFragment : Fragment() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: FragmentCommentsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        mainViewModel.localCourseComments.observe(viewLifecycleOwner){
//            showLog("response ", it.joinToString())
//        }

        binding = FragmentCommentsBinding.inflate(layoutInflater)
        val adapter = CommentsAdapter()
        binding.commentsList.adapter = adapter.withLoadStateFooter(
            MainLoadStateAdapter()
        )

        lifecycleScope.launch {
            showLog("lifecycleScope ","")
            mainViewModel.data.collectLatest {
                adapter.submitData(it)
            }
        }
        return binding.root
        //return inflater.inflate(R.layout.fragment_comments, container, false)
    }
}