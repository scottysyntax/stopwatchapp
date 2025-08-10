package com.example.stopwatchapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stopwatchapp.ui.theme.StopwatchappTheme
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

// This runs everything
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StopwatchappTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    StopwatchScreen()
                }
            }
        }
    }
}


@Composable
fun StopwatchScreen() {

    // initialize state variables
    var isRunning by remember { mutableStateOf(false) }
    //var elapsedTime by remember { mutableStateOf(0L) }
    var elapsedTimeInMillis by remember { mutableStateOf(0L)}

    // while isRunning is true
    LaunchedEffect(key1 = isRunning) {
        if (isRunning) {
            while (isRunning) {
                //delay(1000L)  was 1000 millisecond delay
                delay(10L)  // is now 10 millisecond delay
                elapsedTimeInMillis += 10
            }
        }
    }

    // Main Column
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Sets the font attributes for the numbers
        Text(
            text = formatTime(elapsedTimeInMillis   ),
            fontSize = 60.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Button row
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Change the Start button display to Pause, Resume, or Start, depending.
            Button(onClick = { isRunning = !isRunning }) {
                Text(
                    text = when {
                        isRunning -> "Pause"
                        elapsedTimeInMillis > 0L -> "Resume"
                        else -> "Start"
                    }
                )
            }

            //Reset button
            Button(onClick = {
                isRunning = false
                elapsedTimeInMillis = 0
            }) {
                Text("Reset")
            }
        } // end Button Row
    } //end Main Column
} // end fun StopwatchScreen

private fun formatTime(timeInMillis: Long): String {
    // determine the seconds by dividing milliseconds by 1000
    val totalSeconds = timeInMillis / 1000

    val hours = TimeUnit.SECONDS.toHours(totalSeconds)
    val minutes = TimeUnit.SECONDS.toMinutes(totalSeconds) % 60
    val secs = totalSeconds % 60
    val hundredths = (timeInMillis % 1000) / 10 // This line is crucial for hundredths


    return String.format("%01d:%02d:%02d.%01d", hours, minutes, secs, hundredths/10)
    //return String.format(":%02d", hundredths)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StopwatchappTheme {
        StopwatchScreen()
    }
}