package com.tac.media.audioplayer.interfaces;

/**
 * Created by tac
 * Date: 7/17/14.
 */
public interface ProgressUpdater {
    void onProgressUpdate(int progress);

    void onFinish();
}
