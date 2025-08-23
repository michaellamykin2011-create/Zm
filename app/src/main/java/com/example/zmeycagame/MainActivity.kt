package com.example.zmeycagame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.zmeycagame.ui.navigation.GameNavigation
import com.example.zmeycagame.ui.theme.ZmeycaGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZmeycaGameTheme {
                GameNavigation()
            }
        }
    }
}
