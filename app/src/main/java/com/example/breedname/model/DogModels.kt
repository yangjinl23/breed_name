package com.example.breedname.model

data class BreedResponse(val message: Map<String, List<String>>, val status: String)
data class BreedImagesResponse(val message: List<String>, val status: String)