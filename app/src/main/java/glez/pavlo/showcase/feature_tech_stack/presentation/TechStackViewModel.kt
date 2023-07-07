package glez.pavlo.showcase.feature_tech_stack.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import glez.pavlo.showcase.core.model.Result
import glez.pavlo.showcase.feature_tech_stack.domain.repository.TechStackResponse
import glez.pavlo.showcase.feature_tech_stack.domain.use_case.TechUseCases
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TechStackViewModel @Inject constructor(private val techUseCases: TechUseCases) : ViewModel() {

    private val _techStackResponse: MutableLiveData<TechStackResponse> = MutableLiveData()
    val techStackResponse: LiveData<TechStackResponse> = _techStackResponse

    init {
        getLocalTechStack()
    }

    private fun getLocalTechStack() = viewModelScope.launch {
        _techStackResponse.value = techUseCases.getLocalTechStack()
        if (techStackResponse.value !is Result.Success) {
            getTechStack()
        }
    }

    fun getTechStack() = viewModelScope.launch {
        _techStackResponse.value = techUseCases.getTechStack()
    }
}