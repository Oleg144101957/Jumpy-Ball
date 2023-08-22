package com.trinks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.trinks.ui.theme.JumpyBallTheme
import com.trinks.ui.theme.screens.JumpyScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JumpyBallTheme {
                // LoadingActivity
                JumpyScreen()
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
