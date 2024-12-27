package com.samsung.android.settings.wifi.mobileap.autohotspot.safamily;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamMediaService$4$$ExternalSyntheticOutline0;

import com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiApSaFamilySharingSwitchEnabler implements LifecycleEventObserver {
    public static final IntentFilter INTENT_FILTER;
    public final Activity mActivity;
    public final AnonymousClass1 mBroadcastReceiver = new AnonymousClass1();
    public final Context mContext;
    public OnStateChangeListener mOnStateChangeListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.autohotspot.safamily.WifiApSaFamilySharingSwitchEnabler$1, reason: invalid class name */
    public final class AnonymousClass1 extends BroadcastReceiver {
        public AnonymousClass1() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (AudioStreamMediaService$4$$ExternalSyntheticOutline0.m(
                    "Broadcast Received: ",
                    action,
                    "WifiApSaFamilySharingSwitchEnabler",
                    "com.samsung.android.server.wifi.softap.smarttethering.familySharingDisabledIntent")) {
                WifiApSaFamilySharingSwitchEnabler.this.updateSwitchState();
                return;
            }
            if (action.equals("com.samsung.android.server.wifi.softap.smarttethering.changed")) {
                WifiApSaFamilySharingSwitchEnabler.this.updateSwitchState();
                return;
            }
            if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                new Handler()
                        .postDelayed(
                                new Runnable() { // from class:
                                                 // com.samsung.android.settings.wifi.mobileap.autohotspot.safamily.WifiApSaFamilySharingSwitchEnabler$1$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        WifiApSaFamilySharingSwitchEnabler.this.updateSwitchState();
                                    }
                                },
                                500L);
                return;
            }
            if (action.equals("com.samsung.account.SAMSUNGACCOUNT_SIGNOUT_COMPLETED")) {
                WifiApSaFamilySharingSwitchEnabler.this.updateSwitchState();
            } else if (action.equals(
                    "com.samsung.android.server.wifi.softap.smarttethering.familyid")) {
                WifiApFrameworkUtils.setFamilySharingDB(
                        context, !SaFamilyUtils.getFamilyGroupId(context).isEmpty());
                WifiApSaFamilySharingSwitchEnabler.this.updateSwitchState();
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnStateChangeListener {
        void onStateChanged(int i);
    }

    static {
        IntentFilter intentFilter = new IntentFilter();
        INTENT_FILTER = intentFilter;
        intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("com.samsung.account.SAMSUNGACCOUNT_SIGNIN_COMPLETED");
        intentFilter.addAction("com.samsung.account.SAMSUNGACCOUNT_SIGNOUT_COMPLETED");
        intentFilter.addAction("com.samsung.android.server.wifi.softap.smarttethering.changed");
        intentFilter.addAction(
                "com.samsung.android.server.wifi.softap.smarttethering.familySharingDisabledIntent");
        intentFilter.addAction("com.samsung.android.server.wifi.softap.smarttethering.familyid");
    }

    public WifiApSaFamilySharingSwitchEnabler(
            SettingsPreferenceFragment settingsPreferenceFragment) {
        Context context = settingsPreferenceFragment.getContext();
        this.mContext = context;
        this.mActivity = (Activity) context;
        settingsPreferenceFragment.getSettingsLifecycle().addObserver(this);
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        Lifecycle.Event event2 = Lifecycle.Event.ON_START;
        AnonymousClass1 anonymousClass1 = this.mBroadcastReceiver;
        if (event == event2) {
            this.mContext.registerReceiver(anonymousClass1, INTENT_FILTER, 2);
        } else if (event == Lifecycle.Event.ON_STOP) {
            this.mContext.unregisterReceiver(anonymousClass1);
        }
    }

    public final void updateSwitchState() {
        this.mActivity.invalidateOptionsMenu();
        boolean isTetheringRestricted = WifiApSettingsUtils.isTetheringRestricted(this.mContext);
        boolean isAirplaneModeOn = WifiApSettingsUtils.isAirplaneModeOn(this.mContext);
        boolean z = !WifiApSettingsUtils.isActiveNetworkHasInternet(this.mContext);
        boolean isSamsungAccountLoggedOut =
                WifiApSettingsUtils.isSamsungAccountLoggedOut(this.mContext);
        boolean z2 = !WifiApSettingsUtils.isNearByDeviceScanningEnabled(this.mContext);
        boolean isEmpty = TextUtils.isEmpty(SaFamilyUtils.getFamilyGroupId(this.mContext));
        boolean z3 = !WifiApFrameworkUtils.isAutoHotspotSetOn(this.mContext);
        Context context = this.mContext;
        boolean isSupportFamilyService =
                SaFamilyUtils.isSupportFamilyService(
                        context, context.getString(R.string.security_dashboard_sa_client_id));
        StringBuilder m =
                Utils$$ExternalSyntheticOutline0.m(
                        "Updating Family Sharing Switch state - isTetheringRestricted : ",
                        isTetheringRestricted,
                        ", isAirplaneModeOn : ",
                        isAirplaneModeOn,
                        ", isNetworkDisconnected : ");
        m.append(z);
        m.append(", isSamsungAccountLoggedOut : ");
        m.append(isSamsungAccountLoggedOut);
        m.append(", isNearByDeviceScanningDisabled : ");
        m.append(z2);
        m.append(", isFamilyGroupIdEmpty : ");
        m.append(isEmpty);
        m.append(", isSaFamilySupported : ");
        m.append(isSupportFamilyService);
        m.append(", isAutoHotspotOff : ");
        m.append(z3);
        Log.d("WifiApSaFamilySharingSwitchEnabler", m.toString());
        if (isTetheringRestricted) {
            WifiApFrameworkUtils.setFamilySharingDB(this.mContext, false);
            this.mOnStateChangeListener.onStateChanged(5);
            return;
        }
        if (isAirplaneModeOn) {
            WifiApFrameworkUtils.setFamilySharingDB(this.mContext, false);
            this.mOnStateChangeListener.onStateChanged(1);
            return;
        }
        if (z) {
            this.mOnStateChangeListener.onStateChanged(3);
            return;
        }
        if (isSamsungAccountLoggedOut) {
            WifiApFrameworkUtils.setFamilySharingDB(this.mContext, false);
            this.mOnStateChangeListener.onStateChanged(4);
            return;
        }
        if (z2) {
            WifiApFrameworkUtils.setFamilySharingDB(this.mContext, false);
            this.mOnStateChangeListener.onStateChanged(6);
            return;
        }
        if (isEmpty) {
            WifiApFrameworkUtils.setFamilySharingDB(this.mContext, false);
            this.mOnStateChangeListener.onStateChanged(8);
        } else if (z3) {
            WifiApFrameworkUtils.setFamilySharingDB(this.mContext, false);
            this.mOnStateChangeListener.onStateChanged(9);
        } else if (isSupportFamilyService) {
            this.mOnStateChangeListener.onStateChanged(-1);
        } else {
            SaFamilyUtils.setFamilySharingDB(this.mContext, false);
            this.mOnStateChangeListener.onStateChanged(11);
        }
    }
}
