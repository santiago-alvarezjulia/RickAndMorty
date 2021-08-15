package com.saj.rickandmorty.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saj.rickandmorty.di.IoDispatcher
import com.saj.rickandmorty.idlingResources.EspressoCountingIdlingResource
import com.saj.rickandmorty.models.ShowCharacter
import com.saj.rickandmorty.models.ShowCharactersPage
import com.saj.rickandmorty.network.responses.NetworkResponse
import com.saj.rickandmorty.repositories.ShowCharactersRepositoryInt
import com.saj.rickandmorty.viewmodels.singleEvent.Event
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

    private val _loadPageErrorLiveData = MutableLiveData<Event<String>>()
    val loadPageErrorLiveData : LiveData<Event<String>>
        get() = _loadPageErrorLiveData

    private var lastPage: ShowCharactersPage? = null

    init {
        loadNewShowCharactersPage()
    }

    fun loadNewShowCharactersPage() {
        EspressoCountingIdlingResource.processStarts()
        viewModelScope.launch(dispatcher) {
            when (val response = showCharactersRepository.fetchNewShowCharactersPage(lastPage)) {
                is NetworkResponse.Success -> {
                    val newPage = response.body
                    lastPage = newPage
                    val newCharactersCompleteList = mutableListOf<ShowCharacter>()
                    _showCharactersLiveData.value?.let {
                        newCharactersCompleteList.addAll(it)
                    }
                    newCharactersCompleteList.addAll(newPage.showCharacters)
                    _showCharactersLiveData.postValue(newCharactersCompleteList)
                    EspressoCountingIdlingResource.processEnds()
                }
                is NetworkResponse.Error -> {
                    _loadPageErrorLiveData.postValue(Event(response.message))
                }
            }
        }
    }
}