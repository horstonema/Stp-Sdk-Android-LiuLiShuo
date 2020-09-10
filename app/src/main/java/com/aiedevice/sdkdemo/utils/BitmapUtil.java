package com.aiedevice.sdkdemo.utils;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;

/**
 * Created by monster on 17-6-2.
 */

public class BitmapUtil {

    public static Bitmap getVideoThumbnail(String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            bitmap = retriever.getFrameAtTime();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

}
