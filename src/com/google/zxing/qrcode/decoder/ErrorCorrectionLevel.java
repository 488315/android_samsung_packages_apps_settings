package com.google.zxing.qrcode.decoder;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r1v1 com.google.zxing.qrcode.decoder.ErrorCorrectionLevel, still in use, count: 1, list:
  (r1v1 com.google.zxing.qrcode.decoder.ErrorCorrectionLevel) from 0x002a: FILLED_NEW_ARRAY 
  (r1v1 com.google.zxing.qrcode.decoder.ErrorCorrectionLevel)
  (r0v0 com.google.zxing.qrcode.decoder.ErrorCorrectionLevel)
  (r3v2 com.google.zxing.qrcode.decoder.ErrorCorrectionLevel)
  (r2v1 com.google.zxing.qrcode.decoder.ErrorCorrectionLevel)
 A[WRAPPED] (LINE:43) elemType: com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:99)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:98)
	at jadx.core.utils.InsnRemover.removeAllAndUnbind(InsnRemover.java:252)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:180)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ErrorCorrectionLevel {
    L(1),
    /* JADX INFO: Fake field, exist only in values array */
    M(0),
    /* JADX INFO: Fake field, exist only in values array */
    Q(3),
    H(2);

    public static final ErrorCorrectionLevel[] FOR_BITS;
    private final int bits;

    static {
        ErrorCorrectionLevel errorCorrectionLevel = L;
        ErrorCorrectionLevel errorCorrectionLevel2 = H;
        FOR_BITS = new ErrorCorrectionLevel[]{r1, errorCorrectionLevel, errorCorrectionLevel2, r2};
    }

    public ErrorCorrectionLevel(int i) {
        this.bits = i;
    }

    public static ErrorCorrectionLevel valueOf(String str) {
        return (ErrorCorrectionLevel) Enum.valueOf(ErrorCorrectionLevel.class, str);
    }

    public static ErrorCorrectionLevel[] values() {
        return (ErrorCorrectionLevel[]) $VALUES.clone();
    }

    public final int getBits() {
        return this.bits;
    }
}
