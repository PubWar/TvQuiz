package com.pubwar.quiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Allow content to extend into the system bars (behind the status bar)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge();
        // Optionally set the status bar color to be transparent
        window.statusBarColor = Color.Transparent.toArgb()

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier.fillMaxSize() // Fill the entire screen, including behind the status bar
                ) {
                    // Your composable content here
                    // This content will now extend behind the status bar
                    App()
                }
            }
        }
    }
}

