package com.android.youtubeproject.fragment.myvideofragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.android.youtubeproject.fragment.myvideofragment.db.UserData
import com.android.youtubeproject.fragment.myvideofragment.repository.MyVideoRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class MyVideoViewModelFactory(private val repository: MyVideoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyVideoViewModel::class.java)) {
            return MyVideoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class MyVideoViewModel(private val repository: MyVideoRepository) : ViewModel() {

    private val _selectedUser = MutableLiveData<UserData?>()
    val selectedUser: LiveData<UserData?> get() = _selectedUser

    fun getUserByIndex(index: Int) = viewModelScope.launch {
        _selectedUser.value = repository.getUserByIndex(index)
    }

    fun insertUser(user: UserData): Job = viewModelScope.launch {
        repository.insertUser(user)
    }

    fun deleteUser(user: UserData) = viewModelScope.launch {
        repository.deleteUser(user)
    }

    val allUsers = viewModelScope.launch{
        repository.getAllUsers()
    }
}
