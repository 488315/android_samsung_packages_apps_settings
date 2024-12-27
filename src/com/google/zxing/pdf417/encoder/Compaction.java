package com.google.zxing.pdf417.encoder;

import com.sec.ims.settings.ImsSettings;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class Compaction {
    public static final /* synthetic */ Compaction[] $VALUES;
    public static final Compaction AUTO;

    static {
        Compaction compaction = new Compaction("AUTO", 0);
        AUTO = compaction;
        $VALUES =
                new Compaction[] {
                    compaction,
                    new Compaction(ImsSettings.TYPE_TEXT, 1),
                    new Compaction("BYTE", 2),
                    new Compaction("NUMERIC", 3)
                };
    }

    public static Compaction valueOf(String str) {
        return (Compaction) Enum.valueOf(Compaction.class, str);
    }

    public static Compaction[] values() {
        return (Compaction[]) $VALUES.clone();
    }
}
