package com.trinks.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    }
}

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