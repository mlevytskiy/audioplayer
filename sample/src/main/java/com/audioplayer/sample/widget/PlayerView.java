package com.audioplayer.sample.widget;

import android.content.*;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.UiThread;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.tac.media.audioplayer.AudioPlayer;
import com.tac.media.audioplayer.enums.State;
import com.tac.media.audioplayer.interfaces.ProgressUpdater;
import com.tac.media.audioplayer.interfaces.TimeUpdater;

import appicon.funakoshi.com.apploadiconasync.R;

/**
 * Created by max on 13.10.15.
 */
public class PlayerView extends LinearLayout implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private AudioPlayer audioPlayer;
    private Uri uri;
    private RoundCornerProgressBar progressBar;
    private ToggleButton playPause;
    private TimeTextView currentTime;
    private TimeTextView totalTime;

    public PlayerView(Context context) {
        super(context);
        init(context);
    }

    public PlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.fg_article_player, this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setBackgroundColor(getResources().getColor(R.color.primary, context.getTheme()));
        } else {
            setBackgroundColor(getResources().getColor(R.color.primary));
        }
        setOrientation(VERTICAL);
        playPause = ((ToggleButton) findViewById(R.id.play_pause));
        currentTime = (TimeTextView) findViewById(R.id.tvCurrentTime);
        totalTime = (TimeTextView) findViewById(R.id.tvTotalTime);

        findViewById(R.id.iv_close).setOnClickListener(this);
        findViewById(R.id.iv_repeat).setOnClickListener(this);
        findViewById(R.id.iv_highlight).setOnClickListener(this);
        findViewById(R.id.iv_fastforward).setOnClickListener(this);
        findViewById(R.id.iv_step_forward).setOnClickListener(this);
        findViewById(R.id.iv_step_back).setOnClickListener(this);
        findViewById(R.id.iv_fastbackward).setOnClickListener(this);
        playPause.setOnCheckedChangeListener(this);

        progressBar = ((RoundCornerProgressBar) findViewById(R.id.progresBar));

        audioPlayer = new AudioPlayer(context);
        audioPlayer.setProgressUpdate(new ProgressUpdater() {

            @Override
            public void onProgressUpdate(int progress) {
                progressBar.setProgress(progress);
            }

            @Override
            public void onFinish() {
                progressBar.setProgress(0);
                currentTime.resetTime();
            }
        });

        audioPlayer.setTimeUpdater(new TimeUpdater() {
            @Override
            public void updateTime(long millis) {
                currentTime.setTime(millis);
            }

            @Override
            public void onStart(long duration) {
                totalTime.setTime(duration);
            }

            @Override
            protected void onChangeDuration(long newDurationMilis) {
                totalTime.setTime(newDurationMilis);
            }
        });
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_fastbackward :

                break;
            case R.id.iv_step_back :

                break;
            case R.id.iv_step_forward : //

                break;
            case R.id.iv_fastforward : // интервал перемотки, в секундах

                break;
            case R.id.iv_highlight : // включить выключить подсветку слов

                break;
            case R.id.iv_repeat :

                break;
            case R.id.iv_close :
                PlayerView.this.setVisibility(View.GONE);
                reset();
                break;
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        audioPlayer.onDestroy();
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        reset();
    }

    private void resetProgressState() {
        progressBar.setProgress(0);
        currentTime.resetTime();
        totalTime.resetTime();
    }

    private void reset() {
        resetProgressState();
        playPause.setChecked(true);
        audioPlayer.reset();
    }

    @UiThread
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.play_pause: {
                if (isChecked) {
                    audioPlayer.pause();
                } else {
                    if (audioPlayer.getCurrentState() == State.Paused) {
                        audioPlayer.play();
                    } else {
                        audioPlayer.playFrom(uri.getPath());
                    }
                }
                break;
            }

        }
    }
}
