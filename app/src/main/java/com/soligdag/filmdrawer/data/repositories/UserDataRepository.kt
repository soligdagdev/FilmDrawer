package com.soligdag.filmdrawer.data.repositories

import com.soligdag.filmdrawer.data.RepositoryResource
import com.soligdag.filmdrawer.data.models.MediaItem
import com.soligdag.filmdrawer.data.models.Recommendation
import com.soligdag.filmdrawer.data.models.User
import com.soligdag.filmdrawer.data.models.WishlistItem
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    suspend fun addItemToWishlist(mediaItem: MediaItem) : RepositoryResource<MediaItem>
    suspend fun getAllWishlistItems() : RepositoryResource<List<WishlistItem>>
    suspend fun deleteWishlistItem(wishlistItem: WishlistItem) : RepositoryResource<List<WishlistItem>>
    suspend fun getRecentRecommendations() : RepositoryResource<List<Recommendation>>
    suspend fun signInUserWithCredentials(email : String, password : String) : RepositoryResource<Boolean>
    suspend fun signUpUserWithCredentials(email : String, password : String, firstName : String, lastName : String) : RepositoryResource<Boolean>
    suspend fun resendVerificationEmailToUser() : RepositoryResource<Boolean>
     fun getUserEmailAddress() : String
    suspend fun reloadUserInfo() : RepositoryResource<User>

    suspend fun logoutUser() : RepositoryResource<Boolean>
    fun getWishListFlow() : Flow<List<WishlistItem>>

    suspend fun clearAllLocalData() : RepositoryResource<Boolean>
    suspend fun checkForLatestWishListData()

     var userObj : User
}