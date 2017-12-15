package com.zy.musicplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.zy.musicplayer.activity.QueryLocalMusic;
import com.zy.musicplayer.adapter.QueryMusicAdapter;
import com.zy.musicplayer.constant.AppConstant;
import com.zy.musicplayer.db.DbManager;
import com.zy.musicplayer.entity.MediaEntity;
import com.zy.musicplayer.listener.CustomControllerListener;
import com.zy.musicplayer.service.MusicPlayService;
import com.zy.musicplayer.ui.RecyclerViewTool;
import com.zy.musicplayer.widget.CustomController;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CustomControllerListener {

    @BindView(R.id.main_controller)
    CustomController mainController;
    @BindView(R.id.main_list)
    RecyclerView mainList;
    private QueryMusicAdapter queryMusicAdapter;
    private List<MediaEntity> mediaList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        updateList();
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
        mediaList = new ArrayList<MediaEntity>();
        RecyclerViewTool recyclerViewTool = new RecyclerViewTool(mainList, this);
        recyclerViewTool.initRecyle(RecyclerViewTool.RVTYPE_GENERAL);
        queryMusicAdapter = new QueryMusicAdapter(mediaList, R.layout.adapter_querymusic);
        mainList.setAdapter(queryMusicAdapter);
        mainController.setListener(this);
        DbManager.getIntance(this);
        startService(new Intent(this, MusicPlayService.class)); //开启服务
    }

    private void updateList() {
        List<MediaEntity> query = DbManager.query(this);
        if (query.size() > 0) {
            mediaList.addAll(query);
            queryMusicAdapter.notifyDataSetChanged();
        }
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

        } else if (id == R.id.nav_send) {
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


    @Override
    public void next() {


    }

    @Override
    public void before() {


    }
}
