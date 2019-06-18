package com.reabilitic.m3u.models;


import java.util.LinkedList;
import java.util.List;

public class Playlist {
    List<Stream> streams;

    public List<Stream> getStreams() {
        return streams;
    }

    public void setStreams(List<Stream> streams) {
        this.streams = streams;
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "streams=" + streams +
                '}';
    }
}
