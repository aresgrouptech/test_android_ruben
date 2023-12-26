package com.ridery.here.startModule.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.ridery.here.R
import com.ridery.here.common.utils.validateEmail
import com.ridery.here.common.utils.validateName
import com.ridery.here.common.utils.validatePassword
import com.ridery.here.common.utils.validatePhone
import com.ridery.here.databinding.FragmentSignInBinding
import com.ridery.here.startModule.viewModel.SignInViewModel
import kotlinx.coroutines.launch

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater)
        setupViewModel()
        return binding.root
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
        _binding?.lifecycleOwner = this
        _binding?.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect{uiState ->
                    if (uiState.emptyField) showEmptyAlert()
                    if (uiState.userExists) findNavController().navigate(R.id.action_SignInFragment_to_LogInFragment)
                }
            }
        }

        addValidations(binding)
    }

    private fun addValidations(binding: FragmentSignInBinding){
        validateName(binding.name.editText!!)
        validateName(binding.surname.editText!!)
        validateEmail(binding.email.editText!!)
        validatePassword(binding.password.editText!!)
        validatePhone(binding.phone.editText!!)
    }

    private fun showEmptyAlert(){
        AlertDialog.Builder(activity)
            .setTitle("Error")
            .setMessage("Por favor, completa todos los campos")
            .setPositiveButton("OK") { dialogInterface, i ->
                viewModel.uiState.value.emptyField = false
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}