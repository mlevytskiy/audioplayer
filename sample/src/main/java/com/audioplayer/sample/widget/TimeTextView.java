package com.audioplayer.sample.widget;

import android.content.*;
import android.util.AttributeSet;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by max on 17.10.15.
 */
public class TimeTextView extends TextView {

    private DateFormat dateFormat = new SimpleDateFormat("mm:ss");

    public TimeTextView(Context context) {
        super(context);
        init();
    }

    public TimeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TimeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        resetTime();
    }

    public void setTime(long millis) {
        setText(dateFormat.format(millis));
    }

    public void resetTime() {
        setTime(0);
    }
}
