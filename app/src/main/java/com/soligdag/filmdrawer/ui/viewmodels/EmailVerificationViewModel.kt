package com.soligdag.filmdrawer.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soligdag.filmdrawer.data.RepositoryResource
import com.soligdag.filmdrawer.data.repositories.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailVerificationViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    private var _uiState= MutableStateFlow(EmailVerificationScreenState())
    var uiState = _uiState.asStateFlow()

    private val emailAddress : String = userDataRepository.getUserEmailAddress()?:""
    val maskedEmailAddress : String =  getMaskedEmail(emailAddress)

    init {
        checkIfEmailIsVerified() // check if email is verified and keep checking after every 2 seconds until the email is verified
    }
    private fun getMaskedEmail(email : String) : String {
        val regex = """^([^@]{2})([^@]+)([^@]{0}@)([^@]{4})""".toRegex()
        val emailMask = email.replace(regex) {
            it.groupValues[1] + "*".repeat(it.groupValues[2].length) + it.groupValues[3] + "*".repeat(it.groupValues[4].length) }
        return emailMask
    }

    fun resetState() {
        _uiState.value = EmailVerificationScreenState()
    }
    fun sendVerificationEmail() {
        _uiState.value = EmailVerificationScreenState().copy(isLoading = true, emailSent = false)
        viewModelScope.launch {
            when(val response = userDataRepository.resendVerificationEmailToUser()) {
                is RepositoryResource.Success -> {
                    if(response.value) {
                        _uiState.value = EmailVerificationScreenState().copy(isLoading = false,emailSent = true)
                    }
                }
                is RepositoryResource.Error -> {
                    _uiState.value = EmailVerificationScreenState().copy(isLoading = false,emailSent = false)
                    Log.d("", response.error.errorMessage)
                }
            }
        }
    }
    private fun checkIfEmailIsVerified() {
        viewModelScope.launch {
            while (!_uiState.value.emailVerified) {
                delay(3000)
                when(val response = userDataRepository.reloadUserInfo()) {
                    is RepositoryResource.Success -> {
                        val user = response.value
                        if(user.isEmailVerified == true) { // User has verified the email
                            _uiState.value = EmailVerificationScreenState(emailVerified = true)
                        }
                    }
                    is RepositoryResource.Error -> { }
                }
            }

        }
    }

}

data class EmailVerificationScreenState(
    val isLoading: Boolean = false,
    val emailSent : Boolean = false,
    val emailVerified : Boolean = false,
)