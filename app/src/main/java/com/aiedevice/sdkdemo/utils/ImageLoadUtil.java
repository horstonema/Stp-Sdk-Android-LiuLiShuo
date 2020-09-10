package com.aiedevice.sdkdemo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.widget.ImageView;

import com.aiedevice.jssdk.StpSDK;
import com.aiedevice.sdkdemo.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.lang.ref.WeakReference;

public class ImageLoadUtil {

    public interface ImageLoadCallback {
        void onResourceReady(Bitmap resource);

        void onException(Exception e);
    }

    public static void showImage(String path, ImageView imageView, int placeHolder) {
        if (TextUtils.isEmpty(path)) {
            imageView.setImageResource(placeHolder);
            return;
        }

        if (path.toLowerCase().startsWith("http")) {
            showImageForUrl(path, imageView, placeHolder);
        } else {
            showImageForFilePath(path, imageView, placeHolder);
        }
    }

    public static void showImageForUrl(String path, ImageView imageView) {

        showImageForUrl(path, imageView, R.drawable.default_placeholder);
    }

    public static void showImageForUrl(String path, ImageView imageView, int placeHolder) {

        setImage(path, imageView, placeHolder);
    }

    public static void showCacheImageForUrl(String path, ImageView imageView) {

        setCacheImage(path, imageView);
    }

    public static void showImageForFilePath(String path, ImageView imageView) {

        showImageForFilePath(path, imageView, R.drawable.default_placeholder);
    }

    public static void showImageForFilePath(String path, ImageView imageView, int placeHolder) {

        setImage(path, imageView, placeHolder);
    }

    public static void setImageBitmapForUrl(String path, ImageView imageView, int placeHolder) {

        setImage(path, imageView, placeHolder);
    }

    public static void setImageBitmapForFilePath(String path, ImageView imageView, int placeHolder) {

        setImage(path, imageView, placeHolder);
    }

    public static void showImageForResource(int drawableRes, ImageView imageView, int placeHolder) {
        Glide.with(StpSDK.getInstance().getContext()).load(drawableRes).asBitmap().centerCrop().placeholder(placeHolder).into(imageView);
    }

    public static void loadBitmapForUrl(String path, final WeakReference<ImageLoadCallback> imageLoadCallbackRef) {
        Glide.with(StpSDK.getInstance().getContext()).load(path).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                if (resource != null && imageLoadCallbackRef != null && imageLoadCallbackRef.get() != null) {
                    imageLoadCallbackRef.get().onResourceReady(resource);
                }
            }
        });
    }

    public static void loadBitmapForUrl(String path, final ImageLoadCallback imageLoadCallbackRef) {
        Glide.with(StpSDK.getInstance().getContext()).load(path).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                if (imageLoadCallbackRef != null) {
                    if (resource != null) imageLoadCallbackRef.onResourceReady(resource);
                    else imageLoadCallbackRef.onException(null);
                }
            }
        });
    }

    public static void loadBitmapForUrlDiskSource(String path, @DrawableRes int loadingID, ImageView iv) {
        Glide.with(StpSDK.getInstance().getContext()).load(path).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(loadingID).error(loadingID).into(iv);
    }

    public static void loadBitmapForUrlDiskNone(String path, @DrawableRes int loadingID, ImageView iv) {
        Glide.with(StpSDK.getInstance().getContext()).load(path).asBitmap().diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(loadingID).error(loadingID).into(iv);
    }

    public static void loadBitmapForUrlDiskSource(String path, final ImageLoadCallback imageLoadCallbackRef) {
        Glide.with(StpSDK.getInstance().getContext()).load(path).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                if (imageLoadCallbackRef != null) {
                    if (resource != null) imageLoadCallbackRef.onResourceReady(resource);
                    else imageLoadCallbackRef.onException(null);
                }
            }
        });
    }

    public static void loadBitmapForFile(File file, final WeakReference<ImageLoadCallback> imageLoadCallbackRef) {
        Glide.with(StpSDK.getInstance().getContext()).load(file).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                if (resource != null && imageLoadCallbackRef != null && imageLoadCallbackRef.get() != null) {
                    imageLoadCallbackRef.get().onResourceReady(resource);
                }
            }
        });
    }

    public static void loadBitmapForFile(File file, ImageView imageView, final ImageLoadCallback imageLoadCallback) {
        Glide.with(StpSDK.getInstance().getContext()).load(file).asBitmap().listener(new RequestListener<File, Bitmap>() {
            @Override
            public boolean onException(Exception e, File model, Target<Bitmap> target, boolean isFirstResource) {
                if (imageLoadCallback != null) {
                    imageLoadCallback.onException(e);
                }
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, File model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                if (resource != null && imageLoadCallback != null) {
                    imageLoadCallback.onResourceReady(resource);
                }
                return false;
            }
        }).into(imageView);
    }

    public static void setCropBitmap(String path, ImageView imageView, int placeHolder) {
        try {
            if (!TextUtils.isEmpty(path)) {
                File file = new File(path);
                if (file.exists()) {
                    Glide.with(StpSDK.getInstance().getContext()).load(file).asBitmap().centerCrop().placeholder(placeHolder).into(imageView);
                } else {
                    Glide.with(StpSDK.getInstance().getContext()).load(path).asBitmap().centerCrop().placeholder(placeHolder).into(imageView);
                }
            } else {
                imageView.setImageResource(placeHolder);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param path
     * @param imageView
     * @param placeHolder
     */
    private static void setImage(String path, final ImageView imageView, final int placeHolder) {
        if (TextUtils.isEmpty(path)) {
            imageView.setImageResource(placeHolder);
            return;
        }
        SimpleTarget target = new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                if (imageView != null && resource != null && !resource.isRecycled()) {
                    imageView.setImageBitmap(resource);
                }
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                if (imageView != null) {
                    imageView.setImageResource(placeHolder);
                }
            }
        };
        Context context = StpSDK.getInstance().getContext();
        Glide.with(context).load(path).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(placeHolder).into(target);
    }

    private static void setCacheImage(String path, ImageView imageView) {
        Context context = StpSDK.getInstance().getContext();
        Glide.with(context).load(path).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
    }

}
