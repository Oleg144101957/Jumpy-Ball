package com.trinks.ui.theme.screens

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trinks.NoNoActivity
import com.trinks.R
import kotlin.random.Random

data class Obstacle(
    val x: Dp,
    val y: Dp,
    val width: Dp,
    val height: Dp,
    val draw: Boolean = true
) {
    companion object {
        val zero: Obstacle
            get() = Obstacle(
                x = 0.dp,
                y = 0.dp,
                width = 0.dp,
                height = 0.dp
            )
    }
}

@Composable
fun GameScreen(
) {


    val a = LocalContext.current as Activity
    a.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    val context = LocalContext.current

    val density = LocalDensity.current

    val isOver = remember {
        mutableStateOf(false)
    }

    val offsetXBaseInDp = remember { mutableStateOf(0.dp) }
    val random = remember { Random(System.currentTimeMillis()) }

    var ballX by remember { mutableStateOf(1.dp) }
    var ballY by remember { mutableStateOf(1.dp) }
    var xDirection by remember { mutableStateOf(1) } // 1 - to the right, -1 - to the left
    var yDirection by remember { mutableStateOf(1) } // 1 - downwards, -1 - upwards


    val ballDiameter = 50.dp
    var ballSpeed by remember {
        mutableStateOf(5.dp)
    }

    val scores = remember {
        mutableStateOf(0)
    }

    val isBallVisibleBall = remember {
        mutableStateOf(true)
    }

    val platform = remember { mutableStateOf(Obstacle.zero) }

    val obstacles = remember {
        mutableStateListOf<Obstacle>()
    }

    LaunchedEffect(Unit) {


        repeat(5) {
            obstacles.add(
                Obstacle(
                    x = random.nextInt(0, (screenWidth - 50.dp).value.toInt()).dp,
                    y = random.nextInt(64, (screenHeight - 70.dp).value.toInt()).dp,
                    width = 50.dp,
                    height = 50.dp
                )
            )
        }

        fun check(x: Dp, y: Dp, obstacle: Obstacle): Boolean {
            return x + ballDiameter >= obstacle.x &&
                    x <= obstacle.x + obstacle.width &&
                    y + ballDiameter >= obstacle.y &&
                    y <= obstacle.y + obstacle.height
        }

        while (true) {
            withFrameNanos { _ ->
                if (ballY + ballDiameter >= screenHeight) {
                    isBallVisibleBall.value = false
                    isOver.value = true
                } else if (check(ballX, ballY, platform.value)) {
                    ballY = platform.value.y - ballDiameter
                    yDirection *= -1
                } else if (ballY <= 0.dp) {
                    ballY = 0.dp
                    yDirection *= -1
                }  else if (ballX <= 0.dp) {
                    ballX = 0.dp
                    xDirection *= -1
                } else if (ballX >= screenWidth - ballDiameter) {
                    ballX = screenWidth - ballDiameter
                    xDirection *= -1
                } else {
                    val hit: List<Obstacle> = obstacles.filter { check(ballX, ballY, it) }
                    obstacles.removeAll(hit)
                    repeat(hit.size) {

                        val obstacle = Obstacle(
                            x = random.nextInt(0, (screenWidth - 50.dp).value.toInt()).dp,
                            y = random.nextInt(64, (screenHeight - 70.dp).value.toInt()).dp,
                            width = 50.dp,
                            height = 50.dp
                        )

                        obstacles.add(obstacle)
                        scores.value += 1
                    }
                    if (hit.isNotEmpty()) {
                        yDirection *= -1
                    }
                }

                // Update ball position
                ballX += ballSpeed * xDirection
                ballY += ballSpeed * yDirection
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

        if (isBallVisibleBall.value){
            Image(
                painter = painterResource(id = R.drawable.trinket),
                contentDescription = "ball",
                modifier = Modifier
                    .size(ballDiameter)
                    .offset(x = ballX, y = ballY)
            )
        }


        Image(
            painter = painterResource(id = R.drawable.bristle),
            contentDescription = "movable base element",
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(32.dp)
                .aspectRatio(3.8f)
                .align(Alignment.BottomCenter)
                .offset(x = offsetXBaseInDp.value)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        offsetXBaseInDp.value += dragAmount.x.toDp()
                    }
                }
                .onGloballyPositioned {
                    with(density) {
                        platform.value = Obstacle(
                            x = it.positionInRoot().x.toDp(),
                            y = it.positionInRoot().y.toDp(),
                            width = it.size.width.toDp(),
                            height = it.size.height.toDp()
                        )
                    }
                }
        )


        obstacles.forEach {
            Image(
                painter = painterResource(id = R.drawable.mosaic),
                contentDescription = "obstacle",
                modifier = Modifier
                    .size(it.width, it.height)
                    .offset(x = it.x, y = it.y)
                    .background(Color.Blue)
            )
        }

        if (isOver.value){
            GameOverScreen(context)
        }
    }
}


@Composable
fun BoxScope.GameOverScreen(context: Context){

    Box(modifier = Modifier
        .background(Color.Black)
        .fillMaxSize()
    ){

        Text(
            text = "Game over !!!",
            color = Color.White,
            fontSize = 48.sp,
            modifier = Modifier
                .align(Alignment.Center)
        )

        Icon(
            imageVector = Icons.Rounded.ExitToApp,
            contentDescription = "close",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(32.dp)
                .clickable {
                    NoNoActivity.navigateToMenuActivity(context)
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

