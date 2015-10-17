package com.audioplayer.sample.widget;

import android.content.*;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.UiThread;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.tac.media.audioplayer.AudioPlayer;
import com.tac.media.audioplayer.enums.State;
import com.tac.media.audioplayer.interfaces.ProgressUpdater;
import com.tac.media.audioplayer.interfaces.StateNotifier;
import com.tac.media.audioplayer.interfaces.TimeUpdater;

import java.util.concurrent.TimeUnit;

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

    private ImageView nextWord;
    private ImageView prevWord;

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
        prevWord = (ImageView) findViewById(R.id.iv_step_back);
        nextWord = (ImageView) findViewById(R.id.iv_step_forward);

        findViewById(R.id.iv_close).setOnClickListener(this);
        findViewById(R.id.iv_repeat).setOnClickListener(this);
        findViewById(R.id.iv_fastforward).setOnClickListener(this);
        prevWord.setOnClickListener(this);
        nextWord.setOnClickListener(this);
        findViewById(R.id.iv_fastbackward).setOnClickListener(this);

        playPause.setOnCheckedChangeListener(this);
        ((ToggleButton) findViewById(R.id.highlight)).setOnCheckedChangeListener(this);

        progressBar = ((RoundCornerProgressBar) findViewById(R.id.progresBar));

        audioPlayer = new AudioPlayer(context);
        audioPlayer.setProgressUpdate(new ProgressUpdater() {

            @Override
            public void onProgressUpdate(int progress) {
                progressBar.setProgress(progress);
            }

        });

        audioPlayer.setStateUpdater(new StateNotifier() {
            @Override
            public void onStart() {
                totalTime.setTime(audioPlayer.getDuration());
            }

            @Override
            public void onPause() {
                //do nothing
            }

            @Override
            public void onStop() {
                reset();
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
        int millis;
        switch (v.getId()) {
            case R.id.iv_fastbackward :
                millis = audioPlayer.getCurrentProgressInMillis();
                audioPlayer.seekToInMillis(millis - (int) TimeUnit.SECONDS.toMillis(30));
                break;
            case R.id.iv_step_back :

                break;
            case R.id.iv_step_forward : //

                break;
            case R.id.iv_fastforward : // интервал перемотки, в секундах
                millis = audioPlayer.getCurrentProgressInMillis();
                audioPlayer.seekToInMillis(millis + (int) TimeUnit.SECONDS.toMillis(30));
                break;
            case R.id.iv_repeat :
                reset();
                playPause.setChecked(false);
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

    public boolean onClickBackButton() {
        if (getVisibility() == View.VISIBLE) {
            reset();
            hide();
            return true;
        } else {
            return false;
        }
    }

    public void hide() {
        setVisibility(View.GONE);
    }

    public void show() {
        setVisibility(View.VISIBLE);
    }

    @UiThread
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.play_pause : {
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
            case R.id.highlight : {
                prevWord.setEnabled(isChecked);
                nextWord.setEnabled(isChecked);
            }
        }
    }
}
