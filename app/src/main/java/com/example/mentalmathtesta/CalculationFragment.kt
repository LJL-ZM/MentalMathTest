package com.example.mentalmathtesta

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.navigation.fragment.findNavController
import com.example.mentalmathtesta.databinding.FragmentCalculationBinding

class CalculationFragment : Fragment() {

    private var _binding: FragmentCalculationBinding? = null
    private val binding get() = _binding!!

    val vm : MyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalculationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = vm
        binding.lifecycleOwner = viewLifecycleOwner

        vm.userInput.observe(viewLifecycleOwner){ inputStr ->
            if(inputStr.isEmpty()){
                binding.textView3.text = getString(R.string.your_answer)
            }else{
                binding.textView3.text = inputStr
            }
        }

        //数字按键
        binding.button1.setOnClickListener { vm.appendUserInput("1") }
        binding.button2.setOnClickListener { vm.appendUserInput("2") }
        binding.button3.setOnClickListener { vm.appendUserInput("3") }
        binding.button4.setOnClickListener { vm.appendUserInput("4") }
        binding.button5.setOnClickListener { vm.appendUserInput("5") }
        binding.button6.setOnClickListener { vm.appendUserInput("6") }
        binding.button7.setOnClickListener { vm.appendUserInput("7") }
        binding.button8.setOnClickListener { vm.appendUserInput("8") }
        binding.button9.setOnClickListener { vm.appendUserInput("9") }
        binding.button0.setOnClickListener { vm.appendUserInput("0") }

        //C清除
        binding.buttonC.setOnClickListener {
            vm.clearUserInput()
        }

        binding.buttonOk.setOnClickListener {
            val inputStr = vm.userInput.value ?: ""
            if(inputStr.isEmpty()){
                return@setOnClickListener
            }
            val userAnswer = inputStr.toInt()
            val realAnswer = vm.answer.value ?: 0

            if(userAnswer == realAnswer){
                vm.onCorrect()
                vm.getNewQuestion()
            }else{
                if(vm.isWin.value ?: false) {
                    findNavController().navigate(R.id.action_calculationFragment_to_winFragment)
                } else {
                    findNavController().navigate(R.id.action_calculationFragment_to_loseFragment)
                }
            }
            //提交后清空
            vm.clearUserInput()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}