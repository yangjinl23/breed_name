package com.example.breedname.repository

import com.example.breedname.api.DogApiService
class DogRepository(private val apiService: DogApiService) {
    suspend fun getBreeds() = apiService.getBreeds()
    suspend fun getBreedImages(breed: String) = apiService.getBreedImages(breed)
}