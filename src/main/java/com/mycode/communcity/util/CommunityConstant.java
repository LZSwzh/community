package com.mycode.communcity.util;

public interface CommunityConstant {
    //成功
    int ACTIVATION_SUCCESS = 0;
    //重复
    int ACTIVATION_REPEAT = 1;
    //失败
    int ACTIVATION_FAILURE = 2;
    /**
     * 默认超时时间
     */
    int DEFAULT_EXPIRED_SECOND = 3600 * 12;//12h
    /**
     * 记住状态下超时时间
     */
    int REMEBER_EXPIRED_SECOND = 3600 * 24 * 100;

}
