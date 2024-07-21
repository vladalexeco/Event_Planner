package com.example.eventplanner.data.network.models

import com.example.eventplanner.domain.models.submodels.Condition

data class ConditionDto(
    val text: String,
    val icon: String
)

fun ConditionDto.toCondition(): Condition {
    return Condition(
        text = this.text,
        icon = this.icon
    )
}