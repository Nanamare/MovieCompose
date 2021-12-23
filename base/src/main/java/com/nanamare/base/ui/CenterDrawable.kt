package com.nanamare.base.ui

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Rect
import android.graphics.drawable.Drawable
import kotlin.math.roundToInt

class CenterDrawable(
    private val drawable: Drawable,
    private val size: Int,
) : Drawable() {

    override fun draw(canvas: Canvas) {
        drawable.draw(canvas)
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        val left = bounds.left + ((bounds.width() - size) * 0.5f).roundToInt()
        val top = bounds.top + ((bounds.height() - size) * 0.5f).roundToInt()
        drawable.setBounds(left, top, left + size, top + size)
    }

    override fun setAlpha(alpha: Int) {
        drawable.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        drawable.colorFilter = colorFilter
    }

    override fun getOpacity(): Int {
        return drawable.opacity
    }
}