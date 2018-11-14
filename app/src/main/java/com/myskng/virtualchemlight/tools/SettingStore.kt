package com.myskng.virtualchemlight.tools

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import java.lang.Exception

class SettingStore(val context: Context) {
    companion object {
        private inline fun <reified T> convertToNumber(text: String?, defvalue: Int): T {
            when (T::class) {
                Int::class -> {
                    return try {
                        text!!.toInt() as T
                    } catch (_: Exception) {
                        defvalue as T
                    }
                }
                Float::class -> {
                    return try {
                        text!!.toFloat() as T
                    } catch (_: Exception) {
                        defvalue.toFloat() as T
                    }
                }
                Double::class -> {
                    return try {
                        text!!.toDouble() as T
                    } catch (_: Exception) {
                        defvalue.toDouble() as T
                    }
                }
            }
            throw NotImplementedError()
        }
    }

    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    var uoForce: Float
        get() {
            // 設定画面に数値じゃなくて文字を入れる不届き者への対策
            return convertToNumber(sharedPreferences.getString("pref_key_uoforce", "40"), 40)
        }
        set(value) {
            sharedPreferences.edit().putString("pref_key_uoforce", value.toString()).apply()
        }
}