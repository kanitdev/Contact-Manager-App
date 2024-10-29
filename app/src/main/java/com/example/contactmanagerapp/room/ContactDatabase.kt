package com.example.contactmanagerapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase:RoomDatabase() {
    abstract val contactDAO: ContactDAO

    //singleton design pattern
    companion object{
        @Volatile
        private var INSTANCE: ContactDatabase?= null
        fun getInstance(context: Context): ContactDatabase {
            synchronized(this){
                var instance = INSTANCE
                if (instance==null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ContactDatabase::class.java,
                        "contact_db"
                    ).build()

                }
                INSTANCE = instance
                return instance
            }

        }

    }


}
