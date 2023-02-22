package com.yusuftalhaklc.todoapplication

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay

@Composable
fun SplashScreen() {
    val context = LocalContext.current
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
        LaunchedEffect(true) {

            delay(3000L) // 3 saniye bekleme süresi
            // MainActivity'ye yönlendirme yapılıyor
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

}