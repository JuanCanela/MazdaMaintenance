package com.example.mazdamaintenance.ui.call

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CallViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is call Fragment"
    }
    val text: LiveData<String> = _text
}