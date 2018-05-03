package com.zy.musicplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.zy.musicplayer.MainActivity;
import com.zy.musicplayer.R;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/24 0024.
 */

public class SplashActivity extends AppCompatActivity {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();

                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        handler.sendEmptyMessageDelayed(1, 5000);

    }


}
