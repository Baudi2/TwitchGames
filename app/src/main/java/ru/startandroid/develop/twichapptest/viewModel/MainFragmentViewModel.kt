package ru.startandroid.develop.twichapptest.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.startandroid.develop.twichapptest.model.Repository
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    repository: Repository
) : ViewModel() {

    val games = repository.getTopGames().cachedIn(viewModelScope)
}