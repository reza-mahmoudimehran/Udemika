package ir.reza_mahmoudi.udemika.view.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ir.reza_mahmoudi.udemika.R
import ir.reza_mahmoudi.udemika.databinding.FragmentHomeBinding
import ir.reza_mahmoudi.udemika.utils.NetworkResult
import ir.reza_mahmoudi.udemika.utils.showLog
import ir.reza_mahmoudi.udemika.view.activity.MainViewModel
import ir.reza_mahmoudi.udemika.view.adapter.CoursesAdapter

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var mainViewModel: MainViewModel
    private  lateinit var binding: FragmentHomeBinding
    private val mAdapter by lazy {
        CoursesAdapter(
            {goToComments()},
            {isLiked:Boolean, courseId: Long ->changeIsLiked(isLiked, courseId)}) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        mainViewModel.getCoursesFromLocal()
        observeViewModel()
        return binding.root
    }
    private fun observeViewModel(){
        mainViewModel.localCourses.observe(viewLifecycleOwner) {
            binding.coursesList.adapter = mAdapter
            binding.coursesList.layoutManager = LinearLayoutManager(requireContext())
            mAdapter.updateCourses(it)
        }

    }
    private fun goToComments(){
        findNavController().navigate(R.id.action_homeFragment_to_commentsFragment)
    }
    private fun changeIsLiked(isLiked:Boolean, courseId: Long){
        mainViewModel.changeIsLiked(isLiked, courseId)
    }
}