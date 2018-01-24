package com.zy.musicplayer.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by  zy on 2017/8/29.
 * 佛曰:
 * 写字楼里写字间，写字间里程序员；
 * 程序人员写程序，又拿程序换酒钱。
 * 酒醒只在网上坐，酒醉还来网下眠；
 * 酒醉酒醒日复日，网上网下年复年。
 * 但愿老死电脑间，不愿鞠躬老板前；
 * 奔驰宝马贵者趣，公交自行程序员。
 * 别人笑我忒疯癫，我笑自己命太贱；
 * 不见满街漂亮妹，哪个归得程序员？
 * 适配6.0以上的权限申请类
 */

public class PermissionUtils {

    //请求吗
    public static final int REQ_PERMISSION_CODE = 0x12;

    //检测是否需动态申请权限
    public static boolean checkPermission(Activity activity, String[] permissions) {
        //如果是6.0以上
        if (Build.VERSION.SDK_INT >= 23) {
            //需要的权限如果没有就不需要动态获取了
            if (permissions == null || permissions.length <= 0)
                return true;
            // 检查权限是否以授权
            List<String> noOkPermission = new ArrayList<>();
            for (String permission : permissions) {
                if (activity.checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED) {
                    noOkPermission.add(permission);
                }
            }
            if (noOkPermission.size() <= 0) {
                //没有需要申请的权限
                return true;
            }
            //需要申请权限
            activity.requestPermissions(noOkPermission.toArray(new String[noOkPermission.size()]), REQ_PERMISSION_CODE);
            return false;
        }


        return true;
    }


    /**
     * 处理权限申请的结果，返回结构化的数据
     *
     * @param requestCode  请求码
     * @param permissions  被请求的权限
     * @param grantResults 请求结果
     * @param listener     监听
     */
    public static void onRequestPermissionsResult(int requestCode,
                                                  @NonNull String[] permissions,
                                                  @NonNull int[] grantResults,
                                                  OnPermissionHandleOverListener listener) {
        if (requestCode != REQ_PERMISSION_CODE)
            return;
        Map<String, Integer> result = new HashMap<>();
        boolean isHavePermissionNotOk = false;
        for (int i = 0; i < Math.min(permissions.length, grantResults.length); i++) {
            result.put(permissions[i], grantResults[i]);
            //有权限没有同意
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                isHavePermissionNotOk = true;
            }
        }
        //如果权限全部同意，继续执行
        if (listener != null)
            listener.onHandleOver(!isHavePermissionNotOk, result);
    }

    public interface OnPermissionHandleOverListener {
        void onHandleOver(boolean isOkExactly, Map<String, Integer> result);
    }


}
