package com.saj.rickandmorty.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saj.rickandmorty.di.IoDispatcher
import com.saj.rickandmorty.models.ShowCharacter
import com.saj.rickandmorty.models.ShowCharactersPage
import com.saj.rickandmorty.repositories.ShowCharactersRepositoryInt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowCharactersMasterViewModel @Inject constructor(
        private val showCharactersRepository: ShowCharactersRepositoryInt,
        @IoDispatcher private val dispatcher: CoroutineDispatcher
    ): ViewModel() {

    private val _showCharactersLiveData : MutableLiveData<List<ShowCharacter>> by lazy {
        MutableLiveData()
    }
    val showCharacterLiveData : LiveData<List<ShowCharacter>>
        get() = _showCharactersLiveData

    private var lastPage: ShowCharactersPage? = null

    init {
        loadNewShowCharactersPage()
    }

    fun loadNewShowCharactersPage() {
        viewModelScope.launch(dispatcher) {
            val newPage = showCharactersRepository.fetchNewShowCharactersPage(lastPage)
            lastPage = newPage
            val newCharactersCompleteList = mutableListOf<ShowCharacter>()
            _showCharactersLiveData.value?.let {
                newCharactersCompleteList.addAll(it)
            }
            newCharactersCompleteList.addAll(newPage.showCharacters)
            _showCharactersLiveData.postValue(newCharactersCompleteList)
        }
    }
}