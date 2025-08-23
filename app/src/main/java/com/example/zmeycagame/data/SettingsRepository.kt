package com.example.zmeycagame.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class SettingsRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private val dataStore = context.dataStore

    private object PreferencesKeys {
        val GAME_SPEED = intPreferencesKey("game_speed")
        val GRID_SIZE = intPreferencesKey("grid_size")
        val VIBRATION_ENABLED = booleanPreferencesKey("vibration_enabled")
        val SOUNDS_ENABLED = booleanPreferencesKey("sounds_enabled")
    }

    val gameSpeed: Flow<Int> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.GAME_SPEED] ?: 120
    }

    suspend fun setGameSpeed(speed: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.GAME_SPEED] = speed
        }
    }

    val gridSize: Flow<Int> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.GRID_SIZE] ?: 20
    }

    suspend fun setGridSize(size: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.GRID_SIZE] = size
        }
    }

    val vibrationEnabled: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.VIBRATION_ENABLED] ?: true
    }

    suspend fun setVibrationEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.VIBRATION_ENABLED] = enabled
        }
    }

    val soundsEnabled: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.SOUNDS_ENABLED] ?: true
    }

    suspend fun setSoundsEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SOUNDS_ENABLED] = enabled
        }
    }
}