package ru.gmasalskih.lesson2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import ru.gmasalskih.lesson2.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {
    lateinit var binding: FragmentTitleBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTitleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        initListener()
    }

    private fun initListener(){
        binding.btnExercise1.setOnClickListener {
            val action = TitleFragmentDirections.actionTitleFragmentToExercise1()
            navController.navigate(action)
        }
        binding.btnExercise2.setOnClickListener {
            val action = TitleFragmentDirections.actionTitleFragmentToExercise2()
            navController.navigate(action)
        }
    }
}
