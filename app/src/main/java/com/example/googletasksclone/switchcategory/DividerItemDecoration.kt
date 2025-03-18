package com.example.googletasksclone.switchcategory

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class DividerItemDecoration(private val dividerHeight: Int, private val colorRes: Int) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)

        // Add space for the divider below the first item
        if (position == 0) {
            outRect.bottom = dividerHeight
        }

        // Add space for the divider below the last item
        if (position == state.itemCount - 1) {
            outRect.bottom = dividerHeight
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val paint = Paint().apply {
            color = colorRes // Set your desired divider color
            strokeWidth = dividerHeight.toFloat()
        }

        // Draw the divider below the first item
        if (parent.childCount > 0) {
            val firstChild = parent.getChildAt(0)
            c.drawLine(
                firstChild.left.toFloat(),
                firstChild.bottom.toFloat(),
                firstChild.right.toFloat(),
                firstChild.bottom.toFloat(),
                paint
            )
        }

        // Draw the divider below the last item
        val lastChild = parent.getChildAt(parent.childCount - 1)
        if (lastChild != null) {
            c.drawLine(
                lastChild.left.toFloat(),
                lastChild.bottom.toFloat(),
                lastChild.right.toFloat(),
                lastChild.bottom.toFloat(),
                paint
            )
        }
    }
}
