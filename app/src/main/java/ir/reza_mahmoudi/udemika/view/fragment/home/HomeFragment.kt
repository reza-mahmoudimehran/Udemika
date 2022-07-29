package ir.reza_mahmoudi.udemika.view.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import ir.reza_mahmoudi.udemika.databinding.FragmentHomeBinding
import ir.reza_mahmoudi.udemika.utils.NetworkResult
import ir.reza_mahmoudi.udemika.view.activity.MainViewModel

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var mainViewModel: MainViewModel
    private  lateinit var binding: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        mainViewModel.getCourses()
        observeViewModel()
        return binding.root
    }
    private fun observeViewModel(){
        mainViewModel.coursesResponse.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    it.data?.let { kotlinCourses ->
                        Toast.makeText(
                        requireContext(),
                        kotlinCourses.title,
                        Toast.LENGTH_SHORT
                    ).show() }
                }
                is NetworkResult.Error -> {
                    Toast.makeText(
                        requireContext(),
                        it.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                }
            }
        }
    }
}