package com.example.mentalmathtesta

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.navigation.fragment.findNavController
import com.example.mentalmathtesta.databinding.FragmentTitleBinding


class TitleFragment : Fragment() {
    val vm : MyViewModel by activityViewModels()
    var  binding : FragmentTitleBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTitleBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.lifecycleOwner = viewLifecycleOwner
        binding?.vm = vm
        binding?.btnStart?.setOnClickListener {
            vm.startNewGame()
            findNavController().navigate(R.id.action_titleFragment_to_calculationFragment)
        }
    }
}