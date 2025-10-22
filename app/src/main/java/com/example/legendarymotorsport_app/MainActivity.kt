package com.example.legendarymotorsport_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.legendarymotorsport_app.view.components.AppTheme
import com.example.legendarymotorsport_app.viewmodel.AppContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                AppContent()
            }
        }
    }
}
