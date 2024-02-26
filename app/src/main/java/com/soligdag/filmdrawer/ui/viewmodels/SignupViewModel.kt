package com.soligdag.filmdrawer.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soligdag.filmdrawer.data.RepositoryResource
import com.soligdag.filmdrawer.data.repositories.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository
) : ViewModel() {
    private val _email = MutableLiveData("")
    val email = _email
    private val _password = MutableLiveData("")
    val password = _password
    private val _confirmPassword = MutableLiveData("")
    val confirmPassword = _confirmPassword
    private var _uiState= MutableStateFlow(SignupScreenState())
    var uiState = _uiState.asStateFlow()

    fun onEmailChange(newValue: String) {
        _email.value = newValue
    }
    fun onPasswordChange(newValue : String) {
        _password.value = newValue
    }
    fun onConfirmPasswordChange(newValue : String) {
        _confirmPassword.value = newValue
    }

    fun signupUser() {
        _uiState.value = SignupScreenState(isLoading = true)
        //_uiState.value = SignupScreenState(isLoading = false,signupErrorMessage = "", hasSignedUp = true)
        viewModelScope.launch {
            when(val response = userDataRepository.signUpUserWithCredentials(email.value!!,password.value!!)) {
                is RepositoryResource.Success -> {
                    _uiState.value = SignupScreenState(isLoading = false,signupErrorMessage = "", hasSignedUp = true)
                }
                is RepositoryResource.Error -> {
                    _uiState.value = SignupScreenState(isLoading = false,signupErrorMessage = response.error.errorMessage, hasSignedUp = false)
                }

            }
        }
    }

    fun resetState() {
        _uiState.update { it.copy(isLoading = false, signupErrorMessage = "", hasSignedUp = false) }
    }
}

data class SignupScreenState(
    val isLoading: Boolean = false,
    val signupErrorMessage: String = "",
    val hasSignedUp: Boolean = false
)