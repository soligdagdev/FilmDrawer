package com.soligdag.filmdrawer.data.repositories

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import com.soligdag.filmdrawer.data.RepositoryResource
import com.soligdag.filmdrawer.data.ResourceError
import com.soligdag.filmdrawer.data.models.MediaItem
import com.soligdag.filmdrawer.data.models.Recommendation
import com.soligdag.filmdrawer.data.models.User
import com.soligdag.filmdrawer.data.models.WishlistItem
import com.soligdag.filmdrawer.data.room.FilmDrawerDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val database: FilmDrawerDatabase,
    private val sharedPrefs : SharedPrefRepository
) : UserDataRepository {


    private val firebaseAuthInstance = FirebaseAuth.getInstance()
    private val fireStore = Firebase.firestore
    override var userObj : User = User()
    override fun getWishListFlow(): Flow<List<WishlistItem>>  = database.wishlistDao().getAllFlow()

    override suspend fun checkForLatestWishListData() {
        val localWishListVersion = sharedPrefs.getWishListVersionPref()
        if(localWishListVersion!=userObj.wishListVersion) { // Server and local WishListCounter does not align, get latest Wishlist From Server
            database.wishlistDao().deleteAll()
            val results = firestoreUserDocument().collection("WishList").get().await().toObjects(WishlistItem::class.java)
            database.wishlistDao().insertAllItems(results)
            sharedPrefs.setWishListVersionPref(userObj.wishListVersion) // Set local watchlist version to server watchlist version
        }
        else {
            Log.d("Loading From Cache","Wishlist is up to date")
        }
    }
    override suspend fun clearAllLocalData(): RepositoryResource<Boolean> {
        try {
            database.wishlistDao().deleteAll()
            sharedPrefs.clearAllSharedPrefs()
            return RepositoryResource.Success(true)
        }
        catch (ex : Exception) {
            return RepositoryResource.Error(ResourceError(errorMessage = ex.message.toString()))
        }

    }

    override suspend fun addItemToWishlist(mediaItem: MediaItem): RepositoryResource<MediaItem> {
        return try {
            val wishlistItem = WishlistItem(mediaItem = mediaItem)
            database.wishlistDao().insertAll(wishlistItem)
            firestoreUserDocument().collection("WishList").document(wishlistItem.id.toString()).set(wishlistItem).await()
            synchronizeWishListVersionNumber()
            RepositoryResource.Success(mediaItem)
        }
        catch (ex: Exception) {
            RepositoryResource.Error(ResourceError(errorMessage = ex.message.toString() ))
        }
    }

    private fun synchronizeWishListVersionNumber() {
        var newVersionNumber = userObj.wishListVersion+1
        userObj.wishListVersion = newVersionNumber
        val data = hashMapOf("wishListVersion" to newVersionNumber)
        firestoreUserDocument().set(data, SetOptions.merge())
        sharedPrefs.setWishListVersionPref(newVersionNumber)
    }
    private fun firestoreUserDocument() : DocumentReference {
        return fireStore.collection("Users").document(firebaseAuthInstance.currentUser?.uid?:"")
    }


    override suspend fun getAllWishlistItems(): RepositoryResource<List<WishlistItem>> {
        val allWishlistItems = database.wishlistDao().getAll()
        return RepositoryResource.Success(allWishlistItems)
    }

    override suspend fun deleteWishlistItem(wishlistItem: WishlistItem): RepositoryResource<List<WishlistItem>> {
        firestoreUserDocument().collection("WishList").document(wishlistItem.id.toString()).delete().await()
        database.wishlistDao().delete(wishlistItem)
        synchronizeWishListVersionNumber()
        val allWishlistItems = database.wishlistDao().getAll()
        return RepositoryResource.Success(allWishlistItems)
    }

    override suspend fun getRecentRecommendations(): RepositoryResource<List<Recommendation>> {
        val recentRecommendations = database.recommendationsDao().getAll()
        return RepositoryResource.Success(recentRecommendations)
    }

    override suspend fun signInUserWithCredentials(
        email: String,
        password: String
    ): RepositoryResource<Boolean> {
        return try {
            firebaseAuthInstance.signInWithEmailAndPassword(email, password).await()
            userObj = firestoreUserDocument().get().await().toObject(User::class.java)!!
            userObj.isEmailVerified = firebaseAuthInstance.currentUser?.isEmailVerified
            RepositoryResource.Success(userObj.isEmailVerified?:false)
        } catch (ex: FirebaseAuthInvalidCredentialsException) {
            RepositoryResource.Error(ResourceError(errorMessage = ex.message.toString() ))
        } catch (ex: Exception) {
            RepositoryResource.Error(ResourceError(errorMessage = ex.message.toString() ))
        }
    }

    private val emailVerificationActions = ActionCodeSettings.newBuilder()
        .setUrl("https://filmdrawer.page.link/emailVerification")
        .setAndroidPackageName("com.soligdag.filmdrawer", false, null)
        .build()

    override suspend fun signUpUserWithCredentials(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): RepositoryResource<Boolean> {
        return try {
            firebaseAuthInstance.createUserWithEmailAndPassword(email, password).await()
            firebaseAuthInstance.currentUser?.sendEmailVerification(emailVerificationActions)
                ?.await()
            val firestoreUser = hashMapOf(
                "id" to firebaseAuthInstance.currentUser?.uid,
                "email" to email,
                "firstName" to firstName,
                "lastName" to lastName,
                "wishListVersion" to 0
            )
            fireStore.collection("Users").document(firebaseAuthInstance.currentUser?.uid?:"").set(firestoreUser).await()
            userObj = firestoreUserDocument().get().await().toObject(User::class.java)!!
            RepositoryResource.Success(true)
        } catch (ex: Exception) {
            RepositoryResource.Error(ResourceError(errorMessage = ex.message.toString() ))
        }
    }

    override suspend fun resendVerificationEmailToUser(): RepositoryResource<Boolean> {
        return try {
            firebaseAuthInstance.currentUser?.sendEmailVerification(emailVerificationActions)
                ?.await()
            RepositoryResource.Success(true)
        } catch (ex: Exception) {
            RepositoryResource.Error(ResourceError(errorMessage = ex.message.toString() ))
        }
    }

    override fun getUserEmailAddress(): String {
        return firebaseAuthInstance.currentUser?.email ?: ""
    }

    override suspend fun reloadUserInfo(): RepositoryResource<User> {
        return try {
            firebaseAuthInstance.currentUser?.reload()?.await()
            userObj = firestoreUserDocument().get().await().toObject(User::class.java)!!
            userObj.isEmailVerified = firebaseAuthInstance.currentUser?.isEmailVerified
            RepositoryResource.Success(userObj)
        } catch (ex: Exception) {
            RepositoryResource.Error(error = ResourceError(errorMessage = ex.message ?: ""))
        }
    }

    override suspend fun logoutUser(): RepositoryResource<Boolean> {
        firebaseAuthInstance.signOut()
        sharedPrefs.clearAllSharedPrefs()
        return RepositoryResource.Success(true)
    }




}