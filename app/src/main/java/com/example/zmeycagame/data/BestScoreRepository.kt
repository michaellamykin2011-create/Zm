package com.example.zmeycagame.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "best_score")

@Singleton
class BestScoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private val dataStore = context.dataStore

    private object PreferencesKeys {
        val BEST_SCORE = intPreferencesKey("best_score")
    }

    val bestScore: Flow<Int> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.BEST_SCORE] ?: 0
    }

    suspend fun updateBestScore(score: Int) {
        dataStore.edit { preferences ->
            val currentBestScore = preferences[PreferencesKeys.BEST_SCORE] ?: 0
            if (score > currentBestScore) {
                preferences[PreferencesKeys.BEST_SCORE] = score
            }
        }
    }

    suspend fun clearBestScore() {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.BEST_SCORE] = 0
        }
    }
}