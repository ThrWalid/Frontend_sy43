package com.example.pro_test.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pro_test.viewmodel.ChatViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationScreen(contactId: Int, contactName: String, viewModel: ChatViewModel, onBack: () -> Unit
) {
    val msgs by viewModel.messages.collectAsState()
    var input by remember { mutableStateOf("") }

    LaunchedEffect(contactId) { viewModel.fetchMessages(contactId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Column { Text(contactName, fontWeight = FontWeight.Bold); Text("En ligne", fontSize = 12.sp) } },
                navigationIcon = { IconButton(onBack) { Icon(Icons.Default.ArrowBack, null) } }
            )
        },
        bottomBar = {
            Row(Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = input,
                    onValueChange = { input = it },
                    placeholder = { Text("Écrire…") },
                    shape = RoundedCornerShape(24.dp),
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = {
                        if (input.isNotBlank()) {
                            viewModel.sendMessage(contactId, input)
                            input = ""
                        }
                    },
                    modifier = Modifier.size(40.dp).background(Color(0xFF1B2A58), CircleShape)
                ) {
                    Icon(Icons.Default.Send, null, tint = Color.White)
                }
            }
        }
    ) { pad ->
        LazyColumn(Modifier.padding(pad).fillMaxSize(), reverseLayout = true) {
            items(msgs.reversed()) { m ->
                MessageBubble(m.content, m.timestamp.take(5), m.senderId == viewModel.currentUserId)
                Spacer(Modifier.height(4.dp))
            }
        }
    }
}

@Composable
private fun MessageBubble(text: String, time: String, isMe: Boolean) {
    Column(
        Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = if (isMe) Alignment.End else Alignment.Start
    ) {
        Box(
            Modifier.clip(RoundedCornerShape(12.dp)).background(if (isMe) Color(0xFF1B2A58) else Color.LightGray.copy(0.3f)).padding(12.dp)
        ) { Text(text, color = if (isMe) Color.White else Color.Black) }
        Text(time, fontSize = 10.sp, color = Color.Gray)
    }
}