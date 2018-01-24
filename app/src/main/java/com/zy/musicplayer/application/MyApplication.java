package com.zy.musicplayer.application;

import android.app.Application;
import android.content.Intent;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.zy.musicplayer.db.DbManager;
import com.zy.musicplayer.entity.MediaEntity;
import com.zy.musicplayer.eventmsg.BindDataMsg;
import com.zy.musicplayer.service.MusicPlayService;

import org.simple.eventbus.EventBus;

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
    @Override
    public void onCreate() {
        super.onCreate();
        //开启服务

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
}
