package com.google.zxing;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class FormatException extends ReaderException {
    public static final FormatException INSTANCE;

    static {
        FormatException formatException = new FormatException();
        INSTANCE = formatException;
        formatException.setStackTrace(ReaderException.NO_TRACE);
    }

    private FormatException() {}

    public static FormatException getFormatInstance() {
        return ReaderException.isStackTrace ? new FormatException() : INSTANCE;
    }
}
