package com.trinks.ui.theme.screens

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trinks.MainActivity
import com.trinks.R
import kotlinx.coroutines.delay

@Composable
fun JumpyScreen(){

    val a = LocalContext.current as Activity
    a.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    val context = LocalContext.current

    val animCircleRadius = remember {
        Animatable(1.50f)
    }

    val animateSize = remember {
        Animatable(128f)
    }

    LaunchedEffect(Unit){
        animateSize.animateTo(
            targetValue = 256f,
            infiniteRepeatable(
                tween(700, 100),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    LaunchedEffect(Unit){
        animCircleRadius.animateTo(
            targetValue = 0f,
            animationSpec = tween(1800, 100, FastOutSlowInEasing)
        )
    }

    LaunchedEffect(Unit){
        //Navigate to menu
        delay(2000)
        MainActivity.navigateToMenuActivity(context)
    }

    Box(modifier = Modifier.fillMaxSize()){

        Image(
            painter = painterResource(id = R.drawable.v1080_1920_1_1258),
            contentDescription = "back",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Canvas(modifier = Modifier.fillMaxSize()) {
            val circleRadius = size.minDimension * animCircleRadius.value // Change this value to adjust the circle size
            val circleCenter = center
            drawCircle(
                color = Color.White,
                center = circleCenter,
                radius = circleRadius
            )
        }

        Image(
            painter = painterResource(id = R.drawable.cascade),
            contentDescription = "rotating",
            modifier = Modifier
                .align(Alignment.Center)
                .size(animateSize.value.dp)
        )

        Text(
            text = "Loading...",
            color = Color.White,
            fontSize = 32.sp,
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (-160).dp)
        )
    }
}