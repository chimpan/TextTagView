package com.cyp.texttag

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import com.cyp.texttag.databinding.ItemTextTagBinding

/**
 * 带标签的TextView
 * 使用场景：当文字内容超过限定行数时，在末尾显示标签或文字
 */
class TextTagView(context: Context, attributeSet: AttributeSet?) : ConstraintLayout(context, attributeSet) {
	private val vb = ItemTextTagBinding.inflate(LayoutInflater.from(context), this, true)
	private var maxLines = Int.MAX_VALUE
	private var lineSpace = 0
	
	init {
		val attrs = context.obtainStyledAttributes(attributeSet, R.styleable.TextTagView)
		initAttrs(attrs)
		attrs.recycle()
	}
	
	private fun initAttrs(attrs: TypedArray) {
		lineSpace = attrs.getDimensionPixelSize(R.styleable.TextTagView_ttv_line_space, 0)
		maxLines = attrs.getInt(R.styleable.TextTagView_ttv_max_lines, -1)
		val text = attrs.getString(R.styleable.TextTagView_ttv_text)
		val textSize = attrs.getDimensionPixelSize(R.styleable.TextTagView_ttv_text_size, 30)
		val textColor = attrs.getColor(R.styleable.TextTagView_ttv_text_color, Color.BLACK)
		val imgTagRes = attrs.getResourceId(R.styleable.TextTagView_ttv_tag_img_src, 0)
		val tvTagText = attrs.getString(R.styleable.TextTagView_ttv_tag_text)
		val tvTagTextColor = attrs.getColor(R.styleable.TextTagView_ttv_tag_text_color, Color.RED)
		val tvTagSize = attrs.getDimensionPixelSize(R.styleable.TextTagView_ttv_tag_size, 0)
		val tvTagWidth = attrs.getDimensionPixelSize(R.styleable.TextTagView_ttv_tag_width, 30)
		val tvTagHeight = attrs.getDimensionPixelSize(R.styleable.TextTagView_ttv_tag_height, 30)
		
		setTextColor(textColor)
		setTextSize(textSize.toFloat())
		
		setTagText(tvTagText)
		setTagTextColor(tvTagTextColor)
		
		if (tvTagSize > 0) {
			setTagImageRes(imgTagRes, tvTagSize, tvTagSize)
		} else {
			setTagImageRes(imgTagRes, tvTagWidth, tvTagHeight)
		}
		
		setText(text)
	}
	
	fun setMaxLines(maxLines: Int) {
		this.maxLines = maxLines
	}
	
	fun setTextColor(textColor: Int) {
		vb.tvContent1.setTextColor(textColor)
		vb.tvContent2.setTextColor(textColor)
	}
	
	fun setTextSize(textSize: Float) {
		vb.tvContent1.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
		vb.tvContent2.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
		vb.tvTag.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
	}
	
	fun setTagText(tagText: String?) {
		vb.tvTag.text = tagText
	}
	
	fun setTagTextColor(tvTagTextColor: Int) {
		vb.tvTag.setTextColor(tvTagTextColor)
	}
	
	fun setTagImageRes(res: Int, w: Int = 0, h: Int = 0) {
		if (res != 0) {
			vb.imgTag.setImageResource(res)
			if (w != 0 && h != 0) {
				vb.imgTag.layoutParams = vb.imgTag.layoutParams.also {
					it.width = w
					it.height = h
				}
			}
		}
	}
	
	fun getTagImageView() = vb.imgTag
	
	/**
	 * 设置文本内容
	 * !!!若其他属性通过代码设置，最后需调用该方法
	 */
	fun setText(str: String?) {
		resetViewStatus()
		vb.tvContent1.text = str
		//内容是空或未设置最大行数，隐藏第二个TextView
		if (str.isNullOrEmpty() || maxLines < 1) {
			vb.tvContent2.isGone = true
			vb.tvContent1.maxLines = Int.MAX_VALUE
			setRealLineSpace(false)
			return
		}
		vb.tvContent1.post {
			val layout = vb.tvContent1.layout
			var line = 1 //str全部显示需要的行数，
			while (true) {
				val e = layout.getLineEnd(line - 1)
				if (e >= str.length - 1) {
					break
				}
				line++
			}
			when {
				line == maxLines && maxLines == 1 -> {
					//最多显示一行，且一行能显示下所有内容，隐藏上面的TextView
					vb.tvContent1.isGone = true
					vb.tvContent2.text = str
					setRealLineSpace(false)
				}
				line > maxLines && maxLines == 1 -> {
					//最多显示一行且实际行数大于一行
					vb.tvTag.isGone = false
					vb.imgTag.isGone = false
					vb.tvContent1.isGone = true
					vb.tvContent2.text = str
					setRealLineSpace(false)
				}
				line > maxLines -> {
					//最大行数<全部行数
					//第一个TextView设置最大行数-1
					//第二个TextView显示剩下的内容
					vb.tvContent1.maxLines = maxLines - 1
					val s = layout.getLineEnd(maxLines - 2)
					vb.tvContent2.text = str.substring(s)
					vb.tvTag.isGone = false
					vb.imgTag.isGone = false
					setRealLineSpace(true)
				}
				else -> {
					//最大行数>=全部行数
					//隐藏第二个TextView
					vb.tvContent2.isGone = true
					setRealLineSpace(false)
				}
			}
		}
	}
	
	fun setLineSpace(lineSpace: Int) {
		this.lineSpace = lineSpace
	}
	
	/**
	 * 设置行间距
	 * @param bothTextShow 是否2个TextView都显示
	 */
	private fun setRealLineSpace(bothTextShow: Boolean) {
		vb.tvContent1.setLineSpacing(lineSpace.toFloat(), 1f)
		if (bothTextShow) {
			val realSpace = if (bothTextShow) lineSpace else 0
			vb.tvContent2.layoutParams = (vb.tvContent2.layoutParams as LayoutParams).also {
				it.topMargin = realSpace
			}
		}
	}
	
	private fun resetViewStatus() {
		vb.tvTag.isGone = true
		vb.imgTag.isGone = true
		vb.tvContent1.isGone = false
		vb.tvContent2.isGone = false
	}
}