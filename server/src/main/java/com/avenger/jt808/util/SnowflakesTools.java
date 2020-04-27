package com.avenger.jt808.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Random;

/**
 * Created by jone.wang on 2019/10/20.
 * Description: 雪花算法
 */
@SuppressWarnings({"FieldCanBeLocal", "WeakerAccess"})
public class SnowflakesTools {
    //开始时间截 (2015-01-01)
    private final long twepoch = 1489111610226L;
    //机器ID所占位置
    private static final long workerIdBits = 5L;
    //数据标识所占位数
    private final long datacenterIdBits = 5L;
    //支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
    private final static long maxWorkerId = ~(-1L << workerIdBits);
    //支持的最大数据标识id，结果是31
    private final long maxDatacenterId = ~(-1L << datacenterIdBits);
    //序列在id中占的位数
    private final long sequenceBits = 12L;
    //机器ID向左移12位
    private final long workerIdShift = sequenceBits;
    //数据标识id向左移17位(12+5)
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    //时间截向左移22位(5+5+12)
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    //生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
    private final long sequenceMask = ~(-1L << sequenceBits);

    //工作机器ID(0~31)
    private long workerId;

    //数据中心ID(0~31)
    private long datacenterId;

    //毫秒内序列(0~4095)
    private long sequence = 0L;

    //上次生成ID的时间截
    private long lastTimestamp = -1L;

    private volatile static SnowflakesTools snowflake;

    private static final Object lock = new Object();

    public SnowflakesTools(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format(
                    "worker Id can't be greater than %d or less than 0",
                    maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format(
                    "datacenter Id can't be greater than %d or less than 0",
                    maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return id
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format(
                    "Clock moved backwards.  Refusing to generate id for %d milliseconds",
                    lastTimestamp - timestamp));
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {//时间戳改变，毫秒内序列重置
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - twepoch) << timestampLeftShift)
                | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift)
                | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 时间戳
     * @return long
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return long 时间戳
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }

    public static SnowflakesTools getInstanceSnowflake() {
        if (snowflake == null) {
            synchronized (lock) {
                if (Objects.nonNull(snowflake)) {
                    return snowflake;
                }
                long workerId;
                long dataCenterId = getRandom();
                try {
                    //第一次使用获取mac地址的
                    workerId = getWorkerId() % 31;
                } catch (Exception e) {
                    workerId = getRandom() % 31;
                }
                snowflake = new SnowflakesTools(workerId, dataCenterId);
            }
        }
        return snowflake;
    }

    /**
     * 生成1-31之间的随机数
     *
     * @return random
     */
    private static long getRandom() {
        int max = (int) (maxWorkerId);
        int min = 1;
        Random random = new Random();
        return (long) (random.nextInt(max - min) + min);
    }

    private static long getWorkerId() throws SocketException, UnknownHostException, NullPointerException {
        @SuppressWarnings("unused")
        InetAddress ip = InetAddress.getLocalHost();

        NetworkInterface network = null;
        Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
        while (en.hasMoreElements()) {
            NetworkInterface nint = en.nextElement();
            if (!nint.isLoopback() && nint.getHardwareAddress() != null) {
                network = nint;
                break;
            }
        }

        @SuppressWarnings("ConstantConditions")
        byte[] mac = network.getHardwareAddress();

        Random rnd = new Random();
        byte rndByte = (byte) (rnd.nextInt() & 0x000000FF);

        // take the last byte of the MAC address and a random byte as worker ID
        return ((0x000000FF & (long) mac[mac.length - 1]) | (0x0000FF00 & (((long) rndByte) << 8))) >> 6;
    }

}
