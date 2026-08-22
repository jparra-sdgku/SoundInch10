package com.example.soundinch10.ui.screens

import android.widget.Switch
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.soundinch10.ui.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.nio.file.WatchEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    val rememberSession by viewModel.rememberSession.collectAsStateWithLifecycle()
    val emailError by viewModel.emailError.collectAsStateWithLifecycle()
    val passwordError by viewModel.passwordError.collectAsStateWithLifecycle()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("SoundIn") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        LoginContent(
            paddingValues = paddingValues,
            email = email,
            password = password,
            rememberSession = rememberSession,
            emailError = emailError,
            passwordError = passwordError,
            onEmailChanged = viewModel::onEmailChange,
            onPasswordChanged = viewModel::onPasswordChange,
            onRememberSessionChanged = viewModel::onRememberSessionChange,
            onLoginClick = {
                val isValid = viewModel.validateAndLogin()
                scope.launch {
                    if (isValid) {
                        snackbarHostState.showSnackbar(message = "Welcome to SoundIn")
                        onLoginSuccess()
                    } else {
                        snackbarHostState.showSnackbar(message = "Please review the marked fields")
                    }
                }
            },
            onNavigateToRegister = onNavigateToRegister
        )
    } // End Scaffold
} //  End LoginScreen

@Composable
fun LoginContent(
    paddingValues: PaddingValues,
    email: String,
    password: String,
    rememberSession: Boolean,
    emailError: Boolean,
    passwordError: Boolean,
    // Functions to update the state
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onRememberSessionChanged: (Boolean) -> Unit,
    onLoginClick: () -> Unit,
    onNavigateToRegister: () -> Unit
) {

    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "S",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "SoundIn",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(32.dp))
        // Email TextField
        OutlinedTextField(
            value = email,
            onValueChange = { onEmailChanged(it) },
            label = { Text("Email Address") },
            isError = emailError,
            supportingText = {
                if (emailError) {
                    Text(text = "Please enter a valid email address")
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )// End OutlinedTextField for Email
        // Challenge OutlinedTextField for Password

        OutlinedTextField(
            value = password,
            onValueChange = { onPasswordChanged(it) },
            label = { Text("Password") },
            isError = passwordError,
            supportingText = {
                if (passwordError) {
                    Text(text = "Please enter a valid password")
                }
            },
            visualTransformation = if (passwordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) {
                            Icons.Default.Visibility
                        } else {
                            Icons.Default.VisibilityOff
                        },
                        contentDescription = if (passwordVisible) "Hide Password" else "Show Password"
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )// End OutlinedTextField for Password
//  Remember Session Switch
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Remember Session",
                style = MaterialTheme.typography.bodyMedium
            )
            Switch(
                checked = rememberSession,
                onCheckedChange = { onRememberSessionChanged(it) }
            )
        } // End Row for Remember Session
        // Login Button with Validation
        Button(
            onClick = {
                onLoginClick()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Log In")
        }
        TextButton(onClick = onNavigateToRegister) { Text("Don't have an account? Register") }
    } // End Column
}// End LoginCo    var passwordVisible by remember { mutableStateOf(false) }









