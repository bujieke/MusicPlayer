package com.zy.musicplayer.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.zy.musicplayer.constant.AppConstant;
import com.zy.musicplayer.db.DbManager;
import com.zy.musicplayer.entity.MediaEntity;
import com.zy.musicplayer.eventmsg.BindDataMsg;
import com.zy.musicplayer.eventmsg.ControllerMsg;
import com.zy.musicplayer.listener.PreparedListener;
import com.zy.musicplayer.listener.ServiceListener;
import com.zy.musicplayer.utils.LogUtils;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

public class MusicPlayService extends Service implements ServiceListener {

    private MediaPlayer mediaPlayer = new MediaPlayer();
    private int position = 0;
    public static String playmodel = AppConstant.PlayBackMode.LISTPLAY; //播放模式

    private List<MediaEntity> entityList;
    private int index;






    @Override
    public void onCreate() {
        super.onCreate();

        EventBus.getDefault().register(this);
        entityList = new ArrayList<>();
        List<MediaEntity> query = DbManager.query(this);
        LogUtils.LogD("加载播放列表" + query.size());
        if (query.size() > 0) {
            entityList.clear();
            entityList.addAll(query);
        }
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        LogUtils.LogD("启动音乐服务");

    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MusicBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.LogD("MusicPlayService : onStartCommand");
        //加载默认播放列表

        return super.onStartCommand(intent, flags, startId);
    }


    private void play(final MediaEntity entity) {
        try {
            mediaPlayer.reset();//把各项参数恢复到初始状态
            mediaPlayer.setDataSource(entity.path);
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
                            index++;
                            if (index < entityList.size()) {
                                play(entityList.get(index));
                            } else {
                                index = 0;
                                play(entityList.get(0));
                            }
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
    private void controller(ControllerMsg msg) {
        LogUtils.LogD("---controller---" + msg);
        String action = msg.getAction();
        switch (action) {
            case "before":
                index--;
                if (index >= 0) {
                    play(entityList.get(index));
                } else {
                    index++;
                    Toast.makeText(this, "没有上一曲了", Toast.LENGTH_SHORT).show();
                }
                break;
            case "next":
                index++;
                if (index < entityList.size()) {
                    play(entityList.get(index));
                } else {
                    index--;
                    Toast.makeText(this, "没有下一曲了", Toast.LENGTH_SHORT).show();
                }
                break;
            case "seek":
                int arge = msg.getArge();
                if (mediaPlayer != null) {
                    mediaPlayer.seekTo(arge);
                }
                break;
            case "play":
                index = msg.getArge();
                play(entityList.get(index));
                break;
            case "pause":
                pause();
                break;
        }
    }

    @Subscriber()
    private void bindData(BindDataMsg msg) {
        LogUtils.LogD("---binddata---" + msg);
        entityList.clear();
        entityList.addAll(msg.getEntity());
    }

    @Subscriber(tag = "playmode")
    private void playmodel(String mode) {
        LogUtils.LogD("---playmode---" + mode);
        playmodel = mode;
    }

    /**
     * 暂停音乐
     */
    private void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();

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

    @Override
    public boolean isPlay() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public int CurrentTime() {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public MediaEntity currentMedia() {
        return entityList.get(index);
    }

    @Override
    public List<MediaEntity> currentList() {
        return entityList;
    }

    public class MusicBinder extends Binder {

        public MusicPlayService getService() {
            return MusicPlayService.this;
        }
    }

}

