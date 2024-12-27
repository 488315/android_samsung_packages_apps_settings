package com.android.settings.wifi.repository;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkScoreManager;
import android.net.wifi.WifiManager;

import com.android.settingslib.wifi.WifiStatusTracker;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiStatusRepository {
    public static final IntentFilter INTENT_FILTER;
    public final Context context;
    public final Function1 wifiStatusTrackerFactory;

    static {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        intentFilter.addAction("android.net.wifi.RSSI_CHANGED");
        INTENT_FILTER = intentFilter;
    }

    public WifiStatusRepository(final Context context) {
        Function1 function1 =
                new Function1() { // from class:
                                  // com.android.settings.wifi.repository.WifiStatusRepository.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        Runnable callback = (Runnable) obj;
                        Intrinsics.checkNotNullParameter(callback, "callback");
                        Context context2 = context;
                        return new WifiStatusTracker(
                                context2,
                                (WifiManager) context2.getSystemService(WifiManager.class),
                                (NetworkScoreManager)
                                        context.getSystemService(NetworkScoreManager.class),
                                (ConnectivityManager)
                                        context.getSystemService(ConnectivityManager.class),
                                callback);
                    }
                };
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.wifiStatusTrackerFactory = function1;
    }

    public final Flow wifiStatusTrackerFlow() {
        return FlowKt.flowOn(
                FlowKt.buffer$default(
                        FlowKt.callbackFlow(
                                new WifiStatusRepository$wifiStatusTrackerFlow$1(this, null)),
                        -1),
                Dispatchers.Default);
    }
}
