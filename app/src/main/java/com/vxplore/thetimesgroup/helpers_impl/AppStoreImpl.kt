package com.vxplore.thetimesgroup.helpers_impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.vxplore.core.common.PrefConstants
import com.vxplore.core.helpers.AppStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppStoreImpl @Inject constructor(
    private val prefs: DataStore<androidx.datastore.preferences.core.Preferences>
) : AppStore {
    override suspend fun intro(status: Boolean) {
        prefs.edit {
            it[booleanPreferencesKey(PrefConstants.INTRO_STATUS)] = status
        }
    }

    override suspend fun isIntroDone(): Boolean {
        return prefs.data.map {
            it[booleanPreferencesKey(PrefConstants.INTRO_STATUS)]
        }.first() ?: false
    }

    override suspend fun login(userId: String) {
        prefs.edit {
            it[stringPreferencesKey(PrefConstants.USER_ID)] = userId
        }
    }

    override suspend fun userId(): String {
        return prefs.data.map {
            it[stringPreferencesKey(PrefConstants.USER_ID)]
        }.first() ?: ""
    }

    override suspend fun logout() {
        prefs.edit {
            if (it.contains(stringPreferencesKey(PrefConstants.USER_ID))) {
                it.remove(stringPreferencesKey(PrefConstants.USER_ID))
            }
        }
    }

    override suspend fun isLoggedIn(): Boolean {
        val userID = prefs.data.map {
            it[stringPreferencesKey(PrefConstants.USER_ID)]
        }.first()

        if (userID != null && userID.isNotEmpty()) return true

        return false
    }

    override suspend fun storeFCMToken(token: String) {
        prefs.edit {
            it[stringPreferencesKey(PrefConstants.FCM_TOKEN)] = token
        }
    }

    override suspend fun lastFCMToken(): String {
        return prefs.data.map {
            it[stringPreferencesKey(PrefConstants.FCM_TOKEN)]
        }.first() ?: ""
    }

    override suspend fun removeLastFCMToken(): Boolean {
        return prefs.edit {
            it.remove(stringPreferencesKey(PrefConstants.FCM_TOKEN))
        }.contains(stringPreferencesKey(PrefConstants.FCM_TOKEN))
    }
}