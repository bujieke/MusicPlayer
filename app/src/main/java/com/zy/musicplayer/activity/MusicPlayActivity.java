package com.zy.musicplayer.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.IBinder;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zy.musicplayer.R;
import com.zy.musicplayer.base.BaseActivity;
import com.zy.musicplayer.eventmsg.BindDataMsg;
import com.zy.musicplayer.listener.CustomControllerListener;
import com.zy.musicplayer.present.MusicPlayPresent;
import com.zy.musicplayer.service.MusicPlayService;
import com.zy.musicplayer.utils.LogUtils;
import com.zy.musicplayer.view.MusicPlayView;
import com.zy.musicplayer.widget.CustomController;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import butterknife.BindView;

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
public class MusicPlayActivity extends BaseActivity implements MusicPlayView, ServiceConnection {
    @BindView(R.id.play_title)
    TextView play_title;
    @BindView(R.id.customcontroller)
    CustomController customcontroller;
    @BindView(R.id.music_iv_icon)
    ImageView icon;
    private MusicPlayPresent present;

    @Override
    public int getLayout() {
        return R.layout.acticvity_musicplay;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        initData();


    }

    private void initData() {
        Intent intent = new Intent(mContext, MusicPlayService.class);
        bindService(intent, this, BIND_AUTO_CREATE);
    }

    @Subscriber(mode = ThreadMode.MAIN)
    public void setPros(Integer pro) {
        LogUtils.LogD(pro + "-----------------");
        setPro(pro);
    }

    private void initView() {

        customcontroller.setListener(new CustomControllerListener() {
            @Override
            public void next() {
                present.next();
            }

            @Override
            public void before() {
                present.before();
            }

            @Override
            public void seek(int positon) {
                present.touchSeekBar(positon);
            }

            @Override
            public boolean playOrPause() {
                return present.playOrPause();

            }
        });

    }

    @Override
    public void setPro(int i) {
        customcontroller.setDuration(i);

    }

    @Override
    public void setControllerProMax(int duration) {
        customcontroller.setMaxTime(duration);

    }


    @Subscriber()
    private void updateUI(String msg) {
        LogUtils.LogD("updateUI:" + msg);
        if (msg.equals("update")) {
            present.initMusicData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unbindService(this);
    }

    @Override
    public void setTitle(String title) {
        play_title.setText(title);
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        MusicPlayService.MusicBinder binder = (MusicPlayService.MusicBinder) iBinder;
        MusicPlayService service = binder.getService();
        initView();
        present = new MusicPlayPresent(this, this, service);
        present.initMusicData();
        Animation operatingAnim = AnimationUtils.loadAnimation(this, R.anim.icon);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        icon.startAnimation(operatingAnim);
        present.getPro();
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }


}
