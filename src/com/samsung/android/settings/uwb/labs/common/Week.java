package com.samsung.android.settings.uwb.labs.common;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public enum Week {
    /* JADX INFO: Fake field, exist only in values array */
    EF7("SUNDAY", "Sun"),
    /* JADX INFO: Fake field, exist only in values array */
    EF17("MONDAY", "Mon"),
    /* JADX INFO: Fake field, exist only in values array */
    EF27("TUESDAY", "Tue"),
    /* JADX INFO: Fake field, exist only in values array */
    EF37("WEDNESDAY", "Wen"),
    /* JADX INFO: Fake field, exist only in values array */
    EF47("THURSDAY", "Thu"),
    /* JADX INFO: Fake field, exist only in values array */
    EF57("FRIDAY", "Fri"),
    /* JADX INFO: Fake field, exist only in values array */
    EF67("SATURDAY", "Sat");

    private final int entry;
    private final String name;

    Week(String str, String str2) {
        this.entry = r2;
        this.name = str2;
    }

    public final String getName() {
        return this.name;
    }
}
