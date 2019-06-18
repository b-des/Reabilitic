package com.reabilitic.m3u;

import android.util.Log;

import com.reabilitic.m3u.uri.Uri;
import com.reabilitic.m3u.utils.IOUtils;
import com.reabilitic.m3u.utils.Strings;
import com.reabilitic.m3u.models.*;
import com.reabilitic.models.ChanelModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

//https://pypkg.com/pypi/pytoutv/src/toutv/m3u8.py
//https://developer.apple.com/library/content/technotes/tn2288/_index.html#//apple_ref/doc/uid/DTS40012238-CH1-ALTERNATE_MEDIA

public class M3UParser {
    private static final Logger LOG = Logger.getLogger(M3UParser.class.getName());


    public  List<ChanelModel> parse(Uri uri) throws IOException {
        if (uri.getScheme().startsWith("http")) {
            InputStream is = null;
            try {
                URL url = new URL(uri.toString());
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                // optional default is GET
                con.setRequestMethod("GET");
                if (con.getResponseCode() == 200) {
                    is = con.getInputStream();
                    return parse(is);
                }
            } finally {
                IOUtils.closeQuietly(is);
            }
        }

        else {
            InputStream is = null;
            try {
                is = new FileInputStream(new File(uri.toString()));
                return parse(is);

            } finally {
                IOUtils.closeQuietly(is);
            }
        }

        return null;
    }

    public  List<ChanelModel> parse(final InputStream inputStream) throws IOException {
        String[] lines = IOUtils.toStringArray(inputStream);
        if (lines.length <= 1) {
            throw new IllegalArgumentException("input stream is empty");
        }

        Playlist playlist = new Playlist();

        Key currentKey = null;
        System.out.println("parse: "+lines[1]);
        //if (!M3U.SIGNATURE.equals(lines[0]))
        //    throw new IllegalArgumentException("Invalid M3U8 file");
        List<ChanelModel> streams = new ArrayList<>();
        ChanelModel channel = new ChanelModel();
        for (int i = 0; i < lines.length; i++) {

            if(lines[i].contains("webhalpme") || lines[i].contains("#EXTGRP:")){
                continue;
            }

            if(lines[i].startsWith("#EXTINF:")){
                channel = new ChanelModel();
                String[] name = lines[i].split(",");//.replaceAll("\\[.*\\]", "").replaceAll("#EXTINF:-1,","").trim();
                channel.setName(name[1].trim().replaceAll("\\[.*\\]", ""));
            }
            else if(lines[i].startsWith("http")){
                channel.setUrl(lines[i]);
                streams.add(channel);
            }
        }
        return streams;
    }

    /**
     * If the line contains a tag return the name without the '#'
     * @param line
     * @return the tag name without the '#'
     */
    private M3U.Tag getTag(final String line) {
        int index = (!Strings.isNullOrEmpty(line) && (line.length() > 1)) ?
                line.indexOf(':') : -1;
        String tagName = (index != -1) ? line.substring(1, index) : "";
        return M3U.Tag.fromTagName(tagName);
    }

    private List<String> getAttributes(final String line) {
        List<String> attributes = new LinkedList<>();
        if (!Strings.isNullOrEmpty(line)) {

            int index = line.indexOf(':');
            if (index != -1) {
                String[] pairs = line.substring(index + 1).split(",");
                for (String pair : pairs) {
                    attributes.add(pair.trim());
                }
            }
        }

        return attributes;
    }

    private boolean isTagLine(final String line) {
        return (!Strings.isNullOrEmpty(line) && (line.length() > 3)) &&
                line.substring(0, 4).equals(M3U.EXT_PREFIX);
    }


    private int getAttributeValueAsInt(final List<String> attributes,
                                       final int position,
                                       final int defaultValue) {
        String attribute = null;
        try {
            attribute = getAttribute(attributes, position);
            return Strings.isNullOrEmpty(attribute) ? defaultValue : Integer.valueOf(attribute);
        } catch (Exception err) {
            throw new IllegalArgumentException("while coercing int value for attribute: " + attribute, err);
        }
    }

    private double getAttributeValueAsDouble(final List<String> attributes,
                                          final int position,
                                          final double defaultValue) {
        String attribute = null;
        try {
            attribute = getAttribute(attributes, position);
            return Strings.isNullOrEmpty(attribute) ? defaultValue : Double.valueOf(attribute);
        } catch (Exception err) {
            throw new IllegalArgumentException("while coercing int value for attribute: " + attribute, err);
        }
    }

    private String getAttributeValueAsString(final List<String> attributes,
                                             final int position,
                                             final String defaultValue) {
        String attribute = null;
        try {
            attribute = getAttribute(attributes, position);
            return Strings.isNullOrEmpty(attribute) ? defaultValue : attribute;
        } catch (Exception err) {
            throw new IllegalArgumentException("while coercing string value for attribute: " + attribute, err);
        }
    }

    private String getAttributeValue(final List<String> attributes, final String name) {
        if ((attributes == null) || Strings.isNullOrEmpty(name))
            return null;

        for (String attribute: attributes) {
            int index = attribute.indexOf('=');
            if (index != -1) {
                String key = attribute.substring(0, index).trim();
                String val = attribute.substring(index + 1).trim();
                if (key.equalsIgnoreCase(name)) {
                    return Strings.strip(val, "\"");
                }
            }
        }

        return "";
    }

    private String getAttribute(final List<String> attributes, final int position) {
        return  ((attributes != null) && (attributes.size() > position)) ?
                attributes.get(position) : null;
    }

}
