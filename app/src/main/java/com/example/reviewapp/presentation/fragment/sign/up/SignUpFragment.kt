package com.example.reviewapp.presentation.fragment.sign.up

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.reviewapp.R
import com.example.reviewapp.databinding.FragmentSignUpBinding
import com.example.reviewapp.model.accounts.entities.SignUpData
import com.example.reviewapp.presentation.base.BaseFragment
import com.example.reviewapp.utils.toCharArray
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(
    R.layout.fragment_sign_up,
    FragmentSignUpBinding::inflate
) {

    override val viewModel by viewModels<SignUpViewModel>()

    private val args by navArgs<SignUpFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signUpButton.setOnClickListener { onSignUpButtonPressed() }

        if (savedInstanceState == null && getEmailArgument() != null) {
            binding.emailEditText.setText(getEmailArgument())
        }

        observeState()
        observeGoBackEvent()
        observeShowSuccessSignUpMessageEvent()
    }

    private fun observeState() = viewModel.state.observe(viewLifecycleOwner){
        binding.signUpButton.isEnabled = it.enableViews
        binding.emailTextInput.isEnabled = it.enableViews
        binding.usernameTextInput.isEnabled = it.enableViews
        binding.passwordTextInput.isEnabled = it.enableViews
        binding.repeatPasswordTextInput.isEnabled = it.enableViews

        fillError(binding.emailTextInput, it.emailErrorMessageRes)
        fillError(binding.usernameTextInput, it.usernameErrorMessageRes)
        fillError(binding.passwordTextInput, it.passwordErrorMessageRes)
        fillError(binding.repeatPasswordTextInput, it.repeatPasswordErrorMessageRes)

        binding.progressBar.visibility = if (it.showProgress) View.VISIBLE else View.INVISIBLE
    }

    private fun observeGoBackEvent() = viewModel.goBackEvent.observe(viewLifecycleOwner){
        findNavController().popBackStack()
    }

    private fun observeShowSuccessSignUpMessageEvent() =
        viewModel.showSuccessSignUpMessageEvent.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), R.string.success_sign_up_message, Toast.LENGTH_SHORT)
                .show()
        }

    private fun onSignUpButtonPressed() {
        val singUpData = SignUpData(
            email = binding.emailEditText.text.toString(),
            username = binding.usernameEditText.text.toString(),
            password = binding.passwordEditText.text.toCharArray(),
            repeatPassword = binding.repeatPasswordEditText.text.toCharArray()
        )
        viewModel.signUp(singUpData)
    }

    private fun fillError(input: TextInputLayout, @StringRes stringRes: Int) {
        if (stringRes == SignUpViewModel.NO_ERROR_MESSAGE) {
            input.error = null
            input.isErrorEnabled = false
        } else {
            input.error = getString(stringRes)
            input.isErrorEnabled = true
        }
    }

    private fun getEmailArgument(): String? = args.email

}