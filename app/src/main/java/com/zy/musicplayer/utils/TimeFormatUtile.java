package com.zy.musicplayer.utils;

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
public class TimeFormatUtile {
    public static String format(int time) {
        time = time / 1000;  //转化为秒

        int minute = time / 60;
        int hour = minute / 60;
        int secont = time % 60;
        StringBuffer sb = new StringBuffer();
        if (hour > 0) {
            sb.append(hour + " 小时");
        }
        if (minute > 0) {
            sb.append(minute + " 分");
        }
        if (secont > 0) {
            sb.append(secont + " 秒");
        }

        return sb.toString();
    }


}
