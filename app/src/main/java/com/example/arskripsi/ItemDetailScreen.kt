package com.example.arskripsi

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.arskripsi.ui.theme.ARSkripsiTheme

@Composable
fun ItemDetailScreen(item: String) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Detail of $item", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val intent = Intent(context, LihatAR::class.java)
            intent.putExtra("MODEL_LOCATION", "models/$item.glb")
            context.startActivity(intent)
        }) {
            Text(text = "Lihat dalam bentuk AR")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemDetailScreenPreview() {
    ARSkripsiTheme {
        ItemDetailScreen("Meja")
    }
}