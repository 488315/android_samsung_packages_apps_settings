package com.android.settings.connecteddevice.audiosharing.audiostreams;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
final class SourceOriginForLogging {
    public static final /* synthetic */ SourceOriginForLogging[] $VALUES;
    public static final SourceOriginForLogging BROADCAST_SEARCH;
    public static final SourceOriginForLogging QR_CODE_SCAN_OTHER;
    public static final SourceOriginForLogging QR_CODE_SCAN_SETTINGS;
    public static final SourceOriginForLogging UNKNOWN;

    static {
        SourceOriginForLogging sourceOriginForLogging = new SourceOriginForLogging("UNKNOWN", 0);
        UNKNOWN = sourceOriginForLogging;
        SourceOriginForLogging sourceOriginForLogging2 =
                new SourceOriginForLogging("QR_CODE_SCAN_SETTINGS", 1);
        QR_CODE_SCAN_SETTINGS = sourceOriginForLogging2;
        SourceOriginForLogging sourceOriginForLogging3 =
                new SourceOriginForLogging("QR_CODE_SCAN_OTHER", 2);
        QR_CODE_SCAN_OTHER = sourceOriginForLogging3;
        SourceOriginForLogging sourceOriginForLogging4 =
                new SourceOriginForLogging("BROADCAST_SEARCH", 3);
        BROADCAST_SEARCH = sourceOriginForLogging4;
        $VALUES =
                new SourceOriginForLogging[] {
                    sourceOriginForLogging,
                    sourceOriginForLogging2,
                    sourceOriginForLogging3,
                    sourceOriginForLogging4,
                    new SourceOriginForLogging("REPOSITORY", 4)
                };
    }

    public static SourceOriginForLogging valueOf(String str) {
        return (SourceOriginForLogging) Enum.valueOf(SourceOriginForLogging.class, str);
    }

    public static SourceOriginForLogging[] values() {
        return (SourceOriginForLogging[]) $VALUES.clone();
    }
}
