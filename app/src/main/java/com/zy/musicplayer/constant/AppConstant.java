package com.zy.musicplayer.constant;

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
public class AppConstant {


    public class PlayerMsg {
        public static final int PLAY_MSG = 0;
        public static final int PAUSE_MSG = 1;
        public static final int STOP_MSG = 2;
        public static final int REPLAY_MSG = 3;
    }

    public class PlayBackMode {

        public static final String LISTPLAY = "list"; //列表
        public static final String SINGLEPLAY = "single"; //单曲
        public static final String RANDOMPLAY = "random";  //随机


    }

    public class Datebase {

        public static final String DB_NAME = "music_db"; //列表
        public static final int DB_VERSON = 1;
        public static final String TB_NAME = "playlist";
        public static final String URL = "url";

        public static final String DATA_JSON= "Json";
    }

}

