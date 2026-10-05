package com.lab.ffoverlay

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import java.io.File

class OverlayView(context: Context) : View(context) {

    private val boxPaint = Paint().apply {
        color = Color.GREEN
        style = Paint.Style.STROKE
        strokeWidth = 4f
        isAntiAlias = true
    }

    private val dotPaint = Paint().apply {
        color = Color.RED
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val textPaint = Paint().apply {
        color = Color.YELLOW
        textSize = 40f
        isAntiAlias = true
        isFakeBoldText = true
    }

    var targetX: Float = 500f
    var targetY: Float = 800f

    private var lastRead = 0L

    fun tick() {
        val now = System.currentTimeMillis()
        if (now - lastRead < 200) return
        lastRead = now

        try {
            val f = File(context.getExternalFilesDir(null), "pos.txt")
            if (!f.exists()) return
            val parts = f.readText().trim().split(",")
            if (parts.size == 2) {
                targetX = parts[0].toFloat()
                targetY = parts[1].toFloat()
                invalidate()
            }
        } catch (_: Exception) {}
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val w = 80f
        val h = 160f
        val left = targetX - w / 2
        val top = targetY - h

        canvas.drawRect(left, top, left + w, top + h, boxPaint)
        canvas.drawCircle(targetX, targetY, 12f, dotPaint)
        canvas.drawText("TARGET (${targetX.toInt()},${targetY.toInt()})",
            targetX + 50, targetY, textPaint)
    }
}
