package com.example.pro_test
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
// home
import androidx.compose.runtime.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pro_test.ui.theme.Pro_testTheme
import com.example.td4_kotlin_utbm.R
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Task
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme as Pro_testTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.pro_test.data.local.OfflineTaskRepository
import com.example.pro_test.data.local.Task
import com.example.pro_test.data.local.TaskDatabase
import com.example.pro_test.data.local.TaskRepository
import com.example.pro_test.data.network.HomeApi
import com.example.pro_test.data.network.HomeResponse

import com.example.pro_test.data.network.RetrofitInstance
import com.example.pro_test.data.network.LoginRequest
import com.example.pro_test.data.network.RegisterRequest

// Entry point of the Android application using Jetpack Compose
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Creating repository for the task database
        val taskRepository: TaskRepository by lazy {
            OfflineTaskRepository(TaskDatabase.getDatabase(this).taskDao())
        }

        // Set the UI content using Jetpack Compose
        setContent {
            // Apply the Material Design theme
            MaterialTheme {
                // Call the main navigation function
                AppNavigation(taskRepository)
            }
        }
    }
}

// Composable function that handles navigation between screens
@Composable
fun AppNavigation(taskRepository: TaskRepository) {
    // NavController used to manage app navigation
    val navController = rememberNavController()

    // Initializing the general AppViewModel to provide data to screens in application
    val viewModel = ApplicationViewModel(taskRepository)

    // NavHost defines the navigation graph with screen destinations
    NavHost(navController = navController, startDestination = "welcome") {

        // Welcome screen route
        composable("welcome") {
            WelcomeScreen(
                onGetStartedClick = { navController.navigate("login") } // Navigate to login screen
            )
        }

        // Login screen route
        composable("login") {
            ModernLoginScreen(
                onLoginSuccess = { navController.navigate("home") }, // Navigate to home screen on success
                onNavigateToSignUp = { navController.navigate("signup") }, // Navigate to sign-up screen
                onForgotPassword = { /* to be implemented */ } // Action to reset password (not yet implemented)
            )
        }

        // Sign-up screen route
        composable("signup") {
            SignUpScreen(
                onSignUpSuccess = { navController.navigate("home") }, // Navigate to home after successful sign-up
                onNavigateBack = { navController.popBackStack() } // Navigate back to previous screen
            )
        }

        // Home screen route
        composable("home") {
            HomeScreen(
                onLogout = { navController.popBackStack("welcome", inclusive = false) }, // Log out and go back to welcome
                onNavigateToContacts = { navController.navigate("contacts") }, // Navigate to contacts screen
                onNavigateToGroups = { navController.navigate("groups") }, // Navigate to groups screen
                onNavigateToTasks = { navController.navigate("tasks") }, // Navigate to tasks screen
                onNavigateToSchedule = { navController.navigate("schedule") } // Navigate to schedule screen
            )
        }

        // Contacts screen route
        composable("contacts") {
            ContactsScreen(
                onBack = { navController.popBackStack() }, // Go back to previous screen
                onNavigateToHome = { navController.navigate("home") }, // Navigate to home
                onNavigateToGroups = { navController.navigate("groups") },
                onNavigateToTasks = { navController.navigate("tasks") },
                onNavigateToSchedule = { navController.navigate("schedule") },
                navController = navController // Pass navController for internal navigation within contacts
            )
        }

        // Chat screen for a specific contact, using dynamic route parameters
        composable("conversation/{contactId}/{contactName}") { backStackEntry ->
            val contactId = backStackEntry.arguments?.getString("contactId") ?: ""
            val contactName = backStackEntry.arguments?.getString("contactName") ?: ""
            ConversationScreen(
                contactName = contactName,
                onBack = { navController.popBackStack() } // Return to the previous screen
            )
        }
        composable("groupConversation/{groupName}") { backStackEntry ->
            val groupName = backStackEntry.arguments?.getString("groupName") ?: ""
            GroupConversationScreen(
                groupName = groupName,
                onBack = { navController.popBackStack() }
            )
        }


        // Groups screen route
        // Groups screen route
        composable("groups") {
            GroupsScreen(
                navController = navController, // 🔹 Ajouté ici
                onNavigateToHome = { navController.navigate("home") },
                onNavigateToContacts = { navController.navigate("contacts") },
                onNavigateToTasks = { navController.navigate("tasks") },
                onNavigateToSchedule = { navController.navigate("schedule") }
            )
        }


        // Tasks screen route
        composable("tasks") {
            TasksScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onNavigateToHome = { navController.navigate("home") },
                onNavigateToContacts = { navController.navigate("contacts") },
                onNavigateToGroups = { navController.navigate("groups") },
                onNavigateToSchedule = { navController.navigate("schedule") }
            )
        }

        // Schedule/calendar screen route
        composable("schedule") {
            ScheduleScreen(
                onBack = { navController.popBackStack() },
                onNavigateToHome = { navController.navigate("home") },
                onNavigateToContacts = { navController.navigate("contacts") },
                onNavigateToGroups = { navController.navigate("groups") },
                onNavigateToTasks = { navController.navigate("tasks") }
            )
        }
        composable("editEvent"){
            EditEventScreen(
                onCancel = {
                    navController.popStackBack()
                },
                onSave = {
                    /* Save changes */
                    navController.popStackBack()
                }
            )
        }
        composable("sendInvitation"){
            SendInvitationScreen(
                onSend = {
                    /* Send invitation */
                    navController.popStackBack()
                }
            )
        }
    }
}

@Composable
fun SendInvitationScreen(onSend: () -> Unit) {
    TODO("Not yet implemented")
}

private fun NavHostController.popStackBack() {
    TODO("Not yet implemented")
}


// ------------------------- Welcome screen UI composable --------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendInviteScreen(
    onSend: () -> Unit
) {
    var friendName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Send Friend Invitation",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1B2A58)
        )

        OutlinedTextField(
            value = friendName,
            onValueChange = { friendName = it },
            label = { Text("Friend's Username") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF1B2A58),
                unfocusedBorderColor = Color.Gray
            )
        )

        Button(
            onClick = onSend,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B2A58))
        ) {
            Icon(Icons.Default.Send, contentDescription = "Send", tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Send", color = Color.White)
        }
    }
}
@Composable
fun InvitationCard(
    name: String,
    onAccept: () -> Unit,
    onReject: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF6F6F6)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFF1B2A58), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = name.first().toString(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = name, fontWeight = FontWeight.Bold)
                Text(text = "wants to be your friend", fontSize = 13.sp, color = Color.Gray)
            }

            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Accept",
                tint = Color(0xFF4CAF50),
                modifier = Modifier
                    .size(28.dp)
                    .clickable { onAccept() }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Reject",
                tint = Color(0xFFF44336),
                modifier = Modifier
                    .size(28.dp)
                    .clickable { onReject() }
            )
        }
    }
}

@Composable
fun EditEventScreen(
    onCancel: () -> Unit,
    onSave: () -> Unit
) {
    var name by remember { mutableStateOf("ESP") }
    var startTime by remember { mutableStateOf("13:00") }
    var duration by remember { mutableStateOf("2 hours") }
    var difficulty by remember { mutableStateOf("Medium") }
    var priority by remember { mutableStateOf("High") }
    var breakNeeded by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Edit Event",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1B2A58)
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = startTime,
            onValueChange = { startTime = it },
            label = { Text("Start Time") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = duration,
            onValueChange = { duration = it },
            label = { Text("Duration") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = difficulty,
            onValueChange = { difficulty = it },
            label = { Text("Difficulty") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = priority,
            onValueChange = { priority = it },
            label = { Text("Priority") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Break Needed", modifier = Modifier.weight(1f))
            Switch(checked = breakNeeded, onCheckedChange = { breakNeeded = it })
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onSave,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B2A58))
        ) {
            Text("Save Changes", color = Color.White)
        }

        OutlinedButton(
            onClick = onCancel,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color(0xFFE0E0E0))
        ) {
            Text("Cancel", color = Color.Black)
        }
    }
}


