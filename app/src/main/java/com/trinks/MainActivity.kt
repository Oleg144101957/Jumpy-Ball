package com.trinks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.trinks.ui.theme.JumpyBallTheme
import com.trinks.ui.theme.screens.LoadingScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JumpyBallTheme {
                // LoadingActivity
                LoadingScreen()
            }
        }
    }

    override fun onBackPressed() {
        //Not works
    }

    companion object{
        fun navigateToMenuActivity(context: Context){
            val intent = Intent(context, NotMainActivity::class.java)
            context.startActivity(intent)
        }
    }
}
