package com.samsung.android.settings.connection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.telephony.ServiceState;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.RestrictedPreference;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecMobileNetworkPreferenceController extends BasePreferenceController
        implements PreferenceControllerMixin, LifecycleObserver, OnStart, OnStop {
    static final String KEY_MOBILE_NETWORK_SETTINGS = "mobile_network_settings";
    private static final String TAG = "MobileNetworkPreferenceController";
    private BroadcastReceiver mAirplanModeChangedReceiver;
    private boolean mIsSecondaryUser;
    private Preference mPreference;
    MobileNetworkTelephonyCallback mTelephonyCallback;
    private TelephonyManager mTelephonyManager;
    private UserManager mUserManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class MobileNetworkTelephonyCallback extends TelephonyCallback
            implements TelephonyCallback.ServiceStateListener {
        public MobileNetworkTelephonyCallback() {}

        @Override // android.telephony.TelephonyCallback.ServiceStateListener
        public final void onServiceStateChanged(ServiceState serviceState) {
            if (SecMobileNetworkPreferenceController.this.mPreference != null) {
                SecMobileNetworkPreferenceController secMobileNetworkPreferenceController =
                        SecMobileNetworkPreferenceController.this;
                secMobileNetworkPreferenceController.updateState(
                        secMobileNetworkPreferenceController.mPreference);
            }
        }
    }

    public SecMobileNetworkPreferenceController(Context context) {
        super(context, KEY_MOBILE_NETWORK_SETTINGS);
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mTelephonyManager = (TelephonyManager) context.getSystemService("phone");
        this.mIsSecondaryUser = !this.mUserManager.isAdminUser();
        this.mAirplanModeChangedReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.samsung.android.settings.connection.SecMobileNetworkPreferenceController.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        SecMobileNetworkPreferenceController secMobileNetworkPreferenceController =
                                SecMobileNetworkPreferenceController.this;
                        secMobileNetworkPreferenceController.updateState(
                                secMobileNetworkPreferenceController.mPreference);
                    }
                };
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = findPreference;
        if (findPreference != null) {
            updateState(findPreference);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0075, code lost:

       if ("VPP".equals(com.android.settings.Utils.getSalesCode()) == false) goto L32;
    */
    @Override // com.android.settings.core.BasePreferenceController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getAvailabilityStatus() {
        /*
            r7 = this;
            boolean r0 = r7.isUserRestricted()
            r1 = 4
            if (r0 != 0) goto Lc6
            android.content.Context r0 = r7.mContext
            boolean r0 = com.android.settingslib.Utils.isWifiOnly(r0)
            if (r0 == 0) goto L11
            goto Lc6
        L11:
            android.content.Context r0 = r7.mContext
            int r2 = com.samsung.android.settings.connection.ConnectionsUtils.$r8$clinit
            java.lang.String r2 = "com.samsung.android.app.telephonyui"
            r3 = 0
            android.content.pm.PackageManager r4 = r0.getPackageManager()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L23
            android.content.pm.ApplicationInfo r2 = r4.getApplicationInfo(r2, r3)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L23
            boolean r2 = r2.enabled     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L23
            goto L24
        L23:
            r2 = r3
        L24:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "isHideMobileNetworks: isEnabled = "
            r4.<init>(r5)
            r4.append(r2)
            java.lang.String r4 = r4.toString()
            java.lang.String r5 = "ConnectionsUtils"
            android.util.Log.d(r5, r4)
            java.lang.String r4 = "phone"
            java.lang.Object r0 = r0.getSystemService(r4)
            android.telephony.TelephonyManager r0 = (android.telephony.TelephonyManager) r0
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r6 = "for mobile_networks -> phoneType: "
            r4.<init>(r6)
            int r0 = r0.getPhoneType()
            r4.append(r0)
            java.lang.String r0 = r4.toString()
            android.util.Log.d(r5, r0)
            if (r2 == 0) goto Lb8
            boolean r0 = com.samsung.android.settings.connection.ConnectionsUtils.isNoSIM()
            if (r0 == 0) goto L78
            java.lang.String r0 = com.samsung.android.settings.Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER
            java.lang.String r0 = com.android.settings.Utils.getSalesCode()
            java.lang.String r2 = "VZW"
            boolean r0 = r2.equals(r0)
            if (r0 != 0) goto L78
            java.lang.String r0 = com.android.settings.Utils.getSalesCode()
            java.lang.String r2 = "VPP"
            boolean r0 = r2.equals(r0)
            if (r0 != 0) goto L78
            goto Lb8
        L78:
            boolean r0 = com.samsung.android.settings.connection.ConnectionsUtils.isDuosModel()
            if (r0 == 0) goto Lb7
            java.lang.String r0 = "gsm.sim.currentcardstatus"
            java.lang.String r2 = "9"
            java.lang.String r4 = com.samsung.android.settings.connection.ConnectionsUtils.getTelephonyProperty(r3, r0, r2)
            r5 = 1
            java.lang.String r0 = com.samsung.android.settings.connection.ConnectionsUtils.getTelephonyProperty(r5, r0, r2)
            android.content.Intent r2 = new android.content.Intent
            java.lang.String r6 = "com.samsung.android.app.telephonyui.action.OPEN_NET_SETTINGS"
            r2.<init>(r6)
            int r4 = java.lang.Integer.parseInt(r4)
            int r0 = java.lang.Integer.parseInt(r0)
            r6 = 3
            if (r4 == r6) goto La8
            if (r0 == r6) goto La8
            java.lang.String r7 = com.samsung.android.settings.connection.SecMobileNetworkPreferenceController.TAG
            java.lang.String r0 = "remove mobile network CDMA"
            android.util.Log.i(r7, r0)
            return r1
        La8:
            if (r0 != r6) goto Lab
            goto Lac
        Lab:
            r5 = r3
        Lac:
            java.lang.String r0 = "sim_id"
            r2.putExtra(r0, r5)
            androidx.preference.Preference r7 = r7.mPreference
            r7.setIntent(r2)
        Lb7:
            return r3
        Lb8:
            java.lang.String r7 = "isHideMobileNetworks: return true"
            android.util.Log.d(r5, r7)
            java.lang.String r7 = com.samsung.android.settings.connection.SecMobileNetworkPreferenceController.TAG
            java.lang.String r0 = "remove mobile network by HideMobildeNetwork"
            android.util.Log.i(r7, r0)
            return r1
        Lc6:
            java.lang.String r7 = com.samsung.android.settings.connection.SecMobileNetworkPreferenceController.TAG
            java.lang.String r0 = "remove mobile network by restricted or wifi only"
            android.util.Log.i(r7, r0)
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.connection.SecMobileNetworkPreferenceController.getAvailabilityStatus():int");
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY_MOBILE_NETWORK_SETTINGS;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!KEY_MOBILE_NETWORK_SETTINGS.equals(preference.getKey())) {
            return false;
        }
        LoggingHelper.insertFlowLogging(3550);
        this.mContext.startActivity(preference.getIntent());
        return true;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    public boolean isUserRestricted() {
        return this.mIsSecondaryUser
                || RestrictedLockUtilsInternal.hasBaseUserRestriction(
                        this.mContext, UserHandle.myUserId(), "no_config_mobile_networks");
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        if (isAvailable()) {
            if (this.mTelephonyCallback == null) {
                this.mTelephonyCallback = new MobileNetworkTelephonyCallback();
            }
            this.mTelephonyManager.registerTelephonyCallback(
                    this.mContext.getMainExecutor(), this.mTelephonyCallback);
        }
        BroadcastReceiver broadcastReceiver = this.mAirplanModeChangedReceiver;
        if (broadcastReceiver != null) {
            this.mContext.registerReceiver(
                    broadcastReceiver, new IntentFilter("android.intent.action.AIRPLANE_MODE"), 2);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        MobileNetworkTelephonyCallback mobileNetworkTelephonyCallback = this.mTelephonyCallback;
        if (mobileNetworkTelephonyCallback != null) {
            this.mTelephonyManager.unregisterTelephonyCallback(mobileNetworkTelephonyCallback);
        }
        BroadcastReceiver broadcastReceiver = this.mAirplanModeChangedReceiver;
        if (broadcastReceiver != null) {
            this.mContext.unregisterReceiver(broadcastReceiver);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if ((preference instanceof RestrictedPreference)
                && ((RestrictedPreference) preference).mHelper.mDisabledByAdmin) {
            return;
        }
        preference.setEnabled(
                Settings.Global.getInt(this.mContext.getContentResolver(), "airplane_mode_on", 0)
                        == 0);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public SecMobileNetworkPreferenceController(Context context, String str) {
        super(context, str);
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
