package com.ariesvelasquez.selectionprototype

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

object BindingUtil {

    @BindingAdapter("bind:imageUrlSrc")
    @JvmStatic
    fun setImageViaURLSrc(view: ImageView?, url: String?) {
        Picasso.get().load(url).into(view)
    }

    @BindingAdapter("bind:imageDrawableSrc")
    @JvmStatic
    fun setImageViaDrawableSrc(imageView: ImageView?, imgRef: String?) {

        if (imgRef == null) {
            // Show Image From Remote url
            // https://picsum.photos/200/300

            return
        }

        // Set the icon
        val itemResourceId = imageView!!.context.resources.getIdentifier(
            "drawable/test_img_$imgRef", null, imageView.context.packageName
        )
        imageView.setImageResource(itemResourceId)
    }
}