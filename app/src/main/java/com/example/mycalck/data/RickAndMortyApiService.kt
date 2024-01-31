package com.example.mycalck.data

import com.example.mycalck.character.CharacterResponse
import com.example.mycalck.character.CharacterZ
import retrofit2.http.GET
import retrofit2.http.Path

interface RickAndMortyApiService {
    @GET("character")
    suspend fun getCharacters(): CharacterResponse

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): CharacterZ
}
