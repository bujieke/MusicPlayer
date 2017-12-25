package com.zy.musicplayer.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.zy.musicplayer.entity.MediaEntity;

import java.util.ArrayList;
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
public class QueryFileUtils {

    public static List<MediaEntity> getAllMediaList(Context context, String selection) {
        Cursor cursor = null;
        List<MediaEntity> list = new ArrayList<MediaEntity>();
        try {


            cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, new String[]{

                    MediaStore.Audio.Media._ID,
                    MediaStore.Audio.Media.TITLE,
                    MediaStore.Audio.Media.DISPLAY_NAME,
                    MediaStore.Audio.Media.DURATION,
                    MediaStore.Audio.Media.ARTIST,
                    MediaStore.Audio.Media.DATA,
                    MediaStore.Audio.Media.SIZE

            }, selection, null, MediaStore.Audio.Media.DATE_ADDED + " DESC");
            if (cursor == null) {
                LogUtils.LogD("getAllMediaList cursor is null");
                return list;
            }
            int count = cursor.getCount();
            if (count <= 0) {
                LogUtils.LogD(" getAllMediaList cursor  count is 0");
                return list;
            }
            MediaEntity mediaEntity = null;
            while (cursor.moveToNext()) {
                mediaEntity = new MediaEntity();
                mediaEntity.id = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
                mediaEntity.title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                mediaEntity.display_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                mediaEntity.duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                mediaEntity.size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
//            mediaEntity.durationStr = longToStrTime(mediaEntity.duration);
                if (!checkIsMusic(mediaEntity.duration, mediaEntity.size)) {
                    continue;
                }
                mediaEntity.artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                mediaEntity.path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                list.add(mediaEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }


    /**
     * 根据时间和大小，来判断所筛选的media 是否为音乐文件，具体规则为筛选小于30秒和1m一下的
     */
    public static boolean checkIsMusic(int time, long size) {
        if (time <= 0 || size <= 0) {
            return false;
        }

        time /= 1000;
        int minute = time / 60;
//  int hour = minute / 60;
        int second = time % 60;
        minute %= 60;
        if (minute <= 0 && second <= 30) {
            return false;
        }
        if (size <= 1024 * 1024) {
            return false;
        }
        return true;
    }
}
