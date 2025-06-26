package com.example.pro_test.data.network

data class ContactResponse(
    val id: Int,
    val name: String,
    val lastMessage: String,
    val lastMessageTime: String
)

data class MessageRequest(
    val receiverId: Int,
    val content: String
)

data class MessageResponse(
    val id: Int,
    val senderId: Int,
    val receiverId: Int,
    val content: String,
    val timestamp: String,
    val isRead: Boolean
)


