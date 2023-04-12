package com.plugi.domain.gateways.preferencesGateways

import android.content.SharedPreferences

class PreferencesHelper(val sharedPreferences: SharedPreferences) {
     inline fun <reified T : Any> save(key: String, value: T) {
        sharedPreferences.edit().apply { putValue(key, value) }.apply()
    }


     inline fun <reified T : Any> load(key: String, defaultValue: T): T {
        return sharedPreferences.getValue(key, defaultValue)
    }


     fun isSaved(key: String): Boolean {
        return sharedPreferences.contains(key)
    }


     fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

}

inline fun <reified T : Any> SharedPreferences.Editor.putValue(key: String, value: T) {
    when (T::class) {
        Boolean::class -> putBoolean(key, value as Boolean)
        Int::class -> putInt(key, value as Int)
        Long::class -> putLong(key, value as Long)
        Float::class -> putFloat(key, value as Float)
        String::class -> putString(key, value as String)
        Set::class -> putStringSet(key,value as Set<String>)
        HashSet::class -> putStringSet(key,value as Set<String>)
        else -> throw UnsupportedOperationException("not supported preferences type")
    }
}

inline fun <reified T : Any> SharedPreferences.getValue(key: String, defaultValue: T): T {
    return when (T::class) {
        Boolean::class -> getBoolean(key, defaultValue as Boolean) as T
        Int::class -> getInt(key, defaultValue as Int) as T
        Long::class -> getLong(key, defaultValue as Long) as T
        Float::class -> getFloat(key, defaultValue as Float) as T
        String::class -> getString(key, defaultValue as String) as T
        Set::class -> getStringSet(key, defaultValue as Set<String>) as T
        HashSet::class -> getStringSet(key, defaultValue as Set<String>) as T
        else -> throw UnsupportedOperationException("not supported preferences type")
    }
}

