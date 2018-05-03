package com.zy.musicplayer.listener;

import android.media.MediaPlayer;

import org.simple.eventbus.EventBus;

/**
 * Created by  zy on 2017/12/15.
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
public class PreparedListener implements MediaPlayer.OnPreparedListener {
    private int positon;

    public PreparedListener(int positon) {
        this.positon = positon;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();    //开始播放
        EventBus.getDefault().post("update");
        if (positon > 0) {    //如果音乐不是从头播放
            mp.seekTo(positon);
        }
    }

}
