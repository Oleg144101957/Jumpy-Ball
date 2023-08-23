package com.trinks.ui.theme.screens

import android.app.Activity
import android.content.pm.ActivityInfo
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trinks.NoNoActivity
import com.trinks.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun GameScreen(){

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    val density = LocalDensity.current

    val offsetXBase = remember { mutableStateOf(0f) }

    var platformX by remember { mutableStateOf(0.dp) }
    var platformXEnd by remember { mutableStateOf(0.dp) }
    var platformY by remember { mutableStateOf(0.dp) }


    var ballX by remember { mutableStateOf(0.dp) }
    var ballY by remember { mutableStateOf(0.dp) }
    var xDirection by remember { mutableStateOf(1) } // 1 - to the right, -1 - to the left
    var yDirection by remember { mutableStateOf(1) } // 1 - downwards, -1 - upwards

    val ballDiameter = 50.dp
    val ballSpeed = 5.dp

    val scores = remember {
        mutableStateOf(0)
    }

    val isBallVisible = remember {
        mutableStateOf(true)
    }


    LaunchedEffect(Unit) {
        while (true) {
            withFrameNanos { _ ->
                // Update ball position
                ballX += ballSpeed * xDirection
                ballY += ballSpeed * yDirection

                //if y directio
                //when if else Rect.f

                Log.d("111111", "ballY $ballY ballX $ballX platformX $platformX platformY $platformY xDirection $xDirection yDirection $yDirection")


//                if(abs(xDirection) > abs(yDirection)){
//
//                } else {
//
//                }


                if (ballX in platformX..platformXEnd && ballY + ballDiameter > platformY){
                    Log.d("111111", "платформа отскок")
                    yDirection *= -1

                } else if (ballX <= 0.dp || ballX >= screenWidth - ballDiameter) {
                    Log.d("111111", "Сталкиваемся со стеной ")
                    xDirection *= -1

                } else if(ballY <= 0.dp){
                    Log.d("111111", "сталкиваемся с потолком")
                    yDirection *= -1

                } else if (ballY >= screenHeight - ballDiameter){
                    Log.d("111111", "соприкосновение с полом")
                    isBallVisible.value = false

                } else {
                    Log.d("111111", "else")
                }



//                if (ballX in platformX-5.dp..platform1of3+5.dp && ballY + ballDiameter in platformY..platformY+5.dp){
//                    yDirection *= -2
//                }
//
//                if (ballX in platformXEnd-platform1of3-5.dp..platformXEnd+5.dp && ballY + ballDiameter in platformY..platformY+5.dp){
//                    yDirection *= -2
//                }
            }
        }
    }


    Box(modifier = Modifier
        .fillMaxSize()
    ){

        Image(
            painter = painterResource(id = R.drawable.bg),
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        PlayDecor(scores.value)

        if (isBallVisible.value){
            Image(
                painter = painterResource(id = R.drawable.trinket),
                contentDescription = "ball",
                modifier = Modifier
                    .size(ballDiameter)
                    .offset(x = ballX, y = ballY)
                    .onGloballyPositioned {

                    }
            )
        }

        Image(
            painter = painterResource(id = R.drawable.bristle),
            contentDescription = "movable base element",
            modifier = Modifier
                .scale(3f)
                .align(Alignment.BottomCenter)
                .padding(32.dp)
                .offset {
                    IntOffset(x = offsetXBase.value.roundToInt(), y = 0)
                }
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        offsetXBase.value += dragAmount.x
                    }
                }
                .onGloballyPositioned {
                    with(density) {
                        platformX = it.positionInRoot().x.toDp()
                        platformXEnd = it.positionInRoot().x.toDp() + it.size.width.toDp()
                        platformY = it.positionInRoot().y.toDp()
                    }
                }
        )
    }
}

@Composable
fun BoxScope.PlayDecor(scores: Int){

    val context = LocalContext.current

    Icon(
        imageVector = Icons.Rounded.ExitToApp,
        contentDescription = "Btn close",
        tint = Color.LightGray,
        modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(16.dp)
            .size(32.dp)
            .clickable {
                NoNoActivity.navigateToMenuActivity(context)
            }
    )

    Box(modifier = Modifier
        .align(Alignment.TopCenter)
        .padding(top = 32.dp)

    ){
        Image(
            painter = painterResource(id = R.drawable.spiral),
            contentDescription = "scores background",
            modifier = Modifier
                .scale(2f)
                .align(Alignment.Center)
        )

        Text(
            text = "Scores is $scores",
            color = Color.White,
            fontSize = 24.sp,
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}

