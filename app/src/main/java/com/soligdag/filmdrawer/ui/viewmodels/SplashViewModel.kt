package com.soligdag.filmdrawer.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soligdag.filmdrawer.data.RepositoryResource
import com.soligdag.filmdrawer.data.repositories.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    private var _uiState= MutableStateFlow(SplashScreenState())
    var uiState = _uiState.asStateFlow()

    init {
        checkUserData()
    }

    private fun checkUserData() {
        viewModelScope.launch {
            delay(500)
            when(val response = userDataRepository.reloadUserInfo()) {
                is RepositoryResource.Error -> {
                    _uiState.value = SplashScreenState(hasDataReceived = true, isLoggedIn = false)
                }
                is RepositoryResource.Success -> {
                    val user = response.value
                    if(user.uid!=null)
                        _uiState.value = SplashScreenState(hasDataReceived = true, isLoggedIn = true, isEmailVerified = user.isEmailVerified?:false)
                    else
                        _uiState.value = SplashScreenState(hasDataReceived = true, isLoggedIn = false, isEmailVerified = false)
                }
            }
            //_uiState.value = SplashScreenState(hasDataReceived = true, isLoggedIn = false)

        }
    }
}

data class SplashScreenState(
    val hasDataReceived : Boolean = false,
    val isLoggedIn : Boolean = false,
    val isEmailVerified : Boolean = false,
)