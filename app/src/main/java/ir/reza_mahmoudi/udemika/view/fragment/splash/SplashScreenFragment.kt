package ir.reza_mahmoudi.udemika.view.fragment.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.reza_mahmoudi.udemika.R
import ir.reza_mahmoudi.udemika.databinding.FragmentSplashScreenBinding
import ir.reza_mahmoudi.udemika.utils.NetworkResult
import ir.reza_mahmoudi.udemika.utils.showLog
import ir.reza_mahmoudi.udemika.view.activity.MainViewModel

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : Fragment() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: FragmentSplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashScreenBinding.inflate(layoutInflater)
        loadAnimation()
        mainViewModel.getCoursesFromApi()
        observeViewModel()
        return binding.root
    }
    private fun loadAnimation(){
        binding.loadingAnimation.setAnimation("logo_animation.json")
        binding.loadingAnimation.playAnimation()
    }
    private fun observeViewModel(){
        mainViewModel.coursesListFromApi.observe(viewLifecycleOwner) { it ->
            when (it) {
                is NetworkResult.Success -> {
                    goToHome()
                }
                is NetworkResult.Error -> {
                    showLog("observe Home ViewModel: ",it.message.toString())
                }
            }
        }
    }
    private fun goToHome(){
        //TODO: change splash time
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_splashScreenFragment_to_homeFragment)
        }, 5)
    }
}