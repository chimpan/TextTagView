package com.cyp.texttag

import android.content.Context
import android.graphics.Canvas
import androidx.appcompat.widget.AppCompatTextView
import kotlin.jvm.JvmOverloads
import android.text.StaticLayout
import android.util.AttributeSet

class AlignTextView(context: Context, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {
	override fun setTextColor(color: Int) {
		super.setTextColor(color)
		paint.color = currentTextColor
	}
	
	override fun onDraw(canvas: Canvas) {
		val content = text
		if (content !is String) {
			super.onDraw(canvas)
			return
		}
		val layout = layout
		for (i in 0 until layout.lineCount) {
			val lineBaseline = layout.getLineBaseline(i) + paddingTop
			val lineStart = layout.getLineStart(i)
			val lineEnd = layout.getLineEnd(i)
			if (i != layout.lineCount - 1 && layout.lineCount == 1) { //只有一行
				val line = content.substring(lineStart, lineEnd)
				val width = StaticLayout.getDesiredWidth(content, lineStart, lineEnd, paint)
				drawScaledText(canvas, line, lineBaseline.toFloat(), width)
			} else if (i == layout.lineCount - 1) { //最后一行
				canvas.drawText(content.substring(lineStart), paddingLeft.toFloat(), lineBaseline.toFloat(), paint)
			} else { //中间行
				val line = content.substring(lineStart, lineEnd)
				val width = StaticLayout.getDesiredWidth(content, lineStart, lineEnd, paint)
				drawScaledText(canvas, line, lineBaseline.toFloat(), width)
			}
		}
	}
	
	private fun drawScaledText(canvas: Canvas, line: String, baseLineY: Float, lineWidth: Float) {
		if (line.isEmpty()) {
			return
		}
		var x = paddingLeft.toFloat()
		val forceNextLine = line[line.length - 1].toInt() == 10
		val length = line.length - 1
		if (forceNextLine || length == 0) {
			canvas.drawText(line, x, baseLineY, paint)
			return
		}
		val d = (measuredWidth - lineWidth - paddingLeft - paddingRight) / length
		for (element in line) {
			val c = element.toString()
			val cw = StaticLayout.getDesiredWidth(c, this.paint)
			canvas.drawText(c, x, baseLineY, this.paint)
			x += cw + d
		}
	}
}