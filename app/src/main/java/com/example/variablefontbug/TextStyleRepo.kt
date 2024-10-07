package com.example.variablefontbug

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class TextStyleData(
    val fontIndex: Int = 0,
    val fontWeight: Int = 100,
)

class TextStyleRepo(private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "text_style_prefs")

    val textStyle: Flow<TextStyleData> = context.dataStore.data
        .map { preferences ->
            val fontIndex = preferences[FONT_INDEX_KEY] ?: 0
            val fontWeight = preferences[FONT_WEIGHT_KEY] ?: 100
            TextStyleData(fontIndex, fontWeight)
        }

    suspend fun setStyle(textStyleData: TextStyleData) {
        context.dataStore.edit { preferences ->
            preferences[FONT_INDEX_KEY] = textStyleData.fontIndex
            preferences[FONT_WEIGHT_KEY] = textStyleData.fontWeight
        }
    }

    companion object {
        private val FONT_INDEX_KEY = intPreferencesKey("FONT_INDEX")
        private val FONT_WEIGHT_KEY = intPreferencesKey("FONT_WEIGHT")
    }
}