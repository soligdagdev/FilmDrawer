package com.soligdag.filmdrawer.data.repositories

import com.soligdag.filmdrawer.data.RepositoryResource
import com.soligdag.filmdrawer.data.models.MediaItem
import com.soligdag.filmdrawer.data.models.WishlistItem
import com.soligdag.filmdrawer.data.network.NetworkAPIService
import com.soligdag.filmdrawer.data.room.FilmDrawerDatabase
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val database: FilmDrawerDatabase
) : UserDataRepository {
    override suspend fun addItemToWishlist(mediaItem: MediaItem): RepositoryResource<MediaItem> {
        val wishlistItem = WishlistItem(mediaItem = mediaItem)
        database.wishlistDao().insertAll(wishlistItem)
        return RepositoryResource.Success(mediaItem)
    }

    override suspend fun getAllWishlistItems(): RepositoryResource<List<WishlistItem>> {
        val allWishlistItems = database.wishlistDao().getAll()
        return RepositoryResource.Success(allWishlistItems)
    }

    override suspend fun deleteWishlistItem(wishlistItem: WishlistItem): RepositoryResource<List<WishlistItem>> {
        database.wishlistDao().delete(wishlistItem)
        val allWishlistItems = database.wishlistDao().getAll()
        return RepositoryResource.Success(allWishlistItems)
    }
}