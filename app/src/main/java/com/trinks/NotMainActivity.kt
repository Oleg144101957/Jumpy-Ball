package com.trinks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.trinks.ui.theme.screens.MenuScreen
import kotlin.system.exitProcess

class NotMainActivity : ComponentActivity() {

    //Menu Screen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MenuScreen()
        }
    }

    override fun onBackPressed() {
        //Not works
    }

    companion object{
        fun navigateToGameActivity(context: Context){
            val intent = Intent(context, NoNoActivity::class.java)
            context.startActivity(intent)
        }

        fun navigateToExit(context: Context){
            val activity = context as NotMainActivity
            activity.moveTaskToBack(true)
            exitProcess(-1)
        }
    }
}