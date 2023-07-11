package glez.pavlo.showcase.feature_dev_profile.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import glez.pavlo.showcase.core.model.Result
import glez.pavlo.showcase.feature_dev_profile.domain.repository.DevProfileResponse
import glez.pavlo.showcase.feature_dev_profile.domain.repository.SkillsResponse
import glez.pavlo.showcase.feature_dev_profile.domain.use_case.UseCases
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DevProfileViewModel @Inject constructor(private val useCases: UseCases) : ViewModel() {

    private val _devProfileResponse: MutableLiveData<DevProfileResponse> = MutableLiveData()
    val devProfileResponse: LiveData<DevProfileResponse> = _devProfileResponse

    private val _skills: MutableLiveData<SkillsResponse> = MutableLiveData()
    val skills: LiveData<SkillsResponse> = _skills

    init {
        getLocalDevProfile()
        getLocalSkills()
    }

    private fun getDevProfile() = viewModelScope.launch {
        _devProfileResponse.value = useCases.getDevProfile()
    }

    private fun getLocalDevProfile() = viewModelScope.launch {
        _devProfileResponse.value = useCases.getLocalDevProfile()
        if (devProfileResponse.value !is Result.Success) {
            getDevProfile()
        }
    }

    fun getSkills() = viewModelScope.launch {
        _skills.value = useCases.getSkills()
    }

    private fun getLocalSkills() = viewModelScope.launch {
        _skills.value = useCases.getLocalSkills()
        if (skills.value !is Result.Success) {
            getSkills()
        }
    }
}