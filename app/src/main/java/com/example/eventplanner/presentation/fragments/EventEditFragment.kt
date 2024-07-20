package com.example.eventplanner.presentation.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.example.eventplanner.R
import com.example.eventplanner.databinding.FragmentEventEditBinding
import com.example.eventplanner.presentation.utils.CompareDateResult
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class EventEditFragment : Fragment() {

    private var _binding: FragmentEventEditBinding? = null
    private val binding get() = _binding!!

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

        val eventId = arguments?.getInt(EventListFragment.EVENT_ID_KEY)

        if (eventId != null) {
            Log.d("CHECK", "Edit fragment eventId: $eventId")
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
            when(val compareDateTimeResult = compareCurrentAndEnteredDate()) {
                is CompareDateResult.Success -> {
                    if (compareDateTimeResult.isValidDateTime) {
                        if (binding.eventEditEditNameEditText.text.toString().isBlank() ||
                            binding.eventEditLatitudeEditText.text.toString().isBlank() ||
                            binding.eventEditLongitudeEditText.text.toString().isBlank()) {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.event_name_field_is_blank_toast_message),
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Log.d("CHECK", "Saving event")
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
        //
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

    private fun setUnderlineText(@StringRes resId: Int): SpannableString {
        val content = SpannableString(getString(resId))
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        return content
    }

    private fun showDatePickerDialogForSettingDate() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            val dateFormat = SimpleDateFormat("dd.MM.YYYY", Locale("ru")).format(calendar.time)
            binding.eventEditEditDateTextView.text = dateFormat
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

        datePickerDialog.show()
    }

    private fun showTimePickerDialogForSettingTime() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(requireContext(), { _, selectedHour, selectedMinute ->
            val formattedTime = String.format(Locale("ru"), "%02d:%02d", selectedHour, selectedMinute)
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
}