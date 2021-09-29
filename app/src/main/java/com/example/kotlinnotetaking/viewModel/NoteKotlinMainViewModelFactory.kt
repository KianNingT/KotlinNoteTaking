package com.example.kotlinnotetaking.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinnotetaking.repository.NoteRepository

@Suppress("UNCHECKED_CAST")
class NoteKotlinMainViewModelFactory(
    private val repository: NoteRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NoteKotlinMainViewModel(repository) as T
    }
}