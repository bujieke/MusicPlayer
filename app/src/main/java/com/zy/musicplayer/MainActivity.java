package com.zy.musicplayer;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.zy.musicplayer.activity.MusicPlayActivity;
import com.zy.musicplayer.activity.QueryLocalMusic;
import com.zy.musicplayer.adapter.QueryMusicAdapter;
import com.zy.musicplayer.base.BaseAdapter;
import com.zy.musicplayer.constant.API;
import com.zy.musicplayer.constant.AppConstant;
import com.zy.musicplayer.db.DbManager;
import com.zy.musicplayer.entity.MediaEntity;
import com.zy.musicplayer.eventmsg.ControllerMsg;
import com.zy.musicplayer.ui.RecyclerViewTool;
import com.zy.musicplayer.utils.LogUtils;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @BindView(R.id.main_list)
    RecyclerView mainList;
    @BindView(R.id.main_btn_startsearch)
    ImageButton mainBtnStartsearch;
    @BindView(R.id.main_ed_search)
    EditText mainEdSearch;
    private QueryMusicAdapter queryMusicAdapter;
    private List<MediaEntity> mediaList = new ArrayList<MediaEntity>();
    private ServiceConnection mConneciton;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;
        initView();
        getData();
        initListener();

    }

    private void getData() {
        List<MediaEntity> query = DbManager.query(this);
        if (query.size() > 0) {
            mediaList.addAll(query);
            queryMusicAdapter.notifyDataSetChanged();
        } else {


        }
    }

    private void initListener() {

        queryMusicAdapter.setItemClickLitener(new BaseAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                EventBus.getDefault().post(new ControllerMsg("play", position), "controller");
                Intent intent = new Intent(context, MusicPlayActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        RecyclerViewTool recyclerViewTool = new RecyclerViewTool(mainList, this);
        recyclerViewTool.initRecyle(RecyclerViewTool.RVTYPE_GENERAL);
        queryMusicAdapter = new QueryMusicAdapter(mediaList, R.layout.adapter_querymusic);
        mainList.setAdapter(queryMusicAdapter);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_randomplay) {
            EventBus.getDefault().post(AppConstant.PlayBackMode.RANDOMPLAY, "playmode");
        } else if (id == R.id.nav_listplay) {
            EventBus.getDefault().post(AppConstant.PlayBackMode.LISTPLAY, "playmode");
        } else if (id == R.id.nav_singleplay) {
            EventBus.getDefault().post(AppConstant.PlayBackMode.SINGLEPLAY, "playmode");
        } else if (id == R.id.nav_querymusic) {
            //进入本地音乐界面
            Intent intent = new Intent(this, QueryLocalMusic.class);
            intent.putExtra("title", "本地音乐");
            startActivity(intent);
        } else if (id == R.id.nav_share) {
            //分享功能

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.anim_in_right_left, R.anim.anim_out_right_left); //切换动画
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_in_left_rigth, R.anim.anim_out_left_rigth);//切换动画
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        overridePendingTransition(R.anim.anim_in_right_left, R.anim.anim_out_right_left); //切换动画
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.main_btn_startsearch)
    void search() {

        String queryinfo = mainEdSearch.getText().toString().trim();
        if (TextUtils.isEmpty(queryinfo)) {
            Toast.makeText(context, "请输入要查询的内容", Toast.LENGTH_SHORT).show();
        } else {
            getNetEasyMusicData(queryinfo);
        }


    }

    public void getNetEasyMusicData(String queryinfo) {
        HttpParams params = new HttpParams();
        params.put("s", queryinfo);
        params.put("offset", 1);
        params.put("limit", 3);
        params.put("type", 1);
        OkGo.<String>post(API.SEARCH).params(params).tag(this).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.LogD(response.body().toString());
            }
        });
    }
}
