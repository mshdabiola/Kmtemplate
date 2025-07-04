/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mshdabiola.kotlinmultiplatformtemplate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.repository.UserDataRepository
import com.mshdabiola.kotlinmultiplatformtemplate.MainActivityUiState.Loading
import com.mshdabiola.kotlinmultiplatformtemplate.MainActivityUiState.Success
import com.mshdabiola.model.UserData
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainAppViewModel(
    userDataRepository: UserDataRepository,
) : ViewModel() {
    val uiState: StateFlow<MainActivityUiState> =
        userDataRepository.userData.map {
            Success(it)
        }.stateIn(
            scope = viewModelScope,
            initialValue = Loading,
            started = SharingStarted.WhileSubscribed(5_000),
        )
}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState

    data class Success(val userData: UserData) : MainActivityUiState
}
