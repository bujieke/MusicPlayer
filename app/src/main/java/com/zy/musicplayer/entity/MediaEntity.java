package com.zy.musicplayer.entity;

import java.io.Serializable;

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
public class MediaEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    public int id; //id标识
    public String title; // 显示名称
    public String display_name; // 文件名称
    public String path; // 音乐文件的路径
    public int duration; // 媒体播放总时间
    public String albums; // 专辑
    public String artist; // 艺术家
    public String singer; //歌手
    public long size;


    @Override
    public String toString() {
        return "MediaEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", display_name='" + display_name + '\'' +
                ", path='" + path + '\'' +
                ", duration=" + duration +
                ", albums='" + albums + '\'' +
                ", artist='" + artist + '\'' +
                ", singer='" + singer + '\'' +
                ", size=" + size +
                '}';
    }
}
