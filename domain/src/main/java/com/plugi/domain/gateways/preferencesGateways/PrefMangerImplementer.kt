package com.plugi.domain.gateways.preferencesGateways

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.provider.Settings
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.plugi.domain.Domain
import com.plugi.domain.aamodels.CustomerModel
import com.plugi.domain.aamodels.UserModel
import java.util.*
import kotlin.collections.HashSet

private const val NAME = "PREFERENCES_NAME"


val preferencesGateway by lazy {
    PrefMangerImplementer(
        PreferencesHelper(
            Domain.application.getSharedPreferences(
                NAME,
                MODE_PRIVATE
            )
        )
    )
}

object PreCon {
    internal const val LANDING_STATUS = "a1"
    internal const val LANGUAGE_SETTINGS = "a3"
    internal const val STATUS_LOGGED = "a4"
    internal const val USER_MODEL = "a5"
    internal const val API_TOKEN = "a6"
    internal const val COUNTRY = "a7"
    internal const val FIREBASE_NOTIFICATION = "a9"
    internal const val LAST_DATABASE_UPDATE = "b1"


    internal const val FILTER_SPECIAL = "u1"
    internal const val FILTER_MYTable = "u2"
    internal const val FILTER_BLOCK = "u3"

    internal const val DARK_MODE = "p3"
    internal const val IS_24_HOUR = "p24"

    internal const val EVENT_CALENDAR = "p2045"
    internal const val VISITOR_MODE = "pQ2045"
    internal const val HAS_SELECT_LANGUAGE = "aq_1"
}

