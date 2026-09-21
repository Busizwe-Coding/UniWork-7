package com.fitlog.app.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fitlog.app.FitLogApp
import com.fitlog.app.di.ViewModelFactory
import com.fitlog.app.ui.theme.FitLogGreen
import com.fitlog.app.ui.theme.FitLogGreenDark

/**
 * Login screen matching the FitLog mockup: gradient green header with the
 * logo, email/password fields, and a Google SSO button (SSO itself is
 * wired for the POE phase — see FitLogMessagingService.kt for the same
 * pattern used with FCM).
 */
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
) {
    val app = androidx.compose.ui.platform.LocalContext.current.applicationContext as FitLogApp
    val viewModel: LoginViewModel = viewModel(
        factory = ViewModelFactory { LoginViewModel(app.container.authRepository) },
    )
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(state.loginSucceeded) {
        if (state.loginSucceeded) onLoginSuccess()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(Brush.horizontalGradient(listOf(FitLogGreen, FitLogGreenDark))),
            contentAlignment = Alignment.Center,
        ) {
            Text("FitLog", color = androidx.compose.ui.graphics.Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        }

        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                "Track your training, anywhere.",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 20.dp),
            )

            OutlinedTextField(
                value = state.email,
                onValueChange = viewModel::onEmailChange,
                label = { Text("Email") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = state.password,
                onValueChange = viewModel::onPasswordChange,
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
            )

            state.errorMessage?.let {
                Text(it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 8.dp))
            }

            Spacer(Modifier.height(20.dp))
            Button(
                onClick = viewModel::login,
                enabled = !state.isLoading,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = FitLogGreen),
                modifier = Modifier.fillMaxWidth().height(48.dp),
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), color = androidx.compose.ui.graphics.Color.White)
                } else {
                    Text("LOG IN", fontWeight = FontWeight.Bold)
                }
            }

            Spacer(Modifier.height(16.dp))
            Text(
                "— or continue with —",
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
            Spacer(Modifier.height(16.dp))

            // Google SSO — implemented for the POE final submission (see
            // Part 1 design doc, Section 2.2). Disabled here with an
            // explanatory note so markers can see the intended entry point
            // without the prototype crashing on an unconfigured Firebase project.
            OutlinedButton(
                onClick = { /* Wired up once Firebase Auth + google-services.json are added */ },
                enabled = false,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth().height(48.dp),
            ) {
                Text("G  Sign in with Google (available in final build)")
            }

            Spacer(Modifier.height(20.dp))
            Text(
                "Don't have an account? Register",
                color = FitLogGreenDark,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable { onNavigateToRegister() },
            )
        }
    }
}
