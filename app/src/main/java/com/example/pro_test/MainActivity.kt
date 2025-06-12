package com.example.pro_test
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
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


// Entry point of the Android application using Jetpack Compose
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the UI content using Jetpack Compose
        setContent {
            // Apply the Material Design theme
            MaterialTheme {
                // Call the main navigation function
                AppNavigation()
            }
        }
    }
}

// Composable function that handles navigation between screens
@Composable
fun AppNavigation() {
    // NavController used to manage app navigation
    val navController = rememberNavController()

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
    }
}



// ------------------------- Welcome screen UI composable --------------------------------------------------------



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



// -------------------------------------- Login Screen --------------------------------------



@Composable
fun ModernLoginScreen(
    onLoginSuccess: () -> Unit,       // Callback triggered when login is successful
    onNavigateToSignUp: () -> Unit,   // Callback to navigate to the sign-up screen
    onForgotPassword: () -> Unit      // Callback for forgot password action
) {
    // State variables for email and password inputs
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) } // Toggle for password visibility

    // UI colors and gradient
    val primaryColor = Color(0xFF1B2A58)     // Main theme color (dark blue)
    val signUpAccent = Color(0xFFC49F50)     // Accent color for sign-up (golden)
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFFE3F2FD), Color.White) // Light blue to white background
    )

    // Outer container for the screen layout
    Box(
        modifier = Modifier
            .fillMaxSize()                  // Fills the entire screen
            .background(backgroundGradient) // Applies background gradient
            .padding(horizontal = 24.dp)    // Horizontal padding for content
    ) {
        // Vertical layout for the login content
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,          // Centers content vertically
            horizontalAlignment = Alignment.CenterHorizontally // Centers content horizontally
        ) {
            // Heading text
            Text(
                text = "Welcome Back",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = primaryColor
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Subheading text
            Text("Your professional hub starts here", fontSize = 14.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(32.dp))

            // Email input field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it }, // Update email state
                label = { Text("Email") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = primaryColor) },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password input field with visibility toggle
            OutlinedTextField(
                value = password,
                onValueChange = { password = it }, // Update password state
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
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Forgot password text aligned to the right
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onForgotPassword) {
                    Text("Forgot password?", color = signUpAccent)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Login button
            Button(
                onClick = {
                    if (email.isNotBlank() && password.isNotBlank()) {
                        onLoginSuccess() // Trigger success callback if fields are not empty
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

            Spacer(modifier = Modifier.height(16.dp))

            // Divider with "OR" text
            Text("OR", color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))

            // Sign-up button (outlined)
            OutlinedButton(
                onClick = onNavigateToSignUp, // Navigate to sign-up screen
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.5.dp, signUpAccent)
            ) {
                Text("Create New Account", fontWeight = FontWeight.Medium)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Legal disclaimer
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
            Text(
                text = "Create Account",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = primaryColor
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Join the hub and start your journey",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Full Name
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Full Name") },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = null, tint = primaryColor)
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = primaryColor,
                    cursorColor = primaryColor
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                leadingIcon = {
                    Icon(Icons.Default.Email, contentDescription = null, tint = primaryColor)
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = primaryColor,
                    cursorColor = primaryColor
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = null, tint = primaryColor)
                },
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
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = primaryColor,
                    cursorColor = primaryColor
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Confirm Password
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = null, tint = primaryColor)
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = primaryColor,
                    cursorColor = primaryColor
                ),
                modifier = Modifier.fillMaxWidth()
            )

            if (showPasswordError) {
                Text(
                    text = "Passwords do not match",
                    color = Color.Red,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Create Account Button
            Button(
                onClick = {
                    if (name.isNotBlank() && email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()) {
                        if (password == confirmPassword) {
                            showPasswordError = false
                            onSignUpSuccess()
                        } else {
                            showPasswordError = true
                        }
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
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        TopSection(onLogout)

        MessageSection(
            onFriendClick = { Toast.makeText(context, "Friend Clicked", Toast.LENGTH_SHORT).show() },
            onGroupClick  = { Toast.makeText(context, "Group Clicked",  Toast.LENGTH_SHORT).show() }
        )

        Spacer(Modifier.height(24.dp))

        EventsRowSection(
            onPersonalEventClick = { showPersonalDialog = true },
            onSharedEventClick   = { showSharedDialog   = true }
        )

        Spacer(modifier = Modifier.weight(1f))

        NavigationBar(containerColor = Color.White) {
            val items = listOf("Groups","Contacts","Home","Tasks","Schedule")
            val icons = listOf(
                Icons.Default.Groups,
                Icons.Default.Person,
                Icons.Default.Home,
                Icons.Default.CheckCircle,
                Icons.Default.CalendarToday
            )
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon =    { Icon(icons[index], contentDescription = item) },
                    label =   { Text(item) },
                    selected = selectedIndex == index,
                    onClick = {
                        selectedIndex = index
                        when(index){
                            0 -> onNavigateToGroups()
                            1 -> onNavigateToContacts()
                            3 -> onNavigateToTasks()
                            4 -> onNavigateToSchedule()
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF1B2A58),
                        selectedTextColor = Color(0xFF1B2A58),
                        indicatorColor    = Color(0xFFF1F1F1)
                    )
                )
            }
        }
    }

    if (showPersonalDialog) {
        PersonalTaskDialog(
            onDismiss = { showPersonalDialog = false },
            onSave    = { /* sauver */ showPersonalDialog = false }
        )
    }

    if (showSharedDialog) {
        SharedTaskDialog(
            onDismiss = { showSharedDialog = false },
            onSave    = { /* sauver */ showSharedDialog = false }
        )
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
    var selectedUser by remember { mutableStateOf("Ouiam") }   // Currently selected user

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

        // Row with user name, dropdown icon, and account switch/logout menu
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { expanded = true } // Expand dropdown on click
        ) {
            Text(selectedUser, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)

            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Change account",
                tint = Color.White
            )

            // Dropdown menu for selecting user or logging out
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Ouiam") },
                    onClick = {
                        selectedUser = "Ouiam"
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Claire") },
                    onClick = {
                        selectedUser = "Claire"
                        expanded = false
                    }
                )
                Divider()
                DropdownMenuItem(
                    text = { Text("Logout") },
                    onClick = {
                        expanded = false
                        onLogout() // Trigger logout
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

// Data class representing a task item with metadata
data class TaskData(
    val title: String,                       // Task title
    val subtitle: String,                    // Task subtitle or short description
    val date: String,                        // Scheduled date for the task
    @DrawableRes val members: List<Int>,     // List of drawable resource IDs for assigned members
    val done: Boolean                        // Status indicating if the task is completed
)





//------------------------------ TASK SCREEN -----------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(
    onBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToContacts: () -> Unit,
    onNavigateToGroups: () -> Unit,
    onNavigateToSchedule: () -> Unit
) {
    // ─── Liste initiale avec le paramètre `members` fourni ───
    var tasks by remember {
        mutableStateOf(
            listOf(
                TaskData(
                    title = "Design 2 App Screens",
                    subtitle = "Need to design for citter",
                    date = "Mon, 10 July 2025",
                    members = listOf(R.drawable.person4, R.drawable.person3, R.drawable.person7),
                    done = true
                ),
                TaskData(
                    title = "Design 1 Website",
                    subtitle = "Need to design for citter",
                    date = "Tue, 23 July 2025",
                    members = listOf(R.drawable.person6, R.drawable.person1, R.drawable.person2),
                    done = true
                ),
                TaskData(
                    title = "Data-Base",
                    subtitle = "Need to design for citter",
                    date = "Sat, 14 July 2026",
                    members = emptyList(),  // pas d’avatars ici
                    done = false
                )
            )
        )
    }

    // ─── États de recherche ───
    var searchMode by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredTasks = remember(searchQuery, tasks) {
        if (searchQuery.isBlank()) tasks
        else tasks.filter { it.title.contains(searchQuery, ignoreCase = true) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                title = {
                    if (searchMode) {
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
                        Text("Tasks", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
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
                onClick = { /* Add task */ },
                containerColor = Color(0xFF1B2A58),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        },
        bottomBar = {
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
                    selected = true,
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
                        tasks = tasks.toMutableList().also {
                            it[index] = it[index].copy(done = !it[index].done)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun TaskCard(
    task: TaskData,
    onToggle: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF1F3FF), shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(task.title, fontWeight = FontWeight.Bold, color = Color(0xFF1B2A58), fontSize = 15.sp)
                Spacer(Modifier.height(4.dp))
                Text(task.subtitle, fontSize = 13.sp, color = Color.Gray)
            }
            IconButton(onClick = onToggle) {
                if (task.done) {
                    Box(
                        Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF1B2A58)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Check, contentDescription = "Done", tint = Color.White, modifier = Modifier.size(18.dp))
                    }
                } else {
                    Box(
                        Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color(0xFF1B2A58), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Check, contentDescription = "Not Done", tint = Color.White, modifier = Modifier.size(18.dp))
                    }
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.DateRange, contentDescription = "Calendar", tint = Color.Gray, modifier = Modifier.size(16.dp))
            Spacer(Modifier.width(4.dp))
            Text(task.date, fontSize = 12.sp, color = Color.Gray)
        }
    }
}


// Data class representing a scheduled event
data class EventItem(
    val time: String,
    val category: String,
    val description: String,
    val color: Color
)





// ---------------------------- SCHEDULE SCREEN --------------------------------




@OptIn(ExperimentalMaterial3Api::class)
// Main composable for the Schedule screen
@Composable
fun ScheduleScreen(
    onBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToContacts: () -> Unit,
    onNavigateToGroups: () -> Unit,
    onNavigateToTasks: () -> Unit
) {
    Scaffold(
        // Top app bar with title and search icon
        topBar = {
            TopAppBar(
                title = { Text("Schedule", color = Color.White) },
                actions = {
                    IconButton(onClick = { /* Handle search */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1B2A58))
            )
        },

        // Floating action button to add a new event
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Add event */ },
                containerColor = Color(0xFF1B2A58),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Event")
            }
        },

        // Bottom navigation bar with different app sections
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                modifier = Modifier.shadow(elevation = 8.dp) // Elevation for shadow effect
            ) {
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
                    selected = false,
                    onClick = onNavigateToTasks
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            Icons.Default.CalendarToday,
                            contentDescription = "Schedule",
                            tint = Color(0xFF1B2A58)
                        )
                    },
                    label = {
                        Text(
                            "Schedule",
                            color = Color(0xFF1B2A58)
                        )
                    },
                    selected = true, // Current screen is Schedule
                    onClick = {}
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding) // Prevent overlap with top/bottom bars
                .padding(16.dp)
        ) {
            // Static date picker (not editable, placeholder format)
            OutlinedTextField(
                value = "DD/MM/YYYY", // Placeholder date format
                onValueChange = {},
                enabled = false,
                trailingIcon = {
                    Icon(Icons.Default.CalendarToday, contentDescription = "Calendar")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Event section for Monday
            DayBlock("18 / 07 / 2025                                               Monday", listOf(
                EventItem("13:00 - 15:00", "A", "Team meeting with marketing", Color(0xFFFFA726)),
            ))

            Spacer(modifier = Modifier.height(12.dp))

            // Event section for Tuesday
            DayBlock("20 / 08 / 2025                                              Tuesday", listOf(
                EventItem("14:00 - 14:30", "C", "Interview with candidate", Color(0xFF4CAF50))
            ))
        }
    }
}

// Composable that displays a block of scheduled events for a specific day
@Composable
fun DayBlock(day: String, events: List<EventItem>) {
    Card(
        shape = RoundedCornerShape(12.dp), // Rounded corners for the card
        modifier = Modifier.fillMaxWidth(), // Card fills available width
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F8F8)), // Light background
        border = BorderStroke(1.dp, Color(0xFFE0E0E0)) // Light gray border
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Day header (e.g., "18/07/2025 Monday") with full-width background
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1B2A58)) // Dark blue header background
                    .padding(vertical = 12.dp) // Vertical padding inside header
            ) {
                Text(
                    text = day,
                    fontSize = 14.sp, // Slightly smaller text
                    fontWeight = FontWeight.Normal,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp), // Left/right padding inside text
                    textAlign = TextAlign.Start // Align text to the start
                )
            }

            // Event list for this day
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp) // Padding around the list of events
            ) {
                events.forEach { event ->
                    Spacer(modifier = Modifier.height(12.dp)) // Space between events
                    EventItemView(event) // Display each individual event
                }
            }
        }
    }
}

// Composable that shows details for a single event item
@Composable
fun EventItemView(event: EventItem) {
    Column(
        modifier = Modifier.fillMaxWidth() // Ensure full-width layout
    ) {
        // Display the time range of the event (e.g., "13:00 - 15:00")
        Text(
            text = event.time,
            fontSize = 13.sp,
            color = Color(0xFF757575), // Medium gray text
            modifier = Modifier.padding(start = 4.dp) // Slight left padding
        )

        // Row containing the event's colored category box and its description
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(8.dp)) // White background for event row
                .padding(12.dp) // Inner padding for spacing
                .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(8.dp)) // Light gray border
        ) {
            Spacer(modifier = Modifier.width(11.dp)) // Small left spacing to offset content

            // Colored category box (e.g., "A", "B", "C") with fixed size
            Box(
                modifier = Modifier
                    .size(36.dp) // Square box
                    .clip(RoundedCornerShape(4.dp)) // Slightly rounded corners
                    .background(event.color), // Dynamic color based on event
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = event.category,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(16.dp)) // Space between box and text

            Column(
                modifier = Modifier.weight(1f) // Take remaining horizontal space
            ) {
                // Description of the event
                Text(
                    text = event.description,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )

                // Location hint based on the category (placeholder logic)
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
