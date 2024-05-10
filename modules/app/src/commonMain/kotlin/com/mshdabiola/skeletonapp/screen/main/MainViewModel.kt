package com.mshdabiola.skeletonapp.screen.main

import com.mshdabiola.data.repository.IModelRepository
import com.mshdabiola.model.Model
import com.mshdabiola.mvvn.ViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainViewModel constructor(
    private val modelRepository: IModelRepository,
) : ViewModel() {

    val modelState = modelRepository
        .getAllModel()
        .map { it.map { it.asModelUiState() }.toImmutableList() }
        //  .cachedIn(viewModelScope)
        //  .asResult()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(3000),
            initialValue = emptyList<ModelUiState>().toImmutableList(),
        )

    private val _mainState = MutableStateFlow(MainState())
    val mainState = _mainState.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2000)
            addNotify("Add Model")
            addNotify("remove model")
        }
    }

    fun addName(name: String) {
        insert(Model(name = name, id = Random(34).nextLong()))
    }

    fun insert(model: Model) {
        viewModelScope.launch(Dispatchers.IO) {
            modelRepository.insert(model)
        }
    }

    private fun addNotify(text: String) {
//        val notifies = mainState.value.messages.toMutableList()
//
//        notifies.add(Notify(message = text, callback = ::onNotifyDelive))
//        _mainState.update {
//            it.copy(messages = notifies.toImmutableList())
        // }
    }

    private fun onNotifyDelive() {
//        val notifies = mainState.value.messages.toMutableList()
//
//        notifies.removeFirst()
//        _mainState.value = mainState.value.copy(messages = notifies.toImmutableList())
    }
}
