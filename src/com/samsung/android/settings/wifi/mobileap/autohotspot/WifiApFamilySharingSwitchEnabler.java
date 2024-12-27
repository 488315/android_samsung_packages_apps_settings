package com.samsung.android.settings.wifi.mobileap.autohotspot;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamMediaService$4$$ExternalSyntheticOutline0;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.WifiApUtils;
import com.samsung.android.settings.voiceinput.samsungaccount.contract.SaContract;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFeatureUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSmartTetheringApkUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiApFamilySharingSwitchEnabler implements LifecycleEventObserver {
    public static final IntentFilter INTENT_FILTER;
    public final Activity mActivity;
    public final AnonymousClass1 mBroadcastReceiver = new AnonymousClass1();
    public final Context mContext;
    public OnStateChangeListener mOnStateChangeListener;
    public final SettingsPreferenceFragment mSettingsFragmentActivity;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApFamilySharingSwitchEnabler$1, reason: invalid class name */
    public final class AnonymousClass1 extends BroadcastReceiver {
        public AnonymousClass1() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            IntentFilter intentFilter = WifiApFamilySharingSwitchEnabler.INTENT_FILTER;
            if (AudioStreamMediaService$4$$ExternalSyntheticOutline0.m(
                    "Broadcast Received: ",
                    action,
                    "WifiApFamilySharingSwitchEnabler",
                    "com.samsung.android.server.wifi.softap.smarttethering.familySharingDisabledIntent")) {
                WifiApFamilySharingSwitchEnabler.this.updateSwitchState();
                return;
            }
            if (action.equals("com.samsung.android.server.wifi.softap.smarttethering.changed")) {
                WifiApFamilySharingSwitchEnabler.this.updateSwitchState();
                return;
            }
            if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                new Handler()
                        .postDelayed(
                                new Runnable() { // from class:
                                                 // com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApFamilySharingSwitchEnabler$1$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        WifiApFamilySharingSwitchEnabler.this.updateSwitchState();
                                    }
                                },
                                500L);
                return;
            }
            if (action.equals("com.samsung.account.SAMSUNGACCOUNT_SIGNOUT_COMPLETED")) {
                WifiApFamilySharingSwitchEnabler.this.updateSwitchState();
                return;
            }
            if (action.equals(
                    "com.samsung.android.server.wifi.softap.smarttethering.ACTION_WIFI_AP_FAMILY_LOCAL_GROUP_UPDATED")) {
                WifiApFamilySharingSwitchEnabler.this.updateSwitchState();
                return;
            }
            if (action.equals("com.sec.mhs.smarttethering.MY_INVITATION_LIST_CHANGED")) {
                WifiApFamilySharingSwitchEnabler.this.updateSwitchState();
                return;
            }
            if (action.equals("com.samsung.android.server.wifi.softap.smarttethering.familyid")) {
                WifiApFrameworkUtils.setFamilySharingDB(
                        WifiApFamilySharingSwitchEnabler.this.mContext,
                        !WifiApSmartTetheringApkUtils.getFamilyGroupId(
                                        WifiApFamilySharingSwitchEnabler.this.mContext)
                                .isEmpty());
                WifiApFamilySharingSwitchEnabler.this.updateSwitchState();
            } else if (action.equals(
                    "com.sec.mhs.smarttethering.GROUP_API_RESULT_AND_DB_READ_REQUEST")) {
                WifiApFamilySharingSwitchEnabler.this.updateSwitchState();
            } else if (action.equals(
                    "com.samsung.android.server.wifi.ap.smarttethering.DEVICE_ADDED")) {
                WifiApFamilySharingSwitchEnabler.this.updateSwitchState();
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
        intentFilter.addAction("com.sec.mhs.smarttethering.MY_INVITATION_LIST_CHANGED");
        intentFilter.addAction("com.samsung.android.server.wifi.softap.smarttethering.familyid");
        intentFilter.addAction("com.sec.mhs.smarttethering.GROUP_API_RESULT_AND_DB_READ_REQUEST");
        intentFilter.addAction(
                "com.samsung.android.server.wifi.softap.smarttethering.ACTION_WIFI_AP_FAMILY_LOCAL_GROUP_UPDATED");
        intentFilter.addAction("com.samsung.android.server.wifi.ap.smarttethering.DEVICE_ADDED");
    }

    public WifiApFamilySharingSwitchEnabler(SettingsPreferenceFragment settingsPreferenceFragment) {
        Context context = settingsPreferenceFragment.getContext();
        this.mContext = context;
        this.mActivity = (Activity) context;
        this.mSettingsFragmentActivity = settingsPreferenceFragment;
        settingsPreferenceFragment.getSettingsLifecycle().addObserver(this);
    }

    public final void onActivityResult(int i, int i2, Intent intent) {
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                "onActivityResult() - requestCode: ",
                ", resultCode(-1 for RESULT_OK) : ",
                i,
                i2,
                "WifiApFamilySharingSwitchEnabler");
        if (i == 600 && !WifiApFeatureUtils.isSAFamilySupportedBasedOnCountry(this.mContext)) {
            if (i2 != -1) {
                setChecked(false);
                return;
            } else {
                WifiApSmartTetheringApkUtils.setFamilySharingServiceRegisteredDB(
                        this.mContext, true);
                setChecked(true);
                return;
            }
        }
        if (i == 610) {
            if (i2 != -1) {
                setChecked(false);
                return;
            }
            WifiApSmartTetheringApkUtils.startSmartTetheringApk(this.mContext, 1, null);
            WifiApFrameworkUtils.setFamilySharingDB(this.mContext, true);
            setChecked(true);
        }
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

    public final void setChecked(boolean z) {
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "setChecked: ", "WifiApFamilySharingSwitchEnabler", z);
        WifiApFrameworkUtils.setFamilySharingDB(this.mContext, z);
        if (z) {
            Log.i("WifiApFamilySharingSwitchEnabler", "Checking setCheck conditions.");
            boolean z2 = !WifiApSettingsUtils.isSimEnabled(this.mContext);
            boolean z3 = !WifiApSettingsUtils.isActiveNetworkHasInternet(this.mContext);
            boolean isSamsungAccountLoggedOut =
                    WifiApSettingsUtils.isSamsungAccountLoggedOut(this.mContext);
            boolean isGroupSharingAppDisabled =
                    WifiApSmartTetheringApkUtils.isGroupSharingAppDisabled(this.mContext);
            boolean z4 =
                    !WifiApSmartTetheringApkUtils.isFamilySharingServiceRegisteredOn(this.mContext);
            boolean isEmpty =
                    WifiApSmartTetheringApkUtils.getFamilyGroupId(this.mContext).isEmpty();
            boolean z5 = !WifiApFrameworkUtils.isAutoHotspotSetOn(this.mContext);
            if (isGroupSharingAppDisabled) {
                Log.i(
                        "WifiApFamilySharingSwitchEnabler",
                        "Group sharing App check condition failed.");
                WifiApFrameworkUtils.setFamilySharingDB(this.mContext, false);
                String string = WifiApUtils.getString(this.mContext, R.string.sems_app_name);
                Log.d("WifiApFamilySharingSwitchEnabler", "Showing Enable AppDialog");
                new AlertDialog.Builder(this.mContext, 0)
                        .setTitle(
                                this.mContext
                                        .getResources()
                                        .getString(
                                                R.string.wifi_ap_disabled_app_popup_title, string))
                        .setCancelable(true)
                        .setMessage(
                                this.mContext
                                        .getResources()
                                        .getString(
                                                R.string.wifi_ap_disabled_app_popup_message,
                                                string))
                        .setPositiveButton(
                                R.string.sec_app_more_settings_btn_tooltip,
                                new DialogInterface.OnClickListener() { // from class:
                                    // com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApFamilySharingSwitchEnabler$$ExternalSyntheticLambda0
                                    public final /* synthetic */ String f$1 =
                                            SaContract.OLD_PACKAGE_NAME;

                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(
                                            DialogInterface dialogInterface, int i) {
                                        WifiApFamilySharingSwitchEnabler
                                                wifiApFamilySharingSwitchEnabler =
                                                        WifiApFamilySharingSwitchEnabler.this;
                                        String str = this.f$1;
                                        wifiApFamilySharingSwitchEnabler.getClass();
                                        Intent intent =
                                                new Intent(
                                                        "android.settings.APPLICATION_DETAILS_SETTINGS");
                                        intent.setData(Uri.parse("package:" + str));
                                        try {
                                            wifiApFamilySharingSwitchEnabler.mActivity
                                                    .startActivity(intent);
                                        } catch (ActivityNotFoundException unused) {
                                            Log.e(
                                                    "WifiApFamilySharingSwitchEnabler",
                                                    "ActivityNotFoundException:" + str);
                                        }
                                    }
                                })
                        .setNegativeButton(
                                R.string.dlg_cancel,
                                new WifiApFamilySharingSwitchEnabler$$ExternalSyntheticLambda1())
                        .setOnCancelListener(
                                new WifiApFamilySharingSwitchEnabler$$ExternalSyntheticLambda2())
                        .create()
                        .show();
            } else if (z2 && WifiApFeatureUtils.isOneUIVersion6_0_AtMost()) {
                Log.i("WifiApFamilySharingSwitchEnabler", "Sim check condition failed.");
                WifiApFrameworkUtils.setAutoHotspotDB(this.mContext, false);
                WifiApFrameworkUtils.setFamilySharingDB(this.mContext, false);
                if ("CTC".equals(Utils.getSalesCode())) {
                    Context context = this.mContext;
                    Toast.makeText(
                                    context,
                                    WifiApUtils.getString(
                                            context,
                                            R.string.mobile_hotspot_dialog_nouim_or_nosim_warning),
                                    1)
                            .show();
                } else {
                    Context context2 = this.mContext;
                    Toast.makeText(
                                    context2,
                                    WifiApUtils.getString(
                                            context2, R.string.wifi_tether_dialog_nosim_warning),
                                    1)
                            .show();
                }
            } else if (z5) {
                Log.i(
                        "WifiApFamilySharingSwitchEnabler",
                        "Auto Hotspot On check condition failed.");
                WifiApFrameworkUtils.setFamilySharingDB(this.mContext, false);
                if (Rune.isJapanModel()) {
                    Toast.makeText(
                                    this.mContext,
                                    R.string.wifi_ap_turn_on_auto_hotspot_to_use_family_sharing_jpn,
                                    1)
                            .show();
                } else {
                    Toast.makeText(
                                    this.mContext,
                                    R.string.wifi_ap_turn_on_auto_hotspot_to_use_family_sharing,
                                    1)
                            .show();
                }
            } else {
                if (z3) {
                    Log.i("WifiApFamilySharingSwitchEnabler", "Network check condition failed.");
                } else if (isSamsungAccountLoggedOut) {
                    Log.i(
                            "WifiApFamilySharingSwitchEnabler",
                            "Samsung Account check condition failed.");
                    WifiApFrameworkUtils.setFamilySharingDB(this.mContext, false);
                    WifiApSettingsUtils.launchAddSamsungAccountActivity(this.mActivity);
                }
                SettingsPreferenceFragment settingsPreferenceFragment =
                        this.mSettingsFragmentActivity;
                if (z4) {
                    Log.i(
                            "WifiApFamilySharingSwitchEnabler",
                            "Family service registered check condition failed.");
                    WifiApSmartTetheringApkUtils.launchFamilyServiceRegisterActivityForResult(
                            settingsPreferenceFragment, 600);
                } else if (isEmpty) {
                    Log.i(
                            "WifiApFamilySharingSwitchEnabler",
                            "Family group Id check failed. Opening group create activity");
                    if (WifiApFeatureUtils.isSAFamilySupportedBasedOnCountry(this.mContext)) {
                        WifiApSmartTetheringApkUtils.launchSaFamilyServiceGroupActivity(
                                settingsPreferenceFragment, 610);
                    } else {
                        WifiApSmartTetheringApkUtils.launchSocialPickerForCreatingGroupActivity(
                                settingsPreferenceFragment, 610);
                    }
                }
            }
        } else {
            WifiApSmartTetheringApkUtils.setFamilySharingServiceRegisteredDB(this.mContext, false);
        }
        updateSwitchState();
    }

    public final void updateSwitchState() {
        this.mActivity.invalidateOptionsMenu();
        boolean isTetheringRestricted = WifiApSettingsUtils.isTetheringRestricted(this.mContext);
        boolean isAirplaneModeOn = WifiApSettingsUtils.isAirplaneModeOn(this.mContext);
        boolean z = !WifiApSettingsUtils.isSimEnabled(this.mContext);
        boolean z2 = !WifiApSettingsUtils.isActiveNetworkHasInternet(this.mContext);
        boolean isSamsungAccountLoggedOut =
                WifiApSettingsUtils.isSamsungAccountLoggedOut(this.mContext);
        boolean z3 = !WifiApSettingsUtils.isNearByDeviceScanningEnabled(this.mContext);
        boolean isGroupSharingAppDisabled =
                WifiApSmartTetheringApkUtils.isGroupSharingAppDisabled(this.mContext);
        boolean z4 =
                !WifiApSmartTetheringApkUtils.isFamilySharingServiceRegisteredOn(this.mContext);
        boolean isEmpty = WifiApSmartTetheringApkUtils.getFamilyGroupId(this.mContext).isEmpty();
        boolean z5 = !WifiApFrameworkUtils.isAutoHotspotSetOn(this.mContext);
        StringBuilder m =
                Utils$$ExternalSyntheticOutline0.m(
                        "Updating Family Sharing Switch state - isTetheringRestricted : ",
                        isTetheringRestricted,
                        ", isAirplaneModeOn : ",
                        isAirplaneModeOn,
                        ", isSimDisabled : ");
        m.append(z);
        m.append(", isNetworkDisconnected : ");
        m.append(z2);
        m.append(", isSamsungAccountLoggedOut : ");
        m.append(isSamsungAccountLoggedOut);
        m.append(", isNearByDeviceScanningDisabled : ");
        m.append(z3);
        m.append(", isGroupSharingAppDisabled : ");
        m.append(isGroupSharingAppDisabled);
        m.append(", isFamilySharingServiceNotRegistered : ");
        m.append(z4);
        m.append(", isFamilyGroupIdEmpty : ");
        m.append(isEmpty);
        m.append(", isAutoHotspotOff : ");
        m.append(z5);
        Log.d("WifiApFamilySharingSwitchEnabler", m.toString());
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
        if (z2) {
            this.mOnStateChangeListener.onStateChanged(3);
            return;
        }
        if (isSamsungAccountLoggedOut) {
            WifiApFrameworkUtils.setFamilySharingDB(this.mContext, false);
            this.mOnStateChangeListener.onStateChanged(4);
            return;
        }
        if (z3) {
            WifiApFrameworkUtils.setFamilySharingDB(this.mContext, false);
            this.mOnStateChangeListener.onStateChanged(6);
            return;
        }
        if (!WifiApFeatureUtils.isSAFamilySupportedBasedOnCountry(this.mContext)) {
            if (isGroupSharingAppDisabled) {
                WifiApFrameworkUtils.setFamilySharingDB(this.mContext, false);
                this.mOnStateChangeListener.onStateChanged(9);
                return;
            } else if (z4) {
                WifiApFrameworkUtils.setFamilySharingDB(this.mContext, false);
                this.mOnStateChangeListener.onStateChanged(7);
                return;
            }
        }
        if (isEmpty) {
            WifiApFrameworkUtils.setFamilySharingDB(this.mContext, false);
            this.mOnStateChangeListener.onStateChanged(8);
        } else {
            if (!z5) {
                this.mOnStateChangeListener.onStateChanged(-1);
                return;
            }
            WifiApSmartTetheringApkUtils.setFamilySharingServiceRegisteredDB(this.mContext, false);
            WifiApFrameworkUtils.setFamilySharingDB(this.mContext, false);
            this.mOnStateChangeListener.onStateChanged(9);
        }
    }
}
