package com.example.breedname.api

import com.example.breedname.model.BreedImagesResponse
import com.example.breedname.model.BreedResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DogApiService {
    @GET("breeds/list/all")
    suspend fun getBreeds(): BreedResponse

    @GET("breed/{dogBreed}/images")
    suspend fun getBreedImages(@Path("dogBreed") dogBreed: String): BreedImagesResponse
}