package com.google.zxing.common;

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class StringUtils {
    public static final boolean ASSUME_SHIFT_JIS;
    public static final Charset GB2312_CHARSET;
    public static final Charset PLATFORM_DEFAULT_ENCODING = Charset.defaultCharset();
    public static final Charset SHIFT_JIS_CHARSET = Charset.forName("SJIS");

    static {
        Charset charset;
        try {
            charset = Charset.forName("GB2312");
        } catch (UnsupportedCharsetException unused) {
            charset = null;
        }
        GB2312_CHARSET = charset;
        Charset forName = Charset.forName("EUC_JP");
        Charset charset2 = SHIFT_JIS_CHARSET;
        Charset charset3 = PLATFORM_DEFAULT_ENCODING;
        ASSUME_SHIFT_JIS = charset2.equals(charset3) || forName.equals(charset3);
    }
}
