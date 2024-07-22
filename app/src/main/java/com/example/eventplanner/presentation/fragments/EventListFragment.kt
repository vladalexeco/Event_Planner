package com.example.eventplanner.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventplanner.R
import com.example.eventplanner.core.App
import com.example.eventplanner.databinding.FragmentEventListBinding
import com.example.eventplanner.presentation.adapters.EventAdapter
import com.example.eventplanner.presentation.adapters.decorators.BottomSpaceItemDecoration
import com.example.eventplanner.presentation.states.EventListEvent
import com.example.eventplanner.presentation.viewmodels.EventListViewModel
import com.example.eventplanner.presentation.viewmodels.EventListViewModelFactory
import javax.inject.Inject

class EventListFragment : Fragment() {

    private var _binding: FragmentEventListBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var eventListViewModelFactory: EventListViewModelFactory
    private lateinit var viewModel: EventListViewModel

    private var eventListAdapter: EventAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, eventListViewModelFactory)[EventListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventListAdapter = EventAdapter(
            onItemClick = { eventId ->
                handleOnItemClick(eventId)
            },
        )

        binding.eventListListOfEventsRecycleView.apply {
            adapter = eventListAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            this.addItemDecoration(BottomSpaceItemDecoration(16, requireContext()))
        }

        binding.eventListAddEventButton.setOnClickListener {
            findNavController().navigate(R.id.action_eventListFragment_to_eventEditFragment)
        }

        viewModel.onEvent(EventListEvent.GetAllEventsFromDatabase)

        viewModel.uiState.observe(viewLifecycleOwner) { screenState ->
            Log.d("Check", screenState.toString())
            eventListAdapter?.setNewData(screenState.eventList)
        }
    }

    private fun handleOnItemClick(eventId: String) {
        val bundle = Bundle()
        bundle.putString(EVENT_ID_KEY, eventId)
        findNavController().navigate(R.id.action_eventListFragment_to_eventDetailsFragment, bundle)
    }

    companion object {
        const val EVENT_ID_KEY = "event_id_key"
    }
}