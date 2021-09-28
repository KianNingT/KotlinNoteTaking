package com.example.kotlinnotetaking.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.kotlinnotetaking.entity.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    //use this annotation when there's no supported annotation for the method to call
    //which means to write your own sqlite query to do the things you want to perform
    @Query("DELETE FROM note_kotlin_room")
    fun deleteAllNotes()

    @Query("SELECT * FROM note_kotlin_room ORDER BY note_priority DESC")
    //live data to observe for any changes to our object automatically
    fun getAllNotes(): LiveData<List<Note>>

}