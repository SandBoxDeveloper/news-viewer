package andre.hitchman.com.news.base.image

import andre.hitchman.com.news.base.R
import android.widget.ImageView
import com.bumptech.glide.Glide
import javax.inject.Inject

class GlideImageLoader @Inject constructor() : ImageLoader {

    override fun loadImage(imageView: ImageView?, imageUrl: String?) {
        if (imageView != null) {
            Glide.with(imageView.context)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(imageView)
        }
    }
}