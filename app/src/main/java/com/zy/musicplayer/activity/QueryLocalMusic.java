package com.zy.musicplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zy.musicplayer.R;
import com.zy.musicplayer.adapter.QueryMusicAdapter;
import com.zy.musicplayer.application.MyApplication;
import com.zy.musicplayer.base.BaseActivity;
import com.zy.musicplayer.base.BaseAdapter;
import com.zy.musicplayer.db.DbManager;
import com.zy.musicplayer.entity.MediaEntity;
import com.zy.musicplayer.eventmsg.BindDataMsg;
import com.zy.musicplayer.eventmsg.ControllerMsg;
import com.zy.musicplayer.eventmsg.LocalQueryMusic;
import com.zy.musicplayer.ui.RecyclerViewTool;
import com.zy.musicplayer.utils.LogUtils;
import com.zy.musicplayer.utils.QueryFileUtils;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by  zy on 2017/12/11.
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
public class QueryLocalMusic extends BaseActivity {

    @BindView(R.id.query_rv_list)
    RecyclerView queryRvList;
    @BindView(R.id.query_tv_btn)
    TextView queryTvBtn;
    private List<MediaEntity> mediaList;
    private QueryMusicAdapter queryMusicAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        mediaList = new ArrayList<MediaEntity>();
        RecyclerViewTool recyclerViewTool = new RecyclerViewTool(queryRvList, mContext);
        recyclerViewTool.initRecyle(RecyclerViewTool.RVTYPE_GENERAL);
        queryMusicAdapter = new QueryMusicAdapter(mediaList, R.layout.adapter_querymusic);
        queryRvList.setAdapter(queryMusicAdapter);
        if (mediaList.size() == 0) {
            queryTvBtn.setVisibility(View.VISIBLE);
            queryRvList.setVisibility(View.GONE);
        } else {
            queryTvBtn.setVisibility(View.GONE);
            queryRvList.setVisibility(View.VISIBLE);
        }
        queryMusicAdapter.setItemClickLitener(new BaseAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {

                EventBus.getDefault().post(new ControllerMsg("play", position), "controller");
                opeanActivity(MusicPlayActivity.class);
                DbManager.insert(mediaList.get(position));
            }
        });
    }

    @Subscriber(tag = "query")
    private void updata(LocalQueryMusic music) {
        if (music.getStatu() == 0) {
            mediaList.clear();
            mediaList.addAll(music.getMediaList());
            if (mediaList.size() == 0) {
                queryTvBtn.setVisibility(View.VISIBLE);
                queryRvList.setVisibility(View.GONE);
                queryTvBtn.setText("未找到音乐文件");
            } else {
                queryTvBtn.setVisibility(View.GONE);
                queryRvList.setVisibility(View.VISIBLE);
                queryMusicAdapter.notifyDataSetChanged();
                MyApplication.getInstance().setSongsList(mediaList);
            }
        }

    }

    @Override
    public int getLayout() {
        return R.layout.activity_querylocalmusic;
    }


    @OnClick(R.id.query_tv_btn)
    void query() {
        LogUtils.LogD("query");
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<MediaEntity> allMediaList = QueryFileUtils.getAllMediaList(mContext, "");
                LocalQueryMusic localQueryMusic = new LocalQueryMusic();
                if (allMediaList.size() > 0) {
                    localQueryMusic.setMediaList(allMediaList);
                    localQueryMusic.setMsg("查询成功，本地共有歌曲" + allMediaList.size() + "首");
                    localQueryMusic.setStatu(0);
                } else {
                    localQueryMusic.setMediaList(allMediaList);
                    localQueryMusic.setMsg("查询完成，本地未找到歌曲文件");
                    localQueryMusic.setStatu(1);
                }
                EventBus.getDefault().post(localQueryMusic, "query");
            }

        }).start();
    }
}

