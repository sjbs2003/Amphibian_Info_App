package com.example.amphibianinfoapp.ui.screens

import com.example.amphibianinfoapp.model.AmphibianData

sealed interface AmphibianUiState{
    data class Success(val data: List<AmphibianData>) : AmphibianUiState
    object Error : AmphibianUiState
    object Loading : AmphibianUiState
}