package andre.hitchman.com.news.base.image

import android.widget.ImageView

interface ImageLoader {

    fun loadImage(imageView: ImageView?, imageUrl: String?)
}