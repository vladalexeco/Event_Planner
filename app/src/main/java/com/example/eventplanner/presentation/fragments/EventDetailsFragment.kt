package com.example.eventplanner.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.eventplanner.core.App
import com.example.eventplanner.databinding.FragmentEventDetailsBinding
import com.example.eventplanner.presentation.viewmodels.EventDetailsViewModel
import com.example.eventplanner.presentation.viewmodels.EventDetailsViewModelFactory
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

    }
}