package com.reabilitic.m3u.models;

import com.reabilitic.m3u.uri.Uri;

import java.util.Arrays;

/**
 * https://pypkg.com/pypi/pytoutv/src/toutv/m3u8.py
 */
public class Stream {
    String title;
    String stream;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    @Override
    public String toString() {
        return "Stream{" +
                "title='" + title + '\'' +
                ", stream='" + stream + '\'' +
                '}';
    }
}
