package com.jaydip.todotut.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.jaydip.todotut.data.ToDoDatabase
import com.jaydip.todotut.data.models.ToDoData
import com.jaydip.todotut.data.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application):AndroidViewModel(application) {

    private val toDoDao= ToDoDatabase.getDatabase(application).ToDoDao()
    private val repository:ToDoRepository

    val getAllData: LiveData<List<ToDoData>>

    init {
        repository=ToDoRepository(toDoDao)
        getAllData=repository.getAllData
    }

    fun insertData(toDoData: ToDoData){
        viewModelScope.launch (Dispatchers.IO){
            repository.insertData(toDoData)
        }
    }

    fun updateData(toDoData: ToDoData){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateData(toDoData)
        }
    }

    fun deleteData(toDoData: ToDoData){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteItem(toDoData)
        }
    }

    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAll()
        }
    }
}