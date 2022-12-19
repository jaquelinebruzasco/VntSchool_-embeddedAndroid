package br.org.venturus.mentoriasoftex.screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.org.venturus.mentoriasoftex.Routes
import br.org.venturus.mentoriasoftex.screen.createAccount.CreateAccountState
import br.org.venturus.mentoriasoftex.screen.createAccount.CreateAccountViewModel

@Composable
fun CreateAccountScreen(navController: NavController) {

    var usernameText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false)}
    val viewModel: CreateAccountViewModel = viewModel()

    when (viewModel.validateField) {
        CreateAccountState.Failure -> {
            Toast.makeText(
                LocalContext.current,
                "Error creating new account",
                Toast.LENGTH_LONG
            ).show()
            viewModel.resetValidateField()
        }
        CreateAccountState.FailureEmail -> {
            Toast.makeText(
                LocalContext.current,
                "E-mail can not be blank and must be a valid e-mail",
                Toast.LENGTH_LONG
            ).show()
            viewModel.resetValidateField()
        }
        CreateAccountState.FailurePassword -> {
            Toast.makeText(
                LocalContext.current,
                "Password can not be blank and must be at least 8 characters",
                Toast.LENGTH_LONG
            ).show()
            viewModel.resetValidateField()
        }
        CreateAccountState.Idle -> {}
        CreateAccountState.Valid -> {
            Toast.makeText(
                LocalContext.current,
                "Account created successfully",
                Toast.LENGTH_LONG
            ).show()
            navController.navigate(Routes.LoginScreen.route)
            viewModel.resetValidateField()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Create account",
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
            onClick = { viewModel.validateNewAccountField(usernameText, passwordText) },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Sign up")
        }
    }

}