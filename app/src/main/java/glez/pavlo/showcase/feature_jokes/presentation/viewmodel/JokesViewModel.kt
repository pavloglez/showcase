package glez.pavlo.showcase.feature_jokes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import glez.pavlo.showcase.feature_jokes.data.remote.RemoteJoke
import glez.pavlo.showcase.feature_jokes.domain.use_case.JokesUseCases
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JokesViewModel @Inject constructor(private val jokesUseCases: JokesUseCases) : ViewModel() {

    private val _jokesFlow: MutableStateFlow<List<RemoteJoke>> = MutableStateFlow(emptyList())
    val jokesFlow: StateFlow<List<RemoteJoke>> get() = _jokesFlow

    init {
        getJokes()
    }

    fun getJokes() = viewModelScope.launch {
        _jokesFlow.value = (_jokesFlow.value + jokesUseCases.getProgrammingJoke()).distinctBy { it.id }
    }
}