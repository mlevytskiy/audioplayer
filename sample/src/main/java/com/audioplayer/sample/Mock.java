package com.audioplayer.sample;

import android.net.Uri;
import android.os.Environment;

import java.io.File;

/**
 * Created by max on 14.10.15.
 */
public class Mock {

    private static File musicFile;

    static {
        String path = Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_MUSIC;
        musicFile = new File(path, "test.mp3");
//        musicFile = new File(path, "test2.mp3"); //shot music
    }

    public static final Uri MP3_URI = Uri.fromFile(musicFile);
}
