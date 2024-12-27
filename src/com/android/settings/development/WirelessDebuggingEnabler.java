package com.android.settings.development;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.debug.FingerprintAndPairDevice;
import android.debug.IAdbManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.widget.MainSwitchBarController;
import com.android.settings.widget.SwitchWidgetController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.restriction.RestrictionPolicy;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WirelessDebuggingEnabler
        implements SwitchWidgetController.OnSwitchChangeListener,
                LifecycleObserver,
                OnResume,
                OnPause {
    public final ContentResolver mContentResolver;
    public final Context mContext;
    public EnterpriseDeviceManager mEDM;
    public final OnEnabledListener mListener;
    public boolean mListeningToOnSwitchChange = false;
    public final AnonymousClass1 mRampartObserver;
    public RestrictionPolicy mRestrictionPolicy;
    public final AnonymousClass1 mSettingsObserver;
    public final SwitchWidgetController mSwitchWidget;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnEnabledListener {}

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.settings.development.WirelessDebuggingEnabler$1] */
    /* JADX WARN: Type inference failed for: r4v2, types: [com.android.settings.development.WirelessDebuggingEnabler$1] */
    public WirelessDebuggingEnabler(
            SettingsActivity settingsActivity,
            MainSwitchBarController mainSwitchBarController,
            OnEnabledListener onEnabledListener,
            Lifecycle lifecycle) {
        Handler handler = new Handler(Looper.getMainLooper());
        this.mEDM = null;
        this.mRestrictionPolicy = null;
        final int i = 0;
        this.mRampartObserver =
                new ContentObserver(
                        this,
                        handler) { // from class:
                                   // com.android.settings.development.WirelessDebuggingEnabler.1
                    public final /* synthetic */ WirelessDebuggingEnabler this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public void onChange(boolean z) {
                        switch (i) {
                            case 0:
                                super.onChange(z);
                                this.this$0.updateState$1();
                                break;
                            default:
                                super.onChange(z);
                                break;
                        }
                    }

                    @Override // android.database.ContentObserver
                    public void onChange(boolean z, Uri uri) {
                        switch (i) {
                            case 1:
                                Log.i(
                                        "WirelessDebuggingEnabler",
                                        "ADB_WIFI_ENABLED=" + this.this$0.isAdbWifiEnabled());
                                WirelessDebuggingEnabler wirelessDebuggingEnabler = this.this$0;
                                wirelessDebuggingEnabler.onWirelessDebuggingEnabled(
                                        wirelessDebuggingEnabler.isAdbWifiEnabled());
                                break;
                            default:
                                super.onChange(z, uri);
                                break;
                        }
                    }
                };
        this.mContext = settingsActivity;
        this.mSwitchWidget = mainSwitchBarController;
        mainSwitchBarController.mListener = this;
        mainSwitchBarController.setupView();
        this.mListener = onEnabledListener;
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
        this.mContentResolver = settingsActivity.getContentResolver();
        final int i2 = 1;
        this.mSettingsObserver =
                new ContentObserver(
                        this,
                        handler) { // from class:
                                   // com.android.settings.development.WirelessDebuggingEnabler.1
                    public final /* synthetic */ WirelessDebuggingEnabler this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public void onChange(boolean z) {
                        switch (i2) {
                            case 0:
                                super.onChange(z);
                                this.this$0.updateState$1();
                                break;
                            default:
                                super.onChange(z);
                                break;
                        }
                    }

                    @Override // android.database.ContentObserver
                    public void onChange(boolean z, Uri uri) {
                        switch (i2) {
                            case 1:
                                Log.i(
                                        "WirelessDebuggingEnabler",
                                        "ADB_WIFI_ENABLED=" + this.this$0.isAdbWifiEnabled());
                                WirelessDebuggingEnabler wirelessDebuggingEnabler = this.this$0;
                                wirelessDebuggingEnabler.onWirelessDebuggingEnabled(
                                        wirelessDebuggingEnabler.isAdbWifiEnabled());
                                break;
                            default:
                                super.onChange(z, uri);
                                break;
                        }
                    }
                };
    }

    public final boolean isAdbWifiEnabled() {
        return Settings.Global.getInt(this.mContentResolver, "adb_wifi_enabled", 0) != 0;
    }

    public final boolean isUsbDebuggingAllowed() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        EnterpriseDeviceManager enterpriseDeviceManager = this.mEDM;
        if (enterpriseDeviceManager != null && this.mRestrictionPolicy == null) {
            this.mRestrictionPolicy = enterpriseDeviceManager.getRestrictionPolicy();
        }
        boolean isUsbDebuggingEnabled = this.mRestrictionPolicy.isUsbDebuggingEnabled();
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "isUsbDebuggingAllowed = ", "WirelessDebuggingEnabler", isUsbDebuggingEnabled);
        return isUsbDebuggingEnabled;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public final void onPause() {
        if (this.mListeningToOnSwitchChange) {
            this.mSwitchWidget.stopListening();
            this.mListeningToOnSwitchChange = false;
        }
        this.mContentResolver.unregisterContentObserver(this.mSettingsObserver);
        this.mContentResolver.unregisterContentObserver(this.mRampartObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        boolean z = this.mListeningToOnSwitchChange;
        SwitchWidgetController switchWidgetController = this.mSwitchWidget;
        if (!z) {
            switchWidgetController.startListening();
            this.mListeningToOnSwitchChange = true;
        }
        onWirelessDebuggingEnabled(isAdbWifiEnabled());
        this.mContentResolver.registerContentObserver(
                Settings.Global.getUriFor("adb_wifi_enabled"), false, this.mSettingsObserver);
        if (isUsbDebuggingAllowed()) {
            switchWidgetController.setEnabled(true);
        } else {
            switchWidgetController.setChecked(false);
            switchWidgetController.setEnabled(false);
        }
        this.mContentResolver.registerContentObserver(
                Settings.Secure.getUriFor("rampart_blocked_adb_cmd"), false, this.mRampartObserver);
        updateState$1();
    }

    @Override // com.android.settings.widget.SwitchWidgetController.OnSwitchChangeListener
    public final boolean onSwitchToggled(boolean z) {
        if (!z || WirelessDebuggingPreferenceController.isWifiConnected(this.mContext)) {
            Settings.Global.putInt(
                    this.mContext.getContentResolver(), "adb_wifi_enabled", z ? 1 : 0);
            return true;
        }
        Toast.makeText(this.mContext, R.string.adb_wireless_no_network_msg, 1).show();
        this.mSwitchWidget.setChecked(false);
        return false;
    }

    public final void onWirelessDebuggingEnabled(boolean z) {
        this.mSwitchWidget.setChecked(z);
        OnEnabledListener onEnabledListener = this.mListener;
        if (onEnabledListener != null) {
            WirelessDebuggingFragment wirelessDebuggingFragment =
                    (WirelessDebuggingFragment) onEnabledListener;
            if (!z) {
                wirelessDebuggingFragment.mDeviceNamePreference.setVisible(false);
                wirelessDebuggingFragment.mIpAddrPreference.setVisible(false);
                wirelessDebuggingFragment.mPairingMethodsCategory.setVisible(false);
                wirelessDebuggingFragment.mPairedDevicesCategory.setVisible(false);
                wirelessDebuggingFragment.mFooterCategory.setVisible(true);
                return;
            }
            wirelessDebuggingFragment.mDeviceNamePreference.setVisible(true);
            wirelessDebuggingFragment.mIpAddrPreference.setVisible(true);
            wirelessDebuggingFragment.mPairingMethodsCategory.setVisible(true);
            wirelessDebuggingFragment.mPairedDevicesCategory.setVisible(true);
            wirelessDebuggingFragment.mFooterCategory.setVisible(false);
            IAdbManager asInterface =
                    IAdbManager.Stub.asInterface(ServiceManager.getService("adb"));
            wirelessDebuggingFragment.mAdbManager = asInterface;
            try {
                FingerprintAndPairDevice[] pairedDevices = asInterface.getPairedDevices();
                HashMap hashMap = new HashMap();
                for (FingerprintAndPairDevice fingerprintAndPairDevice : pairedDevices) {
                    hashMap.put(
                            fingerprintAndPairDevice.keyFingerprint,
                            fingerprintAndPairDevice.device);
                }
                wirelessDebuggingFragment.updatePairedDevicePreferences(hashMap);
                int adbWirelessPort = wirelessDebuggingFragment.mAdbManager.getAdbWirelessPort();
                wirelessDebuggingFragment.mConnectionPort = adbWirelessPort;
                if (adbWirelessPort > 0) {
                    Log.i(
                            "WirelessDebuggingFrag",
                            "onEnabled(): connect_port="
                                    + wirelessDebuggingFragment.mConnectionPort);
                }
            } catch (RemoteException unused) {
                Log.e(
                        "WirelessDebuggingFrag",
                        "Unable to request the paired list for Adb wireless");
            }
            WirelessDebuggingFragment.sAdbIpAddressPreferenceController.updateState(
                    wirelessDebuggingFragment.mIpAddrPreference);
        }
    }

    public final void updateState$1() {
        boolean isUsbDebuggingAllowed = isUsbDebuggingAllowed();
        SwitchWidgetController switchWidgetController = this.mSwitchWidget;
        if (isUsbDebuggingAllowed
                && Settings.Secure.getInt(
                                this.mContext.getContentResolver(), "rampart_blocked_adb_cmd", 0)
                        != 1) {
            switchWidgetController.setEnabled(true);
        } else {
            switchWidgetController.setChecked(false);
            switchWidgetController.setEnabled(false);
        }
    }
}
