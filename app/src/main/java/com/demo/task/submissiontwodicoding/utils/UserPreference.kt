package com.demo.task.submissiontwodicoding.utils

import android.content.Context
import com.demo.task.submissiontwodicoding.models.UserModel

internal class UserPreference(context: Context) {
    companion object {
        private const val PREF_NAME = "user_pref"
        private const val NAME = "name"
        private const val EMAIL = "email"
        private const val AGE = "age"
        private const val PHONE_NO = "phone"
        private const val LOVE_MU = "isLove"
    }

    private val preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun setUsers(value: UserModel) {
        val editor = preferences.edit()
        editor.putString(NAME, value.name)
        editor.putString(EMAIL, value.email)
        editor.putInt(AGE, value.age)
        editor.putString(PHONE_NO, value.phoneNumber)
        editor.putBoolean(LOVE_MU, value.isLove)

        editor.apply()
    }

    fun getUsers(): UserModel {
        val model = UserModel()
        model.name = preferences.getString(NAME, "")
        model.email = preferences.getString(EMAIL, "")
        model.age = preferences.getInt(AGE, 0)
        model.phoneNumber = preferences.getString(PHONE_NO, "")
        model.isLove = preferences.getBoolean(LOVE_MU, false)

        return model
    }
}