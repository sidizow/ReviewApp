package com.example.signin.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.presentation.base.BaseFragment
import com.example.presentation.utils.toCharArray
import com.example.signin.R
import com.example.signin.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>(
    R.layout.fragment_sign_in,
    FragmentSignInBinding::inflate
) {

    override val viewModel by viewModels<SignInViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signInButton.setOnClickListener { onSignInButtonPressed() }
        binding.signUpButton.setOnClickListener { onSignUpButtonPressed() }

        observeState()
        observeClearPasswordEvent()
        observeShowAuthErrorMessageEvent()
        observeNavigateToCatalogEvent()
    }

    private fun observeState() = viewModel.state.observe(viewLifecycleOwner) {
        binding.emailTextInput.error =
            if (it.emptyEmailError) getString(R.string.field_is_empty) else null
        binding.passwordTextInput.error =
            if (it.emptyPasswordError) getString(R.string.field_is_empty) else null

        binding.emailTextInput.isEnabled = it.enableViews
        binding.passwordTextInput.isEnabled = it.enableViews
        binding.signInButton.isEnabled = it.enableViews
        binding.signUpButton.isEnabled = it.enableViews
        binding.progressBar.visibility = if (it.showProgress) View.VISIBLE else View.INVISIBLE
    }

    private fun observeClearPasswordEvent() =
        viewModel.clearPasswordEvent.observe(viewLifecycleOwner) {
            binding.passwordEditText.text?.clear()
        }


    private fun observeShowAuthErrorMessageEvent() =
        viewModel.showAuthErrorToast.observe(viewLifecycleOwner) {
            Toast.makeText(
                requireContext(),
                R.string.invalid_email_or_password_message,
                Toast.LENGTH_SHORT
            ).show()
        }

    private fun observeNavigateToCatalogEvent() =
        viewModel.navigateToCatalogFilmsEvent.observe(viewLifecycleOwner) {
            findNavController().navigate(com.example.navigation.R.id.action_signInFragment_to_catalogFilmsFragment)

        }

    private fun onSignInButtonPressed() {
        viewModel.singIn(
            email = binding.emailEditText.text.toString(),
            password = binding.passwordEditText.text.toCharArray()
        )
    }

    private fun onSignUpButtonPressed() {
        val email = binding.emailEditText.text.toString()
        val emailArg = email.ifBlank { null }
        val direction = SignInFragmentDirections.actionSignInFragmentToSignUpFragment(emailArg)
        findNavController().navigate(direction)
    }

}
