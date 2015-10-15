package com.audioplayer.sample.widget;

import android.content.*;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import appicon.funakoshi.com.apploadiconasync.R;

/**
 * Created by max on 13.10.15.
 */
public class PlayerView extends LinearLayout implements View.OnClickListener {

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

        findViewById(R.id.iv_close).setOnClickListener(this);
        findViewById(R.id.iv_repeat).setOnClickListener(this);
        findViewById(R.id.iv_highlight).setOnClickListener(this);
        findViewById(R.id.iv_fastforward).setOnClickListener(this);
        findViewById(R.id.iv_step_forward).setOnClickListener(this);
        findViewById(R.id.iv_play).setOnClickListener(this);
        findViewById(R.id.iv_step_back).setOnClickListener(this);
        findViewById(R.id.iv_fastbackward).setOnClickListener(this);
    }

    public void setUri(Uri uri) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_fastbackward :

                break;
            case R.id.iv_step_back :

                break;
            case R.id.iv_play :

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
                break;
        }
    }

}
