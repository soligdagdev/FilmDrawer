package com.soligdag.filmdrawer.data.repositories

import com.soligdag.filmdrawer.data.RepositoryResource
import com.soligdag.filmdrawer.data.models.MediaItem
import com.soligdag.filmdrawer.data.models.WishlistItem

interface UserDataRepository {
    suspend fun addItemToWishlist(mediaItem: MediaItem) : RepositoryResource<MediaItem>
    suspend fun getAllWishlistItems() : RepositoryResource<List<WishlistItem>>
    suspend fun deleteWishlistItem(wishlistItem: WishlistItem) : RepositoryResource<List<WishlistItem>>
}