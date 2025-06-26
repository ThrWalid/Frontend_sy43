package com.example.pro_test.data.network

class ChatRepository(private val api: ApiService) {
    suspend fun getContacts() = api.getContacts()
    suspend fun getMessages(userId: Int) = api.getMessages(userId)
    suspend fun sendMessage(receiverId: Int, text: String) =
        api.sendMessage(MessageRequest(receiverId, text))
}