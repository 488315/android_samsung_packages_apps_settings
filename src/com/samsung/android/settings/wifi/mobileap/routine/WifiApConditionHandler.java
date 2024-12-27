package com.samsung.android.settings.wifi.mobileap.routine;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;

import com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamMediaService$4$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.sdk.routines.v3.interfaces.RoutineConditionHandler;
import com.samsung.android.sdk.routines.v3.internal.ConditionStatusManagerImpl;
import com.samsung.android.sdk.routines.v3.internal.RoutineSdkImpl;
import com.samsung.android.sepunion.SemEventDelegationManager;
import com.samsung.android.settings.wifi.mobileap.clients.WifiApMobileDataSharedTodayPreferenceController;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApConditionHandler extends BroadcastReceiver implements RoutineConditionHandler {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (!AudioStreamMediaService$4$$ExternalSyntheticOutline0.m(
                "onReceive:  action: ",
                action,
                "WifiApConditionHandler",
                "com.samsung.android.settings.wifi.mobileap.routine.ACTION_WIFI_AP_STATE_ROUTINE_PENDING")) {
            if (action.equals("android.intent.action.BOOT_COMPLETED")) {
                Settings.Secure.putString(
                        context.getContentResolver(),
                        "wifi_ap_routine_instances",
                        ApnSettings.MVNO_NONE);
                return;
            }
            return;
        }
        if (intent.hasExtra("action_origin")) {
            String stringExtra = intent.getStringExtra("action_origin");
            int intExtra = intent.getIntExtra("wifi_state", 14);
            Log.d(
                    "WifiApConditionHandler",
                    "Event Action origin : " + stringExtra + ", apstate : " + intExtra);
            if (stringExtra.equals(
                    WifiApMobileDataSharedTodayPreferenceController.ACTION_WIFI_AP_STATE_CHANGED)) {
                if (intExtra == 13 || intExtra == 11) {
                    RoutineSdkImpl routineSdkImpl = RoutineSdkImpl.LazyHolder.a;
                    if (routineSdkImpl.a == null) {
                        routineSdkImpl.a = new ConditionStatusManagerImpl();
                    }
                    routineSdkImpl.a.getClass();
                    context.getContentResolver()
                            .notifyChange(
                                    Uri.parse(
                                            "content://"
                                                    + context.getPackageName()
                                                    + ".provider.routines.v3/hotspot_condition_tag"),
                                    null);
                }
            }
        }
    }

    public final void pendingIntentRegistration(Context context, boolean z) {
        SemEventDelegationManager semEventDelegationManager =
                (SemEventDelegationManager) context.getSystemService("semeventdelegator");
        if (semEventDelegationManager == null) {
            Log.e(
                    "WifiApConditionHandler",
                    "pendingIntentRegistration() - SemEventDelegator system service not available");
            return;
        }
        Intent intent =
                new Intent(
                        "com.samsung.android.settings.wifi.mobileap.routine.ACTION_WIFI_AP_STATE_ROUTINE_PENDING");
        intent.setClass(context, getClass());
        intent.setFlags(268435456);
        PendingIntent broadcast = PendingIntent.getBroadcast(context, 0, intent, 167772160);
        IntentFilter intentFilter =
                new IntentFilter(
                        WifiApMobileDataSharedTodayPreferenceController
                                .ACTION_WIFI_AP_STATE_CHANGED);
        Log.i(
                "WifiApConditionHandler",
                "pendingIntentRegistration() - shouldRegister: " + z + ", intent: " + intent);
        if (z) {
            semEventDelegationManager.registerPendingIntent(
                    intentFilter, broadcast, 0, (List) null);
        } else {
            semEventDelegationManager.unregisterIntentFilter(intentFilter, broadcast);
        }
    }
}
