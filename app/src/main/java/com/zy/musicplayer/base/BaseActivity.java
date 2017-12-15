package com.zy.musicplayer.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.zy.musicplayer.R;
import com.zy.musicplayer.ui.ToolbarX;

import org.simple.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * Created by  zy on 2017/6/1.
 * //                            _ooOoo_
 * //                           o8888888o
 * //                           88" . "88
 * //                           (| -_- |)
 * //                            O\ = /O
 * //                        ____/`---'\____
 * //                      .   ' \\| |// `.
 * //                       / \\||| : |||// \
 * //                     / _||||| -:- |||||- \
 * //                       | | \\\ - /// | |
 * //                     | \_| ''\---/'' | |
 * //                      \ .-\__ `-` ___/-. /
 * //                   ___`. .' /--.--\ `. . __
 * //                ."" '< `.___\_<|>_/___.' >'"".
 * //               | | : `- \`.;`\ _ /`;.`/ - ` : | |
 * //                 \ \ `-. \_ __\ /__ _/ .-` / /
 * //         ======`-.____`-.___\_____/___.-`____.-'======
 * //                            `=---='
 * //
 * //         .............................................
 * //                  佛祖保佑             永无BUG
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
public abstract class BaseActivity extends AppCompatActivity {
    private RelativeLayout mRlContent;
    private Toolbar mToolbar;
    private ToolbarX mtoolbarX;
    public Context mContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setTheme(R.style.baseActivity);
        setContentView(R.layout.base_layout);
        initRootView();
        View view = getLayoutInflater().inflate(getLayout(), mRlContent, false);
        mRlContent.addView(view);
        mtoolbarX = new ToolbarX(mToolbar, this);
        setTitle();
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    protected void setTitle() {
        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra("title");
            if (!TextUtils.isEmpty(title)) {
                mtoolbarX.setTitle(title);
            }
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.anim_in_right_left, R.anim.anim_out_right_left); //切换动画
    }

    /**
     * 获取布局
     *
     * @return
     */
    public abstract int getLayout();

    /**
     * 获取页面的标题
     *
     * @return
     */


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        overridePendingTransition(R.anim.anim_in_right_left, R.anim.anim_out_right_left); //切换动画
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_in_left_rigth, R.anim.anim_out_left_rigth);//切换动画
    }

    private void initRootView() {
        mRlContent = (RelativeLayout) findViewById(R.id.rl_Content);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

    }

    public ToolbarX getToolbar() {
        if (null == mtoolbarX) {
            mtoolbarX = new ToolbarX(mToolbar, this);
        }
        return mtoolbarX;
    }

    /**
     * 不带参数
     *
     * @param clazz
     */
    public void opeanActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    public void opeanActivity(Class clazz, String title) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra("title", title);
        startActivity(intent);
    }


    public void opeanActivityForResult(Class clazz, String title, int QueryCode) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra("title", title);
        startActivityForResult(intent, QueryCode);
    }

    /**
     * 带Intent
     */
    public void opeanActivity(Intent intent) {
        startActivity(intent);
    }


    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
