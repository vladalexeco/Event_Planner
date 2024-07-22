package com.example.eventplanner.presentation.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.eventplanner.R
import com.example.eventplanner.databinding.ItemViewEventCardBinding
import com.example.eventplanner.domain.models.Event

class EventAdapter(
    private val onItemClick: (String) -> Unit,
) : RecyclerView.Adapter<EventViewHolder>() {

    private val events = ArrayList<Event>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(
            ItemViewEventCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(events[position])
        holder.itemView.setOnClickListener {
            onItemClick(events[position].id)
        }
    }

    fun setNewData(newEventList: List<Event>) {
        events.clear()
        events.addAll(newEventList)
        notifyDataSetChanged()
    }
}