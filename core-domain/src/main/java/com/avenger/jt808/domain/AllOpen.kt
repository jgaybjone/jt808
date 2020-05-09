package com.avenger.jt808.domain

/**
 * Created by jg.wang on 2020/4/8.
 * Description:
 */

annotation class AllOpen

annotation class NoArg

@AllOpen
@NoArg
annotation class AllOpenAndNoArg


annotation class ReadingMessageType(val type: Short, val needReply: Boolean = false)

/**
 * type 消息id，needReply 为true时需要终端回复的消息
 */
annotation class WritingMessageType(val type: Short, val needReply: Boolean = false)

annotation class AdditionalAble(val type: Byte)