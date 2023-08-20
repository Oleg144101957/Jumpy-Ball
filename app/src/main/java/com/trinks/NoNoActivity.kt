package com.trinks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.trinks.ui.theme.screens.GameScreen

class NoNoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameScreen()
        }
    }

    companion object{
        fun navigateToMenuActivity(context: Context){
            val intent = Intent(context, NotMainActivity::class.java)
            context.startActivity(intent)
        }
    }
}