package com.example.kotlinnotetaking.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinnotetaking.entity.Note
import com.example.kotlinnotetaking.repository.NoteRepository

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NoteKotlinMainViewModel(private val repository: NoteRepository) : ViewModel() {

    /**usually we would do database operations in coroutine IO scope. But Room persistence library
    provides main safety, so we are fine to launch in the main context.
    default context is for long running and complex operations.*/
    fun insert(note: Note) = GlobalScope.launch {
        repository.insert(note)
    }

    fun update(note: Note) = GlobalScope.launch {
        repository.update(note)
    }

    fun delete(note: Note) = GlobalScope.launch {
        repository.delete(note)
    }

    fun getAllNotes() = repository.getAllNotes()

    //fun deleteAllNotes() = repository.deleteAllNotes()

    fun deleteAllNotes() = GlobalScope.launch {
        repository.deleteAllNotes()
    }
}