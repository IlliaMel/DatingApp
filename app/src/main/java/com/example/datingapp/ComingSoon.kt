package com.example.datingapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun ComingSoon() {
    Box(modifier = Modifier.fillMaxSize().background(Color.LightGray),
        contentAlignment = Alignment.Center){
        Text(fontSize = 22.sp, text = "coming soon...", color = Color.Gray)
    }
}
