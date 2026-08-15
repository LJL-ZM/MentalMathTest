package com.example.mentalmathtesta

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.navigation.fragment.findNavController
import com.example.mentalmathtesta.databinding.FragmentWinBinding

class WinFragment : Fragment() {

    private var _binding: FragmentWinBinding? = null
    private val binding get() = _binding!!

    val vm : MyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWinBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = vm
        binding.lifecycleOwner = viewLifecycleOwner

        binding.button11.setOnClickListener {
            findNavController().navigate(R.id.action_winFragment_to_titleFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object
}