@Composable
fun WelcomeScreen(onGetStartedClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Full screen white background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally // Center content horizontally
        ) {
            Spacer(modifier = Modifier.height(170.dp)) // Top spacing

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // App tagline
                Text(
                    text = "Bring People\n\nTogether, Make\n\nIdeas Happen",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B2A58),
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                )

                Spacer(modifier = Modifier.height(16.dp)) // Spacing between texts

                // Brief app description
                Text(
                    text = "Welcome to your professional hub.\nCreate event groups, chat with colleagues,\nand stay organized with ease.",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp)) // Spacing before button

                // "Get Started" button to go to login
                Button(
                    onClick = onGetStartedClick,
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B2A58)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Get Started", color = Color.White)
                }
            }

            // Image at the bottom of the welcome screen
            Image(
                painter = painterResource(id = R.drawable.first_page_pic),
                contentDescription = "Welcome Illustration",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
        }
    }
}





// --------------------------- Login Screen --------------------------------------

@Composable
fun ModernLoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onForgotPassword: () -> Unit
) {
    // States
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var loginError by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // UI Colors
    val primaryColor = Color(0xFF1B2A58)
    val signUpAccent = Color(0xFFC49F50)
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFFE3F2FD), Color.White)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
            .padding(horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome Back", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = primaryColor)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Your professional hub starts here", fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(32.dp))

            // Email Input
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = primaryColor) },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password Input
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = primaryColor) },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null,
                            tint = primaryColor
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Forgot Password
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onForgotPassword) {
                    Text("Forgot password?", color = signUpAccent)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Login Button
            Button(
                onClick = {
                    if (email.isNotBlank() && password.isNotBlank()) {
                        coroutineScope.launch {
                            try {
                                val response = RetrofitInstance.authApi.login(
                                    LoginRequest(email = email, password = password)
                                )
                                if (response.isSuccessful) {
                                    val loginData = response.body()
                                    val token = loginData?.token ?: ""

                                    context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE)
                                        .edit()
                                        .putString("JWT_TOKEN", token)
                                        .apply()

                                    onLoginSuccess()
                                } else {
                                    val errorMsg = response.errorBody()?.string()?.lowercase()
                                    loginError = when {
                                        response.code() == 401 && errorMsg?.contains("email") == true -> " Incorrect email"
                                        response.code() == 401 && errorMsg?.contains("password") == true -> " Incorrect password"
                                        response.code() == 401 -> " Invalid credentials"
                                        response.code() == 500 -> " Server error. Please try again later"
                                        else -> "Error: ${response.code()}"
                                    }

                                }
                            } catch (e: Exception) {
                                loginError = "Erreur réseau: ${e.localizedMessage}"
                            }
                        }
                    } else {
                        loginError = "Tous les champs sont requis"
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
            ) {
                Text("Login", color = Color.White, fontWeight = FontWeight.Bold)
            }

            // Error message
            loginError?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = it,
                    color = Color.Red,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("OR", color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))

            // Sign-up
            OutlinedButton(
                onClick = onNavigateToSignUp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.5.dp, signUpAccent)
            ) {
                Text("Create New Account", fontWeight = FontWeight.Medium)
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "By continuing, you agree to our Terms and Privacy Policy",
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}



//------------------------------------------- Sign Up Screen ---------------------------

@Composable
fun SignUpScreen(
    onSignUpSuccess: () -> Unit,
    onNavigateBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var showPasswordError by remember { mutableStateOf(false) }
    var registerError by remember { mutableStateOf<String?>(null) }

    val primaryColor = Color(0xFF1B2A58)
    val signUpAccent = Color(0xFFC49F50)
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFFE3F2FD), Color.White)
    )

    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
            .padding(horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Create Account", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = primaryColor)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Join the hub and start your journey", fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(32.dp))

            // Full Name
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Full Name") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = primaryColor) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = primaryColor) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = primaryColor) },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null,
                            tint = primaryColor
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Confirm Password
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = primaryColor) },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(16.dp)
            )

            if (showPasswordError) {
                Text("Passwords do not match", color = Color.Red)
            }

            registerError?.let {
                Spacer(modifier = Modifier.height(12.dp))
                Text(it, color = Color.Red)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Register Button
            Button(
                onClick = {
                    if (name.isNotBlank() && email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()) {
                        if (password == confirmPassword) {
                            showPasswordError = false
                            coroutineScope.launch {
                                try {
                                    val response = RetrofitInstance.authApi.register(
                                        RegisterRequest(name = name, email = email, password = password)
                                    )
                                    if (response.isSuccessful) {
                                        val msg = response.body()?.message ?: "Inscription réussie"
                                        Log.d("REGISTER_SUCCESS", msg)
                                        onSignUpSuccess()
                                    } else {
                                        val errorMsg = response.errorBody()?.string()
                                        registerError = "Erreur ${response.code()}:\n$errorMsg"
                                    }
                                } catch (e: Exception) {
                                    registerError = "Erreur: ${e.localizedMessage}"
                                }
                            }
                        } else {
                            showPasswordError = true
                        }
                    } else {
                        registerError = "All fields are required"
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
            ) {
                Text("Sign Up", color = Color.White, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onNavigateBack) {
                Text("Already have an account? Login", color = signUpAccent)
            }
        }
    }
}

// ----------------------------------------- Home Screen -----------------------------------
// Define state for Home API
data class HomeUiState(
    val isLoading: Boolean = false,
    val data: HomeResponse? = null,
    val error: String? = null
)

@Composable
fun rememberHomeState(token: String): State<HomeUiState> {
    val state = remember { mutableStateOf(HomeUiState(isLoading = true)) }

    LaunchedEffect(token) {
        try {
            val response = withContext(Dispatchers.IO) {
                RetrofitInstance.retrofit.create(HomeApi::class.java)
                    .getHomeData("Bearer $token")
            }
            if (response.isSuccessful) {
                state.value = HomeUiState(data = response.body())
            } else {
                state.value = HomeUiState(error = "Error ${response.code()}")
            }
        } catch (e: Exception) {
            state.value = HomeUiState(error = e.message ?: "Unknown error")
        }
    }

    return state
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onLogout: () -> Unit,
    onNavigateToContacts: () -> Unit,
    onNavigateToGroups: () -> Unit,
    onNavigateToTasks: () -> Unit,
    onNavigateToSchedule: () -> Unit
) {
    var showPersonalDialog by remember { mutableStateOf(false) }
    var showSharedDialog by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(2) }
    var showFriends by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val jwt = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE)
        .getString("JWT_TOKEN", null) ?: ""
    val homeState = rememberHomeState(jwt)

    Column(modifier = Modifier.fillMaxSize()) {
        TopSection(onLogout)

        when {
            homeState.value.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            homeState.value.error != null -> {
                Text("Error: ${homeState.value.error}", color = Color.Red, modifier = Modifier.padding(16.dp))
            }
            homeState.value.data != null -> {
                InfoCard(
                    title = "Current Friend Messages",
                    icon = Icons.Default.Person,
                    onClick = { showFriends = !showFriends }
                )
                if (showFriends) {
                    FriendsList(homeState.value.data!!.friends)
                }

                InfoCard(
                    title = "Current Group Messages",
                    icon = Icons.Default.Groups,
                    onClick = { /* TODO: Add group dropdown */ }
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        EventsRowSection(
            onPersonalEventClick = { showPersonalDialog = true },
            onSharedEventClick = { showSharedDialog = true }
        )

        Spacer(modifier = Modifier.weight(1f))

        NavigationBar(containerColor = Color.White) {
            val items = listOf("Groups", "Contacts", "Home", "Tasks", "Schedule")
            val icons = listOf(
                Icons.Default.Groups,
                Icons.Default.Person,
                Icons.Default.Home,
                Icons.Default.CheckCircle,
                Icons.Default.CalendarToday
            )
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = { Icon(icons[index], contentDescription = item) },
                    label = { Text(item) },
                    selected = selectedIndex == index,
                    onClick = {
                        selectedIndex = index
                        when (index) {
                            0 -> onNavigateToGroups()
                            1 -> onNavigateToContacts()
                            3 -> onNavigateToTasks()
                            4 -> onNavigateToSchedule()
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF1B2A58),
                        selectedTextColor = Color(0xFF1B2A58),
                        indicatorColor = Color(0xFFF1F1F1)
                    )
                )
            }
        }
    }

    if (showPersonalDialog) {
        PersonalTaskDialog(
            onDismiss = { showPersonalDialog = false },
            onSave = { showPersonalDialog = false }
        )
    }

    if (showSharedDialog) {
        SharedTaskDialog(
            onDismiss = { showSharedDialog = false },
            onSave = { showSharedDialog = false }
        )
    }
}

