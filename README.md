# TextTagView
带标签的文本控件。当内容超过设置的最大行数时，末尾显示标签。
## 效果预览
[![预览](https://github.com/chimpan/TextTagView/blob/master/shot/1677653316578.jpg "预览")](https://github.com/chimpan/TextTagView/blob/master/shot/1677653316578.jpg "预览")
## 属性列表
|  属性名 |说明   |
| ------------ | ------------ |
| ttv_line_space  |  行间距 |
|ttv_text |内容 |
| ttv_text_size|文字大小 |
| ttv_text_color|文字颜色 |
| ttv_max_lines| 最大显示行数|
|ttv_tag_img_src | 图片标签（本地资源）|
|ttv_tag_text | 文字标签内容|
| ttv_tag_text_color| 文字标签颜色|
| ttv_tag_size| 图片标签大小，宽=高|
| ttv_tag_width|图片标签宽|
| ttv_tag_height|图片标签高|
## 代码设置属性
|  方法名 |说明   |
| ------------ | ------------ |
| setLineSpace  |  行间距 |
|setText |内容 |
| setText|文字大小 |
| setTextColor|文字颜色 |
| setMaxLines| 最大显示行数|
|setTagImageRes | 设置图片标签（本地资源）|
|setTagText | 文字标签内容|
| setTagTextColor| 文字标签颜色|
|getTagImageView|获取图片标签控件（网络图片自行设置）|
## 如何使用
```
 implementation 'com.github.chimpan:TextTagView:1.0.0'
```
```
 <com.cyp.texttag.TextTagView
        android:id="@+id/ttv_text_tag"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="14dp"
        app:ttv_line_space="15dp"
        app:ttv_tag_text="全文"
        app:ttv_max_lines="1"
        app:ttv_tag_text_color="#f00"
        app:ttv_text_color="#666"
        app:ttv_text_size="14sp" />
```
