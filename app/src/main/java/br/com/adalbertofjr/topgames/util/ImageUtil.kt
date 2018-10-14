package br.com.adalbertofjr.topgames.util

import android.widget.ImageView
import com.squareup.picasso.Picasso

/**
 * Images
 * Created by Adalberto Fernandes Júnior on 07/10/2018.
 * Copyright © 2018. All rights reserved.
 */

class ImageUtil {

    fun loadImage(fromUrl: String, toView: ImageView) {
        Picasso.get()
                .load(fromUrl)
                .into(toView)
    }
}

