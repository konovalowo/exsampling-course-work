package com.konovalovea.expsampling.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.konovalovea.expsampling.R
import kotlin.math.max
import kotlin.math.min

class AffectGrid @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var cellCount: Int

    private val axisPaint = Paint(ANTI_ALIAS_FLAG).apply {
        strokeWidth = 3f
    }
    private val gridPaint = Paint(ANTI_ALIAS_FLAG).apply {
        color = context.getColor(R.color.colorGreyDarker)
        strokeWidth = 2f
    }
    private val pointPaint = Paint(ANTI_ALIAS_FLAG).apply {
        color = context.getColor(R.color.colorAccent)
    }

    private var pointX = 0f
        get() = max(min(field, width.toFloat()), 0f)
    private var pointY = 0f
        get() = max(min(field, height.toFloat()), 0f)

    var xRelativeValue
        get() = pointX / width * cellCount.toDouble() - cellCount / 2f
        set(value) {
            pointX = ((value + cellCount / 2f) * width / cellCount).toFloat()
        }

    var yRelativeValue
        get() = -pointY / height * cellCount.toDouble() + cellCount / 2f
        set(value) {
            pointY = (-(value - cellCount / 2f) * height / cellCount).toFloat()
        }


    init {
        context.obtainStyledAttributes(attrs, R.styleable.AffectGrid).apply {
            cellCount = getInteger(R.styleable.AffectGrid_cellCount, CELL_COUNT_DEF)
            recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        pointX = measuredWidth / 2f
        pointY = measuredHeight / 2f
    }

    override fun onDraw(canvas: Canvas) {
        val gridYInterval = height / cellCount.toFloat()
        val gridXInterval = width / cellCount.toFloat()
        for (i in 0..cellCount) {
            val linePaint = if (cellCount % 2 == 0 && i == cellCount / 2) axisPaint else gridPaint

            val positionY = i * gridYInterval
            canvas.drawLine(0f, positionY, width.toFloat(), positionY, linePaint)

            val positionX = i * gridXInterval
            canvas.drawLine(positionX, 0f, positionX, height.toFloat(), linePaint)
        }

        canvas.drawCircle(pointX, pointY, POINT_RADIUS, pointPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        pointX = event.x
        pointY = event.y

        performClick()
        invalidate()
        return true
    }

    override fun performClick(): Boolean {
        super.performClick()
        return false
    }

    companion object {
        private const val POINT_RADIUS = 16f

        private const val CELL_COUNT_DEF = 10
    }
}