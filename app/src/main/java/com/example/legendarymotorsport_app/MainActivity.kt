package com.example.legendarymotorsport_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.legendarymotorsport_app.view.LegendaryMotorSportTheme
import com.example.legendarymotorsport_app.viewmodel.AppContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LegendaryMotorSportTheme {
                AppContent()
            }
        }
    }
}
