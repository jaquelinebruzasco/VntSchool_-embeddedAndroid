package br.org.venturus.mentoriasoftex.screen.login

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.org.venturus.mentoriasoftex.screen.createAccount.CreateAccountState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private var auth: FirebaseAuth = Firebase.auth

    var validateData by mutableStateOf<LoginState>(LoginState.Idle)

    init {
        resetValidateData()
    }

    fun resetValidateData() {
        validateData = LoginState.Idle
    }

        fun validateDataCollection(email: String, password: String) {
        viewModelScope.launch {
            validateData = LoginState.Loading
            if (validateEmailData(email)) {
                if (validatePasswordData(password)) {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            validateData = if (task.isSuccessful) {
                                LoginState.Valid
                            } else {
                                if (task.exception is FirebaseAuthInvalidUserException
                                    && (task.exception as FirebaseAuthInvalidUserException).errorCode == "ERROR_USER_NOT_FOUND") {
                                    LoginState.UserNotValid
                                } else {
                                    LoginState.Failure
                                }
                            }
                        }
                } else {
                    validateData = LoginState.FailurePassword
                }
            } else {
                validateData = LoginState.FailureEmail
            }
        }
    }

    private fun validateEmailData(email: String): Boolean {
        return if (email.isNotBlank()) {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else {
            false
        }
    }

    private fun validatePasswordData(password: String): Boolean {
        return if (password.isNotBlank()) {
            password.length >= 8
        } else {
            false
        }
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Valid : LoginState()
    object Failure : LoginState()
    object UserNotValid : LoginState()
    object FailureEmail : LoginState()
    object FailurePassword: LoginState()
}
