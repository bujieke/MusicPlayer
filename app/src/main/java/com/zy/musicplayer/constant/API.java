package com.zy.musicplayer.constant;

/**
 * Created by  zy on 2018/1/24
 */
public class API {
    //网易云音乐
    public static String IP = "http://music.163.com/api/";
    /**
     * 搜索
     * 请求方式 post
     * 参数
     * s -》搜索内容
     * offset -》偏移量
     * limit -》获取的数量
     * type- 》类型（歌曲 1 专辑 10  歌手 100 歌单 1000 用户 1002  mv 1004  歌词 1006  主播电台 1009）
     */
    public static String SEARCH = IP + "search/pc";
    /**
     * get
     * id：歌曲ID
     * ids：不知道干什么用的，用[]括起来的歌曲ID
     */
    public static String SongDetail = IP + "song/detail/";

}
