package com.example.diceroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.diceroller.ui.theme.DiceRollerTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiceRollerTheme {
                DiceRollerApp()
            }
        }
    }

}

@Composable
fun DiceWithButtonAndImage(modifier: Modifier = Modifier) {
    var result by remember { mutableStateOf(1) }
    var showAnimation by remember { mutableStateOf(false) }
    val imageResource = when (result) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }
    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(contentAlignment = Alignment.Center,modifier = modifier.weight(10f)) {

            if(showAnimation){
                LottieDice(onAnimationFinish = { showAnimation = false },modifier=Modifier.size(330.dp).offset(y=-(58.dp)))
            }
            else{
                Image(
                    modifier=Modifier.size(200.dp),
                    painter = painterResource(imageResource),
                    contentDescription = result.toString()
                )
            }
        }
//        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = modifier.weight(2f), contentAlignment = Alignment.Center){
            Button(onClick = {
                result = (1..6).random()
                showAnimation = true
            }) {
                Text(stringResource(R.string.roll))
            }
        }
    }
}
@Composable
fun LottieDice(modifier:Modifier=Modifier,onAnimationFinish: () -> Unit){
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.Url("https://lottie.host/bf8ba93a-5e99-4f6f-a90d-d6eecbe24634/deEvdL051x.lottie")
    )
    LaunchedEffect(composition) {
        composition?.run {
            val durationMs = duration.toLong()
            delay(durationMs)
            onAnimationFinish()
        }
    }
    LottieAnimation(
        composition = composition,
        iterations = 1,
        modifier = modifier
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DiceRollerApp() {
    DiceWithButtonAndImage(
        modifier = Modifier
            .fillMaxSize()
    )
}