package com.example.kurierapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kurierapp.profile.ProfileScreen
import com.example.kurierapp.shipments.ShipmentsScreen
import com.example.kurierapp.ui.theme.KurierAppTheme
import androidx.compose.runtime.collectAsState
import com.example.kurierapp.send.SendScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        TokenManager.init(this)
        
        enableEdgeToEdge()
        setContent {
            KurierAppTheme {
                KurierAppApp()
            }
        }
    }
}

@Preview
@Composable
fun KurierAppApp(viewModel: MainViewModel = viewModel()) {

    val uiState = viewModel.state.collectAsState().value

    if (!uiState.isLoggedIn) {
        LoginScreen(
            state = uiState,
            onUsernameChange = { viewModel.onLoginChange(it) },
            onPasswordChange = { viewModel.onPasswordChange(it) },
            onLogin = { viewModel.performLogin() }
        )
        return
    }

    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.SHIPMENTS) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            it.icon,
                            contentDescription = it.label
                        )
                    },
                    label = { Text(it.label) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it }
                )
            }
        }
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            when (currentDestination) {
                AppDestinations.SHIPMENTS -> ShipmentsScreen(
                    modifier = Modifier.padding(innerPadding)
                )
                AppDestinations.SEND -> SendScreen(
                    modifier = Modifier.padding(innerPadding)
                )
                AppDestinations.PROFILE -> ProfileScreen(
                    logout = { viewModel.logout() },
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

enum class AppDestinations(
    val label: String,
    val icon: ImageVector,
) {
    SHIPMENTS("Przesyłki", Icons.Default.Home),
    SEND("Nadaj", Icons.AutoMirrored.Default.Send),
    PROFILE("Profil", Icons.Default.AccountBox),
}

@Composable
fun LoginScreen(
    state: MainUiState,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLogin: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            value = state.login,
            onValueChange = { onUsernameChange(it) },
            label = { Text("Login") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.password,
            onValueChange = { onPasswordChange(it) },
            label = { Text("Hasło") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        if (state.isError) {
            Text("Błąd logowania", color = MaterialTheme.colorScheme.error)
        }

        Button(
            onClick = onLogin,
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            enabled = !state.isLoading
        ) {
            if (state.isLoading) CircularProgressIndicator(modifier = Modifier.size(20.dp))
            else Text("Zaloguj się")
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KurierAppTheme {
        Greeting("Android")
    }
}
