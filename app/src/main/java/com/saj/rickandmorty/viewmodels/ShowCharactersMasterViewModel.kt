package com.saj.rickandmorty.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saj.rickandmorty.models.ShowCharacter
import com.saj.rickandmorty.repositories.ShowCharactersRepositoryInt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowCharactersMasterViewModel @Inject constructor(
        private val showCharactersRepository: ShowCharactersRepositoryInt
    ): ViewModel() {

    private val _showCharactersLiveData : MutableLiveData<List<ShowCharacter>> by lazy {
        MutableLiveData()
    }
    val showCharacterLiveData : LiveData<List<ShowCharacter>>
        get() = _showCharactersLiveData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _showCharactersLiveData.postValue(getShowCharacters())
        }
    }

    suspend fun getShowCharacters(): List<ShowCharacter> {
        return showCharactersRepository.fetchShowCharacters()
    }
}