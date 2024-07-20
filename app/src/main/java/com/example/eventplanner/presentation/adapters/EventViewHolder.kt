package com.example.eventplanner.presentation.adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.eventplanner.R
import com.example.eventplanner.databinding.ItemViewEventCardBinding
import com.example.eventplanner.domain.models.Event
import com.squareup.picasso.Picasso

class EventViewHolder(
    val binding: ItemViewEventCardBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: Event) {
        binding.eventCardEventNameTextView.text = model.name
        binding.eventCardEventDateTextView.text = model.date
        binding.eventCardEventTimeTextView.text = model.date
        binding.eventCardEventStatusTextView.text = model.status

        binding.eventCardForecastConditionsTextView.text = model.forecast ?: "Unknown"

        binding.eventCardLocationTextView.text = model.district ?: "${model.latitude} ${model.longitude}"

        if (model.forecastImageUrl != null) {
             Picasso.get()
                 .load("https:" + model.forecastImageUrl)
                 .placeholder(R.drawable.unknown_forecast)
                 .error(R.drawable.unknown_forecast)
                 .into(binding.eventCardForecastImageImageView)
        } else {
            binding.eventCardForecastImageImageView.setImageResource(
                R.drawable.unknown_forecast
            )
        }
    }
}