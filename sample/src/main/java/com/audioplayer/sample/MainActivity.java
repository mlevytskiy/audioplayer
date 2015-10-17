package com.audioplayer.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.audioplayer.sample.widget.PlayerView;

import appicon.funakoshi.com.apploadiconasync.R;

public class MainActivity extends AppCompatActivity {

    private PlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playerView = ((PlayerView) findViewById(R.id.player));
        playerView.setUri(Mock.MP3_URI);
    }

    public void onClickShowPlayer(View view) {
        findViewById(R.id.player).setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (!playerView.onClickBackButton()) {
            super.onBackPressed();
        } else {
            //do nothing
        }
    }

}
