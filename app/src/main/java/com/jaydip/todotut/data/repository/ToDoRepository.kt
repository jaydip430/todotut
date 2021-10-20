package com.jaydip.todotut.data.repository

import androidx.lifecycle.LiveData
import com.jaydip.todotut.data.ToDoDao
import com.jaydip.todotut.data.models.ToDoData

class ToDoRepository(private val toDoDao: ToDoDao) {

    val getAllData :LiveData<List<ToDoData>> = toDoDao.getAllData()

    suspend fun insertData(toDoData : ToDoData){
        toDoDao.insertData(toDoData)
    }

    suspend fun updateData(toDoData: ToDoData){
        toDoDao.updateData(toDoData)
    }

    suspend fun deleteItem(toDoData: ToDoData){
        toDoDao.deleteData(toDoData)
    }

    suspend fun deleteAll(){
        toDoDao.deleteAll()
    }
}