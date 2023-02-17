package com.src.onboarding.presentation.courses.courses_main.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MainCourseItemDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildLayoutPosition(view)
        if (position < state.itemCount) {
            outRect.right = MARGIN
        }
    }

    private companion object {
        private const val MARGIN = 24
    }
}