class PrefMangerImplementer(
    private val pref: PreferencesHelper
) : PrefManger {


    override fun saveApiToken(token: String) {
        pref.save(PreCon.API_TOKEN, token)
    }

    override fun loadApiToken(): String {
        return pref.load(PreCon.API_TOKEN, "")
//        return "1ilyNvJ6BwCmW7V2"
    }

    override fun setHasSelectLanguage(b: Boolean) {
        pref.save(PreCon.HAS_SELECT_LANGUAGE, b)
    }

    override fun getHasSelectLanguage(): Boolean {
        return pref.load(PreCon.HAS_SELECT_LANGUAGE, false)
    }

    override fun getFilterSpecial(): IntArray {
        val stringValue = pref.load(PreCon.FILTER_SPECIAL, "")
        if (stringValue == null || stringValue.isEmpty()) {
            return intArrayOf()
        } else {
            val myType = object : TypeToken<IntArray>() {}.type
            val logs = Gson().fromJson<IntArray>(stringValue, myType)
            return logs
        }
    }

    override fun setFilterSpecial(value: IntArray) {
        val toJson = Gson().toJson(value)
        pref.save(PreCon.FILTER_SPECIAL, toJson)
    }

    override fun getFilterTable(): Int {
        return pref.load(PreCon.FILTER_MYTable, -1)
    }

    override fun setFilterTable(value: Int) {
        pref.save(PreCon.FILTER_MYTable, value)
    }

    override fun getFilterBlock(): Int {
        return pref.load(PreCon.FILTER_BLOCK, -1)
    }

    override fun setFilterBlock(value: Int) {
        pref.save(PreCon.FILTER_BLOCK, value)
    }

    override fun saveCountryId(id: Int) {
        pref.save(PreCon.COUNTRY, id)
    }

    override fun loadCountryId(): Int {
        return pref.load(PreCon.COUNTRY, -1)
    }

    override fun getAuthorization(): String {
        return """Bearer ${loadApiToken()}"""
    }


    override fun setUserLooged(isLogged: Boolean) {
        if (isLogged) {
            setInVisitorMode(false)
        }
        pref.save(PreCon.STATUS_LOGGED, isLogged)
    }

    override fun getUserLogged(): Boolean {
        return pref.load(PreCon.STATUS_LOGGED, false)
    }

    override fun getSystemLanguage(): String {
        return pref.load(PreCon.LANGUAGE_SETTINGS, "ar")
    }

    override fun setSystemLanguage(language: String) {
        pref.save(PreCon.LANGUAGE_SETTINGS, language)
    }


    override fun getLandingStatus(): Boolean {
        return pref.load(PreCon.LANDING_STATUS, false)
    }

    override fun setLandingStatus(isViewed: Boolean) {
        pref.save(PreCon.LANDING_STATUS, isViewed)
    }

    override fun getFireBaseToken(): String {
        return pref.load(PreCon.FIREBASE_NOTIFICATION, "token")
    }

    override fun setFireBaseToken(token: String) {
        pref.save(PreCon.FIREBASE_NOTIFICATION, token)
    }

    override fun getLastDataBaseUpdate(): String {
        return pref.load(PreCon.LAST_DATABASE_UPDATE, "1970-03-16 12:00:00")
//        return pref.load(PreCon.LAST_DATABASE_UPDATE, "2020-03-16")
    }

    override fun setLastDataBaseUpdate(date: String) {
        pref.save(PreCon.LAST_DATABASE_UPDATE, date)
    }

    override fun isDarkMode(): Boolean {
        return pref.load(PreCon.DARK_MODE, false)
    }

    override fun changeDarkMode(isDark: Boolean) {
        pref.save(PreCon.DARK_MODE, isDark)
    }

    override fun is24Hour(): Boolean {
        return pref.load(PreCon.IS_24_HOUR, false)
    }

    override fun change24Hour(is24Hour: Boolean) {
        pref.save(PreCon.IS_24_HOUR, is24Hour)
    }

    @SuppressLint("HardwareIds")
    override fun getDeviceIndentifer(): String {
        return Settings.Secure.getString(
            Domain.application.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }

    override fun setInVisitorMode(isVisitor: Boolean) {
        pref.save(PreCon.VISITOR_MODE, isVisitor)
    }

    override fun isInVisitorMode(): Boolean {
        return pref.load(PreCon.VISITOR_MODE, false)
    }

    override fun addEventToCalendar(eventId: Int) {
        val favourite: Set<String> =
            pref.load(PreCon.EVENT_CALENDAR, HashSet())
        val newList: MutableSet<String> =
            TreeSet(favourite)
        newList.add(eventId.toString())
        pref.save(PreCon.EVENT_CALENDAR, newList)
    }

    override fun isEventToCalendar(eventId: Int): Boolean {
        //EVENT_CALENDAR
        val events: Set<String> =
            pref.load<Set<String>>(PreCon.EVENT_CALENDAR, HashSet())
        return events.contains(eventId.toString())
    }


    override fun setUserData(value: UserModel?) {
        value?.let {
            val toJson = Gson().toJson(it)
            pref.save(PreCon.USER_MODEL, toJson)
            saveApiToken(it.token)
            setUserLooged(true)
        } ?: kotlin.run {
            pref.save(PreCon.USER_MODEL, "")
            setUserLooged(false)
        }
    }

    override fun loadUserData(): UserModel? {
        val stringModel = pref.load(PreCon.USER_MODEL, "")
        return if (stringModel.isEmpty()) {
            null
        } else {
            val person1: UserModel = Gson().fromJson(stringModel, UserModel::class.java)
            person1
        }
    }
}

interface PrefManger {

    fun getLandingStatus(): Boolean
    fun setLandingStatus(isViewed: Boolean)

    //    fun saveUserData(mode: UserModel?)
//    fun loadUserData(): UserModel?
    fun getUserLogged(): Boolean
    fun setUserLooged(isLogged: Boolean)

    fun setSystemLanguage(language: String)
    fun getSystemLanguage(): String


    fun saveApiToken(token: String)
    fun loadApiToken(): String

    fun saveCountryId(id: Int)
    fun loadCountryId(): Int

    fun getAuthorization(): String
    fun getFireBaseToken(): String
    fun setFireBaseToken(token: String)
    fun getDeviceIndentifer(): String

    fun getLastDataBaseUpdate(): String
    fun setLastDataBaseUpdate(date: String)

    fun getFilterSpecial(): IntArray
    fun setFilterSpecial(value: IntArray)

    fun getFilterTable(): Int
    fun setFilterTable(value: Int)

    fun getFilterBlock(): Int
    fun setFilterBlock(value: Int)

    fun isDarkMode(): Boolean
    fun changeDarkMode(isDark: Boolean)


    fun is24Hour(): Boolean
    fun change24Hour(is24Hour: Boolean)

    fun addEventToCalendar(eventId: Int)
    fun isEventToCalendar(eventId: Int): Boolean

    fun setInVisitorMode(isVisitor: Boolean = true)
    fun isInVisitorMode(): Boolean
    fun setHasSelectLanguage(b: Boolean)
    fun getHasSelectLanguage():Boolean
    fun setUserData(value: UserModel?)
    fun loadUserData(): UserModel?
}

