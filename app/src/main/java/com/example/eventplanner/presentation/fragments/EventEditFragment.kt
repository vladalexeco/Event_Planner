package com.example.eventplanner.presentation.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.eventplanner.R
import com.example.eventplanner.core.App
import com.example.eventplanner.databinding.FragmentEventEditBinding
import com.example.eventplanner.domain.models.Event
import com.example.eventplanner.presentation.states.EventEditEvent
import com.example.eventplanner.presentation.utils.CompareDateResult
import com.example.eventplanner.presentation.utils.getDaysBetween
import com.example.eventplanner.presentation.viewmodels.EventEditViewModel
import com.example.eventplanner.presentation.viewmodels.EventEditViewModelFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class EventEditFragment : Fragment() {

    private var _binding: FragmentEventEditBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var eventEditViewModelFactory: EventEditViewModelFactory
    private lateinit var viewModel: EventEditViewModel

    private var currentStatus = DEFAULT_STATUS
    private var eventId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as App).appComponent.inject(this)
        viewModel =
            ViewModelProvider(this, eventEditViewModelFactory)[EventEditViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventId = arguments?.getString(EventListFragment.EVENT_ID_KEY)

        if (eventId != null) {
            viewModel.onEvent(eventEditEvent = EventEditEvent.GetEventByIdEvent(eventId!!))
        } else {
            renderScreenForNewEvent()
        }

        // Click listeners
        binding.eventEditEditDateTextView.setOnClickListener {
            showDatePickerDialogForSettingDate()
        }

        binding.eventEditEditTimeTextView.setOnClickListener {
            showTimePickerDialogForSettingTime()
        }

        binding.eventEditApproveButton.setOnClickListener {
            saveNewEventAction()
        }

        binding.eventEditEscapeButton.setOnClickListener {
            findNavController().navigate(R.id.action_eventEditFragment_to_eventListFragment)
        }

        binding.eventEditDeleteEventButton.setOnClickListener {
            if (eventId != null) {
               showAlertDialog()
            }
        }

        binding.eventEditRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = view.findViewById<RadioButton>(checkedId)
            val selectedText = radioButton.text.toString()
            currentStatus = selectedText
        }
        //

        viewModel.uiState.observe(viewLifecycleOwner) { screenState ->
            if (screenState.event != null) {
                renderScreenForEditEvent(screenState.event)
            }

            if (screenState.toastMessage != null) {
                Toast.makeText(requireContext(), screenState.toastMessage, Toast.LENGTH_SHORT).show()
            }

            if (screenState.goToScreen) {
                findNavController().navigate(R.id.action_eventEditFragment_to_eventListFragment)
            }
        }
    }

    private fun renderScreenForNewEvent() {
        binding.eventEditMainHeaderTextView.text = getString(R.string.new_event_screen_header)
        binding.eventEditEditNameEditText.setHint(getString(R.string.enter_new_event_hint))
        binding.eventEditEditDateTextView.text = setUnderlineText(R.string.date_choice_underlined)
        binding.eventEditEditTimeTextView.text = setUnderlineText(R.string.time_choice_underlined)
        binding.eventEditEditDescriptionEditText.setHint(R.string.enter_description_hint)
        binding.eventEditApproveButton.text = getString(R.string.create_event_button)
        binding.eventEditEscapeButton.text = getString(R.string.escape_from_screen_button)

        binding.eventEditRadioGroup.visibility = View.GONE
        binding.eventEditDeleteEventButton.visibility = View.GONE
    }

    private fun renderScreenForEditEvent(event: Event?) {
        binding.eventEditMainHeaderTextView.text = getString(R.string.edit_event_screen_header)
        binding.eventEditApproveButton.text = getString(R.string.edit_event_button)
        binding.eventEditEscapeButton.text =
            getString(R.string.escape_without_changes_from_screen_button)
        binding.eventEditRadioGroup.visibility = View.VISIBLE
        binding.eventEditDeleteEventButton.visibility = View.VISIBLE

        if (event != null) {
            binding.eventEditEditNameEditText.setText(event.name)
            binding.eventEditEditDateTextView.text = event.date
            binding.eventEditEditTimeTextView.text = event.time
            binding.eventEditEditDescriptionEditText.setText(event.description)
            binding.eventEditLocationEditText.setText(event.location)

            when (event.status) {
                DEFAULT_STATUS -> {
                    binding.radioButton1.isChecked = true
                    currentStatus = DEFAULT_STATUS
                }

                VISITED_STATUS -> {
                    binding.radioButton2.isChecked = true
                    currentStatus = VISITED_STATUS
                }

                SKIPPED_STATUS -> {
                    binding.radioButton3.isChecked = true
                    currentStatus = SKIPPED_STATUS
                }
            }
        }
    }

    private fun setUnderlineText(@StringRes resId: Int): SpannableString {
        val content = SpannableString(getString(resId))
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        return content
    }

    private fun showDatePickerDialogForSettingDate() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd.MM.YYYY", Locale("ru")).format(calendar.time)
                binding.eventEditEditDateTextView.text = dateFormat
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }

    private fun showTimePickerDialogForSettingTime() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog =
            TimePickerDialog(requireContext(), { _, selectedHour, selectedMinute ->
                val formattedTime =
                    String.format(Locale("ru"), "%02d:%02d", selectedHour, selectedMinute)
                binding.eventEditEditTimeTextView.text = formattedTime
            }, hour, minute, true)
        timePickerDialog.show()
    }

    private fun compareCurrentAndEnteredDate(): CompareDateResult {
        val dateStr = binding.eventEditEditDateTextView.text.toString()
        val timeStr = binding.eventEditEditTimeTextView.text.toString()

        val dateTimeStr = "$dateStr $timeStr"
        val dateTimeFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())

        return try {
            val inputDate: Date? = dateTimeFormat.parse(dateTimeStr)
            val currentDate = Date()

            if (currentDate.after(inputDate)) {
                CompareDateResult.Success(isValidDateTime = false)
            } else {
                CompareDateResult.Success(isValidDateTime = true)
            }
        } catch (e: Exception) {
            CompareDateResult.Error(errorMessage = getString(R.string.error_date_time_parsing))
        }
    }

    private fun saveNewEventAction() {
        when (val compareDateTimeResult = compareCurrentAndEnteredDate()) {
            is CompareDateResult.Success -> {
                if (compareDateTimeResult.isValidDateTime) {
                    if (binding.eventEditEditNameEditText.text.toString().isBlank() ||
                        binding.eventEditLocationEditText.text.toString().isBlank()
                    ) {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.event_name_field_is_blank_toast_message),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        if (getDaysBetween(binding.eventEditEditDateTextView.text.toString()) < 14) {
                            if (eventId != null) {
                                editEvent()
                            } else {
                                saveEvent()
                            }
                        } else {
                            showDaysBetweenAlert()
                        }
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.invalid_assigned_date_time_toast_message),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            is CompareDateResult.Error -> {
                Toast.makeText(
                    requireContext(),
                    compareDateTimeResult.errorMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun saveEvent() {
        val newEvent = Event(
            name = binding.eventEditEditNameEditText.text.toString(),
            date = binding.eventEditEditDateTextView.text.toString(),
            time = binding.eventEditEditTimeTextView.text.toString(),
            description = binding.eventEditEditDescriptionEditText.text.toString(),
            status = currentStatus,
            location = binding.eventEditLocationEditText.text.toString()
        )

        viewModel.onEvent(EventEditEvent.SaveEventToDatabase(event = newEvent))
    }

    private fun editEvent() {
        val event = Event(
            id = eventId!!,
            name = binding.eventEditEditNameEditText.text.toString(),
            date = binding.eventEditEditDateTextView.text.toString(),
            time = binding.eventEditEditTimeTextView.text.toString(),
            description = binding.eventEditEditDescriptionEditText.text.toString(),
            status = currentStatus,
            location = binding.eventEditLocationEditText.text.toString()
        )

        viewModel.onEvent(EventEditEvent.SaveEventToDatabase(event = event))
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Удаление события")
        builder.setMessage("Вы действительно хотите удалить событие")
        builder.setPositiveButton("Да") { _, _ ->
            viewModel.onEvent(eventEditEvent = EventEditEvent.DeleteEventByIdFromDatabase(eventId = eventId!!))
            findNavController().navigate(R.id.action_eventEditFragment_to_eventListFragment)
        }
        builder.setNegativeButton("Нет") { _, _ ->
        }
        builder.show()
    }

    private fun showDaysBetweenAlert() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Предупреждение")
        builder.setMessage("Прогноз дается только на 14 дней. Готовы сохранить событие без прогноза")
        builder.setPositiveButton("Да") { _, _ ->
            if (eventId != null) {
                editEvent()
            } else {
                saveEvent()
            }
        }
        builder.setNegativeButton("Нет") { _, _ ->
        }
        builder.show()
    }

    companion object {
        const val DEFAULT_STATUS = "в процессе"
        const val VISITED_STATUS = "посещенное"
        const val SKIPPED_STATUS = "пропущенное"
    }
}