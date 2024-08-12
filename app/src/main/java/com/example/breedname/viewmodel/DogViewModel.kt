package com.example.breedname.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.breedname.repository.DogRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log

class DogViewModel(private val repository: DogRepository) : ViewModel() {
    private val _dogImage = MutableStateFlow("")
    val dogImage: StateFlow<String> = _dogImage

    private val _options = MutableStateFlow<List<String>>(emptyList())
    val options: StateFlow<List<String>> = _options

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message

    private val _isCorrect = MutableStateFlow<Boolean?>(null)
    val isCorrect: StateFlow<Boolean?> = _isCorrect

    private var correctAnswer: String? = null
    val currentCorrectAnswer: String? get() = correctAnswer

    init {
        fetchDogData()
    }

    fun fetchDogData() {
        viewModelScope.launch {
            try {
                val breedsResponse = repository.getBreeds()
                val breeds = breedsResponse.message.keys.toList()
                correctAnswer = breeds.random()
                fetchRandomBreedImage(correctAnswer ?: "")
                _options.value = listOf(correctAnswer!!.capitalize(), breeds.random().capitalize()).shuffled()
            } catch (e: Exception) {
                Log.e("DogQuiz", "Error fetching data", e)
            }
        }
    }

    fun fetchRandomBreedImage(dogBreed: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = repository.getBreedImages(dogBreed)
                val randomImageUrl = response.message.randomOrNull()
                _dogImage.value = randomImageUrl.toString()
            } catch (e: Exception) {
                Log.e("DogQuiz", "Error fetching dog images", e)
                _dogImage.value = ""
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun checkAnswer(selectedOption: String) {
        val isCorrect = selectedOption.equals(correctAnswer, ignoreCase = true)
        _isCorrect.value = isCorrect
        _message.value = if (isCorrect) {
            "Your answer is Correct!"
        } else {
            "${correctAnswer?.capitalize()}"
        }
    }

    fun nextQuestion() {
        _isCorrect.value = null
        _message.value = ""
        fetchDogData()
    }
}