package com.ishani.roomdemo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ishani.roomdemo.db.Subscriber
import com.ishani.roomdemo.db.SubscriberRepository
import kotlinx.coroutines.launch

class SubscriberViewModel(private val subscriberRepository: SubscriberRepository) : ViewModel() {
    val subscribers = subscriberRepository.subscribers
    val inputName = MutableLiveData<String?>()
    val inputEmail = MutableLiveData<String?>()
    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearAllButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllButtonText.value = "Clear All"
    }

    fun saveOrUpdate() {
        val name = inputName.value!!
        val email = inputEmail.value!!
        insert(Subscriber(0, name, email))
        inputEmail.value = null
        inputName.value = null
    }

    fun clearAllOrDelete() {
        clearAll()
    }

    fun insert(subscriber: Subscriber) =
        viewModelScope.launch {
            subscriberRepository.insert(subscriber)
        }

    fun update(subscriber: Subscriber) =
        viewModelScope.launch {
            subscriberRepository.update(subscriber)
        }

    fun delete(subscriber: Subscriber) =
        viewModelScope.launch {
            subscriberRepository.delete(subscriber)
        }

    fun clearAll() =
        viewModelScope.launch {
            subscriberRepository.deleteAll()
        }
}