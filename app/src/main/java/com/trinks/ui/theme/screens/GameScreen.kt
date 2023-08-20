package com.trinks.ui.theme.screens

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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trinks.NoNoActivity
import com.trinks.R
import kotlin.math.roundToInt

@Composable
fun GameScreen(){


    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    val offsetXBase = remember { mutableStateOf(0f) }

    var ballX by remember { mutableStateOf(0.dp) }
    var ballY by remember { mutableStateOf(0.dp) }
    var xDirection by remember { mutableStateOf(1) } // 1 - to the right, -1 - to the left
    var yDirection by remember { mutableStateOf(1) } // 1 - downwards, -1 - upwards

    val ballDiameter = 50.dp
    val ballSpeed = 5.dp

    val scores = remember {
        mutableStateOf(0)
    }

    LaunchedEffect(Unit) {
        while (true) {
            withFrameNanos { _ ->
                // Update ball position
                ballX += ballSpeed * xDirection
                ballY += ballSpeed * yDirection

                // Check for collisions with screen boundaries and change direction
                if (ballX <= 0.dp || ballX >= screenWidth - ballDiameter) {
                    xDirection *= -1
                }
                if (ballY <= 0.dp || ballY >= screenHeight - ballDiameter) {
                    yDirection *= -1
                }
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


        Image(
            painter = painterResource(id = R.drawable.trinket),
            contentDescription = "ball",
            modifier = Modifier
                .size(ballDiameter)
                .offset(x = ballX, y = ballY),
        )

        Image(
            painter = painterResource(id = R.drawable.bristle),
            contentDescription = "movable base element",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(32.dp)
                .scale(2f)
                .offset {
                    IntOffset(x = offsetXBase.value.roundToInt(), y = 0)
                }
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        offsetXBase.value += dragAmount.x
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

