package com.ridery.here.mainModule.view

import android.app.AlertDialog
import android.content.ComponentName
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ridery.here.common.utils.validateEmail
import com.ridery.here.common.utils.validateName
import com.ridery.here.common.utils.validatePassword
import com.ridery.here.common.utils.validatePhone
import com.ridery.here.databinding.FragmentEditBinding
import com.ridery.here.mainModule.viewModel.EditViewModel
import kotlinx.coroutines.launch

class EditFragment : Fragment() {

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: EditViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        setupViewModel()
        return binding.root
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(this).get(EditViewModel::class.java)
        _binding?.lifecycleOwner = this
        _binding?.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect{uiState ->
                    if (uiState.userUpdated) showSuccessAlert()
                    when (uiState.selectedIcon) {
                        1 -> changeIcon(1)
                        2 -> changeIcon(2)
                        3 -> changeIcon(3)
                        else -> changeIcon(0)
                    }
                }
            }
        }

        addValidations(binding)
    }

    private fun addValidations(binding: FragmentEditBinding){
        validateName(binding.name.editText!!)
        validateName(binding.surname.editText!!)
        validateEmail(binding.email.editText!!)
        validatePassword(binding.password.editText!!)
        validatePhone(binding.phone.editText!!)
    }

    private fun showSuccessAlert(){
        AlertDialog.Builder(activity)
            .setTitle("Actualizacion")
            .setMessage("Usuario actualizado con éxito." +
                    "Se obtuvieron ${viewModel.uiState.value.queryElements} elementos del query.")
            .setPositiveButton("OK") { dialogInterface, i ->
                viewModel.uiState.value.userUpdated = false
            }
            .show()
    }

    // FIXME: Los cambios de icono se hacen primero al original y en un segundo intento al que se elige
    private fun changeIcon(icon: Int){
        for (option in 0..3) {
            if (option == icon) adjustComponent(option, PackageManager.COMPONENT_ENABLED_STATE_ENABLED)
            else adjustComponent(option, PackageManager.COMPONENT_ENABLED_STATE_DISABLED)
        }
    }

    private fun adjustComponent(option: Int, componentState: Int) {
        requireActivity().packageManager.setComponentEnabledSetting(
            ComponentName(requireContext(), "com.ridery.here.MainActivity$option"),
            componentState,
            PackageManager.DONT_KILL_APP)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}