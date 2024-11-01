package com.example.googletasksclone.customviews

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.googletasksclone.R
import com.example.googletasksclone.databinding.ListItemLayoutBinding

class ListItemView : ConstraintLayout {

    private lateinit var binding: ListItemLayoutBinding
    var setOnClickListener: (() -> Unit)? = null

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        binding = ListItemLayoutBinding.inflate(LayoutInflater.from(context), this, true)

        binding.container.setOnClickListener {
            setOnClickListener?.invoke()
        }

        context.theme.obtainStyledAttributes(
            attrs, R.styleable.CustomListItemView, defStyle, 0
        ).apply {
            try {
                // Set icon
                val iconSrc = getResourceId(R.styleable.CustomListItemView_iconSrc, 0)
                if (iconSrc != 0) binding.icon.setImageResource(iconSrc)
                // Set title text
                binding.title.text =
                    getString(R.styleable.CustomListItemView_titleText) ?: "Default list"
                // Set title text color
                binding.title.setTextColor(
                    getColor(
                        R.styleable.CustomListItemView_titleTextColor,
                        binding.title.currentTextColor
                    )
                )
                // Set text title
                if (this.hasValue(R.styleable.CustomListItemView_titleTextSize)) {
                    binding.title.textSize =
                        getDimension(R.styleable.CustomListItemView_titleTextSize, 0f)
                }
                // Set icon visibility
                val iconVisibility = getBoolean(R.styleable.CustomListItemView_iconVisibility, true)
                binding.icon.visibility =
                    if (iconVisibility) View.VISIBLE else View.INVISIBLE

            } finally {
                recycle()
            }
        }
    }

    // Custom setter methods if needed
    fun setIcon(drawable: Drawable?) {
        binding.icon.setImageDrawable(drawable)
    }

    fun setTitle(text: String) {
        binding.title.text = text
    }

    fun setIconVisibility(condition: Boolean) {
        binding.icon.visibility = if (condition) View.VISIBLE else View.INVISIBLE
    }
}