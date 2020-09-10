package com.aiedevice.sdkdemo.bean.state;

import java.io.Serializable;

public class PlayInfoExtras implements Serializable {

    private static final long serialVersionUID = 1L;

    private String playing;

    private PlayInfoContent content;

    public String getPlaying() {
        return playing;
    }

    public void setPlaying(String playing) {
        this.playing = playing;
    }

    public PlayInfoContent getContent() {
        return content;
    }

    public void setContent(PlayInfoContent content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "PlayInfoExtras{" +
                "playing='" + playing + '\'' +
                ", content=" + content +
                '}';
    }
}
