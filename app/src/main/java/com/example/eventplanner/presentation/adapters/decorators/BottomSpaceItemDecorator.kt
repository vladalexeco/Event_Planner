package com.example.eventplanner.presentation.adapters.decorators

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class BottomSpaceItemDecoration(private val bottomSpaceDp: Int, context: Context) : RecyclerView.ItemDecoration() {
    private val bottomSpacePx = context.dpToPx(bottomSpaceDp)

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)

        if (position != parent.adapter!!.itemCount - 1) {
            outRect.bottom = bottomSpacePx
        }
    }
}

fun Context.dpToPx(dp: Int): Int {
    return (dp * resources.displayMetrics.density).toInt()
}