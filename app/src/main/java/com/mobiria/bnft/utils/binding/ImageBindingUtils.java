package com.mobiria.bnft.utils.binding;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mobiria.bnft.R;

public final class ImageBindingUtils {

    private ImageBindingUtils() {
        throw new IllegalStateException(getClass().getName());
    }

    private static final String IMAGE_BITMAP = "setImageBitmap";
    private static final String IMAGE_URI = "setImageUri";
    private static final String IMAGE_IMAGE_URI = "setImageUri";
    private static final String SET_IMAGE_RESOURCES = "setImageResources";
    private static final String SET_IMAGE_DRAWABLE = "setImageDrawable";
    private static final String SET_IMAGE_URL = "setImageUrl";

    @BindingAdapter({IMAGE_BITMAP})
    public static void setImageBitmap(@NonNull ImageView imageView,
                                      Bitmap bitmap) {
        if (bitmap != null) {
           imageView.setImageBitmap(bitmap);
        }
    }

    @BindingAdapter({IMAGE_URI})
    public static void setImageUri(@NonNull ImageView imageView,
                                   Uri uri) {
        if (uri != null) {
            imageView.setImageURI(null);
            imageView.setImageURI(uri);
        }
    }

    @BindingAdapter({SET_IMAGE_URL})
    public static void setImageUrl(@NonNull ImageView imageView,
                                   String url) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(imageView.getContext())
                    .load(url)
                    .centerCrop()
                    .priority(Priority.NORMAL)
                    .placeholder(R.drawable.dummy_hud_one)
                    .error(R.drawable.dummy_hud_one)
                    .into(imageView);
        }
    }

    private static class LoadImage implements Runnable {

        private final ImageView mImageView;
        private final String mUrl;

        private LoadImage(ImageView imageView, String url) {
            mImageView = imageView;
            mUrl = url;
        }

        @Override
        public void run() {
            if (!TextUtils.isEmpty(mUrl)) {
                Glide.with(mImageView.getContext())
                        .load(mUrl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .priority(Priority.IMMEDIATE)
                        .placeholder(R.drawable.dummy_hud_one)
                        .error(R.drawable.dummy_hud_one)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .into(mImageView);
            } else {
                mImageView.setImageResource(R.drawable.dummy_hud_one);
            }
        }
    }

    @BindingAdapter({IMAGE_IMAGE_URI})
    public static void setImageUri(@NonNull ImageView imageView,
                                   String path) {
        if (path != null) {
            imageView.setImageURI(Uri.parse(path));
        }
    }

    @BindingAdapter({SET_IMAGE_RESOURCES})
    public static void setImageResources(@NonNull ImageView imageView,
                                         Integer resources) {
        if (resources != null) {
            imageView.setImageResource(resources);
        }
    }

    @BindingAdapter({SET_IMAGE_DRAWABLE})
    public static void setImageDrawable(@NonNull ImageView imageView,
                                        Drawable drawable) {
        if (drawable != null) {
            imageView.setImageDrawable(drawable);
        }
    }

}
