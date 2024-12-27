package com.samsung.android.settings.analyzestorage.presenter.managers.broadcast;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BroadcastType {
    public static final /* synthetic */ BroadcastType[] $VALUES;
    public static final BroadcastType CACHED_FILES_CHANGED;
    public static final BroadcastType LOCALE_CHANGED;
    public static final BroadcastType MEDIA_EJECTED;
    public static final BroadcastType MEDIA_MOUNTED;
    public static final BroadcastType MEDIA_REMOVED;
    public static final BroadcastType MEDIA_UNMOUNTED;
    public static final BroadcastType NEED_REFRESH;
    public static final BroadcastType TIMEZONE_CHANGED;
    public static final BroadcastType TRASH_CHANGED;

    static {
        BroadcastType broadcastType = new BroadcastType("MEDIA_MOUNTED", 0);
        MEDIA_MOUNTED = broadcastType;
        BroadcastType broadcastType2 = new BroadcastType("MEDIA_EJECTED", 1);
        MEDIA_EJECTED = broadcastType2;
        BroadcastType broadcastType3 = new BroadcastType("MEDIA_UNMOUNTED", 2);
        MEDIA_UNMOUNTED = broadcastType3;
        BroadcastType broadcastType4 = new BroadcastType("MEDIA_REMOVED", 3);
        MEDIA_REMOVED = broadcastType4;
        BroadcastType broadcastType5 = new BroadcastType("TRASH_CHANGED", 4);
        TRASH_CHANGED = broadcastType5;
        BroadcastType broadcastType6 = new BroadcastType("CACHED_FILES_CHANGED", 5);
        CACHED_FILES_CHANGED = broadcastType6;
        BroadcastType broadcastType7 = new BroadcastType("TIMEZONE_CHANGED", 6);
        TIMEZONE_CHANGED = broadcastType7;
        BroadcastType broadcastType8 = new BroadcastType("LOCALE_CHANGED", 7);
        LOCALE_CHANGED = broadcastType8;
        BroadcastType broadcastType9 = new BroadcastType("ADD_OBSERVER", 8);
        BroadcastType broadcastType10 = new BroadcastType("NEED_REFRESH", 9);
        NEED_REFRESH = broadcastType10;
        $VALUES =
                new BroadcastType[] {
                    broadcastType,
                    broadcastType2,
                    broadcastType3,
                    broadcastType4,
                    broadcastType5,
                    broadcastType6,
                    broadcastType7,
                    broadcastType8,
                    broadcastType9,
                    broadcastType10,
                    new BroadcastType("ONE_DRIVE_SESSION_EXPIRED", 10),
                    new BroadcastType("PERMISSION_CHANGED", 11)
                };
    }

    public static BroadcastType valueOf(String str) {
        return (BroadcastType) Enum.valueOf(BroadcastType.class, str);
    }

    public static BroadcastType[] values() {
        return (BroadcastType[]) $VALUES.clone();
    }
}
