package com.zy.musicplayer.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.zy.musicplayer.constant.AppConstant;
import com.zy.musicplayer.entity.MediaEntity;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by  zy on 2017/8/9.
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
public class DbManager {
    private static MusicSqliteHelper helper;

    public static MusicSqliteHelper getIntance(Context context) {
        if (helper == null) {
            helper = new MusicSqliteHelper(context);
        }
        return helper;
    }


    public static List<MediaEntity> query(Context context) {
        List<MediaEntity> list = new ArrayList<MediaEntity>();
        SQLiteDatabase db = getIntance(context).getReadableDatabase();
        String sql = "select * FROM " + AppConstant.Datebase.TB_NAME;
        Log.e("sql", sql);
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            String string = cursor.getString(1);
            MediaEntity mediaEntity = JSON.parseObject(string, MediaEntity.class);
            list.add(mediaEntity);
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * 更新
     *
     * @param bean
     */
//    public static void update(ZDLG_bean bean) {
//        SQLiteDatabase writableDatabase = helper.getWritableDatabase();
//        StringBuffer stringBuffer = new StringBuffer();
//        String sql = "UPDATE " + DbConstant.TABLE_NAME + " SET ";
//        int id = bean.getId();
//        if (bean.getDescribe1() != null) {
//            stringBuffer.append("'describe1' = '" + bean.getDescribe1() + "'");
//        }
//
//        if (bean.getDescribe2() != null) {
//            stringBuffer.append(",'describe2' = '" + bean.getDescribe2() + "'");
//        }
//        if (bean.getDescribe3() != null) {
//            stringBuffer.append(",'describe3' = '" + bean.getDescribe3() + "'");
//        }
//        if (bean.getDescribe4() != null) {
//            stringBuffer.append(",'describe4' = '" + bean.getDescribe4() + "'");
//        }
//        writableDatabase.execSQL(sql + stringBuffer.toString() + " where _id = " + id);
//        writableDatabase.close();

//    }

    /**
     * 插入
     *
     * @param bean
     */
    public static void insert(MediaEntity bean) {
        String str = JSON.toJSONString(bean);

        SQLiteDatabase writableDatabase = helper.getWritableDatabase();
        String sql = "insert into " + AppConstant.Datebase.TB_NAME + "(" +
                AppConstant.Datebase.URL + "," + AppConstant.Datebase.DATA_JSON + ")" + "values('" + bean.path + "','" + str +
                "')";
        Log.e("sql :", sql);
        try {
            writableDatabase.execSQL(sql);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writableDatabase.close();
        }

    }
}