@Composable
fun InfoCard(title: String, icon: ImageVector, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3ECF9))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(icon, contentDescription = null, tint = Color(0xFF1B2A58), modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(title, fontWeight = FontWeight.Bold, color = Color(0xFF1B2A58))
                Text("Lorem ipsum dolor sit amet...", fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun FriendsList(friends: List<String>) {
    LazyColumn(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
        items(friends) { friend ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Text(
                    text = friend,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedTaskDialog(
    onDismiss: () -> Unit,
    onSave:    () -> Unit
) {
    // États internes
    var name     by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }
    var startAt  by remember { mutableStateOf("") }
    var selectedContacts by remember { mutableStateOf(listOf<String>()) }
    var selectedGroups   by remember { mutableStateOf(listOf<String>()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "New Shared Events",
                color = Color(0xFF1B2A58),
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = duration,
                    onValueChange = { duration = it },
                    label = { Text("Duration (min)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = startAt,
                    onValueChange = { startAt = it },
                    label = { Text("Start at") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))
                Text("Add contact/group", fontWeight = FontWeight.Medium)
                Spacer(Modifier.height(4.dp))


                // Ici on répartit l'espace entre les deux dropdowns
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Contacts
                    var expandedC by remember { mutableStateOf(false) }
                    Box(
                        modifier = Modifier
                            .weight(1f)  // apply weight here
                    ) {
                        OutlinedButton(
                            onClick = { expandedC = true },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Select contacts")
                        }
                        DropdownMenu(
                            expanded = expandedC,
                            onDismissRequest = { expandedC = false }
                        ) {
                            listOf("Alice","Bob","Claire").forEach { c ->
                                DropdownMenuItem(text = { Text(c) }, onClick = {
                                    selectedContacts = selectedContacts + c
                                    expandedC = false
                                })
                            }
                        }
                    }

                    // Groups
                    var expandedG by remember { mutableStateOf(false) }
                    Box(
                        modifier = Modifier
                            .weight(1f)  // and here
                    ) {
                        OutlinedButton(
                            onClick = { expandedG = true },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Select groups")
                        }
                        DropdownMenu(
                            expanded = expandedG,
                            onDismissRequest = { expandedG = false }
                        ) {
                            listOf("Team A","Team B","Project X").forEach { g ->
                                DropdownMenuItem(text = { Text(g) }, onClick = {
                                    selectedGroups = selectedGroups + g
                                    expandedG = false
                                })
                            }
                        }
                    }
                }

                Spacer(Modifier.height(8.dp))
                if (selectedContacts.isNotEmpty()) {
                    Text("Contacts:", fontWeight = FontWeight.Medium)
                    selectedContacts.forEach { Text("- $it") }
                }
                if (selectedGroups.isNotEmpty()) {
                    Text("Groups:", fontWeight = FontWeight.Medium, modifier = Modifier.padding(top = 4.dp))
                    selectedGroups.forEach { Text("- $it") }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onSave) {
                Text("Save", color = Color(0xFF1B2A58))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        shape = RoundedCornerShape(12.dp)
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalTaskDialog(
    onDismiss: () -> Unit,
    onSave:    () -> Unit
) {
    var name       by remember { mutableStateOf("") }
    var duration   by remember { mutableStateOf("") }
    var difficulty by remember { mutableStateOf("Low") }
    var priority   by remember { mutableStateOf("Low") }
    var needBreak  by remember { mutableStateOf<Boolean?>(null) }
    var startAt    by remember { mutableStateOf("") }
    var deadline   by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "New Personal Task",
                color = Color(0xFF1B2A58),
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(Modifier.fillMaxWidth()) {
                // Nom
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))

                // Durée
                OutlinedTextField(
                    value = duration,
                    onValueChange = { duration = it },
                    label = { Text("Duration (min)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))

                // Difficulty : Low / Medium
                Text("Difficulty", fontWeight = FontWeight.Medium)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    verticalAlignment    = Alignment.CenterVertically
                ) {
                    listOf("Low", "Medium").forEach { level ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = difficulty == level,
                                onClick  = { difficulty = level },
                                colors    = RadioButtonDefaults.colors(selectedColor = Color(0xFF1B2A58))
                            )
                            Text(level, modifier = Modifier.padding(start = 4.dp))
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Priority : Low / Medium
                Text("Priority", fontWeight = FontWeight.Medium)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    verticalAlignment    = Alignment.CenterVertically
                ) {
                    listOf("Low", "Medium").forEach { level ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = priority == level,
                                onClick  = { priority = level },
                                colors    = RadioButtonDefaults.colors(selectedColor = Color(0xFF1B2A58))
                            )
                            Text(level, modifier = Modifier.padding(start = 4.dp))
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Needs Break ?
                Text("Needs Break?", fontWeight = FontWeight.Medium)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    verticalAlignment    = Alignment.CenterVertically
                ) {
                    listOf(true to "Yes", false to "No").forEach { (value, label) ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = needBreak == value,
                                onClick  = { needBreak = value },
                                colors    = RadioButtonDefaults.colors(selectedColor = Color(0xFF1B2A58))
                            )
                            Text(label, modifier = Modifier.padding(start = 4.dp))
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Start at
                OutlinedTextField(
                    value = startAt,
                    onValueChange = { startAt = it },
                    label = { Text("Start at") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))

                // Deadline
                OutlinedTextField(
                    value = deadline,
                    onValueChange = { deadline = it },
                    label = { Text("Deadline") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onSave) {
                Text("Save", color = Color(0xFF1B2A58))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        shape = RoundedCornerShape(12.dp)
    )
}

@Composable
fun EventsRowSection(
    onPersonalEventClick: () -> Unit,
    onSharedEventClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = onPersonalEventClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1B2A58),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text("Personal Events")
        }
        Button(
            onClick = onSharedEventClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1B2A58),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text("Shared Events")
        }
    }
}

// Top section with title, account menu, search bar, and header image
@Composable
fun TopSection(onLogout: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }         // Dropdown menu visibility
    var userName by remember { mutableStateOf<String?>(null) }   // Dynamic user name

    val context = LocalContext.current
    val jwt = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE)
        .getString("JWT_TOKEN", null)

    LaunchedEffect(Unit) {
        if (jwt != null) {
            try {
                val response = RetrofitInstance.authApi.getCurrentUser("Bearer $jwt")
                if (response.isSuccessful) {
                    userName = response.body()?.name
                } else {
                    Log.e("TopSection", "Erreur /me: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("TopSection", "Exception: ${e.localizedMessage}")
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1B2A58))                      // Dark blue background
            .padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 0.dp)
    ) {
        Spacer(modifier = Modifier.height(14.dp))

        // Page title
        Text("Home", color = Color(0xFFFFC107), fontSize = 14.sp)

        Spacer(modifier = Modifier.height(4.dp))

        // Row with user name, dropdown icon, and logout menu
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { expanded = true } // Expand dropdown on click
        ) {
            Text(
                text = userName ?: "Loading...",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Account menu",
                tint = Color.White
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Logout") },
                    onClick = {
                        expanded = false
                        onLogout()
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Search bar with settings icon
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = "",
                onValueChange = {}, // No search logic yet
                placeholder = { Text("Search") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Settings icon
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .padding(8.dp),
                tint = Color(0xFF1B2A58)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Header image
        Image(
            painter = painterResource(id = R.drawable.home_page_pic),
            contentDescription = "Community image",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .padding(bottom = 8.dp)
        )
    }
}





// ------------------------------- Message Section -------------------------------




@Composable
fun MessageSection(
    onFriendClick: () -> Unit, // Callback triggered when the friend message card is clicked
    onGroupClick: () -> Unit   // Callback triggered when the group message card is clicked
) {
    // Vertical layout with padding around the section
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 17.dp)) {

        // First message card for friend messages
        MessageCard(
            icon = Icons.Default.Person,                 // Icon representing a friend
            title = "Current Friend Messages",           // Title of the card
            message = "Lorem ipsum dolor sit amet...",   // Preview of a message (placeholder)
            onClick = onFriendClick                      // Action when the card is clicked
        )

        Spacer(modifier = Modifier.height(15.dp)) // Space between the two message cards

        // Second message card for group messages
        MessageCard(
            icon = Icons.Default.Groups,                 // Icon representing a group
            title = "Current Group Messages",            // Title of the card
            message = "Lorem ipsum dolor sit amet...",   // Preview of a message (placeholder)
            onClick = onGroupClick                       // Action when the card is clicked
        )
    }
}

//  Message Card UI Component
@Composable
fun MessageCard(
    icon: ImageVector,   // Icon to display at the start of the card
    title: String,       // Title text (e.g., "Current Friend Messages")
    message: String,     // Message preview text
    onClick: () -> Unit  // Action to perform when the card is clicked
) {
    // Card with rounded corners and elevation
    Card(
        shape = RoundedCornerShape(20.dp),                                     // Rounded shape for aesthetics
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),       // Subtle shadow for depth
        modifier = Modifier
            .fillMaxWidth()                                                    // Make card full-width
            .clickable { onClick() }                                           // Make card clickable
    ) {
        // Horizontal layout inside the card
        Row(
            modifier = Modifier.padding(16.dp),                                // Inner padding
            verticalAlignment = Alignment.CenterVertically                     // Align icon and text vertically
        ) {
            // Icon displayed on the left
            Icon(
                icon,
                contentDescription = null,
                tint = Color(0xFF1B2A58),                                       // Dark blue color for icon
                modifier = Modifier.size(30.dp)                                 // Icon size
            )

            Spacer(modifier = Modifier.width(12.dp)) // Space between icon and text

            // Text content (title and message)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B2A58)                                   // Same dark blue as icon
                )
                Text(
                    text = message,
                    color = Color.Gray,
                    fontSize = 12.sp                                            // Smaller font for message preview
                )
            }
        }
    }
}

// Events Row Section
@Composable
fun EventsRowSection(
    onPersonalEventClick: () -> Unit,     // Callback when the "Personal Events" card is clicked
    onSharedEventClick: () -> Unit,       // Callback when the "Shared Events" card is clicked
    onAddPersonalEvent: () -> Unit,       // Callback when the "+" button for personal events is clicked
    onAddSharedEvent: () -> Unit          // Callback when the "+" button for shared events is clicked
) {
    // Outer vertical layout with padding
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {

        // Row to place two event cards side-by-side
        Row(
            modifier = Modifier.fillMaxWidth(),                  // Full-width row
            horizontalArrangement = Arrangement.SpaceBetween     // Space between the two cards
        ) {

            // -------------------- Personal Events Card --------------------
            Box(modifier = Modifier.weight(1f)) {
                // The actual card that triggers personal event click
                EventCard(
                    title = "Personal Events",
                    onClick = onPersonalEventClick,
                    modifier = Modifier.fillMaxWidth()
                )

                // Small "+" floating button above the card
                IconButton(
                    onClick = onAddPersonalEvent,                     // Adds a new personal event
                    modifier = Modifier
                        .size(32.dp)                                   // Small button size
                        .offset(y = (-16).dp)                          // Position the button partially over the card
                        .background(
                            color = Color(0xFF1B2A58),                 // Button background color (dark blue)
                            shape = CircleShape                        // Circular shape
                        )
                        .align(Alignment.TopCenter)                   // Centered at the top of the card
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Personal Event",    // Accessibility description
                        tint = Color.White,                           // Icon color
                        modifier = Modifier.size(18.dp)               // Icon size
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp)) // Space between the two cards

            // -------------------- Shared Events Card --------------------
            Box(modifier = Modifier.weight(1f)) {
                // The actual card that triggers shared event click
                EventCard(
                    title = "Shared Events",
                    onClick = onSharedEventClick,
                    modifier = Modifier.fillMaxWidth()
                )

                // Small "+" floating button above the shared event card
                IconButton(
                    onClick = onAddSharedEvent,                       // Adds a new shared event
                    modifier = Modifier
                        .size(32.dp)
                        .offset(y = (-16).dp)
                        .background(
                            color = Color(0xFF1B2A58),
                            shape = CircleShape
                        )
                        .align(Alignment.TopCenter)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Shared Event",     // Accessibility description
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
// Composable function to display an event card with a title
@Composable
fun EventCard(
    title: String,                   // Title text to display in the card
    onClick: () -> Unit,            // Action to perform when the card is clicked
    modifier: Modifier = Modifier   // Optional modifier to customize layout
) {
    Card(
        shape = RoundedCornerShape(20.dp), // Rounded corners
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), // Slight shadow elevation
        modifier = modifier
            .clickable { onClick() } // Makes the card clickable
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()     // Takes full available width
                .height(100.dp),    // Fixed height for consistent layout
            contentAlignment = Alignment.Center // Center content inside the box
        ) {
            Text(
                text = title,                         // Display the title passed in parameter
                fontWeight = FontWeight.Bold,        // Bold font for emphasis
                color = Color(0xFF1B2A58),           // Main theme color
                textAlign = TextAlign.Center         // Center the text
            )
        }
    }
}




//-------------------------------------- Contacts Screen --------------------------------------




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsScreen(
    navController: NavController,
    onBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToGroups: () -> Unit,
    onNavigateToTasks: () -> Unit,
    onNavigateToSchedule: () -> Unit
) {
    val allContacts = listOf(
        Contact("1", "Jean Dupont", "Salut, ça va ?", "10:30", R.drawable.person6),
        Contact("2", "Marie Martin", "À demain !", "Hier", R.drawable.person2),
        Contact("3", "Pierre Durand", "J'ai envoyé le fichier", "12/05", R.drawable.person4),
        Contact("4", "Sophie Lambert", "Merci pour ton aide", "11/05", R.drawable.person1)
    )

    var searchMode by remember { mutableStateOf(false) } // Search bar open or not
    var searchQuery by remember { mutableStateOf("") }   // Search text input
    var selectedIndex by remember { mutableStateOf(1) }  // Bottom navigation index

    val filteredContacts = remember(searchQuery) {
        if (searchQuery.isBlank()) allContacts
        else allContacts.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (searchMode) {
                        // Active search input field
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Search contacts...") },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 8.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                disabledContainerColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                    } else {
                        // Static title text
                        Text("Contacts", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                },
                actions = {
                    if (searchMode) {
                        IconButton(onClick = {
                            searchMode = false
                            searchQuery = ""
                        }) {
                            Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                        }
                    } else {
                        IconButton(onClick = { searchMode = true }) {
                            Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1B2A58))
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                modifier = Modifier.shadow(elevation = 8.dp)
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Group, contentDescription = "Groups") },
                    label = { Text("Groups") },
                    selected = selectedIndex == 0,
                    onClick = {
                        selectedIndex = 0
                        onNavigateToGroups()
                    }
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Contacts",
                            tint = Color(0xFF1B2A58)
                        )
                    },
                    label = {
                        Text("Contacts", color = Color(0xFF1B2A58))
                    },
                    selected = selectedIndex == 1,
                    onClick = {
                        selectedIndex = 1
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = selectedIndex == 2,
                    onClick = {
                        selectedIndex = 2
                        onNavigateToHome()
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.CheckCircle, contentDescription = "Tasks") },
                    label = { Text("Tasks") },
                    selected = selectedIndex == 3,
                    onClick = {
                        selectedIndex = 3
                        onNavigateToTasks()
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.CalendarToday, contentDescription = "Schedule") },
                    label = { Text("Schedule") },
                    selected = selectedIndex == 4,
                    onClick = {
                        selectedIndex = 4
                        onNavigateToSchedule()
                    }
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            items(filteredContacts) { contact ->
                ContactRow(
                    contact = contact,
                    onClick = {
                        navController.navigate("conversation/${contact.id}/${contact.name}")
                    }
                )
                Divider()
            }
        }
    }
}


// Composable function to render a single contact row
@Composable
fun ContactRow(contact: Contact, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick) // Clickable row
            .padding(horizontal = 16.dp, vertical = 12.dp), // Inner padding
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Display the contact's image in a circular shape
        Image(
            painter = painterResource(id = contact.imageRes),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape), // Circular image shape
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp)) // Spacing between image and text

        // Display name and last message
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = contact.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = contact.message,
                color = Color.Gray,
                fontSize = 14.sp,
                maxLines = 1,                       // Limit to one line
                overflow = TextOverflow.Ellipsis   // Truncate with ellipsis if too long
            )
        }

        // Show the date of the last message
        Text(
            text = contact.date,
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}

