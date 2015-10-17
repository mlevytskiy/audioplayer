package com.tac.media.audioplayer.interfaces;

/**
 * Created by tac
 * Date: 7/18/14.
 */
public abstract class TimeUpdater {

    private long duration = 0;

    public abstract void updateTime(long millis);

    public abstract void onStart(long duration);

    protected abstract void onChangeDuration(long newDurationMilis);

    public void changeDuration(long duration) {
        if (this.duration != duration) {
            onChangeDuration(this.duration = duration);
        }
    }


}
