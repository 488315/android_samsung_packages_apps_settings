package com.android.settings.biometrics.activeunlock;

import android.content.Context;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ActiveUnlockSummaryListener {
    public final ActiveUnlockContentListener mContentListener;

    public ActiveUnlockSummaryListener(
            Context context,
            ActiveUnlockStatusPreferenceController.AnonymousClass1 anonymousClass1) {
        this.mContentListener =
                new ActiveUnlockContentListener(
                        context,
                        anonymousClass1,
                        "ActiveUnlockSummaryListener",
                        "getSummary",
                        "com.android.settings.summary");
    }
}
