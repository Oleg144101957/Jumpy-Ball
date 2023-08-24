package com.trinks.ui.theme.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
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
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trinks.NoNoActivity
import com.trinks.R

@Composable
fun GameScreen(){

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    val density = LocalDensity.current

    val offsetXBaseInDp = remember { mutableStateOf(0.dp) }

    var platformX by remember { mutableStateOf(0.dp) }
    var platformXEnd by remember { mutableStateOf(0.dp) }
    var platformY by remember { mutableStateOf(0.dp) }


    var ballX by remember { mutableStateOf(1.dp) }
    var ballY by remember { mutableStateOf(1.dp) }
    var xDirection by remember { mutableStateOf(1) } // 1 - to the right, -1 - to the left
    var yDirection by remember { mutableStateOf(1) } // 1 - downwards, -1 - upwards



    //unmovable element 1
    var unmovableX1 by remember { mutableStateOf(1.dp) } //top left corner X
    var unmovableXEnd1 by remember { mutableStateOf(1.dp) } //top right corner X
    var unmovableY1 by remember { mutableStateOf(1.dp) } //top left Y
    var unmovableYEnd1 by remember { mutableStateOf(1.dp) } //bottom left Y

    //unmovable element 2
    var unmovableX2 by remember { mutableStateOf(1.dp) } //top left corner X
    var unmovableXEnd2 by remember { mutableStateOf(1.dp) } //top right corner X
    var unmovableY2 by remember { mutableStateOf(1.dp) } //top left Y
    var unmovableYEnd2 by remember { mutableStateOf(1.dp) } //bottom left Y




    val ballDiameter = 50.dp
    val ballSpeed = 5.dp

    val scores = remember {
        mutableStateOf(0)
    }

    val isBallVisibleBall = remember {
        mutableStateOf(true)
    }

    val isVisibleElement1 = remember {
        mutableStateOf(true)
    }

    val isVisibleElement2 = remember {
        mutableStateOf(true)
    }

    LaunchedEffect(Unit) {
        while (true) {
            withFrameNanos { _ ->

                //Log.d("123123", "screenWidth $screenWidth platformX $platformX platformXEnd $platformXEnd")


                //checking walls and platform
                if (ballX > platformX && ballX < platformXEnd && ballY + ballDiameter > platformY - 16.dp) {
                    Log.d("123123", "Platform collisions")
                    yDirection *= -1
                } else if (ballX <= 0.dp || ballX >= screenWidth - ballDiameter) {
                    Log.d("123123", "Wall collision ballX $ballX")

                    //don't allow to appear behind wall
                    if (ballX < 0.dp){
                        ballX = 1.dp
                    } else if (ballX + ballDiameter > screenWidth){
                        ballX = screenWidth - ballDiameter
                    }

                    xDirection *= -1

                } else if(ballY <= 0.dp) {
                    Log.d("123123", "Top collision")
                    yDirection *= -1
                } else if (ballY >= screenHeight - ballDiameter) {
                    Log.d("123123", "Floor collision")
                    isBallVisibleBall.value = false
                }


                //check collision with elements


                if (isVisibleElement1.value){
                    //check collisions with element 1

                    if (ballX in unmovableX1..unmovableXEnd1 && ballY + ballDiameter in unmovableY1..unmovableYEnd1){
                        Log.d("123123", "Top wall left part collision with element 1 ballX is $ballX ballY is $ballY unmovableX1 $unmovableX1 unmovableY1 $unmovableY1 unmovableXEnd1 $unmovableXEnd1 unmovableYEnd1 $unmovableYEnd1")
                        ballY = unmovableY1 - 5.dp
                        ballX += 10.dp
                        yDirection *= -1
                    } else if (ballX + ballDiameter in unmovableX1 .. unmovableXEnd1 && ballY + ballDiameter in unmovableY1 .. unmovableYEnd1){
                        Log.d("123123", "Top wall right collision with element 1 ballX is $ballX ballY is $ballY unmovableX1 $unmovableX1 unmovableY1 $unmovableY1 unmovableXEnd1 $unmovableXEnd1 unmovableYEnd1 $unmovableYEnd1")
                        ballY = unmovableY1 - 5.dp
                        ballX -= 10.dp
                        yDirection *= -1
                    } else if(ballX in unmovableXEnd1..unmovableX1 && ballY in unmovableY1..unmovableYEnd1){
                        Log.d("123123", "Right wall top part collision with element 1 ballX is $ballX ballY is $ballY unmovableX1 $unmovableX1 unmovableY1 $unmovableY1 unmovableXEnd1 $unmovableXEnd1 unmovableYEnd1 $unmovableYEnd1")
                        ballX = unmovableXEnd1 + 5.dp
                        ballY -= 10.dp
                        xDirection *= -1
                    } else if (ballX in unmovableXEnd1..unmovableX1 && ballY + ballDiameter in unmovableY1..unmovableYEnd1){
                        Log.d("123123", "Right wall bottom part collision with element 1 ballX is $ballX ballY is $ballY unmovableX1 $unmovableX1 unmovableY1 $unmovableY1 unmovableXEnd1 $unmovableXEnd1 unmovableYEnd1 $unmovableYEnd1")
                        ballX = unmovableXEnd1 + 5.dp
                        ballY += 10.dp
                        xDirection *= -1
                    } else if (ballX in unmovableX1..unmovableXEnd1 && ballY in unmovableYEnd1..unmovableY1){
                        Log.d("123123", "Bottom wall left part collision with element 1 ballX is $ballX ballY is $ballY unmovableX1 $unmovableX1 unmovableY1 $unmovableY1 unmovableXEnd1 $unmovableXEnd1 unmovableYEnd1 $unmovableYEnd1")
                        ballY = unmovableYEnd1 + 5.dp
                        ballX += 10.dp
                        yDirection *= -1
                    } else if (ballX + ballDiameter in unmovableX1..unmovableXEnd1 && ballY in unmovableYEnd1..unmovableY1){
                        Log.d("123123", "Bottom wall right part collision with element 1 ballX is $ballX ballY is $ballY unmovableX1 $unmovableX1 unmovableY1 $unmovableY1 unmovableXEnd1 $unmovableXEnd1 unmovableYEnd1 $unmovableYEnd1")
                        ballY = unmovableYEnd1 + 5.dp
                        ballX -= 10.dp
                        yDirection *= -1
                    } else if (ballX + ballDiameter in unmovableX1..unmovableXEnd1 && ballY in unmovableY1..unmovableYEnd1){
                        Log.d("123123", "Left wall top part collision with element 1 ballX is $ballX ballY is $ballY unmovableX1 $unmovableX1 unmovableY1 $unmovableY1 unmovableXEnd1 $unmovableXEnd1 unmovableYEnd1 $unmovableYEnd1")
                        ballX = unmovableX1 - 25.dp
                        ballY -= 10.dp
                        xDirection *= -1
                    } else if(ballX + ballDiameter in unmovableX1..unmovableXEnd1 && ballY + ballDiameter in unmovableY1..unmovableYEnd1){
                        Log.d("123123", "Left wall bottom part collision with element 1 ballX is $ballX ballY is $ballY unmovableX1 $unmovableX1 unmovableY1 $unmovableY1 unmovableXEnd1 $unmovableXEnd1 unmovableYEnd1 $unmovableYEnd1" )
                        ballX = unmovableX1 + 25.dp
                        ballY += 10.dp
                        xDirection *= -1
                    }




//                    if (ballX + ballDiameter in unmovableX1 .. unmovableXEnd1 && ballY + ballDiameter in unmovableY1..unmovableYEnd1){
//                        //top wall of unmovable element1
//                        Log.d("123123", "Top wall collision with element 1")
//                        yDirection *= -1
////                        isVisibleElement1.value = false
//                    } else if (ballX <= unmovableXEnd1 && ballY in unmovableY1 .. unmovableYEnd1){
//                        //right wall of unmovable element1
//                        Log.d("123123", "Right wall collision with element 1")
//
//                        xDirection *= -1
////                        isVisibleElement1.value = false
//                    } else if (ballX in unmovableX1..unmovableXEnd1 && ballY in unmovableYEnd1..unmovableY1){
//                        //bottom wall of unmovable element 1
//                        Log.d("123123", "Bottom wall collision with element 1")
//                        yDirection *= -1
////                        isVisibleElement1.value = false
//                    } else if (ballX+ballDiameter >= unmovableX1 && ballY + ballDiameter in unmovableY1..unmovableYEnd1){
//                        //left wall of unmovable element 1
//                        Log.d("123123", "Left wall collision with element 1")
//                        xDirection *= -1
////                        isVisibleElement1.value = false
//                    }
                }





                if (isVisibleElement2.value){
                    //check collisions with element 2
                    if (ballX + ballDiameter in unmovableX2 .. unmovableXEnd2 && ballY + ballDiameter in unmovableY2..unmovableYEnd2){
                        //top wall of unmovable element2
                        Log.d("123123", "Top wall collision with element 2")
                        yDirection *= -1
                        isVisibleElement2.value = false
                        scores.value += 1
                    } else if (ballX <= unmovableXEnd2 && ballY in unmovableY2 .. unmovableYEnd2){
//                        right wall of unmovable element2
                        Log.d("123123", "Right wall collision with element 2")

                        xDirection *= -1
                        isVisibleElement2.value = false
                        scores.value += 1

                    } else if (ballX in unmovableX2..unmovableXEnd2 && ballY in unmovableYEnd2..unmovableY2){
                        //bottom wall of unmovable element 2
                        Log.d("123123", "Bottom wall collision with element 2")
                        yDirection *= -1
                        isVisibleElement1.value = false
                        scores.value += 1

                    } else if (ballX+ballDiameter in unmovableX2..unmovableXEnd2 && ballY + ballDiameter in unmovableY2..unmovableYEnd2){
                        //left wall of unmovable element 2
                        Log.d("123123", "Left wall collision with element 2")
                        xDirection *= -1
                        isVisibleElement2.value = false
                        scores.value += 1
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

        if (isVisibleElement1.value){
            Image(
                painter = painterResource(id = R.drawable.spiral),
                contentDescription = "unmovable elemnt 1",
                modifier = Modifier
                    .align(Alignment.Center)
                    .onGloballyPositioned {
                        with(density){
                            unmovableX1 = it.positionInRoot().x.toDp()
                            unmovableY1 = it.positionInRoot().y.toDp()
                            unmovableXEnd1 = unmovableX1 + it.size.width.toDp()
                            unmovableYEnd1 = unmovableY1 + it.size.height.toDp()
                        }
                    }
            )
        }

        if (isVisibleElement2.value){
            Image(
                painter = painterResource(id = R.drawable.mosaic),
                contentDescription = "unmovable elemnt 2",
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(0.07f)
                    .offset(y = (-50).dp, x = (-32).dp)
                    .onGloballyPositioned {
                        with(density){
                            unmovableX2 = it.positionInRoot().x.toDp()
                            unmovableY2 = it.positionInRoot().y.toDp()
                            unmovableXEnd2 = unmovableX1 + it.size.width.toDp()
                            unmovableYEnd2 = unmovableY1 + it.size.height.toDp()
                        }
                    }
            )
        }





        Image(
            painter = painterResource(id = R.drawable.bristle),
            contentDescription = "movable base element",
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .aspectRatio(1.5f)
                .align(Alignment.BottomCenter)
                .padding(32.dp)
                .offset(x = offsetXBaseInDp.value)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        offsetXBaseInDp.value += dragAmount.x.toDp()
                    }
                }
                .onGloballyPositioned {
                    with(density) {
                        platformX = it.positionInRoot().x.toDp()
                        platformXEnd = platformX + it.size.width.toDp()
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

