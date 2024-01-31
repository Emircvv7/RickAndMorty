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
class CharacterDetailViewModel @Inject constructor(
    private val retrofit: Retrofit
) : ViewModel() {
    private val _character = MutableLiveData<Resource<CharacterZ>>()
    val character: LiveData<Resource<CharacterZ>> get() = _character

    fun loadCharacter(id: Int) {
        _character.value = Resource.Loading()
        viewModelScope.launch {
            val api = retrofit.create(RickAndMortyApiService::class.java)

            try {
                val response = withContext(Dispatchers.IO) { api.getCharacter(id) }
                _character.value = Resource.Success(response)
            } catch (e: Exception) {
                _character.value = Resource.Error(e.message ?: "Unknown error")
            }
        }
    }
}
