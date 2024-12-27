package com.samsung.android.settings.accessibility.hearing;

import android.content.Context;
import android.os.SystemProperties;
import android.telephony.SubscriptionManager;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.glance.session.SessionManagerImpl$scope$1$$ExternalSyntheticOutline0;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;

import com.samsung.android.ims.SemImsManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class RealTimeTextUtils {
    public static final Map sImsManagerMap = new HashMap();
    public static SemImsManager[] sImsManagers;

    public static int getDefaultDataPhoneId(Context context) {
        final int defaultDataSubscriptionId = SubscriptionManager.getDefaultDataSubscriptionId();
        return ((Integer)
                        Optional.ofNullable(
                                        (SubscriptionManager)
                                                context.getSystemService(SubscriptionManager.class))
                                .map(
                                        new Function() { // from class:
                                                         // com.samsung.android.settings.accessibility.hearing.RealTimeTextUtils$$ExternalSyntheticLambda0
                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                return ((SubscriptionManager) obj)
                                                        .getActiveSubscriptionInfo(
                                                                defaultDataSubscriptionId);
                                            }
                                        })
                                .map(new RealTimeTextUtils$$ExternalSyntheticLambda1())
                                .orElse(0))
                .intValue();
    }

    public static SemImsManager getImsManager(Context context, final int i) {
        int parseInt = Integer.parseInt(SystemProperties.get("ro.multisim.simslotcount", "1"));
        if (i < 0) {
            i = 0;
        }
        if (sImsManagers == null) {
            sImsManagers = new SemImsManager[parseInt];
        }
        SemImsManager semImsManager =
                (SemImsManager) ((HashMap) sImsManagerMap).get(Integer.valueOf(i));
        if (semImsManager != null) {
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    i, "getImsMgr return cache value:", "RealTimeTextUtils");
            return semImsManager;
        }
        SemImsManager semImsManager2 =
                new SemImsManager(
                        context,
                        new SemImsManager
                                .ImsServiceConnectionListener() { // from class:
                                                                  // com.samsung.android.settings.accessibility.hearing.RealTimeTextUtils.1
                            public final void onConnected() {
                                SemImsManager[] semImsManagerArr = RealTimeTextUtils.sImsManagers;
                                Log.i("RealTimeTextUtils", "Connected IMS Listener:");
                                ((HashMap) RealTimeTextUtils.sImsManagerMap)
                                        .put(Integer.valueOf(i), RealTimeTextUtils.sImsManagers[i]);
                            }

                            public final void onDisconnected() {
                                SemImsManager[] semImsManagerArr = RealTimeTextUtils.sImsManagers;
                                Log.i("RealTimeTextUtils", "Disconnected IMS Listener:");
                                ((HashMap) RealTimeTextUtils.sImsManagerMap)
                                        .remove(Integer.valueOf(i));
                            }
                        },
                        i);
        sImsManagers[i] = semImsManager2;
        semImsManager2.connectService();
        return semImsManager2;
    }

    public static boolean isVoLteSupported(Context context) {
        boolean z;
        try {
            z =
                    getImsManager(context, getDefaultDataPhoneId(context))
                            .isImsFeatureEnabled("mmtel", 13);
        } catch (Exception e) {
            SessionManagerImpl$scope$1$$ExternalSyntheticOutline0.m(
                    "voltesupport:", e, "RealTimeTextUtils");
            z = false;
        }
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m("voltesupport: ", "RealTimeTextUtils", z);
        return z;
    }
}
