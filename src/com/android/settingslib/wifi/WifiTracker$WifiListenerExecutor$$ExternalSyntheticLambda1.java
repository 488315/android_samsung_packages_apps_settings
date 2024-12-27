package com.android.settingslib.wifi;

import com.android.settingslib.utils.ThreadUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ WifiTracker.WifiListenerExecutor f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda1(
            WifiTracker.WifiListenerExecutor wifiListenerExecutor, int i) {
        this.f$0 = wifiListenerExecutor;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        WifiTracker.WifiListenerExecutor wifiListenerExecutor = this.f$0;
        int i = this.f$1;
        WifiTracker.WifiListenerExecutor wifiListenerExecutor2 =
                (WifiTracker.WifiListenerExecutor) wifiListenerExecutor.mDelegatee;
        wifiListenerExecutor2.getClass();
        ThreadUtils.postOnMainThread(
                new WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda3(
                        wifiListenerExecutor2,
                        String.format(
                                "Invoking onWifiStateChanged callback with state %d",
                                Integer.valueOf(i)),
                        new WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda1(
                                wifiListenerExecutor2, i)));
    }
}
