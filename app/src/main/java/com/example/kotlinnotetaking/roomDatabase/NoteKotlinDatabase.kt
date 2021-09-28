package com.example.kotlinnotetaking.roomDatabase

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.kotlinnotetaking.dao.NoteDao
import com.example.kotlinnotetaking.entity.Note


//singleton class because we only want 1 local database in the app
//actual database instance

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteKotlinDatabase : RoomDatabase() {

//    companion object NoteDatabaseObject {
//        private var instance: NoteKotlinDatabase? = null
//
//        @Synchronized
//        fun getInstance(context: Context): NoteKotlinDatabase? {
//            if (instance == null) {
//                instance = Room.databaseBuilder(context.applicationContext,
//                    NoteKotlinDatabase::class.java,
//                    "note_kotlin_database")
//                    .fallbackToDestructiveMigration()
//                    .addCallback(roomCallBack)
//                    .build();
//            }
//            return instance
//        }
//
//        private val roomCallBack: Callback = object : Callback() {
//            override fun onCreate(db: SupportSQLiteDatabase) {
//                super.onCreate(db)
//
//                instance?.let { PopulateDBAsyncTask(it).execute() }
//            }
//        }
//
//        class PopulateDBAsyncTask(db: NoteKotlinDatabase): AsyncTask<Void?, Void?, Void?>() {
//
//            private val noteDao: NoteDao = db.noteDao()
//
//            override fun doInBackground(vararg params: Void?): Void? {
//                noteDao.insert(Note("Title 1", "Desc 1", 1))
//                noteDao.insert(Note("Title 2", "Desc 2", 2))
//                noteDao.insert(Note("Title 3", "Desc 3", 3))
//                return null
//            }
//
//        }
//
//    }

    //method to put into dao interface
    // we will use this dao method here to access database operation methods that we've added into the NoteDao interface
    abstract fun getNoteDao(): NoteDao

    /*we need a singleton instance of ShoppingDatabase, doesn't make sense to have multiple instances
   of ShoppingDatabase while running the app */
    companion object {
        /* write to this instance will be made visible instantly to other threads.
         this is to make sure that only one thread at a time is writing to this instance
         avoid multiple threads that are trying to initialize the instance, which is what we do not want*/
        @Volatile
        private var instance: NoteKotlinDatabase? = null
        private val LOCK = Any()

        /*operator function with invoke keyword usually means that this method will be called whenever
        the instance is created*/
        /**function called to create an instance of shopping database. it will return our instance, but
        if that instance is null, it will call our synchronized block so no other threads can access
        the instance at the time we execute the code inside of the block. Inside of the block, we
        check again if the instance is indeed null. If it is null, we want to create the database instance.
        .also means to set our instance after calling the createDatabase method to whatever the result
        of the method is*/
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }


        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                NoteKotlinDatabase::class.java,
                "ShoppingDB.db").build()
    }



}