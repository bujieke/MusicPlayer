package com.zy.musicplayer.present;

import android.content.Context;

import com.zy.musicplayer.application.MyApplication;
import com.zy.musicplayer.entity.MediaEntity;
import com.zy.musicplayer.eventmsg.ControllerMsg;
import com.zy.musicplayer.service.MusicPlayService;
import com.zy.musicplayer.view.MusicPlayView;

import org.simple.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by  zy on 2017/12/25.
 * //          佛曰:
 * //                  写字楼里写字间，写字间里程序员；
 * //                  程序人员写程序，又拿程序换酒钱。
 * //                  酒醒只在网上坐，酒醉还来网下眠；
 * //                  酒醉酒醒日复日，网上网下年复年。
 * //                  但愿老死电脑间，不愿鞠躬老板前；
 * //                  奔驰宝马贵者趣，公交自行程序员。
 * //                  别人笑我忒疯癫，我笑自己命太贱；
 * //                  不见满街漂亮妹，哪个归得程序员？
 */
public class MusicPlayPresent {
    private Context mContext;
    private MusicPlayView mView;
    private MusicPlayService service;


    public MusicPlayPresent(Context context, MusicPlayView view, MusicPlayService service) {
        this.mContext = context;
        this.mView = view;
        this.service = service;

    }


    public void initMusicData() {

        MyApplication instance = MyApplication.getInstance();
        int songItemPos = instance.songItemPos;
        MediaEntity mediaEntity = instance.songsList.get(songItemPos);
        if (mediaEntity != null) {
            int duration = mediaEntity.duration;
            mView.setControllerProMax(duration);
            mView.setTitle(mediaEntity.title);
        } else {
            mView.showToast("未成功加载歌曲");
        }
    }

    //播放Or暂停
    public boolean playOrPause() {
        return service.isPlay();
    }

    //下一曲
    public void next() {
        EventBus.getDefault().post(new ControllerMsg("next", 0), "controller");

    }

    //上一曲
    public void before() {
        EventBus.getDefault().post(new ControllerMsg("before", 0), "controller");

    }


    //拖动
    public void touchSeekBar(int protion) {
        EventBus.getDefault().post(new ControllerMsg("seek", protion), "controller");
    }
    //设置进度条

    public void getPro() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    EventBus.getDefault().post(service.CurrentTime());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }
        }).start();


    }

}
