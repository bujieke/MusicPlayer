package com.zy.musicplayer.ui;

import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zy.musicplayer.R;


/**
 * Created by  zy on 2017/5/31.
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
public class ToolbarX {
    private Toolbar mToolbar;
    private AppCompatActivity mActivity;
    private ActionBar mActionBar;
    private final TextView cententTitle;
    private boolean ActionBarTitleIsShow = true;//是使用自定义居中的title 还是actionbar的title  居左
    private boolean RightImageIsShow = false; //右上角是否显示
    private RelativeLayout mRl_customView;


    public ToolbarX(Toolbar mToolbar, final AppCompatActivity mApp) {
        this.mToolbar = mToolbar;
        this.mActivity = mApp;
        mActivity.setSupportActionBar(mToolbar);
        mActionBar = mActivity.getSupportActionBar();

        mActionBar.setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.finish();
            }
        });

        cententTitle = (TextView) mToolbar.findViewById(R.id.toolbar_centertitle);
        TextPaint tp = cententTitle.getPaint(); //加粗
        tp.setFakeBoldText(true);
        mRl_customView = (RelativeLayout) mToolbar.findViewById(R.id.toolbar_customView);

    }

    public ToolbarX setTitle(String title) {
        mActionBar.setDisplayShowTitleEnabled(ActionBarTitleIsShow);
        if (ActionBarTitleIsShow) {
            cententTitle.setVisibility(View.GONE);
            mActionBar.setTitle(title);
        } else {
            cententTitle.setText(title);

        }

        return this;
    }

    public ToolbarX setNavigationIcon(Drawable drawable) {
        mToolbar.setNavigationIcon(drawable);
        return this;
    }

    public ToolbarX setTitle(int titleId) {
        mActionBar.setDisplayShowTitleEnabled(ActionBarTitleIsShow);
        if (ActionBarTitleIsShow) {
            cententTitle.setVisibility(View.GONE);
            mActionBar.setTitle(titleId);
        } else {
            cententTitle.setText(mActivity.getString(titleId));
        }
        return this;
    }


    public ToolbarX setNavigationOnClickListener(View.OnClickListener listener) {
        mToolbar.setNavigationOnClickListener(listener);
        return this;
    }

    public ToolbarX setNavigationIcon(int resId) {
        mToolbar.setNavigationIcon(resId);
        return this;
    }

    public ToolbarX setDisplayHomeAsUpEnabled(boolean show) {
        mActionBar.setDisplayHomeAsUpEnabled(show);
        return this;
    }

    public ToolbarX setDisplayShowTitleEnabled(boolean show) {
        ActionBarTitleIsShow = show;
        return this;
    }

    public ToolbarX setCustomView(View view) {

        mRl_customView.removeAllViews();
        mRl_customView.addView(view);
        return this;
    }

    public ToolbarX hide() {
        mActionBar.hide();
        return this;
    }
}
