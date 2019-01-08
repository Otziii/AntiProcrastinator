package com.jorfald.antiprocrastinator.antiprocrastinator

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [ThingToDo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): ThingToDoDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "appdatabase.db"
                    )
                        .build()
                }
            }
            return INSTANCE
        }
    }
}