// Data class representing a contact in the app
data class Contact(
    val id: String,                  // Unique identifier for the contact
    val name: String,                // Contact's name
    val message: String,             // Last message preview
    val date: String,                // Time or date of the last message
    @DrawableRes val imageRes: Int   // Resource ID for contact's image
)



//------------------- Conversation section -------------------------------------------------




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationScreen(
    contactName: String, // Name of the contact passed via navigation
    onBack: () -> Unit   // Callback to navigate back
) {
    // Mutable state holding the list of messages in the conversation
    val (messages, setMessages) = remember {
        mutableStateOf(
            listOf(
                Message("Salut $contactName, comment ça va ?", "10:30", false),
                Message("Ça va bien, merci ! Et toi ?", "10:32", true),
                Message("Je vais bien aussi. Tu as fini le projet ?", "10:33", false),
                Message("Oui, je l'ai envoyé hier soir", "10:35", true),
                Message("Salut,ça va ?", "10:36", false)
            )
        )
    }

    // Mutable state for the current input message
    var newMessage by remember { mutableStateOf("") }

    // Scaffold layout with top and bottom bars
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(contactName, fontWeight = FontWeight.Bold) // Display contact's name
                        Text("En ligne", fontSize = 12.sp)               // Online status subtitle
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) { // Back button
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* More options can be added here */ }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More")
                    }
                }
            )
        },
        bottomBar = {
            Column {
                Divider() // Divider above input area
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .padding(bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Attachment button (functionality can be implemented later)
                    IconButton(onClick = { /* Add attachment */ }) {
                        Icon(Icons.Default.AttachFile, contentDescription = "Attach")
                    }

                    // Input field for writing new messages
                    OutlinedTextField(
                        value = newMessage,
                        onValueChange = { newMessage = it }, // Update message input
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = 4.dp),
                        placeholder = { Text("Écrire un message") },
                        shape = RoundedCornerShape(24.dp),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.LightGray.copy(alpha = 0.2f),
                            unfocusedContainerColor = Color.LightGray.copy(alpha = 0.2f),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )

                    // Send message button
                    IconButton(
                        onClick = {
                            if (newMessage.isNotBlank()) {
                                val newMsg = Message(
                                    text = newMessage,
                                    time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date()),
                                    isMe = true
                                )
                                setMessages(messages + newMsg) // Add message to list
                                newMessage = ""                // Clear input field
                            }
                        },
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color(0xFF1B2A58), CircleShape)
                    ) {
                        Icon(
                            Icons.Default.Send,
                            contentDescription = "Send",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        // Message list using LazyColumn (reversed to show newest at bottom)
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White),
            reverseLayout = true,
            contentPadding = PaddingValues(bottom = 8.dp)
        ) {
            items(messages.reversed()) { message ->
                MessageBubble(message) // Display each message
                Spacer(modifier = Modifier.height(4.dp)) // Space between messages
            }
        }
    }
}

