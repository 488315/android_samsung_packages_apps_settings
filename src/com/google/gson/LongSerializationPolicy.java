package com.google.gson;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class LongSerializationPolicy {
    public static final /* synthetic */ LongSerializationPolicy[] $VALUES;
    public static final LongSerializationPolicy DEFAULT;

    static {
        LongSerializationPolicy longSerializationPolicy =
                new LongSerializationPolicy() { // from class:
                                                // com.google.gson.LongSerializationPolicy.1
                };
        DEFAULT = longSerializationPolicy;
        $VALUES =
                new LongSerializationPolicy[] {
                    longSerializationPolicy,
                    new LongSerializationPolicy() { // from class:
                                                    // com.google.gson.LongSerializationPolicy.2
                    }
                };
    }

    public static LongSerializationPolicy valueOf(String str) {
        return (LongSerializationPolicy) Enum.valueOf(LongSerializationPolicy.class, str);
    }

    public static LongSerializationPolicy[] values() {
        return (LongSerializationPolicy[]) $VALUES.clone();
    }
}
