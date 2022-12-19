package br.org.venturus.mentoriasoftex.screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.org.venturus.mentoriasoftex.Routes
import br.org.venturus.mentoriasoftex.screen.login.LoginState
import br.org.venturus.mentoriasoftex.screen.login.LoginViewModel

@Composable
fun LoginScreen(navController: NavController) {

    var usernameText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false)}
    val viewModel: LoginViewModel = viewModel()

    when (viewModel.validateData) {
        LoginState.Failure -> {
            Toast.makeText(
                LocalContext.current,
                "Failed to login",
                Toast.LENGTH_LONG
            ).show()
            viewModel.resetValidateData()
        }
        LoginState.UserNotValid -> {
            Toast.makeText(
                LocalContext.current,
                "User not found",
                Toast.LENGTH_LONG
            ).show()
            navController.navigate(Routes.CreateAccountScreen.route)
            viewModel.resetValidateData()
        }
        LoginState.FailureEmail -> {
            Toast.makeText(
                LocalContext.current,
                "E-mail or password is invalid",
                Toast.LENGTH_LONG
            ).show()
            viewModel.resetValidateData()
        }
        LoginState.FailurePassword -> {
            Toast.makeText(
                LocalContext.current,
                "E-mail or password is invalid",
                Toast.LENGTH_LONG
            ).show()
            viewModel.resetValidateData()
        }
        LoginState.Idle -> {}
        LoginState.Loading -> {
            CircularProgressIndicator(
                modifier = Modifier
                    .scale(0.3f)
                    .fillMaxSize()
            )
        }
        LoginState.Valid -> {
            Toast.makeText(
                LocalContext.current,
                "Login successful",
                Toast.LENGTH_LONG
            ).show()
            navController.navigate(Routes.WelcomeScreen.route)
            viewModel.resetValidateData()
        }

    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "Login",
            fontSize = 40.sp,
            modifier = Modifier.padding(32.dp)
        )
        TextField(
            value = usernameText,
            onValueChange = { usernameText = it },
            placeholder = { Text(text = "Username") }
        )
        TextField(
            value = passwordText,
            onValueChange = { passwordText = it },
            placeholder = { Text(text = "Password") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff
                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description)
                    }
                },
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = { viewModel.validateDataCollection(usernameText, passwordText) },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Login")
        }
        Box(modifier = Modifier.fillMaxHeight()) {
            ClickableText(
                text = AnnotatedString("Sign up here"),
                onClick = {
                    navController.navigate(Routes.CreateAccountScreen.route)
                },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomCenter)
            )
        }
    }

}