// UI component that displays a message bubble based on sender
@Composable
fun MessageBubble(message: Message) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = if (message.isMe) Alignment.End else Alignment.Start // Align based on sender
    ) {
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 12.dp,
                        topEnd = 12.dp,
                        bottomStart = if (message.isMe) 12.dp else 0.dp,
                        bottomEnd = if (message.isMe) 0.dp else 12.dp
                    )
                )
                .background(
                    if (message.isMe) Color(0xFF1B2A58) // Sent message background
                    else Color.LightGray.copy(alpha = 0.3f) // Received message background
                )
                .padding(12.dp)
        ) {
            Text(
                text = message.text,
                color = if (message.isMe) Color.White else Color.Black // Text color based on sender
            )
        }

        // Timestamp below the message
        Text(
            text = message.time,
            fontSize = 10.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 2.dp, start = 8.dp, end = 8.dp)
        )
    }
}

// Data class representing a chat message
data class Message(
    val text: String,      // Message content
    val time: String,      // Time sent
    val isMe: Boolean      // Indicates if the message was sent by the user
)




// ---------------------------------------------- Groups Screen ------------------------------


// Data class representing a group item
data class GroupItem(
    val name: String,                // Name of the group
    val message: String,            // Last message or description
    val date: String,               // Last activity or creation date
    @DrawableRes val iconRes: Int,  // Resource ID for group icon
    val members: List<String>       // List of member initials
)

// List of fallback colors for member bubbles
val bubbleColors = listOf(
    Color(0xFF3F51B5),  // Indigo
    Color(0xFFFF9800),  // Orange
    Color(0xFF9C27B0)   // Purple
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupsScreen(
    navController: NavController,
    onNavigateToHome: () -> Unit,
    onNavigateToContacts: () -> Unit,
    onNavigateToTasks: () -> Unit,
    onNavigateToSchedule: () -> Unit
) {
    // ─── State ───
    var groups by remember {
        mutableStateOf(
            listOf(
                GroupItem("Project Alpha", "UI/UX meeting", "10 July", R.drawable.groupe1, listOf("A")),
                GroupItem("Marketing Team", "Review slides", "23 July", R.drawable.groupe2, listOf("C")),
                GroupItem("Database Sync", "Schema updates", "14 July", R.drawable.groupe3, emptyList())
            )
        )
    }
    var searchMode by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var newName by remember { mutableStateOf("") }
    var newMessage by remember { mutableStateOf("") }

    val filtered = remember(searchQuery, groups) {
        if (searchQuery.isBlank()) groups
        else groups.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                // no navigationIcon → no back arrow
                title = {
                    if (searchMode) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Search groups...") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                    } else {
                        Text(
                            "Groups",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                },
                actions = {
                    if (searchMode) {
                        IconButton(onClick = {
                            searchMode = false
                            searchQuery = ""
                        }) {
                            Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                        }
                    } else {
                        IconButton(onClick = { searchMode = true }) {
                            Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1B2A58))
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = Color(0xFF1B2A58),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
        bottomBar = {
            NavigationBar(containerColor = Color.White, modifier = Modifier.shadow(8.dp)) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Group, contentDescription = "Groups", tint = Color(0xFF1B2A58)) },
                    label = { Text("Groups", color = Color(0xFF1B2A58)) },
                    selected = true,
                    onClick = {}
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Contacts") },
                    label = { Text("Contacts") },
                    selected = false,
                    onClick = onNavigateToContacts
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = false,
                    onClick = onNavigateToHome
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.CheckCircle, contentDescription = "Tasks") },
                    label = { Text("Tasks") },
                    selected = false,
                    onClick = onNavigateToTasks
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.CalendarToday, contentDescription = "Schedule") },
                    label = { Text("Schedule") },
                    selected = false,
                    onClick = onNavigateToSchedule
                )
            }
        }
    ) { padding ->
        // ─── List of groups ───
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            items(filtered) { group ->
                GroupRow(group) {
                    val encoded = Uri.encode(group.name)
                    navController.navigate("groupConversation/$encoded")
                }
                Divider()
            }
        }

        // ─── Dialog to create a new group ───
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Create New Group") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = newName,
                            onValueChange = { newName = it },
                            label = { Text("Group Name") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(
                            value = newMessage,
                            onValueChange = { newMessage = it },
                            label = { Text("Description") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        groups = groups + GroupItem(
                            name = newName.ifBlank { "Unnamed Group" },
                            message = newMessage,
                            date = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date()),
                            iconRes = R.drawable.groupe1,
                            members = emptyList()
                        )
                        newName = ""
                        newMessage = ""
                        showDialog = false
                    }) {
                        Text("Create")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancel")
                    }
                },
                shape = RoundedCornerShape(12.dp)
            )
        }
    }
}

