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
    private val _email = MutableLiveData("hammadahmed767@gmail.com")
    val email = _email
    private val _password = MutableLiveData("12345678")
    val password = _password
    private val _confirmPassword = MutableLiveData("12345678")
    val confirmPassword = _confirmPassword
    private val _firstName = MutableLiveData("Hammad")
    val firstName = _firstName
    private val _lastName= MutableLiveData("Ahmed")
    val lastName = _lastName
    private var _uiState= MutableStateFlow(SignupScreenState())
    var uiState = _uiState.asStateFlow()

    fun onEmailChange(newValue: String) {
        _email.value = newValue
    }
    fun onFirstNameChange(newValue: String) {
        _firstName.value = newValue
    }
    fun onLastNameChange(newValue: String) {
        _lastName.value = newValue
    }
    fun onPasswordChange(newValue : String) {
        _password.value = newValue
    }
    fun onConfirmPasswordChange(newValue : String) {
        _confirmPassword.value = newValue
    }

    fun signupUser() {
        _uiState.value = SignupScreenState(isLoading = true)
        viewModelScope.launch {
            when(val response = userDataRepository.signUpUserWithCredentials(email = email.value!!,password = password.value!!, firstName = firstName.value!!, lastName = lastName.value!!)) {

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