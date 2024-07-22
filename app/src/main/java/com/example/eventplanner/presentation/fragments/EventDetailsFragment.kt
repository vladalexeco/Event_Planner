package com.example.eventplanner.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.eventplanner.R
import com.example.eventplanner.core.App
import com.example.eventplanner.databinding.FragmentEventDetailsBinding
import com.example.eventplanner.domain.models.Event
import com.example.eventplanner.presentation.states.EventDetailsEvent
import com.example.eventplanner.presentation.viewmodels.EventDetailsViewModel
import com.example.eventplanner.presentation.viewmodels.EventDetailsViewModelFactory
import com.squareup.picasso.Picasso
import javax.inject.Inject

class EventDetailsFragment : Fragment() {

    private var _binding: FragmentEventDetailsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var eventDetailsViewModelFactory: EventDetailsViewModelFactory
    private lateinit var viewModel: EventDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, eventDetailsViewModelFactory)[EventDetailsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val eventId = arguments?.getString(EventListFragment.EVENT_ID_KEY)

        if (eventId != null) {
            viewModel.onEvent(eventDetailsEvent = EventDetailsEvent.GetEventByIdEvent(
                eventId = eventId
            ))
        }

        viewModel.uiState.observe(viewLifecycleOwner) { screenState ->
            renderScreen(screenState?.event)
        }

        binding.eventDetailsApproveButton.setOnClickListener {
            findNavController().navigate(R.id.action_eventDetailsFragment_to_eventListFragment)
        }

        binding.eventDetailsEditButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(EVENT_ID_KEY, eventId)
            findNavController().navigate(R.id.action_eventDetailsFragment_to_eventEditFragment, bundle)
        }
    }

    private fun renderScreen(event: Event?) {
        if (event != null) {
            binding.eventDetailsEventNameTextView.text = event.name
            binding.eventDetailsDateTextView.text = event.date
            binding.eventDetailsTimeTextView.text = event.time
            binding.eventDetailsLocationTextView.text = event.location
            binding.eventDetailsDescriptionTextView.text = event.description

            if (event.forecastImageUrl != null) {
                Picasso.get()
                    .load("https:" + event.forecastImageUrl)
                    .placeholder(R.drawable.unknown_forecast)
                    .error(R.drawable.unknown_forecast)
                    .into(binding.eventDetailsForecastImageImageView)
            } else {
                binding.eventDetailsForecastImageImageView.setImageResource(R.drawable.unknown_forecast)
            }

            binding.eventDetailsForecastConditionTextView.text = event.forecastExtend ?: getString(R.string.unknown_forecast)
        }
    }

    companion object {
        const val EVENT_ID_KEY = "event_id_key"
    }
}