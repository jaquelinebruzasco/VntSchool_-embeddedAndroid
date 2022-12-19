package br.org.venturus.mentoriasoftex.screen.createAccount

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class CreateAccountViewModel : ViewModel() {

    private var auth: FirebaseAuth = Firebase.auth

    var validateField by mutableStateOf<CreateAccountState>(CreateAccountState.Idle)

    init {
        resetValidateField()
    }

    fun resetValidateField() {
        validateField = CreateAccountState.Idle
    }

    fun validateNewAccountField(email: String, password: String) {
        if (validateEmailField(email)) {
            if (validatePasswordField(password)) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        validateField = if (task.isSuccessful) {
                            CreateAccountState.Valid
                        } else {
                            CreateAccountState.Failure
                        }
                    }
            } else {
                validateField = CreateAccountState.FailurePassword
            }

        } else {
            validateField = CreateAccountState.FailureEmail
        }
    }

    private fun validateEmailField(email: String): Boolean {
        return if (email.isNotBlank()) {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else {
            false
        }
    }

    private fun validatePasswordField(password: String): Boolean {
        return if (password.isNotBlank()) {
            password.length >= 8
        } else {
            false
        }
    }
}

sealed class CreateAccountState {
    object Idle : CreateAccountState()
    object Valid : CreateAccountState()
    object Failure : CreateAccountState()
    object FailureEmail : CreateAccountState()
    object FailurePassword : CreateAccountState()
}