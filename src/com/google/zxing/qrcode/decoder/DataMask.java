package com.google.zxing.qrcode.decoder;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
abstract class DataMask {
    public static final /* synthetic */ DataMask[] $VALUES = {
        new DataMask() { // from class: com.google.zxing.qrcode.decoder.DataMask.1
            @Override // com.google.zxing.qrcode.decoder.DataMask
            public final boolean isMasked(int i, int i2) {
                return ((i + i2) & 1) == 0;
            }
        },
        new DataMask() { // from class: com.google.zxing.qrcode.decoder.DataMask.2
            @Override // com.google.zxing.qrcode.decoder.DataMask
            public final boolean isMasked(int i, int i2) {
                return (i & 1) == 0;
            }
        },
        new DataMask() { // from class: com.google.zxing.qrcode.decoder.DataMask.3
            @Override // com.google.zxing.qrcode.decoder.DataMask
            public final boolean isMasked(int i, int i2) {
                return i2 % 3 == 0;
            }
        },
        new DataMask() { // from class: com.google.zxing.qrcode.decoder.DataMask.4
            @Override // com.google.zxing.qrcode.decoder.DataMask
            public final boolean isMasked(int i, int i2) {
                return (i + i2) % 3 == 0;
            }
        },
        new DataMask() { // from class: com.google.zxing.qrcode.decoder.DataMask.5
            @Override // com.google.zxing.qrcode.decoder.DataMask
            public final boolean isMasked(int i, int i2) {
                return (((i2 / 3) + (i / 2)) & 1) == 0;
            }
        },
        new DataMask() { // from class: com.google.zxing.qrcode.decoder.DataMask.6
            @Override // com.google.zxing.qrcode.decoder.DataMask
            public final boolean isMasked(int i, int i2) {
                return (i * i2) % 6 == 0;
            }
        },
        new DataMask() { // from class: com.google.zxing.qrcode.decoder.DataMask.7
            @Override // com.google.zxing.qrcode.decoder.DataMask
            public final boolean isMasked(int i, int i2) {
                return (i * i2) % 6 < 3;
            }
        },
        new DataMask() { // from class: com.google.zxing.qrcode.decoder.DataMask.8
            @Override // com.google.zxing.qrcode.decoder.DataMask
            public final boolean isMasked(int i, int i2) {
                return ((((i * i2) % 3) + (i + i2)) & 1) == 0;
            }
        }
    };
    public static final AnonymousClass1 DATA_MASK_000 = null;
    public static final AnonymousClass2 DATA_MASK_001 = null;
    public static final AnonymousClass3 DATA_MASK_010 = null;
    public static final AnonymousClass4 DATA_MASK_011 = null;
    public static final AnonymousClass5 DATA_MASK_100 = null;
    public static final AnonymousClass6 DATA_MASK_101 = null;
    public static final AnonymousClass7 DATA_MASK_110 = null;
    public static final AnonymousClass8 DATA_MASK_111 = null;

    /* JADX INFO: Fake field, exist only in values array */
    DataMask EF2;

    public static DataMask valueOf(String str) {
        return (DataMask) Enum.valueOf(DataMask.class, str);
    }

    public static DataMask[] values() {
        return (DataMask[]) $VALUES.clone();
    }

    public abstract boolean isMasked(int i, int i2);
}
