package ir.reza_mahmoudi.udemika.view.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.reza_mahmoudi.udemika.R
import ir.reza_mahmoudi.udemika.databinding.FragmentHomeBinding
import ir.reza_mahmoudi.udemika.view.paging.MainLoadStateAdapter
import ir.reza_mahmoudi.udemika.view.adapter.CoursesAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    private  lateinit var binding: FragmentHomeBinding
    private  lateinit var adapter: CoursesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        initViews()
        getCourses()
        return binding.root
    }
    private fun initViews(){
        adapter = CoursesAdapter(
            {goToComments(it)},
            {isLiked:Boolean, courseId: Long-> changeIsLiked(isLiked,courseId)})
        binding.coursesList.adapter = adapter.withLoadStateFooter(
            MainLoadStateAdapter()
        )
    }
    private fun getCourses(){
        lifecycleScope.launch {
            homeViewModel.dataC().collectLatest {
                adapter.submitData(it)
            }
        }
    }
    private fun goToComments(courseId: Long){
        val bundle = Bundle()
        bundle.putLong("courseId", courseId)
        findNavController().navigate(R.id.action_homeFragment_to_commentsFragment,bundle)
    }
    private fun changeIsLiked(isLiked:Boolean, courseId: Long){
        homeViewModel.changeIsLiked(isLiked, courseId)
    }
}