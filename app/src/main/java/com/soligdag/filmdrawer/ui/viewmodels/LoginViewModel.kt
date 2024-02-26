package com.soligdag.filmdrawer.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soligdag.filmdrawer.data.RepositoryResource
import com.soligdag.filmdrawer.data.repositories.MediaRepository
import com.soligdag.filmdrawer.data.repositories.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository
) : ViewModel() {
    private val _email = MutableLiveData("")
    val email = _email
    private val _password = MutableLiveData("")
    val password = _password

    private var _uiState= MutableStateFlow(LoginScreenState())
    var uiState = _uiState.asStateFlow()

    fun updateEmail(newText : String) {
        _email.value = newText
    }
    fun updatePassword(newText : String) {
        _password.value = newText
    }
    fun resetErrorState() {
        _uiState.update { it.copy(loginErrorMessage = "") }
    }
    fun loginUser() {
        _uiState.update { currentState -> currentState.copy(isLoading = true, loginErrorMessage = "")}
        viewModelScope.launch {
            when (val response = userDataRepository.signInUserWithCredentials(email = email.value!!, password = password.value!!)){
                is RepositoryResource.Success -> {
                    val isEmailVerified = response.value
                    _uiState.update { currentState -> currentState.copy(isLoading = false, hasLoggedIn = true, isEmailVerified = isEmailVerified)}
                }
                is RepositoryResource.Error -> {
                    _uiState.update { currentState -> currentState.copy(isLoading = false, loginErrorMessage = response.error.errorMessage)}
                }
            }


        }
    }


//    private var _uiState= MutableStateFlow(SearchUIState())
//    var uiState = _uiState.asStateFlow()
}

data class LoginScreenState(
    val isLoading: Boolean = false,
    val isEmailVerified: Boolean = false,
    val loginErrorMessage: String = "",
    val hasLoggedIn: Boolean = false
)