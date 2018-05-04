package com.zy.musicplayer.application;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.widget.RemoteViews;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.zy.musicplayer.R;
import com.zy.musicplayer.activity.MusicPlayActivity;
import com.zy.musicplayer.db.DbManager;
import com.zy.musicplayer.entity.MediaEntity;
import com.zy.musicplayer.eventmsg.BindDataMsg;
import com.zy.musicplayer.service.MusicPlayService;
import com.zy.musicplayer.utils.LogUtils;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

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
public class MyApplication extends Application {
    private static MyApplication instance;
    public List<MediaEntity> songsList = new ArrayList<>();
    public int songItemPos;//当前播放音乐在列表中的位置
    public NotificationManager notManager;

    public static synchronized MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //开启服务
        List<MediaEntity> query = DbManager.query(this);
        if (query.size() > 0) {
            songsList.clear();
            songsList.addAll(query);
        }

        Intent intent = new Intent(this, MusicPlayService.class);
        startService(intent);


        initOkgo();


    }

    private void initOkgo() {
        OkGo okGo = OkGo.getInstance();
        okGo.init(this);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS); //全局的读取超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);//全局的写入超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);//全局的连接超时时间
        HttpLoggingInterceptor intercepter = new HttpLoggingInterceptor("okgo");
        intercepter.setPrintLevel(HttpLoggingInterceptor.Level.BODY); //log打印全部内容
        intercepter.setColorLevel(Level.INFO);//log颜色等级
        builder.addInterceptor(intercepter);
        builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));  //使用sp保持cookie，如果cookie不过期，则一直有效
        HttpHeaders headers = new HttpHeaders();
        headers.put("appver", "1.5.0.75771");    //header不支持中文，不允许有特殊字符
        headers.put("Referer", "http://music.163.com/");

        okGo.setOkHttpClient(builder.build())
                .setCacheTime(5000) //设置缓存时间
                .setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE) //设置缓存模式
                .addCommonHeaders(headers);

    }


    public void sendNotification() {

        notManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification();
        //初始化通知
        notification.icon = R.drawable.music;
        RemoteViews contentView = new RemoteViews(getPackageName(),
                R.layout.cunstom_notification);
        notification.contentView = contentView;
        Intent intentPlay = new Intent("play");//新建意图，并设置action标记为"play"，用于接收广播时过滤意图信息
        PendingIntent pIntentPlay = PendingIntent.getBroadcast(this, 0,
                intentPlay, 0);
        contentView.setOnClickPendingIntent(R.id.notification_play, pIntentPlay);//为play控件注册事件
        Intent intent = new Intent();
        intent.setClass(this, MusicPlayActivity.class);
        PendingIntent pIntentOpen = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        contentView.setOnClickPendingIntent(R.id.ll_parent, pIntentOpen);
        Intent intentPause = new Intent("pause");
        PendingIntent pIntentPause = PendingIntent.getBroadcast(this, 0,
                intentPause, 0);
        contentView.setOnClickPendingIntent(R.id.notification_pause, pIntentPause);
        Intent intentNext = new Intent("next");
        PendingIntent pIntentNext = PendingIntent.getBroadcast(this, 0,
                intentNext, 0);
        contentView.setOnClickPendingIntent(R.id.notification_next, pIntentNext);

        Intent intentLast = new Intent("last");
        PendingIntent pIntentLast = PendingIntent.getBroadcast(this, 0,
                intentLast, 0);
        contentView.setOnClickPendingIntent(R.id.notification_before, pIntentLast);

        Intent intentCancel = new Intent("close");
        PendingIntent pIntentCancel = PendingIntent.getBroadcast(this, 0,
                intentCancel, 0);
        contentView
                .setOnClickPendingIntent(R.id.notification_close, pIntentCancel);
        notification.flags = notification.FLAG_NO_CLEAR;//设置通知点击或滑动时不被清除
        notManager.notify(1, notification);//开启通知
    }

    public void setSongsList(List<MediaEntity> songsList) {
        this.songsList = songsList;
    }
}
