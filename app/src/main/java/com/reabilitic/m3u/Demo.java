package com.reabilitic.m3u;

import com.reabilitic.m3u.uri.Uri;
import com.reabilitic.m3u.models.Media;
import com.reabilitic.m3u.models.Playlist;
import com.reabilitic.m3u.models.Segment;
import com.reabilitic.m3u.models.Stream;
import com.reabilitic.models.ChanelModel;

import java.util.List;

public class Demo {

    public static void testPlayListFromHttp() throws Exception {
        Uri uri = Uri.parse("https://webhalpme.ru/iptvforever.m3u");
        //Uri uri = Uri.parse("https://raw.githubusercontent.com/grafov/m3u8/master/sample-playlists/wowza-vod-chunklist.m3u8");
        //Uri uri = Uri.parse("https://raw.githubusercontent.com/grafov/m3u8/master/sample-playlists/master-with-alternatives.m3u8");
        //Uri uri = Uri.parse("https://raw.githubusercontent.com/grafov/m3u8/master/sample-playlists/master-with-multiple-codecs.m3u8");
        //Uri uri = Uri.parse("https://raw.githubusercontent.com/grafov/m3u8/master/sample-playlists/widevine-bitrate.m3u8");
        //Uri uri = Uri.parse("https://raw.githubusercontent.com/grafov/m3u8/master/sample-playlists/master-with-stream-inf-name.m3u8");
        M3UParser parser = new M3UParser();
        List<ChanelModel> playlist = parser.parse(uri);
        System.out.println(playlist);
        System.out.println();

    }

    public static void main(String[] args) throws Exception {
        Demo.testPlayListFromHttp();
    }
}
