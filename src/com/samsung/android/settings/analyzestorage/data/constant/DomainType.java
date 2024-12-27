package com.samsung.android.settings.analyzestorage.data.constant;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class DomainType {
    public static final boolean isCloud(int i) {
        return 101 == i || 102 == i;
    }

    public static final boolean isInternalStorage(int i) {
        return i == 0;
    }

    public static final boolean isLocalStorage(int i) {
        return isInternalStorage(i) || 2 == i || 3 == i || 4 == i || isSd(i) || isUsb(i);
    }

    public static final boolean isSd(int i) {
        return 1 == i;
    }

    public static final boolean isUsb(int i) {
        return 10 == i || 11 == i || 12 == i || 13 == i || 14 == i || 15 == i;
    }
}
