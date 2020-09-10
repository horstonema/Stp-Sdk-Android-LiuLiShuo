package com.aiedevice.sdkdemo.bean.study;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FollowReadResult implements Serializable {
    private Date date;                                  //日期
    private int followReadCount;                        //跟读次数
    private List<KnowledgePoint> knowledgePoints;       //知识点列表

    public Date getDate() {
        return date;
    }

    public FollowReadResult() {
        knowledgePoints = new ArrayList<>();
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getFollowReadCount() {
        return followReadCount;
    }

    public void setFollowReadCount(int followReadCount) {
        this.followReadCount = followReadCount;
    }

    public List<KnowledgePoint> getKnowledgePoints() {
        return knowledgePoints;
    }

    public void setKnowledgePoints(List<KnowledgePoint> knowledgePoints) {
        this.knowledgePoints = knowledgePoints;
    }

    public static class KnowledgePoint {
        private String wordName;        //单词
        private String pronounceUrl;    //宝宝发音url
        private int score;              //得分

        public KnowledgePoint(String wordName, String pronounceUrl, int score) {
            this.wordName = wordName;
            this.pronounceUrl = pronounceUrl;
            this.score = score;
        }

        public String getWordName() {
            return wordName;
        }

        public void setWordName(String wordName) {
            this.wordName = wordName;
        }

        public String getPronounceUrl() {
            return pronounceUrl;
        }

        public void setPronounceUrl(String pronounceUrl) {
            this.pronounceUrl = pronounceUrl;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }
}
