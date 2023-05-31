package com.example.countries.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.countries.models.Country


@Database(entities = [Country::class], version = 1)
abstract class Database: RoomDatabase() {

    abstract fun  countryDao(): DAO

    companion object{

       @Volatile private var instance: com.example.countries.service.Database? = null

        private val lock = Any()

        operator fun  invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }


        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            com.example.countries.service.Database::class.java,
            "countryDatabase"
        ).build()


    }

}