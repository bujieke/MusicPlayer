package com.zy.musicplayer.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zy.musicplayer.constant.AppConstant;
import com.zy.musicplayer.utils.LogUtils;

/**
 * Created by  zy on 2017/12/15.
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
public class MusicSqliteHelper extends SQLiteOpenHelper {


    public MusicSqliteHelper(Context context) {
        super(context, AppConstant.Datebase.DB_NAME, null, AppConstant.Datebase.DB_VERSON);
    }

    public MusicSqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table " + AppConstant.Datebase.TB_NAME +
                " (" + AppConstant.Datebase.URL + " TEXT PRIMARY KEY NOT NULL,"
                + AppConstant.Datebase.DATA_JSON + " TEXT NOT NULL)";
        LogUtils.LogD("sql :" + sql);
        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
