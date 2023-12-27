package com.ankit.gupta.weatherapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ankit.gupta.weatherapp.ui.composables.MessageCard
import com.example.compose.AppTheme

class WeatherAppComposeActivity:ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            AppTheme {
                HomeScreen()
            }
        }
    }
}

@Composable
fun HomeScreen(){
    MessageCard(name = "Ankit")
}

@Preview
@Composable
fun PreviewHomeScreen() {
    MessageCard("Android")
}
