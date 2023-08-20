package com.trinks.ui.theme.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trinks.NotMainActivity
import com.trinks.R


@Composable
fun MenuScreen(){

    Box(modifier = Modifier.fillMaxSize()){

        Image(
            painter = painterResource(id = R.drawable.bg),
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Decor()
        Buttons()

        //CustomLoadingAnimation()

    }
}

//@Composable
//fun BoxScope.CustomLoadingAnimation() {
//
//    val infiniteTransitionRect = rememberInfiniteTransition(label = "")
//    val offsetRect by infiniteTransitionRect.animateFloat(
//        initialValue = -32f,
//        targetValue = 32f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(1000, easing = LinearEasing),
//            repeatMode = RepeatMode.Reverse
//        ), label = "animate rect"
//    )
//
//    val infiniteTransitionColumn = rememberInfiniteTransition(label = "")
//    val offsetRectColumn by infiniteTransitionColumn.animateFloat(
//        initialValue = -32f,
//        targetValue = 32f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(1000, easing = FastOutSlowInEasing),
//            repeatMode = RepeatMode.Reverse
//        ), label = "animate column"
//    )
//
//
//
//    Box(modifier = Modifier
//        .fillMaxWidth()
//        .align(Alignment.Center)
//        .offset(y = 128.dp)
//    ){
//
//        Canvas(
//            modifier = Modifier
//                .width(16.dp)
//                .height(56.dp)
//                .align(Alignment.Center)
//                .offset(x = offsetRectColumn.dp)
//                .alpha(0.5f)
//        ) {
//            drawRect(
//                color = Color.Blue,
//                size = size
//            )
//        }
//
//
//        Canvas(
//            modifier = Modifier
//                .width(140.dp)
//                .height(32.dp)
//                .align(Alignment.Center)
//                .offset(x = offsetRect.dp)
//                .alpha(0.3f)
//        ) {
//            drawRect(
//                color = Color.Red,
//                size = size
//            )
//        }
//
//        Text(
//            text = "LOADING",
//            color = Color.White,
//            modifier = Modifier.align(Alignment.Center)
//        )
//
//    }
//}

@Composable
fun BoxScope.Decor(){
    Image(
        painter = painterResource(id = R.drawable.nebula),
        contentDescription = "",
        modifier = Modifier
            .align(Alignment.BottomStart)

    )

    Image(
        painter = painterResource(id = R.drawable.pebble),
        contentDescription = "",
        modifier = Modifier
            .align(Alignment.TopCenter)
    )

    Image(
        painter = painterResource(id = R.drawable.mosaic),
        contentDescription = "",
        modifier = Modifier
            .align(Alignment.TopEnd)
            .offset(y = 128.dp)
    )
}

@Composable
fun BoxScope.Buttons(){

    val context = LocalContext.current

    Column(modifier = Modifier.align(Alignment.Center)) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.zephyr),
                contentDescription = "btn",
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(8.dp)
                    .clickable {
                        NotMainActivity.navigateToGameActivity(context)
                    }
            )

            Text(
                text = "Play game",
                color = Color.White,
                fontSize = 24.sp,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }

        Box {
            Image(
                painter = painterResource(id = R.drawable.zephyr),
                contentDescription = "btn",
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(8.dp)
                    .clickable {
                        NotMainActivity.navigateToExit(context)
                    }
            )

            Text(
                text = "Exit",
                color = Color.White,
                fontSize = 24.sp,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}