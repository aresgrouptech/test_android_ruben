package com.ridery.here.startModule.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.ridery.here.R
import com.ridery.here.common.utils.validateEmail
import com.ridery.here.common.utils.validatePassword
import com.ridery.here.databinding.FragmentLogInBinding
import com.ridery.here.startModule.viewModel.LogInViewModel
import kotlinx.coroutines.launch

class LogInFragment : Fragment() {

    private var _binding: FragmentLogInBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: LogInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogInBinding.inflate(inflater)
        setupViewModel()
        return binding.root
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(this).get(LogInViewModel::class.java)
        _binding?.lifecycleOwner = this
        _binding?.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect{uiState ->
                    if (uiState.user!!.isLogged) findNavController().navigate(R.id.action_LogInFragment_to_main_graph)
                }
            }
        }

        addValidations(binding)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            findNavController().navigate(R.id.action_LogInFragment_to_WelcomeFragment)
        }
    }

    private fun addValidations(binding: FragmentLogInBinding){
        validateEmail(binding.username.editText!!)
        validatePassword(binding.password.editText!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}