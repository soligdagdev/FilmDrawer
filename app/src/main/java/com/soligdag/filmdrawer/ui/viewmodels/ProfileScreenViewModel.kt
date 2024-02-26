package com.soligdag.filmdrawer.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soligdag.filmdrawer.data.repositories.MediaRepository
import com.soligdag.filmdrawer.data.repositories.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(val mediaRepository : MediaRepository, val userDataRepository: UserDataRepository)  : ViewModel() {

    fun logoutUser() {
        viewModelScope.launch {
            userDataRepository.logoutUser()
        }

    }
}