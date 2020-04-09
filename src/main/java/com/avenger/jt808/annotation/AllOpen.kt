package com.avenger.jt808.annotation

/**
 * Created by jg.wang on 2020/4/8.
 * Description:
 */

annotation class AllOpen

annotation class NoArg

@AllOpen
@NoArg
annotation class AllOpenAndNoArg


annotation class ReadingMessageType(val type: Short)


annotation class WritingMessageType(val type: Short)