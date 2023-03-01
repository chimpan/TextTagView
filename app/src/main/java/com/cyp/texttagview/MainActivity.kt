package com.cyp.texttagview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cyp.texttagview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
	private lateinit var vb: ActivityMainBinding
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		vb = ActivityMainBinding.inflate(layoutInflater)
		setContentView(vb.root)
		
		val str =
			"寓言故事是文学体裁的一种。含有讽喻或明显教训意义的故事。它的结构简短，多用借喻手法，" +
					"使富有教训意义的主题或深刻的道理在简单的故事中体现。寓言的故事情节设置的好坏关系到寓言的未来。" +
					"中国历来有些著名的寓言故事如《揠苗助长》、《自相矛盾》、《郑人买履》、《守株待兔》、《刻舟求剑》、" +
					"《画蛇添足》等，古希腊《伊索寓言》中的名篇《农夫和蛇》在世界范围类享有很高的知名度。其成功之处" +
					"在于故事的可读性很强，无论人们的文化水准高低，都能在简练明晰的故事中悟出道理。"
		vb.tvOrigin.text = str
		//文字标签
		vb.ttvTextTag.setText(str)
		//图片标签
		vb.ttvImgTag.setText(str)
		
		vb.ttvTextTag.setOnClickListener {
			vb.ttvTextTag.setText("寓言故事是文学体裁的一种。含")
			vb.ttvImgTag.setText("寓言故事是文学体裁的一种。含")
		}
		vb.tvOrigin.setOnClickListener {
			vb.ttvTextTag.setText(str)
			vb.ttvImgTag.setText(str)
		}
	}
}