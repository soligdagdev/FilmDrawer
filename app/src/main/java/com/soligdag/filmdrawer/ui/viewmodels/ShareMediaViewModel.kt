package com.soligdag.filmdrawer.ui.viewmodels

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soligdag.filmdrawer.data.models.CastList
import com.soligdag.filmdrawer.data.models.SeriesDetail
import com.soligdag.filmdrawer.data.repositories.MediaRepository
import com.soligdag.filmdrawer.data.repositories.UserDataRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import io.branch.indexing.BranchUniversalObject
import io.branch.referral.util.LinkProperties
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ShareMediaViewModel @Inject constructor( private val mediaRepository: MediaRepository, private val userDataRepository : UserDataRepositoryImpl) : ViewModel() {

    private var _uiState = MutableStateFlow(ShareMediaState())
    val uiState = _uiState.asStateFlow()


    fun resetState() {
        _uiState.update { it.copy(isLoading = false, showNativeShareDialog = false ) }
    }

    fun getExternalLinkToShare(context: Context) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val url = GlobalScope.async { getShareURL(context) }.await()
            _uiState.update { it.copy(isLoading = false, externalShareURL = url, showNativeShareDialog = true ) }
        }
    }


    private suspend fun getShareURL(context: Context): String = withContext(Dispatchers.IO) {
        val buo = BranchUniversalObject()
            .setCanonicalIdentifier("content/60622")
        // Create a Link Properties instance
        val lp = LinkProperties()
            .setCampaign("sharing media")

        buo.getShortUrl(context, lp)
    }



    data class ShareMediaState(
        val isLoading: Boolean = false,
        val externalShareURL: String = "",
        val showNativeShareDialog: Boolean = false,
    )


}