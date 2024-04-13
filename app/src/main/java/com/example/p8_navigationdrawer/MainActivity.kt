package com.example.p8_navigationdrawer

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.p8_navigationdrawer.ui.theme.P8navigationDrawerTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            P8navigationDrawerTheme {
                Drawer()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Drawer() {
    val context = LocalContext.current
    // State to manage if the drawer is open or closed
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    // RememberCoroutineScope to launch coroutine for drawer state
    val scope = rememberCoroutineScope()


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Box(modifier = Modifier.background(Color.DarkGray)) {
            ModalDrawerSheet {
                Column {
                Text("Lab Programs", modifier = Modifier
                    .padding(16.dp),
                    fontWeight = FontWeight.Bold)
                }
                Divider()
                // List of navigation items
                val items = listOf("Notification-App", "IPL-App","LAB-6", "LAB-7")
                items.forEach { item ->
                    Row(modifier = Modifier
                        .padding(5.dp,0.dp),verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        Icon(imageVector = Icons.Default.AccountBox, contentDescription = "icon")
                    NavigationDrawerItem(
                        label = { Text(text = item) },
                        selected = false,
                        onClick = {
                            when(item){
                                "Notification-App" -> openApp(context,"com.example.notification","com.example.notification.MainActivity");
                                "IPL-App" -> openApp(context,"com.example.ipl_2024","com.example.ipl_2024.MainActivity");
                                "LAB-6" -> openApp(context,"com.example.p6_fragment_registration","com.example.p6_fragment_registration.MainActivity");
                                "LAB-7" -> openApp(context,"com.example.menu_app","com.example.menu_app.MainActivity");
                            }
                        }
                    )
                    }
                }
            }
            }
        }
    ) {
        Column {

        Column(modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)) {
            IconButton(onClick = {
                // Toggle drawer state
                if (drawerState.isClosed) {
                    scope.launch { drawerState.open() }
                } else {
                    scope.launch { drawerState.close() }
                }
            }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "menu", tint = Color.White)
            }

        }
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Text(text = "Explore My Programs",
                color = Color.White)
        }
        }
    }
}

fun openApp(context: Context, packageName:String, className: String): Boolean {
    return try {
        val intent = Intent().apply {
            setPackage(packageName)
            val componentName = ComponentName(packageName, className) // Replace with the main activity of the app
            setComponent(componentName)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
        true
    } catch (e: ActivityNotFoundException) {
        false
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    P8navigationDrawerTheme {
        Drawer()
    }
}
