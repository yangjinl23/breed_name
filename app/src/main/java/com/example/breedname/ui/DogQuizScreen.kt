package com.example.breedname.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.breedname.viewmodel.DogViewModel

@Composable
fun DogQuizScreen(viewModel: DogViewModel) {
    val dogImage by viewModel.dogImage.collectAsState()
    val options by viewModel.options.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val message by viewModel.message.collectAsState()
    val isCorrect by viewModel.isCorrect.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Dog Trivia",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(vertical = 50.dp))
            } else {
                Image(
                    painter = rememberAsyncImagePainter(dogImage),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 50.dp)
                        .height(200.dp)
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    20.dp,
                    alignment = Alignment.CenterHorizontally
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                options.forEach { option ->
                    Button(
                        onClick = {
                            viewModel.checkAnswer(option)
                            Log.d("DogQuiz", "Selected breed: $option")
                        },
                        enabled = isCorrect == null,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isCorrect == null) MaterialTheme.colorScheme.primary else Color.Gray
                        )
                    ) {
                        Text(option)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (isCorrect != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isCorrect == true) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Color.Green,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Text(
                        text = buildAnnotatedString {
                            if (isCorrect == true) {
                                append(message)
                            } else {
                                append("Oops! The correct breed is ")
                                withStyle(style = SpanStyle(color = Color.Red)) {
                                    append(message)
                                }
                                append(".")
                            }
                        },
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { viewModel.nextQuestion() }) {
                    Text("Next Question")
                }
            }
        }
    }
}