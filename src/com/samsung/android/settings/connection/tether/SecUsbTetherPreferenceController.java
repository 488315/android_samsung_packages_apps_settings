package com.samsung.android.settings.connection.tether;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.TetheringManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamMediaService$4$$ExternalSyntheticOutline0;
import com.android.settings.core.TogglePreferenceController;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.datausage.DataSaverBackend;
import com.android.settings.datausage.DataUsageUtils;
import com.android.settings.network.apn.ApnPreference$$ExternalSyntheticOutline0;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.WirelessUtils;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnCreate;
import com.android.settingslib.core.lifecycle.events.OnDestroy;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.connection.ConnectionsUtils;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecRestrictedSwitchPreference;

import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecUsbTetherPreferenceController extends TogglePreferenceController
        implements SecTetherControllerInterface,
                LifecycleObserver,
                OnCreate,
                OnStart,
                OnStop,
                OnDestroy,
                DataSaverBackend.Listener {
    private static final int DIALOG_USB_TETHER_FIRST_USE = 4;
    private static final int DIALOG_USB_TETHER_HELP = 5;
    private static final int DIALOG_USB_TETHER_NO_SIM = 2;
    private static final int DIALOG_USB_TETHER_USCC = 1;
    private static final int DIALOG_USB_TETHER_WARNING_WIFI_CALLING = 3;
    private static final String PREF_DNS_FIRST_USB_TETHER_DIALOG =
            "pref_dns_first_usb_tether_dialog";
    private static final String TAG = "SecUsbTetherPreferenceController";
    private static final String TETHER_SHARED_PREF_NAME = "TetherPref";
    private DataSaverBackend mDataSaverBackend;
    private boolean mDataSaverEnabled;
    private UsbTetherDialog mDialog;
    private SecTetherSettings mFragment;
    private boolean mMassStorageActive;
    private SecRestrictedSwitchPreference mPreference;
    private TetheringManager mTm;
    private boolean mUsbConnected;
    private String[] mUsbRegexs;
    private BroadcastReceiver mUsbTetherReceiver;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class UsbTetherReceiver extends BroadcastReceiver {
        public UsbTetherReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (AudioStreamMediaService$4$$ExternalSyntheticOutline0.m(
                    "onReceive() :",
                    action,
                    SecUsbTetherPreferenceController.TAG,
                    "android.intent.action.AIRPLANE_MODE")) {
                SecUsbTetherPreferenceController.this.updateController();
                return;
            }
            if (action.equals("android.intent.action.MEDIA_SHARED")) {
                SecUsbTetherPreferenceController.this.mMassStorageActive = true;
                SecUsbTetherPreferenceController.this.updateController();
                return;
            }
            if (action.equals("android.intent.action.MEDIA_UNSHARED")) {
                SecUsbTetherPreferenceController.this.mMassStorageActive = false;
                SecUsbTetherPreferenceController.this.updateController();
                return;
            }
            if (!action.equals("android.hardware.usb.action.USB_STATE")) {
                if (action.equals("android.net.conn.TETHER_STATE_CHANGED")) {
                    SecUsbTetherPreferenceController.this.updateController();
                    return;
                }
                return;
            }
            SecUsbTetherPreferenceController.this.mUsbConnected =
                    intent.getBooleanExtra("connected", false);
            SecUsbTetherPreferenceController.this.updateController();
            if (SecUsbTetherPreferenceController.this.mUsbConnected) {
                return;
            }
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            String salesCode = Utils.getSalesCode();
            if ("TMB".equals(salesCode)
                    || "ASR".equals(salesCode)
                    || ConnectionsUtils.isMetroPCS()) {
                SecUsbTetherPreferenceController.this.removeDialog(4);
            }
        }
    }

    public SecUsbTetherPreferenceController(Context context, String str) {
        super(context, str);
        this.mTm = (TetheringManager) this.mContext.getSystemService("tethering");
    }

    private boolean isWifiConnected() {
        NetworkCapabilities networkCapabilities;
        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.mContext.getSystemService("connectivity");
        Network activeNetwork = connectivityManager.getActiveNetwork();
        if (activeNetwork == null
                || (networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork))
                        == null) {
            return false;
        }
        return networkCapabilities.hasTransport(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeDialog(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(i, "remove msg : ", TAG);
        UsbTetherDialog usbTetherDialog = this.mDialog;
        if (usbTetherDialog != null && usbTetherDialog.dialogId == i) {
            usbTetherDialog.dismissAllowingStateLoss();
        }
        this.mDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDialog(int i) {
        UsbTetherDialog usbTetherDialog;
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(i, "show msg : ", TAG);
        SecTetherSettings secTetherSettings = this.mFragment;
        if (secTetherSettings == null) {
            Log.d(TAG, "Fragment is not ready");
            usbTetherDialog = null;
        } else {
            UsbTetherDialog.usbTetherController = this;
            UsbTetherDialog usbTetherDialog2 = new UsbTetherDialog();
            Bundle bundle = new Bundle();
            bundle.putInt("dialog_id", i);
            usbTetherDialog2.setArguments(bundle);
            usbTetherDialog2.setTargetFragment(secTetherSettings, 0);
            usbTetherDialog2.show(secTetherSettings.getFragmentManager(), "usb_tether_dialog");
            usbTetherDialog = usbTetherDialog2;
        }
        this.mDialog = usbTetherDialog;
    }

    private void startUsbTethering(boolean z) {
        String str = TAG;
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m("setUsbTethering : ", str, z);
        if (this.mTm.setUsbTethering(z) != 0) {
            Log.d(str, "setUsbTethering : usb tethring error");
            this.mPreference.setSummary(R.string.usb_tethering_errored_subtext);
            this.mPreference.setChecked(false);
            return;
        }
        boolean z2 =
                Settings.System.getInt(
                                this.mContext.getContentResolver(), "usb_tethering_enabled", 0)
                        == 1;
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m("usbTetherEnabled : ", str, z2);
        if (z2 != z) {
            this.mPreference.setEnabled(false);
            Settings.System.putInt(
                    this.mContext.getContentResolver(), "usb_tethering_enabled", !z ? 0 : 1);
            if (z) {
                return;
            }
            Settings.System.putInt(this.mContext.getContentResolver(), "enable_mtp_settings", 0);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (SecRestrictedSwitchPreference) preferenceScreen.findPreference(getPreferenceKey());
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x005d A[ORIG_RETURN, RETURN] */
    @Override // com.android.settings.core.BasePreferenceController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getAvailabilityStatus() {
        /*
            r7 = this;
            android.net.TetheringManager r7 = r7.mTm
            java.lang.String[] r7 = r7.getTetherableUsbRegexs()
            int r7 = r7.length
            r0 = 1
            r1 = 0
            if (r7 == 0) goto Ld
            r7 = r0
            goto Le
        Ld:
            r7 = r1
        Le:
            java.lang.String r2 = com.samsung.android.settings.connection.tether.SecUsbTetherPreferenceController.TAG
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "usbAvailable : "
            r3.<init>(r4)
            r3.append(r7)
            java.lang.String r3 = r3.toString()
            android.util.Log.d(r2, r3)
            com.samsung.android.feature.SemCscFeature r3 = com.samsung.android.feature.SemCscFeature.getInstance()
            java.lang.String r5 = "CscFeature_Common_EnableSprintExtension"
            boolean r3 = r3.getBoolean(r5)
            if (r3 == 0) goto L4d
            java.lang.String r3 = "persist.sys.tether_data"
            r5 = -1
            int r3 = android.os.SystemProperties.getInt(r3, r5)
            java.lang.String r6 = "mTetheredData : "
            androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0.m1m(r3, r6, r2)
            r6 = 3
            if (r3 >= r6) goto L3f
            r7 = r1
        L3f:
            java.lang.String r3 = "persist.sys.tether_data_usb"
            int r3 = android.os.SystemProperties.getInt(r3, r5)
            if (r3 == r5) goto L4d
            if (r3 <= 0) goto L4b
            goto L4e
        L4b:
            r0 = r1
            goto L4e
        L4d:
            r0 = r7
        L4e:
            com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0.m(r4, r2, r0)
            if (r0 == 0) goto L5d
            java.lang.StringBuilder r7 = com.android.settings.Utils.sBuilder
            boolean r7 = android.app.ActivityManager.isUserAMonkey()
            if (r7 == 0) goto L5c
            goto L5d
        L5c:
            return r1
        L5d:
            r7 = 4
            return r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.connection.tether.SecUsbTetherPreferenceController.getAvailabilityStatus():int");
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return this.mPreference.mChecked;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    public void onActivityResult(int i, int i2) {
        if (i2 == -1) {
            startUsbTethering(true);
            return;
        }
        Log.i(TAG, "USB Tethering on failed by Provision result");
        if (WirelessUtils.isAirplaneModeOn(this.mContext)) {
            Context context = this.mContext;
            ApnPreference$$ExternalSyntheticOutline0.m(
                    context, R.string.tethering_provisioning_fail, context, 0);
        }
        this.mPreference.setChecked(false);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnCreate
    public void onCreate(Bundle bundle) {
        DataSaverBackend dataSaverBackend = new DataSaverBackend(this.mContext);
        this.mDataSaverBackend = dataSaverBackend;
        dataSaverBackend.addListener(this);
        this.mUsbRegexs = this.mTm.getTetherableUsbRegexs();
    }

    @Override // com.samsung.android.settings.connection.tether.SecTetherControllerInterface,
              // com.android.settings.datausage.DataSaverBackend.Listener
    public void onDataSaverChanged(boolean z) {
        this.mDataSaverEnabled = z;
        SecRestrictedSwitchPreference secRestrictedSwitchPreference = this.mPreference;
        if (secRestrictedSwitchPreference != null) {
            secRestrictedSwitchPreference.setEnabled(!z);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnDestroy
    public void onDestroy() {
        this.mDataSaverBackend.remListener(this);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        this.mMassStorageActive = "shared".equals(Environment.getExternalStorageState());
        this.mUsbTetherReceiver = new UsbTetherReceiver();
        IntentFilter intentFilter = new IntentFilter("android.intent.action.MEDIA_SHARED");
        intentFilter.addAction("android.intent.action.MEDIA_UNSHARED");
        intentFilter.addAction("android.hardware.usb.action.USB_STATE");
        intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
        intentFilter.addAction("android.net.conn.TETHER_STATE_CHANGED");
        Intent registerReceiver =
                this.mContext.registerReceiver(this.mUsbTetherReceiver, intentFilter, 2);
        if (registerReceiver != null) {
            this.mUsbTetherReceiver.onReceive(this.mContext, registerReceiver);
        }
        updateController();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        BroadcastReceiver broadcastReceiver = this.mUsbTetherReceiver;
        if (broadcastReceiver != null) {
            this.mContext.unregisterReceiver(broadcastReceiver);
        }
        this.mUsbTetherReceiver = null;
        UsbTetherDialog usbTetherDialog = this.mDialog;
        if (usbTetherDialog != null) {
            usbTetherDialog.dismissAllowingStateLoss();
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        String salesCode = Utils.getSalesCode();
        this.mFragment.getClass();
        LoggingHelper.insertEventLogging(90, 3440, z);
        boolean semGetAllowInternetSharing =
                ((DevicePolicyManager) this.mContext.getSystemService("device_policy"))
                        .semGetAllowInternetSharing(null);
        if (z && !semGetAllowInternetSharing) {
            Log.d("TetherSettings", "Internet Sharing is restricted by MDM.");
            this.mPreference.setChecked(false);
            Context context = this.mContext;
            ApnPreference$$ExternalSyntheticOutline0.m(
                    context, android.R.string.ime_action_search, context, 0);
            return false;
        }
        if (("VZW".equals(salesCode) || "VPP".equals(salesCode))
                && !DataUsageUtils.hasActiveSim(this.mContext)) {
            Log.d(TAG, "SIM is Not Ready");
            this.mPreference.setChecked(false);
            TetheringManager tetheringManager = this.mTm;
            if (tetheringManager != null) {
                tetheringManager.stopTethering(1);
            }
            showDialog(2);
            return false;
        }
        if (!z) {
            startUsbTethering(z);
        } else if ("USC".equals(salesCode)) {
            showDialog(1);
        } else if ("TMB".equals(salesCode)
                || "ASR".equals(salesCode)
                || ConnectionsUtils.isMetroPCS()) {
            if (this.mContext
                            .getSharedPreferences(TETHER_SHARED_PREF_NAME, 0)
                            .getBoolean(PREF_DNS_FIRST_USB_TETHER_DIALOG, false)
                    || Utils.hasPackage(this.mContext, "com.sec.tetheringprovision")) {
                startProvisioningIfNecessary(this.mFragment, 1);
            } else {
                showDialog(4);
            }
        } else if (isWifiConnected()) {
            Log.d(TAG, "isWifiConnected: true");
            startUsbTethering(z);
        } else {
            startProvisioningIfNecessary(this.mFragment, 1);
        }
        return false;
    }

    public void setHost(SecTetherSettings secTetherSettings) {
        this.mFragment = secTetherSettings;
    }

    @Override // com.samsung.android.settings.connection.tether.SecTetherControllerInterface
    public /* bridge */ /* synthetic */ void startProvisioningIfNecessary(
            SecTetherSettings secTetherSettings, int i) {
        super.startProvisioningIfNecessary(secTetherSettings, i);
    }

    @Override // com.samsung.android.settings.connection.tether.SecTetherControllerInterface
    public void startTethering() {
        startUsbTethering(true);
    }

    @Override // com.samsung.android.settings.connection.tether.SecTetherControllerInterface
    public void updateController() {
        Log.d(TAG, "updateController Start");
        updateController(
                this.mTm.getTetherableIfaces(),
                this.mTm.getTetheredIfaces(),
                this.mTm.getTetheringErroredIfaces());
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public void updateController(String[] strArr, String[] strArr2, String[] strArr3) {
        boolean z;
        int i = 1;
        int i2 = 0;
        if (ConnectionsUtils.isSatelliteNetworksOn(this.mContext)) {
            Log.d(TAG, "Tethering is disabled by UsingNonTerrestrialNetwork");
            this.mPreference.setEnabled(false);
            this.mPreference.setChecked(false);
            return;
        }
        boolean z2 =
                Utils.getEnterprisePolicyEnabled(
                                this.mContext,
                                "content://com.sec.knox.provider/RestrictionPolicy4",
                                "isUsbTetheringEnabled")
                        == 1;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        String salesCode = Utils.getSalesCode();
        if (!z2) {
            Log.d(TAG, "USB Tethering is disabled by MDM");
            this.mPreference.setEnabled(false);
            this.mPreference.setChecked(false);
            return;
        }
        boolean z3 = this.mUsbConnected && !this.mMassStorageActive;
        int i3 = 0;
        for (String str2 : strArr) {
            for (String str3 : this.mUsbRegexs) {
                if (str2.matches(str3) && i3 == 0) {
                    i3 = this.mTm.getLastTetherError(str2);
                }
            }
        }
        int length = strArr2.length;
        int i4 = 0;
        boolean z4 = false;
        boolean z5 = false;
        while (i4 < length) {
            String str4 = strArr2[i4];
            String[] strArr4 = this.mUsbRegexs;
            int length2 = strArr4.length;
            int i5 = i2;
            while (i5 < length2) {
                String str5 = strArr4[i5];
                if ((str4.matches(str5) && str4.contains("usb"))
                        || (str4.matches(str5) && str4.contains("rndis"))) {
                    DialogFragment$$ExternalSyntheticOutline0.m("regx :", str5, TAG);
                    i = 1;
                    z5 = true;
                } else if (str4.matches(str5) && str4.contains("ncm")) {
                    i = 1;
                    z4 = true;
                } else {
                    i = 1;
                }
                i5 += i;
            }
            i4 += i;
            i2 = 0;
        }
        boolean z6 = false;
        for (String str6 : strArr3) {
            for (String str7 : this.mUsbRegexs) {
                if (str6.matches(str7)) {
                    z6 = true;
                }
            }
        }
        String str8 = TAG;
        StringBuilder m =
                RowView$$ExternalSyntheticOutline0.m("usbAvailable : ", ", mUsbConnected : ", z3);
        m.append(this.mUsbConnected);
        m.append(", mMassStorageActive : ");
        m.append(this.mMassStorageActive);
        Log.d(str8, m.toString());
        Log.d(
                str8,
                "usbError : "
                        + i3
                        + ", usbTethered : "
                        + z5
                        + ", usbErrored : "
                        + z6
                        + ", ncmEnabled : "
                        + z4);
        StringBuilder sb = new StringBuilder("UsbRegexs : ");
        sb.append(Arrays.toString(this.mUsbRegexs));
        sb.append(", tethered : ");
        MainClearConfirm$$ExternalSyntheticOutline0.m(sb, Arrays.toString(strArr2), str8);
        boolean z7 =
                Settings.System.getInt(
                                this.mContext.getContentResolver(), "usb_tethering_enabled", 0)
                        == 1;
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                RowView$$ExternalSyntheticOutline0.m(
                        "updateUsbState usbTetheringEnableValue : ", ", mDataSaverEnabled : ", z7),
                this.mDataSaverEnabled,
                str8);
        if (z5) {
            if (!z7) {
                Settings.System.putInt(
                        this.mContext.getContentResolver(), "usb_tethering_enabled", 1);
                Log.d(str8, "USB tethering is already turned ON , but value didn't update yet ");
            }
            if (!"TMB".equals(salesCode)
                    && !"ASR".equals(salesCode)
                    && !ConnectionsUtils.isMetroPCS()) {
                if (Utils.isTablet()) {
                    this.mPreference.setSummary(R.string.usb_tethering_active_subtext_tablet);
                } else {
                    this.mPreference.setSummary(R.string.usb_tethering_active_subtext);
                }
                z = true;
                this.mPreference.setEnabled(!this.mDataSaverEnabled);
            } else {
                z = true;
                this.mPreference.setSummary(R.string.usb_tethering_active_subtext_tmo);
                this.mPreference.setEnabled(true);
            }
            this.mPreference.setChecked(z);
        } else if (z3) {
            if (z7) {
                Settings.System.putInt(
                        this.mContext.getContentResolver(), "usb_tethering_enabled", 0);
                Log.d(
                        str8,
                        "1: USB tethering condition value is not synced with real condition So make"
                            + " it sync !");
            }
            if (i3 != 0) {
                this.mPreference.setSummary(R.string.usb_tethering_errored_subtext);
            } else if (!z4) {
                if (!"TMB".equals(salesCode)
                        && !"ASR".equals(salesCode)
                        && !"TMK".equals(salesCode)) {
                    if (Utils.isTablet()) {
                        this.mPreference.setSummary(
                                R.string.usb_tethering_available_subtext_tablet);
                    } else {
                        this.mPreference.setSummary(R.string.usb_tethering_available_subtext);
                    }
                } else {
                    this.mPreference.setSummary(R.string.usb_tethering_available_subtext_tmo);
                }
            }
            if (!z4) {
                this.mPreference.setEnabled(!this.mDataSaverEnabled);
                this.mPreference.setChecked(false);
            } else {
                this.mPreference.setEnabled(false);
                this.mPreference.setChecked(false);
                this.mPreference.setSummary(R.string.usb_tethering_disabled_ncm_subtext);
            }
        } else if (z6) {
            if (z7) {
                Settings.System.putInt(
                        this.mContext.getContentResolver(), "usb_tethering_enabled", 0);
                Log.d(
                        str8,
                        "2: USB tethering condition value is not synced with real condition So make"
                            + " it sync !");
            }
            this.mPreference.setSummary(R.string.usb_tethering_errored_subtext);
            this.mPreference.setEnabled(false);
            this.mPreference.setChecked(false);
        } else if (this.mMassStorageActive) {
            if (z7) {
                Settings.System.putInt(
                        this.mContext.getContentResolver(), "usb_tethering_enabled", 0);
                Log.d(
                        str8,
                        "3: USB tethering condition value is not synced with real condition So make"
                            + " it sync !");
            }
            this.mPreference.setSummary(R.string.usb_tethering_storage_active_subtext);
            this.mPreference.setEnabled(false);
            this.mPreference.setChecked(false);
        } else {
            if (z7) {
                Settings.System.putInt(
                        this.mContext.getContentResolver(), "usb_tethering_enabled", 0);
                Log.d(
                        str8,
                        "4 : USB tethering condition value is not synced with real condition So"
                            + " make it sync !");
            }
            if (!"TMB".equals(salesCode) && !"ASR".equals(salesCode) && !"TMK".equals(salesCode)) {
                this.mPreference.setSummary(R.string.usb_tethering_unavailable_subtext);
            } else {
                this.mPreference.setSummary(R.string.usb_tethering_unavailable_subtext_tmo);
            }
            this.mPreference.setEnabled(false);
            this.mPreference.setChecked(false);
            Log.d(str8, "setDisable");
        }
        RestrictedLockUtils.EnforcedAdmin checkIfUsbDataSignalingIsDisabled =
                RestrictedLockUtilsInternal.checkIfUsbDataSignalingIsDisabled(
                        this.mContext, UserHandle.myUserId());
        if (checkIfUsbDataSignalingIsDisabled != null) {
            Log.d(str8, "USB tethering condition is in enforcedAdmin");
            this.mPreference.setDisabledByAdmin(checkIfUsbDataSignalingIsDisabled);
        }
    }

    public void onActivityCreated(Bundle bundle) {}

    public void onSaveInstanceState(Bundle bundle) {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class UsbTetherDialog extends InstrumentedDialogFragment
            implements DialogInterface.OnDismissListener {
        public static SecUsbTetherPreferenceController usbTetherController;
        public int dialogId;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.samsung.android.settings.connection.tether.SecUsbTetherPreferenceController$UsbTetherDialog$1, reason: invalid class name */
        public final class AnonymousClass1 implements DialogInterface.OnClickListener {
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SecUsbTetherPreferenceController secUsbTetherPreferenceController =
                        UsbTetherDialog.usbTetherController;
                secUsbTetherPreferenceController.startProvisioningIfNecessary(
                        secUsbTetherPreferenceController.mFragment, 1);
            }
        }

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.samsung.android.settings.connection.tether.SecUsbTetherPreferenceController$UsbTetherDialog$3, reason: invalid class name */
        public final class AnonymousClass3 implements DialogInterface.OnClickListener {
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                UsbTetherDialog.usbTetherController.mPreference.setChecked(false);
            }
        }

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.samsung.android.settings.connection.tether.SecUsbTetherPreferenceController$UsbTetherDialog$4, reason: invalid class name */
        public final class AnonymousClass4 implements DialogInterface.OnClickListener {
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                UsbTetherDialog.usbTetherController.mPreference.setChecked(false);
            }
        }

        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 90;
        }

        @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
                  // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            this.dialogId = getArguments().getInt("dialog_id");
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:67:0x02fa A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Type inference failed for: r8v0 */
        /* JADX WARN: Type inference failed for: r8v1, types: [java.io.InputStream] */
        /* JADX WARN: Type inference failed for: r8v2 */
        @Override // androidx.fragment.app.DialogFragment
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final android.app.Dialog onCreateDialog(android.os.Bundle r15) {
            /*
                Method dump skipped, instructions count: 767
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.samsung.android.settings.connection.tether.SecUsbTetherPreferenceController.UsbTetherDialog.onCreateDialog(android.os.Bundle):android.app.Dialog");
        }

        @Override // androidx.fragment.app.DialogFragment,
                  // android.content.DialogInterface.OnDismissListener
        public final void onDismiss(DialogInterface dialogInterface) {
            super.onDismiss(dialogInterface);
            if (this.dialogId == 4 && usbTetherController.mUsbConnected) {
                SecUsbTetherPreferenceController secUsbTetherPreferenceController =
                        usbTetherController;
                secUsbTetherPreferenceController.startProvisioningIfNecessary(
                        secUsbTetherPreferenceController.mFragment, 1);
            }
        }

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.samsung.android.settings.connection.tether.SecUsbTetherPreferenceController$UsbTetherDialog$2, reason: invalid class name */
        public final class AnonymousClass2 implements DialogInterface.OnClickListener {
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {}
        }

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.samsung.android.settings.connection.tether.SecUsbTetherPreferenceController$UsbTetherDialog$5, reason: invalid class name */
        public final class AnonymousClass5 implements DialogInterface.OnClickListener {
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {}
        }
    }

    @Override // com.android.settings.datausage.DataSaverBackend.Listener
    public void onAllowlistStatusChanged(int i, boolean z) {}

    @Override // com.android.settings.datausage.DataSaverBackend.Listener
    public void onDenylistStatusChanged(int i, boolean z) {}
}
