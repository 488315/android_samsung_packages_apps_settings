package com.android.settings.dashboard;

import java.util.Set;
import java.util.concurrent.CountDownLatch;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UiBlockerController {
    public boolean mBlockerFinished;
    public CountDownLatch mCountDownLatch;
    public Set mKeys;
    public long mTimeoutMillis;
}
