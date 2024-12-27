package com.android.settingslib.wifi;

import com.android.settingslib.utils.ThreadUtils;

import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WifiTracker.WifiListener f$0;

    public /* synthetic */ WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda0(
            WifiTracker.WifiListener wifiListener, int i) {
        this.$r8$classId = i;
        this.f$0 = wifiListener;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        WifiTracker.WifiListenerExecutor wifiListenerExecutor =
                (WifiTracker.WifiListenerExecutor) this.f$0;
        switch (i) {
            case 0:
                WifiTracker.WifiListener wifiListener = wifiListenerExecutor.mDelegatee;
                Objects.requireNonNull(wifiListener);
                ThreadUtils.postOnMainThread(
                        new WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda3(
                                wifiListenerExecutor,
                                "Invoking onAccessPointsChanged callback",
                                new WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda0(
                                        wifiListener, 0)));
                break;
            default:
                WifiTracker.WifiListener wifiListener2 = wifiListenerExecutor.mDelegatee;
                Objects.requireNonNull(wifiListener2);
                ThreadUtils.postOnMainThread(
                        new WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda3(
                                wifiListenerExecutor,
                                "Invoking onConnectedChanged callback",
                                new WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda0(
                                        wifiListener2, 1)));
                break;
        }
    }
}
