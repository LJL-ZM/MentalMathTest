package com.example.mentalmathtesta

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.navigation.fragment.findNavController
import com.example.mentalmathtesta.databinding.FragmentLoseBinding

class LoseFragment : Fragment() {

    private var _binding: FragmentLoseBinding? = null
    private val binding get() = _binding!!

    val vm : MyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //绑定viewmodel和生命周期，分数自动刷新
        binding.vm = vm
        binding.lifecycleOwner = viewLifecycleOwner

        //按钮点击：跳转到titleFragment
        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_loseFragment_to_titleFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object
}