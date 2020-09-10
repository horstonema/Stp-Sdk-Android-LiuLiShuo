package com.aiedevice.sdkdemo;

import java.nio.charset.Charset;

public class AppConstant {
    public static final String PACKAGE_ID = "aie.app";

    public static final int[] VOLUME_VALUES = new int[]{50, 55, 65, 75, 85, 90, 100};

    public static final int PLAY_CONTROL_PREV = 1;
    public static final int PLAY_CONTROL_NEXT = 2;

    // 单曲
    public static final int FAVORITE_TYPE_SINGLE = 1;
    // 专辑
    public static final int FAVORITE_TYPE_ALBUM = 2;

    public static final Charset CHARSET_UTF_8 = Charset.forName("UTF-8");

    public static int getVolumeStep(int volume) {
        for (int i = 0; i < VOLUME_VALUES.length; i++) {
            if (volume <= VOLUME_VALUES[i]) {
                return i;
            }
        }

        return VOLUME_VALUES.length - 1;
    }
}
