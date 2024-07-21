package com.example.eventplanner.data.network.models

import com.example.eventplanner.domain.models.submodels.Astro

data class AstroDto(
    val sunrise: String,
    val sunset: String,
)

fun AstroDto.toAstro(): Astro {
    return Astro(
        sunrise = this.sunrise,
        sunset = this.sunset
    )
}