@Composable
fun GroupRow(group: GroupItem, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = group.iconRes),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(group.name, fontWeight = FontWeight.Bold)
            Text(group.message, fontSize = 13.sp, color = Color.Gray)
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(group.date, fontSize = 12.sp, color = Color.Gray)
            Row {
                group.members.forEachIndexed { index, initial ->
                    val color = when (initial.uppercase()) {
                        "A" -> Color(0xFFFF9800) // Orange
                        "C" -> Color(0xFF4CAF50) // Green
                        else -> bubbleColors[index % bubbleColors.size]
                    }
                    MemberBubble(initial, color)
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupConversationScreen(
    groupName: String,
    onBack: () -> Unit
) {
    // État pour la liste des messages
    val (messages, setMessages) = remember {
        mutableStateOf(
            listOf(
                Message("Bienvenue dans le groupe $groupName !", "10:00", false),
                Message("Salut tout le monde", "10:02", true),
                Message("On commence à quelle heure ?", "10:03", false)
            )
        )
    }

    // État pour le nouveau message à envoyer
    var newMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(groupName, fontWeight = FontWeight.Bold, color = Color.White)
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = { /* Future menu options */ }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1B2A58))
            )
        },
        bottomBar = {
            Column {
                Divider()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { /* future attachment */ }) {
                        Icon(Icons.Default.AttachFile, contentDescription = "Attach")
                    }

                    OutlinedTextField(
                        value = newMessage,
                        onValueChange = { newMessage = it },
                        placeholder = { Text("Écrire un message") },
                        singleLine = true,
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = 4.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.LightGray.copy(alpha = 0.2f),
                            unfocusedContainerColor = Color.LightGray.copy(alpha = 0.2f),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )

                    IconButton(
                        onClick = {
                            if (newMessage.isNotBlank()) {
                                val newMsg = Message(
                                    text = newMessage,
                                    time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date()),
                                    isMe = true
                                )
                                setMessages(messages + newMsg)
                                newMessage = ""
                            }
                        },
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color(0xFF1B2A58), CircleShape)
                    ) {
                        Icon(
                            Icons.Default.Send,
                            contentDescription = "Send",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White),
            reverseLayout = true,
            contentPadding = PaddingValues(bottom = 8.dp)
        ) {
            items(messages.reversed()) { message ->
                MessageBubble(message)
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

// Composable to display a small circular user/member bubble with an initial inside
@Composable
fun MemberBubble(initial: String, color: Color) {
    Box(
        modifier = Modifier
            .size(24.dp)                      // Fixed size of the bubble
            .clip(CircleShape)               // Makes the box circular
            .background(color),              // Background color passed as parameter
        contentAlignment = Alignment.Center  // Centers the text inside the circle
    ) {
        Text(initial, color = Color.White, fontSize = 12.sp) // Displays the initial letter in white
    }
}





//------------------------------ TASK SCREEN -----------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(
    viewModel: ApplicationViewModel,
    onBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToContacts: () -> Unit,
    onNavigateToGroups: () -> Unit,
    onNavigateToSchedule: () -> Unit
) {
    // --- Task state: a reference to the list of tasks from view model
    val tasks by viewModel.taskList

    // --- States for search and dialog management ---
    var searchMode by remember { mutableStateOf(false) }      // Toggle search mode
    var searchQuery by remember { mutableStateOf("") }       // Search text
    var showDialog by remember { mutableStateOf(false) }       // Show add dialog
    var newTaskTitle by remember { mutableStateOf("") }       // New task title input
    var newTaskSubtitle by remember { mutableStateOf("") }    // New task subtitle input
    var newTaskDate by remember { mutableStateOf("") }        // New task date input
    var selectedTask by remember { mutableStateOf<Task?>(null) } // Object of selected task for editing
    var editTitle by remember { mutableStateOf("") }          // Edited title state
    var editSubtitle by remember { mutableStateOf("") }       // Edited subtitle state
    var editDate by remember { mutableStateOf("") }           // Edited date state

    // --- Filter tasks based on search query ---
    val filteredTasks = remember(searchQuery, tasks) {
        if (searchQuery.isBlank()) tasks
        else tasks.filter { it.title.contains(searchQuery, ignoreCase = true) }
    }

    // Provide screen structure: top bar, bottom navigation, floating action button
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (searchMode) {
                        // Search input when in search mode
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Search tasks...") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                    } else {
                        // Title display when not searching
                        Text(
                            "Tasks",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                },
                actions = {
                    if (searchMode) {
                        // Button to exit search mode
                        IconButton(onClick = {
                            searchMode = false
                            searchQuery = ""
                        }) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Close",
                                tint = Color.White
                            )
                        }
                    } else {
                        // Button to enter search mode
                        IconButton(onClick = { searchMode = true }) {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = "Search",
                                tint = Color.White
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1B2A58)
                )
            )
        },
        floatingActionButton = {
            // Button to add a new task
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = Color(0xFF1B2A58),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        },
        bottomBar = {
            // Bottom navigation bar
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Group, contentDescription = "Groups") },
                    label = { Text("Groups") },
                    selected = false,
                    onClick = onNavigateToGroups
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Contacts") },
                    label = { Text("Contacts") },
                    selected = false,
                    onClick = onNavigateToContacts
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = false,
                    onClick = onNavigateToHome
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.CheckCircle, contentDescription = "Tasks") },
                    label = { Text("Tasks") },
                    selected = true,                                  // Current tab
                    onClick = {}
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.CalendarToday, contentDescription = "Schedule") },
                    label = { Text("Schedule") },
                    selected = false,
                    onClick = onNavigateToSchedule
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            // LazyColumn to display task cards
            LazyColumn(
                contentPadding = PaddingValues(
                    top = innerPadding.calculateTopPadding() + 16.dp,
                    bottom = innerPadding.calculateBottomPadding() + 80.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(filteredTasks) { index, task ->
                    TaskCard(
                        task = task,
                        onToggle = {
                            // Toggle task completion status
                            val updatedTask = task.copy(done = !task.done)
                            viewModel.modifyTask(updatedTask)
                        },
                        modifier = Modifier.clickable {
                            // Select task for editing
                            // Retrieving corresponding task element  using a viewmodel coroutine scope
                            viewModel.viewModelScope.launch{
                                selectedTask = viewModel.getTask(task.id)

                                editTitle = task.title
                                editSubtitle = task.subtitle
                                editDate = task.date
                            }
                        }
                    )
                }
            }

            // --- Add Task Dialog ---
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Add a New Task") },
                    text = {
                        Column {
                            OutlinedTextField(
                                value = newTaskTitle,
                                onValueChange = { newTaskTitle = it },
                                label = { Text("Title") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(Modifier.height(8.dp))
                            OutlinedTextField(
                                value = newTaskSubtitle,
                                onValueChange = { newTaskSubtitle = it },
                                label = { Text("Subtitle") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(Modifier.height(8.dp))
                            OutlinedTextField(
                                value = newTaskDate,
                                onValueChange = { newTaskDate = it },
                                label = { Text("Date") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            if (newTaskTitle.isNotBlank()) {
                                // Add new task to list
                                val newTask = Task(
                                    title = newTaskTitle,
                                    subtitle = newTaskSubtitle,
                                    date = newTaskDate,
                                    done = false
                                )

                                viewModel.addTask(newTask)

                                // Reset input fields
                                newTaskTitle = ""
                                newTaskSubtitle = ""
                                newTaskDate = ""
                                showDialog = false
                            }
                        }) {
                            Text("Save")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text("Cancel")
                        }
                    },
                    shape = RoundedCornerShape(12.dp)
                )
            }

            // --- Edit Task Dialog ---
            if (selectedTask != null) {
                AlertDialog(
                    onDismissRequest = { selectedTask = null },
                    title = { Text("Edit Task") },
                    text = {
                        Column {
                            OutlinedTextField(
                                value = editTitle,
                                onValueChange = { editTitle = it },
                                label = { Text("Title") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(Modifier.height(8.dp))
                            OutlinedTextField(
                                value = editSubtitle,
                                onValueChange = { editSubtitle = it },
                                label = { Text("Subtitle") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(Modifier.height(8.dp))
                            OutlinedTextField(
                                value = editDate,
                                onValueChange = { editDate = it },
                                label = { Text("Date") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            if (editTitle.isNotBlank()) {
                                // Save task edits
                                val editedTask = selectedTask!!.copy(
                                    title = editTitle,
                                    subtitle = editSubtitle,
                                    date = editDate
                                )

                                viewModel.modifyTask(editedTask)

                                selectedTask = null
                            }
                        }) {
                            Text("Save")
                        }
                    },
                    dismissButton = {
                        Row {
                            TextButton(
                                onClick = {
                                    // Delete selected task
                                    viewModel.removeTask(selectedTask!!)
                                    selectedTask = null
                                }
                            ) {
                                Text("Delete", color = Color.Red)
                            }
                            Spacer(Modifier.width(16.dp))
                            TextButton(onClick = { selectedTask = null }) {
                                Text("Cancel")
                            }
                        }
                    },
                    shape = RoundedCornerShape(12.dp)
                )
            }
        }
    }
}


// Composable to display a task card
@Composable
fun TaskCard(
    task: Task,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFF1F3FF), shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Column for title and subtitle
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    task.title,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B2A58),
                    fontSize = 15.sp
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    task.subtitle,
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }
            // Button to toggle task completion
            IconButton(onClick = onToggle) {
                if (task.done) {
                    Box(
                        Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF1B2A58)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = "Done",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                } else {
                    Box(
                        Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color(0xFF1B2A58), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = "Not Done",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Default.DateRange,
                contentDescription = "Calendar",
                tint = Color.Gray,
                modifier = Modifier.size(16.dp)
            )
            Spacer(Modifier.width(4.dp))
            Text(
                task.date,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}




// ---------------------------- SCHEDULE SCREEN --------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    onBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToContacts: () -> Unit,
    onNavigateToGroups: () -> Unit,
    onNavigateToTasks: () -> Unit
) {
    // Dialog visibility state for adding a new event
    var showDialog by remember { mutableStateOf(false) }
    // State for input fields when creating a new event
    var newTime by remember { mutableStateOf("") }
    var newCategory by remember { mutableStateOf("") }
    var newDescription by remember { mutableStateOf("") }
    var newDate by remember { mutableStateOf("") }
    // Selected group for the new event
    var selectedGroup by remember { mutableStateOf<String?>(null) }
    // Predefined list of groups
    val groupList = listOf("Project Alpha", "Marketing Team", "Database Sync")
    // State for dropdown menu expansion when choosing a group
    var groupDropdownExpanded by remember { mutableStateOf(false) }

    // ---------- States for editing an existing event ----------
    var editingDay by remember { mutableStateOf<String?>(null) }
    var editingEvent by remember { mutableStateOf<EventItem?>(null) }
    var editTime by remember { mutableStateOf("") }
    var editCategory by remember { mutableStateOf("") }
    var editDescription by remember { mutableStateOf("") }
    var editGroup by remember { mutableStateOf<String?>(null) }

    // ---------- Sample schedule data (only Monday & Tuesday entries) ----------
    var events by remember {
        mutableStateOf(
            listOf<Pair<String, List<EventItem>>>(
                // Example event on Monday, July 18, 2025
                "18 / 07 / 2025 Monday" to listOf(
                    EventItem("13:00 - 15:00", "A", "Team meeting with marketing (Project Alpha)", Color(0xFFFFA726))
                ),
                // Example event on Tuesday, August 20, 2025
                "20 / 08 / 2025 Tuesday" to listOf(
                    EventItem("14:00 - 14:30", "C", "Interview with candidate (Marketing Team)", Color(0xFF4CAF50))
                )
            )
        )
    }

    // ---------- Search and filter states ----------
    var searchMode by remember { mutableStateOf(false) }  // Toggle for search bar visibility
    var searchQuery by remember { mutableStateOf("") }  // Text input for search
    // List of available dates from events
    val dateOptions = events.map { it.first }
    // Currently selected date filter
    var selectedDate by remember { mutableStateOf<String?>(null) }
    // Compute filtered list of events based on search query and selected date
    val filteredEvents = remember(events, searchQuery, selectedDate) {
        events
            .filter { selectedDate == null || it.first == selectedDate }
            .map { (day, list) ->
                val newList = if (searchQuery.isBlank()) list
                else list.filter { it.description.contains(searchQuery, ignoreCase = true) }
                day to newList
            }
            .filter { it.second.isNotEmpty() }
    }

    // Scaffold provides structure: top bar, content, FAB, bottom bar
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (searchMode) {
                        // Show text field when in search mode
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Search schedule...") },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                    } else {
                        // Default title when not searching
                        Text("Schedule", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    }
                },
                actions = {
                    if (searchMode) {
                        // Close search mode action
                        IconButton(onClick = {
                            searchMode = false
                            searchQuery = ""
                        }) {
                            Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                        }
                    } else {
                        // Enter search mode action
                        IconButton(onClick = { searchMode = true }) {
                            Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1B2A58))
            )
        },
        floatingActionButton = {
            // Button to add a new event
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = Color(0xFF1B2A58),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Event")
            }
        },
        bottomBar = {
            // Navigation bar at the bottom of the screen
            NavigationBar(containerColor = Color.White, modifier = Modifier.shadow(elevation = 8.dp)) {
                // Groups tab
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Group, contentDescription = "Groups") },
                    label = { Text("Groups") },
                    selected = false,
                    onClick = onNavigateToGroups
                )
                // Contacts tab
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Contacts") },
                    label = { Text("Contacts") },
                    selected = false,
                    onClick = onNavigateToContacts
                )
                // Home tab
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = false,
                    onClick = onNavigateToHome
                )
                // Tasks tab
                NavigationBarItem(
                    icon = { Icon(Icons.Default.CheckCircle, contentDescription = "Tasks") },
                    label = { Text("Tasks") },
                    selected = false,
                    onClick = onNavigateToTasks
                )
                // Schedule tab (currently selected)
                NavigationBarItem(
                    icon = {
                        Icon(
                            Icons.Default.CalendarToday,
                            contentDescription = "Schedule",
                            tint = Color(0xFF1B2A58)
                        )
                    },
                    label = { Text("Schedule", color = Color(0xFF1B2A58)) },
                    selected = true,
                    onClick = {}
                )
            }
        }
    ) { innerPadding ->
        // Main content column with padding from Scaffold
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Date filter dropdown
            if (dateOptions.isNotEmpty()) {
                var expanded by remember { mutableStateOf(false) }
                OutlinedButton(
                    onClick = { expanded = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(selectedDate ?: "Select a date")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    // Option to show all dates
                    DropdownMenuItem(
                        text = { Text("All dates") },
                        onClick = {
                            selectedDate = null
                            expanded = false
                        }
                    )
                    // List each date option
                    dateOptions.forEach { date ->
                        DropdownMenuItem(
                            text = { Text(date) },
                            onClick = {
                                selectedDate = date
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            // Show message if no events match filters
            if (filteredEvents.isEmpty()) {
                Text("No schedules found.", color = Color.Gray, modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                // Render each day block with its events
                filteredEvents.forEach { (day, eventList) ->
                    DayBlock(day, eventList, onEventClick = { event ->
                        // Prepare editing states when an event is clicked
                        editingDay = day
                        editingEvent = event
                        editTime = event.time
                        editCategory = event.category
                        editDescription = event.description
                        editGroup = groupList.firstOrNull { event.description.contains(it) }
                    })
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }

        // ---------- Dialog for adding a new event ----------
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("New Schedule") },
                text = {
                    Column {
                        // Input for date
                        OutlinedTextField(
                            value = newDate,
                            onValueChange = { newDate = it },
                            label = { Text("Date (ex: 20 / 08 / 2025 Tuesday)") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(Modifier.height(8.dp))
                        // Input for time
                        OutlinedTextField(
                            value = newTime,
                            onValueChange = { newTime = it },
                            label = { Text("Time (ex: 14:00 - 15:00)") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(Modifier.height(8.dp))
                        // Input for category
                        OutlinedTextField(
                            value = newCategory,
                            onValueChange = { newCategory = it },
                            label = { Text("Category (ex: A, B...)") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(Modifier.height(8.dp))
                        // Input for description
                        OutlinedTextField(
                            value = newDescription,
                            onValueChange = { newDescription = it },
                            label = { Text("Description") },
                            singleLine = false,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(Modifier.height(8.dp))
                        // Group selection dropdown
                        Box {
                            OutlinedButton(
                                onClick = { groupDropdownExpanded = true },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(selectedGroup ?: "Choose a group")
                            }
                            DropdownMenu(
                                expanded = groupDropdownExpanded,
                                onDismissRequest = { groupDropdownExpanded = false }
                            ) {
                                groupList.forEach { group ->
                                    DropdownMenuItem(
                                        text = { Text(group) },
                                        onClick = {
                                            selectedGroup = group
                                            groupDropdownExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    // Save button logic for new event creation
                    TextButton(onClick = {
                        if (newDate.isNotBlank() && newTime.isNotBlank() && selectedGroup != null) {
                            val newEvent = EventItem(
                                time = newTime,
                                category = newCategory.ifBlank { "A" },
                                description = newDescription + " (" + selectedGroup + ")",
                                color = Color(0xFF607D8B)
                            )
                            // Add to existing date or append a new date entry
                            val idx = events.indexOfFirst { it.first == newDate }
                            if (idx != -1) {
                                events = events.toMutableList().also {
                                    val list = it[idx].second + newEvent
                                    it[idx] = it[idx].copy(second = list)
                                }
                            } else {
                                events = events + (newDate to listOf(newEvent))
                            }
                            // Reset input fields and close dialog
                            newTime = ""
                            newCategory = ""
                            newDescription = ""
                            newDate = ""
                            selectedGroup = null
                            showDialog = false
                        }
                    }) { Text("Save") }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancel")
                    }
                },
                shape = RoundedCornerShape(12.dp)
            )
        }

        // ---------- Dialog for editing or deleting an existing event ----------
        if (editingEvent != null && editingDay != null) {
            AlertDialog(
                onDismissRequest = {
                    editingEvent = null
                    editingDay = null
                },
                title = { Text("Edit Schedule") },
                text = {
                    Column {
                        // Editable time field
                        OutlinedTextField(
                            value = editTime,
                            onValueChange = { editTime = it },
                            label = { Text("Time") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(Modifier.height(8.dp))
                        // Editable category field
                        OutlinedTextField(
                            value = editCategory,
                            onValueChange = { editCategory = it },
                            label = { Text("Category") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(Modifier.height(8.dp))
                        // Editable description field
                        OutlinedTextField(
                            value = editDescription,
                            onValueChange = { editDescription = it },
                            label = { Text("Description") },
                            singleLine = false,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(Modifier.height(8.dp))
                        // Editable group selection dropdown
                        Box {
                            var expanded by remember { mutableStateOf(false) }
                            OutlinedButton(
                                onClick = { expanded = true },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(editGroup ?: "Choose a group")
                            }
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                groupList.forEach { group ->
                                    DropdownMenuItem(
                                        text = { Text(group) },
                                        onClick = {
                                            editGroup = group
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    // Save changes to the selected event
                    TextButton(onClick = {
                        val newDesc = if (editDescription.contains("(")) editDescription.substringBefore("(").trim() else editDescription
                        val updatedEvent = editingEvent!!.copy(
                            time = editTime,
                            category = editCategory,
                            description = newDesc + " (" + (editGroup ?: "") + ")"
                        )
                        // Update event in the list
                        events = events.map { (day, list) ->
                            if (day == editingDay) {
                                day to list.map { if (it == editingEvent) updatedEvent else it }
                            } else day to list
                        }
                        editingEvent = null
                        editingDay = null
                    }) { Text("Save") }
                },
                dismissButton = {
                    Row {
                        // Delete button to remove the event entirely
                        TextButton(onClick = {
                            events = events.map { (day, list) ->
                                if (day == editingDay) {
                                    day to list.filterNot { it == editingEvent }
                                } else day to list
                            }.filter { it.second.isNotEmpty() }
                            editingEvent = null
                            editingDay = null
                        }) {
                            Text("Delete", color = Color.Red)
                        }
                        Spacer(Modifier.width(16.dp))
                        // Cancel editing dialog
                        TextButton(onClick = {
                            editingEvent = null
                            editingDay = null
                        }) { Text("Cancel") }
                    }
                },
                shape = RoundedCornerShape(12.dp)
            )
        }
    }
}

// Data class representing an event item in the schedule

data class EventItem(
    val time: String,
    val category: String,
    val description: String,
    val color: Color
)

// Composable to render a block for a single day with its events
@Composable
fun DayBlock(
    day: String,
    events: List<EventItem>,
    onEventClick: (EventItem) -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F8F8)),
        border = BorderStroke(1.dp, Color(0xFFE0E0E0))
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Header showing the day label
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1B2A58))
                    .padding(vertical = 12.dp)
            ) {
                Text(
                    text = day,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Start
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                events.forEach { event ->
                    Spacer(modifier = Modifier.height(12.dp))
                    EventItemView(event, onClick = { onEventClick(event) })
                }
            }
        }
    }
}

// Composable to render an individual event item view
@Composable
fun EventItemView(event: EventItem, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        // Display time of the event
        Text(
            text = event.time,
            fontSize = 13.sp,
            color = Color(0xFF757575),
            modifier = Modifier.padding(start = 4.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .padding(12.dp)
                .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(8.dp))
        ) {
            Spacer(modifier = Modifier.width(11.dp))
            // Colored box showing event category
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(event.color),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = event.category,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            // Description and location of the event
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = event.description,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "Location: Meeting Room ${event.category}",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}