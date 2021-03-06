package com.zy.musicplayer.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zy.musicplayer.R;
import com.zy.musicplayer.eventmsg.ControllerMsg;
import com.zy.musicplayer.listener.CustomControllerListener;
import com.zy.musicplayer.utils.TimeFormatUtile;

import org.simple.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by  zy on 2017/12/11.
 * 佛曰:
 * 写字楼里写字间，写字间里程序员；
 * 程序人员写程序，又拿程序换酒钱。
 * 酒醒只在网上坐，酒醉还来网下眠；
 * 酒醉酒醒日复日，网上网下年复年。
 * 但愿老死电脑间，不愿鞠躬老板前；
 * 奔驰宝马贵者趣，公交自行程序员。
 * 别人笑我忒疯癫，我笑自己命太贱；
 * 不见满街漂亮妹，哪个归得程序员？
 */

/**
 * 自定义一个播放控制器
 */
public class CustomController extends LinearLayout {
    private CustomControllerListener listener;
    @BindView(R.id.starttime)
    TextView starttime;
    @BindView(R.id.endtime)
    TextView endtime;
    @BindView(R.id.controller_iv_play)
    ImageView controllerIvPlay;
    @BindView(R.id.seek)
    SeekBar seek;
    @BindView(R.id.controller_seeklayou)
    RelativeLayout controllerSeeklayou;

    @BindView(R.id.controller_before)
    LinearLayout controllerBefore;
    @BindView(R.id.controller_play)
    LinearLayout controllerPlay;
    @BindView(R.id.controller_next)
    LinearLayout controllerNext;

    @BindView(R.id.controller_tv_play)
    TextView controllerTvPlay;
    private Context mContext;


    public CustomController(Context context) {
        this(context, null);
    }

    public CustomController(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomController(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    public void setListener(CustomControllerListener listener) {
        this.listener = listener;
    }

    /**
     * 添加布局
     */
    private void initView() {
        View view = inflate(mContext, R.layout.customcontroller, null);
        ButterKnife.bind(this, view);

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                listener.seek(progress);
            }
        });
        addView(view, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @OnClick(R.id.controller_play)
    void play() {
        boolean b = listener.playOrPause();
        if (b) {
            EventBus.getDefault().post(new ControllerMsg("pause", 0), "controller");
            Toast.makeText(mContext, "暂停", Toast.LENGTH_SHORT).show();
            controllerIvPlay.setImageResource(R.drawable.play);
            controllerTvPlay.setText("播放");
        } else {
            EventBus.getDefault().post(new ControllerMsg("play", 0), "controller");
            Toast.makeText(mContext, "播放", Toast.LENGTH_SHORT).show();
            controllerIvPlay.setImageResource(R.drawable.stop);
            controllerTvPlay.setText("暂停");
        }


    }


    @OnClick(R.id.controller_before)
    void before() {
        listener.before();
    }

    @OnClick(R.id.controller_next)
    void next() {
        listener.next();
    }

    public void setMaxTime(int time) {
        seek.setMax(time);
        String format = TimeFormatUtile.format(time);
        endtime.setText(format);
    }

    public void setDuration(int time) {
        seek.setProgress(time);
        String format = TimeFormatUtile.format(time);
        starttime.setText(format);
    }
}
