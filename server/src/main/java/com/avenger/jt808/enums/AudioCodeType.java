package com.avenger.jt808.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * Created by jg.wang on 2020/4/13.
 * Description:
 */
public enum AudioCodeType {
    G_721(1),
    G_722(2),
    G_723(3),
    G_728(4),
    G_729(5),
    G_711A(6),
    G_711U(7),
    G_726(8),
    G_729A(9),
    DVI4_3(10),
    DVI4_4(11),
    DVI4_8K(12),
    DVI4_16K(13),
    LPC(14),
    S16BE_STEREO(15),
    S16BE_MONO(16),
    MPEG_AUDIO(17),
    LPCM(18),
    ACC(19),
    WMA9STD(20),
    HEACC(21),
    PCM_VOICE(22),
    PCM_AUDIO(23),
    AACLC(24),
    MP3(25),
    ADPCMA(26),
    MP4AUDIO(27),
    AMR(28),
    PASSTHROUGH(91),
    H_264(98),
    H_265(99),
    AVS(100),
    SVAC(101);

    @Getter
    private final int value;

    AudioCodeType(int value) {
        this.value = value;
    }

    public static AudioCodeType valueOf(int value) {
        return Arrays.stream(values())
                .filter(v -> v.getValue() == value)
                .findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(AudioCodeType.class, "illegal value : " + value));
    }
}
