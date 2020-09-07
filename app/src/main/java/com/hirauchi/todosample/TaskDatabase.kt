package com.hirauchi.todosample

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//Databaseクラスは、RoomDatabaseを継承させて実装
//@Databaseをつける事で、Databaseクラスとなる
// entitiesには、データベースのEntityを配列で指定
// 今回はTaskを指定。versionには、データベースのバージョンを指定
@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {

        private var INSTANCE: TaskDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): TaskDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        TaskDatabase::class.java, "Task.db")
                        .build()
                }
                return INSTANCE!!
            }
        }
    }
}