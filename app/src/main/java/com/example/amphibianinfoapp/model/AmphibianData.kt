package com.example.amphibianinfoapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AmphibianData(
    val name: String,
    val type: String,
    val description: String,
    @SerialName("img_src")
    val image: String

)