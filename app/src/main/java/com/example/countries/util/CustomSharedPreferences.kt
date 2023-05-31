package com.example.countries.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class CustomSharedPreferences {

    companion object{

        private const val PrefTime = "PrefTime"
        private var sharedPreferences: SharedPreferences? = null

        @Volatile private var  instance: CustomSharedPreferences? = null

        private val lock = Any()
        operator fun invoke(context: Context): CustomSharedPreferences = instance ?: synchronized(lock){

            instance ?: makeCustomSharedP(context).also{

                instance = it

            }
        }


        private fun makeCustomSharedP(context: Context): CustomSharedPreferences{

            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPreferences()
        }


    }

     fun saveTime(time: Long){
        sharedPreferences?.edit(commit = true) {
            putLong(PrefTime,time)
        }
    }

    fun getTime() = sharedPreferences?.getLong(PrefTime,0)

}
