package com.example.soundinch10.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {
    // Private mutable state flows for form fields only used in the ViewModel
    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _rememberSession = MutableStateFlow(false)
    private val _emailError = MutableStateFlow(false)
    private val _passwordError = MutableStateFlow(false)

    // Public state flows for the UI to observe
    val email: StateFlow<String> = _email.asStateFlow()
    val password: StateFlow<String> = _password.asStateFlow()
    val rememberSession: StateFlow<Boolean> = _rememberSession.asStateFlow()
    val emailError: StateFlow<Boolean> = _emailError.asStateFlow()
    val passwordError: StateFlow<Boolean> = _passwordError.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
        _emailError.value = false
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
        _passwordError.value = false
    }

    fun onRememberSessionChange(newRememberSession: Boolean) {
        _rememberSession.value = newRememberSession
    }

    // Validation -- called when the user tap LogIn button
    fun validateAndLogin(): Boolean {
        val isEmailValid = _email.value.contains("@") && _email.value.contains(".")
        val isPasswordValid = _password.value.length >= 6

        _emailError.value = !isEmailValid
        _passwordError.value = !isPasswordValid

        return isEmailValid && isPasswordValid
    }
}





