package com.zy.musicplayer.adapter;

import android.util.Log;
import android.widget.TextView;

import com.zy.musicplayer.R;
import com.zy.musicplayer.base.BaseAdapter;
import com.zy.musicplayer.base.BaseViewHolder;
import com.zy.musicplayer.entity.MediaEntity;
import com.zy.musicplayer.utils.TimeFormatUtile;

import java.util.List;

/**
 * Created by  zy on 2017/12/12.
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
public class QueryMusicAdapter extends BaseAdapter<MediaEntity> {

    public QueryMusicAdapter(List<MediaEntity> list, int LayoutId) {
        super(list, LayoutId);
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
        MediaEntity mediaEntity = mList.get(position);
        TextView name = holder.getView(R.id.music_tv_name);
        TextView title = holder.getView(R.id.music_tv_title);
        TextView time = holder.getView(R.id.music_tv_time);
        Log.e("sss", mediaEntity.toString());
        name.setText(mediaEntity.display_name + "");
        title.setText(mediaEntity.title + "");
        time.setText(TimeFormatUtile.format(mediaEntity.duration));
//        {id=0, title='半壶纱', display_name='刘珂矣 - 半壶纱.mp3', path='/storage/emulated/0/netease/cloudmusic/Music/刘珂矣 - 半壶纱.mp3', duration=222014, albums='null', artist='刘珂矣', singer='null', size=8982184}


    }
}
