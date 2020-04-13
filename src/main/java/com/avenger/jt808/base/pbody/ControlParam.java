package com.avenger.jt808.base.pbody;

import lombok.Data;

/**
 * Created by jg.wang on 2020/4/10.
 * Description:
 */
@Data
public class ControlParam {
    /**
     * 0:切换到指定监管平台服务器，连接到该服务器后即进入应急状态，此状态下仅有下发控制指令的监管平台可发送包括短信在内的控制指 令;
     * 1:切换回原缺省监控平台服务器，并恢复正常状态。
     */
    private byte conType;

    /**
     * 一般为服务器 APN，无线通信拨号访问点，若网络制式为 CDMA，则该值 为 PPP 连接拨号号码
     */
    private String apn;
    /**
     * 服务器无线通信拨号用户名
     */
    private String username;

    /**
     * 服务器无线通信拨号密码
     */
    private String password;

    private String ip;

    private short tcpPort;

    private short udpPort;

    /**
     * 终端制造商编码
     */
    private String manufId;

    /**
     * 监管平台鉴权码
     */
    private String supervisionPlatAuthCode;

    /**
     * 硬件版本
     */
    private String hardVersion;

    /**
     * 固件版本
     */
    private String frameworkVersion;

    /**
     * 完整url
     */
    private String url;

    /**
     * 连接到指定服务 器时限
     */
    private short connTime;
}
