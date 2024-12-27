package com.samsung.android.settings.uwb.labs.control.uwbtest;

import android.uwb.RangingReport;
import android.uwb.RangingSession;
import android.uwb.UwbManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class Ranging {
    public Callback mCallback;
    public AnonymousClass1 mRangingCallback;
    public RangingSession mRangingSession;
    public int mState;
    public UwbManager mUwbManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Callback {
        void onClosed();

        void onOpenFailed();

        void onOpened();

        void onReportReceived(RangingReport rangingReport);

        void onStarted();

        void onStopped();
    }
}
