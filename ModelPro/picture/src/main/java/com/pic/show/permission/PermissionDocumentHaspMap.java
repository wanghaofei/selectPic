package com.pic.show.permission;

import android.Manifest;
import android.util.SparseArray;

/**
 * Created by wanghaofei on 17/11/14.
 */

public class PermissionDocumentHaspMap {
    /**
     * 权限列表
     */
    private static SparseArray<String> arrayList = new SparseArray<>();

    static {
        arrayList.put(0, Manifest.permission.ACCESS_FINE_LOCATION);
        arrayList.put(1, Manifest.permission.ACCESS_COARSE_LOCATION);
        arrayList.put(2, Manifest.permission.CALL_PHONE);
        arrayList.put(3, Manifest.permission.CAMERA);
        arrayList.put(4, Manifest.permission.RECORD_AUDIO);
        arrayList.put(5, Manifest.permission.READ_CONTACTS);
        arrayList.put(6, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * 获取对应权限的文案
     *
     * @param permission
     * @return String[0] title, String[1] message
     */
    public static String[] getLinkedDocument(String permission) {
        String[] documents = new String[2];
        switch (arrayList.indexOfValue(permission)) {
            case 0:
            case 1:
                documents[0] = "请求定位权限";
                documents[1] = "我们需要定位权限";
                break;
            case 2:
                documents[0] = "获取拨打电话";
                documents[1] = "我们需要获取拨打电话";
                break;
            case 3:
                documents[0] = "获取相机";
                documents[1] = "我们需要获取相机权限";
                break;
            case 4:
                documents[0] = "获取麦克风";
                documents[1] = "我们需要获取麦克风权限";
                break;
            case 5:
                documents[0] = "获取联系人";
                documents[1] = "我们需要获取联系人信息";
                break;
            case 6:
                documents[0] = "获取存储空间";
                documents[1] = "我们需要获取存储空间";
                break;
            default:
                documents[0] = "获取权限";
                documents[1] = "我们需要获取权限";
                break;
        }
        return documents;
    }
}
