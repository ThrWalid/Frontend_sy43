package com.example.pro_test.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pro_test.data.network.ContactResponse
import com.example.pro_test.viewmodel.ChatViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsScreen(nav: NavController, vm: ChatViewModel) {
    val contacts by vm.contacts.collectAsState()

    var searching by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }

    val list = if (query.isBlank()) contacts else contacts.filter {
        it.name.contains(query, true)
    }

    LaunchedEffect(Unit) { vm.fetchContacts() }

    Scaffold(topBar = {
        TopAppBar(
            title = {
                if (searching) {
                    OutlinedTextField(
                        value = query,
                        onValueChange = { query = it },
                        placeholder = { Text("Search…") },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    Text("Contacts", color = Color.White, fontWeight = FontWeight.Bold)
                }
            },
            actions = {
                if (searching) {
                    IconButton({ searching = false; query = "" }) {
                        Icon(Icons.Default.Close, null, tint = Color.White)
                    }
                } else {
                    IconButton({ searching = true }) {
                        Icon(Icons.Default.Search, null, tint = Color.White)
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1B2A58))
        )
    }) { padding ->
        LazyColumn(Modifier.padding(padding)) {
            items(list) { c -> ContactRow(c) {
                nav.navigate("conversation/${c.id}/${c.name}")
            } }
        }
    }
}

@Composable
private fun ContactRow(c: ContactResponse, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Spacer(Modifier.width(16.dp))
        Column(Modifier.weight(1f)) {
            Text(c.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(c.lastMessage, fontSize = 14.sp, color = Color.Gray, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
        Text(c.lastMessageTime, fontSize = 12.sp, color = Color.Gray)
    }
}