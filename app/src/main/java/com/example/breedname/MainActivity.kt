package com.example.breedname

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.breedname.ui.DogQuizScreen
import com.example.breedname.viewmodel.DogViewModel
import com.example.breedname.api.DogApiService
import com.example.breedname.repository.DogRepository
import com.example.breedname.viewmodel.DogViewModelFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    private val viewModel: DogViewModel by viewModels {
        val apiService = Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DogApiService::class.java)

        val repository = DogRepository(apiService)
        DogViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DogQuizScreen(viewModel)
        }
    }
}