package com.example.arskripsi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.example.arskripsi.ui.theme.ARSkripsiTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ARSkripsiTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val window = (context as ComponentActivity).window
    val view = LocalView.current

    SideEffect {
        val windowInsetsController = WindowCompat.getInsetsController(window, view)
        windowInsetsController.systemBarsBehavior.dec()
    }

    var selectedIndex by remember { mutableIntStateOf(0) }
    var selectedItem by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = when {
                        selectedItem != null -> "Detail Barang"
                        selectedIndex == 0 -> "Home"
                        selectedIndex == 1 -> "Keranjang"
                        selectedIndex == 2 -> "Login"
                        else -> "App"
                    })
                },
                navigationIcon = if (selectedItem != null) {
                    {
                        IconButton(onClick = { selectedItem = null }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                } else null
            )
        },
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Column {
                Spacer(modifier = Modifier.height(50.dp))
                BottomNavigationBar(selectedIndex) { index ->
                    selectedIndex = index
                    selectedItem = null // Reset selected item when changing tabs
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when {
                selectedItem != null -> ItemDetailScreen(selectedItem!!)
                selectedIndex == 0 -> HomeScreen { item ->
                    selectedItem = item
                }
                selectedIndex == 1 -> CartScreen()
                selectedIndex == 2 -> LoginScreen()
            }
        }
    }
}

@Composable
fun BottomNavigationBar(selectedIndex: Int, onItemSelected: (Int) -> Unit) {
    BottomNavigation {
        BottomNavigationItem(
            selected = selectedIndex == 0,
            onClick = { onItemSelected(0) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Beranda") }
        )
        BottomNavigationItem(
            selected = selectedIndex == 1,
            onClick = { onItemSelected(1) },
            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Cart") },
            label = { Text("Keranjang") }
        )
        BottomNavigationItem(
            selected = selectedIndex == 2,
            onClick = { onItemSelected(2) },
            icon = { Icon(Icons.Default.Person, contentDescription = "Login") },
            label = { Text("Login") }
        )
    }
}

@Composable
fun HomeScreen(onItemClick: (String) -> Unit) {
    val items = listOf("Meja", "Kursi", "Lemari", "LemariKecil","KursiKayu")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(items) { item ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(8.dp)
                    .background(Color.LightGray)
                    .clickable { onItemClick(item) },
                contentAlignment = Alignment.Center
            ) {
                Text(text = item, style = MaterialTheme.typography.headlineLarge)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    ARSkripsiTheme {
        MainScreen()
    }
}