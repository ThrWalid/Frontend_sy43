package com.example.pro_test.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pro_test.data.network.ChatRepository
import com.example.pro_test.data.network.ContactResponse
import com.example.pro_test.data.network.MessageResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel(
    private val repo: ChatRepository,
    val currentUserId: Int // جاي من LoginResponse
) : ViewModel() {

    private val _contacts = MutableStateFlow<List<ContactResponse>>(emptyList())
    val contacts = _contacts.asStateFlow()

    private val _messages = MutableStateFlow<List<MessageResponse>>(emptyList())
    val messages = _messages.asStateFlow()

    fun fetchContacts() = viewModelScope.launch {
        _contacts.value = repo.getContacts()
    }

    fun fetchMessages(userId: Int) = viewModelScope.launch {
        _messages.value = repo.getMessages(userId)
    }

    fun sendMessage(receiverId: Int, text: String) = viewModelScope.launch {
        repo.sendMessage(receiverId, text)
        fetchMessages(receiverId)
    }
}