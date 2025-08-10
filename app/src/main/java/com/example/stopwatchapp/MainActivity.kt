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
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stopwatchapp.ui.theme.StopwatchappTheme
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit
import androidx.compose.foundation.shape.RoundedCornerShape

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

    // New way to extract time values for individual display
    val totalSeconds = elapsedTimeInMillis / 1000
    val hours = TimeUnit.SECONDS.toHours(totalSeconds)
    val minutes = TimeUnit.SECONDS.toMinutes(totalSeconds) % 60
    val secs = totalSeconds % 60
    val hundredths = (elapsedTimeInMillis % 1000) / 10

    // Main Column
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Sets the font attributes for the numbers
        /*
        Text(
            text = formatTime(elapsedTimeInMillis   ),
            fontSize = 80.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 60.dp)
        )
        */
        // Row to hold all the number columns
        Row(
            modifier = Modifier.padding(bottom = 32.dp),
            verticalAlignment = Alignment.Bottom
        )
        {
            TimeSection(number = String.format("%02d", hours), label = "HOURS")
            Text(
                text = ":",
                fontSize = 60.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .alignByBaseline()
                    .offset(y = 8.dp)
            )
            TimeSection(number = String.format("%02d", minutes), label = "MINS")
            Text(
                text = ":",
                fontSize = 60.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .alignByBaseline()
                    .offset(y = 2.dp)
            )
            TimeSection(number = String.format("%02d", secs), label = "SECS")
            Text(
                text = ".",
                fontSize = 60.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .alignByBaseline()
                    .offset(y = 2.dp)
            )
            TimeSection(number = String.format("%02d", hundredths), label = "1/100")
        }
        // Button row
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 32.dp
                )
        ) {
            // Change the Start button display to Pause, Resume, or Start, depending.
            Button(
                onClick = { isRunning = !isRunning },
                modifier = Modifier
                    .width(180.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = when {
                        isRunning -> "Pause"
                        elapsedTimeInMillis > 0L -> "Resume"
                        else -> "Start"
                    },
                    fontSize=35.sp
                )
            }

            //Reset button
            Button(
                    onClick = {
                        isRunning = false
                        elapsedTimeInMillis = 0
                    },
                    modifier = Modifier
                        .width(180.dp)
                        .height(60.dp),
                    shape = RoundedCornerShape(8.dp)
            ) {
                Text("Reset", fontSize=35.sp)
            }
        } // end Button Row
    } //end Main Column
} // end fun StopwatchScreen

// composable to display each number and its label
// A new composable to display each number and its label
@Composable
fun TimeSection(number: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(horizontal = 4.dp)) {
        Text(
            text = number,
            fontSize = 60.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 10.dp) //// Test with a different modifier
        )
        Text(
            text = label,
            fontSize = 12.sp
        )
    }
}
/*
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
*/

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StopwatchappTheme {
        StopwatchScreen()
    }
}