package com.android.settings.notification.modes;

import java.util.Comparator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class ZenModesBackend$$ExternalSyntheticLambda0 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        ZenMode zenMode = (ZenMode) obj;
        ZenMode zenMode2 = (ZenMode) obj2;
        if (zenMode.mIsManualDnd) {
            return -1;
        }
        if (zenMode2.mIsManualDnd) {
            return 1;
        }
        return zenMode.mRule.getName().compareTo(zenMode2.mRule.getName());
    }
}
