package com.aiedevice.sdkdemo.bean;

import java.util.List;

/**
 * Created by kevin on 17-8-21.
 */

public class HistoryDetail {
    private boolean is_more;
    private List<HistoryInfo> list;

    public List<HistoryInfo> getList() {
        return list;
    }

    public class HistoryInfo {
        private int id;
        private String name;
        private int res_id;
        private String res_db;
        private long time;
        private int current_length;
        private int length;
        private boolean fav_able;
        private int available;
        private int favorite_id;
        private String album_img;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
