package com.example.mycalck.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycalck.character.CharacterZ
import com.example.mycalck.data.RickAndMortyApiService
import com.example.mycalck.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val retrofit: Retrofit
) : ViewModel() {
    private val _characters = MutableLiveData<Resource<List<CharacterZ>>>()
    val characters: LiveData<Resource<List<CharacterZ>>> get() = _characters

    init {
        loadCharacters()
    }

    private fun loadCharacters() {
        _characters.value = Resource.Loading()
        viewModelScope.launch {
            val api = retrofit.create(RickAndMortyApiService::class.java)

            try {
                val response = withContext(Dispatchers.IO) { api.getCharacters() }
                _characters.value = Resource.Success(response.results)
            } catch (e: Exception) {
                _characters.value = Resource.Error(e.message ?: "Unknown error")
            }
        }
    }
}
