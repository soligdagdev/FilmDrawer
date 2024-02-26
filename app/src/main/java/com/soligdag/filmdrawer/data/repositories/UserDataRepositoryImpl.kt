package com.soligdag.filmdrawer.data.repositories

import com.google.firebase.Firebase
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.firestore.firestore
import com.soligdag.filmdrawer.data.RepositoryResource
import com.soligdag.filmdrawer.data.ResourceError
import com.soligdag.filmdrawer.data.models.MediaItem
import com.soligdag.filmdrawer.data.models.Recommendation
import com.soligdag.filmdrawer.data.models.User
import com.soligdag.filmdrawer.data.models.WishlistItem
import com.soligdag.filmdrawer.data.room.FilmDrawerDatabase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val database: FilmDrawerDatabase
) : UserDataRepository {


    private val firebaseAuthInstance = FirebaseAuth.getInstance()

    private val fireStore = Firebase.firestore
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
            val isEmailVerified = firebaseAuthInstance.currentUser?.isEmailVerified ?: false
            RepositoryResource.Success(isEmailVerified)
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
                "lastName" to lastName
            )
            fireStore.collection("Users").add(firestoreUser).await()
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
            val user = User(firebaseAuthInstance.currentUser!!)
            RepositoryResource.Success(user)
        } catch (ex: Exception) {
            RepositoryResource.Error(error = ResourceError(errorMessage = ex.message ?: ""))
        }
    }

    override suspend fun logoutUser(): RepositoryResource<Boolean> {
        firebaseAuthInstance.signOut()
        return RepositoryResource.Success(true)
    }


}