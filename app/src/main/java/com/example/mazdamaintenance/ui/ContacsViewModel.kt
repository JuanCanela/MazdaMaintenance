package com.example.mazdamaintenance.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mazdamaintenance.Entities.Users
import com.example.mazdamaintenance.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContacsViewModel @ViewModelInject constructor (private val repository: Repository): ViewModel() {

    val readData = repository.readData().asLiveData()

    fun insertData(user: Users){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(user)
        }
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Users>> {
        return repository.searchDatabase(searchQuery).asLiveData()
    }

}