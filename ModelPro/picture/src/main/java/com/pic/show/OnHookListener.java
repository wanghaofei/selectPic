package com.pic.show;

import java.util.List;

/**
 * Created by wanghaofei on 17/11/13.
 * @author wanghaofei
 * @date 2017/11/13
 */

public interface OnHookListener {


    void onHook(List<String> clayList);

    /**
     * 异常抛出
     * @param e
     */
    void onError(Throwable e);

    /**
     * 取消操作
     */
    void onCancel();

}
