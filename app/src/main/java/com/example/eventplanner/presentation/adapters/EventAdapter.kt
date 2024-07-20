package com.example.eventplanner.presentation.adapters

import android.content.Context
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
    private val onItemMenuClick: (String, Int) -> Unit
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
        holder.binding.eventCardMenuButton.setOnClickListener {
            showPopupMenu(events[position].id, holder.itemView, onItemMenuClick)
        }
        holder.itemView.setOnClickListener {
            onItemClick(events[position].id)
        }
    }

    private fun showPopupMenu(
        itemId: String,
        view: View,
        onItemClick: (itemId: String, actionId: Int) -> Unit
    ) {
        val popup = PopupMenu(view.context, view)
        popup.menuInflater.inflate(R.menu.event_card_menu, popup.menu)
        popup.setOnMenuItemClickListener { menuItem ->
            onItemClick(itemId, menuItem.itemId)
            true
        }
    }
}