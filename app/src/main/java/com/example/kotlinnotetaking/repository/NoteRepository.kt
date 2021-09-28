package com.example.kotlinnotetaking.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.kotlinnotetaking.dao.NoteDao
import com.example.kotlinnotetaking.entity.Note
import com.example.kotlinnotetaking.roomDatabase.NoteKotlinDatabase

class NoteRepository (private val db:NoteKotlinDatabase) {


    fun insert(note: Note) = db.getNoteDao().insert(note)

    fun update(note: Note) = db.getNoteDao().update(note)

    fun delete(note: Note) = db.getNoteDao().delete(note)

    fun getAllNotes() = db.getNoteDao().getAllNotes()

     fun deleteAllNotes() = db.getNoteDao().deleteAllNotes()

}