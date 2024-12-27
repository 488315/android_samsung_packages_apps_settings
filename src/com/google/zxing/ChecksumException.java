package com.google.zxing;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ChecksumException extends ReaderException {
    public static final ChecksumException INSTANCE;

    static {
        ChecksumException checksumException = new ChecksumException();
        INSTANCE = checksumException;
        checksumException.setStackTrace(ReaderException.NO_TRACE);
    }

    private ChecksumException() {}

    public static ChecksumException getChecksumInstance() {
        return ReaderException.isStackTrace ? new ChecksumException() : INSTANCE;
    }
}
