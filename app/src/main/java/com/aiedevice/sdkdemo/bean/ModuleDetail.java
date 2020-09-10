package com.aiedevice.sdkdemo.bean;

import java.util.List;

/**
 * Created by kevin on 17-8-18.
 */

public class ModuleDetail {
    private int total;
    private List<ModulesInfo> modules;

    public int getTotal() {
        return total;
    }

    public List<ModulesInfo> getModules() {
        return modules;
    }

    public class ModulesInfo {
        private String attr;
        private int id;
        private String title;
        private String description;
        private String icon;
        private int flag;
        private List<CategoriesInfo> categories;

        public int getId() {
            return id;
        }

        public String getAttr() {
            return attr;
        }

        public String getTitle() {
            return title;
        }

        public class CategoriesInfo {
            private int id;
            private String title;
            private String description;
            private String img;
            private String thumb;
            private boolean hots;
            private String act;

        }
    }
}
