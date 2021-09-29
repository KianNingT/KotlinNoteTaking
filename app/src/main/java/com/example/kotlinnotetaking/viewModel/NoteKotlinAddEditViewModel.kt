package com.example.kotlinnotetaking.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NoteKotlinAddEditViewModel : ViewModel() {

    private val _currentNumber = MutableLiveData<Int>()

    val currentNumber: LiveData<Int> get() = _currentNumber

    fun setNumber(number:Int) {
        _currentNumber.value = number
    }
}