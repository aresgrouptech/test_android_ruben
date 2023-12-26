package com.ridery.here.startModule.view

import android.app.AlertDialog
import android.content.DialogInterface
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
import com.ridery.here.databinding.FragmentWelcomeBinding
import com.ridery.here.startModule.viewModel.WelcomeViewModel
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: WelcomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater)
        setupViewModel()
        return binding.root
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(this).get(WelcomeViewModel::class.java)
        _binding?.lifecycleOwner = this
        _binding?.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect{uiState ->
                    if (uiState.userExists && uiState.user!!.isLogged)
                        findNavController().navigate(R.id.action_WelcomeFragment_to_main_graph)
                }
            }
        }
        binding.login.setOnClickListener {
            findNavController().navigate(R.id.action_WelcomeFragment_to_LogInFragment)
        }
        binding.register.setOnClickListener {
            if (binding.viewModel!!.uiState.value.userExists) showLoginDialog()
            else findNavController().navigate(R.id.action_WelcomeFragment_to_SignInFragment)
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            showExitDialog()
        }
    }

    fun showLoginDialog() {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle("Usuario encontrado")
                .setMessage("Usted ya se encuentra registrado. Por favor, inicie sesión")
                .setPositiveButton("Ok") { dialogInterface: DialogInterface, i: Int ->
                    findNavController().navigate(R.id.action_WelcomeFragment_to_LogInFragment)
                }
                .show()
        }
    }

    fun showExitDialog() {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle("Salir de la aplicación")
                .setMessage("¿Estás seguro de que quieres salir?")
                .setPositiveButton("Sí") { dialogInterface: DialogInterface, i: Int ->
                    it.finishAffinity()
                }
                .setNegativeButton("No", null)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}