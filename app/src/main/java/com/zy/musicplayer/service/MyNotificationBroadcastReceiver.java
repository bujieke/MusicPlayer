package com.zy.musicplayer.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zy.musicplayer.application.MyApplication;
import com.zy.musicplayer.eventmsg.ControllerMsg;
import com.zy.musicplayer.utils.LogUtils;

import org.simple.eventbus.EventBus;

/**
 * Created by Administrator on 2018/5/4 0004.
 */

public class MyNotificationBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtils.LogD("MyNotificationBroadcastReceiver--------" + "onReceive" + intent.getAction());
        switch (intent.getAction()) {
            case "close":
                MyApplication.getInstance().notManager.cancel(1);
                Intent intext1 = new Intent(context, MusicPlayService.class);
                context.stopService(intext1);
                break;
            case "play":
                LogUtils.LogD("MyNotificationBroadcastReceiver--------" + "close");
                EventBus.getDefault().post(new ControllerMsg("play", 0), "controller");
                break;
            case "pause":
                LogUtils.LogD("MyNotificationBroadcastReceiver--------" + "close");
                EventBus.getDefault().post(new ControllerMsg("pause", 0), "controller");
                break;

            case "next":
                LogUtils.LogD("MyNotificationBroadcastReceiver--------" + "close");
                EventBus.getDefault().post(new ControllerMsg("next", 0), "controller");
                break;
            case "last":
                LogUtils.LogD("MyNotificationBroadcastReceiver--------" + "close");
                EventBus.getDefault().post(new ControllerMsg("before", 0), "controller");
                break;

        }
    }
}
