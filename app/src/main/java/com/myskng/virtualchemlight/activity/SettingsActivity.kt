package com.myskng.virtualchemlight.activity

import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import com.myskng.virtualchemlight.R

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, SettingFragment()).commit()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return false
            }
        }
        return super.onOptionsItemSelected(item)
    }

    class SettingFragment : PreferenceFragmentCompat(),
            SharedPreferences.OnSharedPreferenceChangeListener {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            addPreferencesFromResource(R.xml.pref_screen)
            sharedPreferences.registerOnSharedPreferenceChangeListener(this)
            sharedPreferences.all.forEach { item ->
                val prefobj = findPreference(item.key)
                if (prefobj is EditTextPreference) {
                    prefobj.summary = item.value.toString()
                }
            }
        }

        val sharedPreferences: SharedPreferences by lazy {
            preferenceManager.sharedPreferences
        }

        override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
            val prefmap = sharedPreferences!!.all
            val changedpref = prefmap[key]
            val prefobj = findPreference(key)
            if (prefobj is EditTextPreference) {
                prefobj.summary = changedpref.toString()
            }
        }

        override fun onDestroy() {
            super.onDestroy()
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        }
    }
}
