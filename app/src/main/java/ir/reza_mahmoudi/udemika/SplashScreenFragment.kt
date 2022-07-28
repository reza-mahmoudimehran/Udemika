package ir.reza_mahmoudi.udemika

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ir.reza_mahmoudi.udemika.databinding.FragmentSplashScreenBinding

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : Fragment() {
    private lateinit var binding: FragmentSplashScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashScreenBinding.inflate(layoutInflater)
        loadAnimation()
        return binding.root
    }
    private fun loadAnimation(){
        binding.loadingAnimation.setAnimation("logo_animation.json")
        binding.loadingAnimation.playAnimation()
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_splashScreenFragment_to_homeFragment)
        }, 5000)
    }
}