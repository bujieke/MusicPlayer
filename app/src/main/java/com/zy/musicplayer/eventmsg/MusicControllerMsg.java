package com.zy.musicplayer.eventmsg;

import com.zy.musicplayer.entity.MediaEntity;

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
public class MusicControllerMsg {
    public MediaEntity getEntity() {
        return entity;
    }

    public int getActicon() {
        return acticon;
    }

    public int getPostion() {
        return postion;
    }

    private MediaEntity entity; //歌曲信息
    private int acticon; //动作
    private int postion;  //跳转位置

    public MusicControllerMsg(MediaEntity entity, int acticon, int postion) {
        this.entity = entity;
        this.acticon = acticon;
        this.postion = postion;
    }
}
