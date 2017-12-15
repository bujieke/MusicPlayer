package com.zy.musicplayer.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.zy.musicplayer.entity.MediaEntity;
import com.zy.musicplayer.eventmsg.MusicControllerMsg;
import com.zy.musicplayer.listener.PreparedListener;
import com.zy.musicplayer.constant.AppConstant;
import com.zy.musicplayer.utils.LogUtils;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

public class MusicPlayService extends Service {

    private MediaPlayer mediaPlayer = new MediaPlayer();
    private String path;
    private boolean isPause;
    public static String playmodel = AppConstant.PlayBackMode.LISTPLAY; //播放模式

    public MusicPlayService() {
        LogUtils.LogD("启动音乐服务");
        EventBus.getDefault().register(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    private void play(int position) {
        try {
            mediaPlayer.reset();//把各项参数恢复到初始状态
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();  //进行缓冲
            mediaPlayer.setOnPreparedListener(new PreparedListener(position));//注册一个监听器
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    //播放模式
                    LogUtils.LogD("PLAYMODE:" + playmodel);
                    switch (playmodel) {
                        case AppConstant.PlayBackMode.LISTPLAY:
                            //列表循环
                            break;
                        case AppConstant.PlayBackMode.SINGLEPLAY:
                            //单曲循环

                            mediaPlayer.seekTo(0);
                            mediaPlayer.start();
                            break;
                        case AppConstant.PlayBackMode.RANDOMPLAY:
                            //随机播放

                            break;

                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscriber(tag = "controller")
    private void controller(MusicControllerMsg msg) {
        MediaEntity entity = msg.getEntity();
        if (entity != null) {
            if (mediaPlayer.isPlaying()) {
                if (path != null && path.equals(entity.path)) {
                    return;
                } else {
                    stop();
                }
            }
            path = entity.path;
        }
        int action = msg.getActicon();
        int postion = msg.getPostion();

        if (action == AppConstant.PlayerMsg.PLAY_MSG) {
            play(postion);
        } else if (action == AppConstant.PlayerMsg.PAUSE_MSG) {
            pause();
        } else if (action == AppConstant.PlayerMsg.STOP_MSG) {
            stop();
        } else if (action == AppConstant.PlayerMsg.REPLAY_MSG) {
            mediaPlayer.start();
        }
    }

    @Subscriber(tag = "playmode")
    private void playmodel(String mode) {
        playmodel = mode;
    }

    /**
     * 暂停音乐
     */
    private void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPause = true;
        }
    }

    /**
     * 停止音乐
     */
    private void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            try {
                mediaPlayer.prepare(); // 在调用stop后如果需要再次通过start进行播放,需要之前调用prepare函数
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        LogUtils.LogD("关闭音乐服务");
        EventBus.getDefault().unregister(this);
    }


}

