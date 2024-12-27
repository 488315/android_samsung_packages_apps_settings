package com.android.settings.development;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.debug.IAdbManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.preference.Preference;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.restriction.RestrictionPolicy;
import com.samsung.android.settings.widget.SecRestrictedSwitchPreferenceScreen;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WirelessDebuggingPreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener,
                PreferenceControllerMixin,
                LifecycleObserver,
                OnResume,
                OnPause {
    public final IAdbManager mAdbManager;
    public final ContentResolver mContentResolver;
    public EnterpriseDeviceManager mEDM;
    public final AnonymousClass1 mRampartObserver;
    public RestrictionPolicy mRestrictionPolicy;
    public final AnonymousClass1 mSettingsObserver;

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.settings.development.WirelessDebuggingPreferenceController$1] */
    /* JADX WARN: Type inference failed for: r6v4, types: [com.android.settings.development.WirelessDebuggingPreferenceController$1] */
    public WirelessDebuggingPreferenceController(Context context, Lifecycle lifecycle) {
        super(context);
        Handler handler = new Handler(Looper.getMainLooper());
        this.mEDM = null;
        this.mRestrictionPolicy = null;
        final int i = 0;
        this.mRampartObserver =
                new ContentObserver(
                        this,
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.android.settings.development.WirelessDebuggingPreferenceController.1
                    public final /* synthetic */ WirelessDebuggingPreferenceController this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public void onChange(boolean z) {
                        switch (i) {
                            case 0:
                                super.onChange(z);
                                WirelessDebuggingPreferenceController
                                        wirelessDebuggingPreferenceController = this.this$0;
                                wirelessDebuggingPreferenceController.updateState(
                                        wirelessDebuggingPreferenceController.mPreference);
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
                                WirelessDebuggingPreferenceController
                                        wirelessDebuggingPreferenceController = this.this$0;
                                wirelessDebuggingPreferenceController.updateState(
                                        wirelessDebuggingPreferenceController.mPreference);
                                break;
                            default:
                                super.onChange(z, uri);
                                break;
                        }
                    }
                };
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
        this.mAdbManager = IAdbManager.Stub.asInterface(ServiceManager.getService("adb"));
        final int i2 = 1;
        this.mSettingsObserver =
                new ContentObserver(
                        this,
                        handler) { // from class:
                                   // com.android.settings.development.WirelessDebuggingPreferenceController.1
                    public final /* synthetic */ WirelessDebuggingPreferenceController this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public void onChange(boolean z) {
                        switch (i2) {
                            case 0:
                                super.onChange(z);
                                WirelessDebuggingPreferenceController
                                        wirelessDebuggingPreferenceController = this.this$0;
                                wirelessDebuggingPreferenceController.updateState(
                                        wirelessDebuggingPreferenceController.mPreference);
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
                                WirelessDebuggingPreferenceController
                                        wirelessDebuggingPreferenceController = this.this$0;
                                wirelessDebuggingPreferenceController.updateState(
                                        wirelessDebuggingPreferenceController.mPreference);
                                break;
                            default:
                                super.onChange(z, uri);
                                break;
                        }
                    }
                };
        this.mContentResolver = context.getContentResolver();
    }

    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return false;
        }
        for (Network network : connectivityManager.getAllNetworks()) {
            NetworkCapabilities networkCapabilities =
                    connectivityManager.getNetworkCapabilities(network);
            if (networkCapabilities != null && networkCapabilities.hasTransport(1)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "toggle_adb_wireless";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        try {
            return this.mAdbManager.isAdbWifiSupported();
        } catch (RemoteException e) {
            Log.e("WirelessDebugPrefCtrl", "Unable to check if adb wifi is supported.", e);
            return false;
        }
    }

    public final boolean isRampartBlockedAdbCommand$1() {
        return Settings.Secure.getInt(
                        this.mContext.getContentResolver(), "rampart_blocked_adb_cmd", 0)
                == 1;
    }

    public final boolean isUsbDebuggingAllowed$1() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        EnterpriseDeviceManager enterpriseDeviceManager = this.mEDM;
        if (enterpriseDeviceManager != null && this.mRestrictionPolicy == null) {
            this.mRestrictionPolicy = enterpriseDeviceManager.getRestrictionPolicy();
        }
        boolean isUsbDebuggingEnabled = this.mRestrictionPolicy.isUsbDebuggingEnabled();
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "isUsbDebuggingAllowed = ", "WirelessDebugPrefCtrl", isUsbDebuggingEnabled);
        return isUsbDebuggingEnabled;
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        this.mPreference.setEnabled(false);
        Settings.Global.putInt(this.mContext.getContentResolver(), "adb_wifi_enabled", 0);
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchEnabled() {
        super.onDeveloperOptionsSwitchEnabled();
        if (isUsbDebuggingAllowed$1()) {
            ((SecRestrictedSwitchPreferenceScreen) this.mPreference).setEnabled(true);
        } else {
            ((SecRestrictedSwitchPreferenceScreen) this.mPreference).setEnabled(false);
        }
        if (!isUsbDebuggingAllowed$1() || isRampartBlockedAdbCommand$1()) {
            ((SecRestrictedSwitchPreferenceScreen) this.mPreference).setEnabled(false);
        } else {
            ((SecRestrictedSwitchPreferenceScreen) this.mPreference).setEnabled(true);
        }
        if (isRampartBlockedAdbCommand$1()) {
            this.mPreference.setSummary(R.string.restricted_by_auto_blocker);
        } else {
            this.mPreference.setSummary(R.string.enable_adb_wireless_summary);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public final void onPause() {
        this.mContentResolver.unregisterContentObserver(this.mSettingsObserver);
        this.mContext.getContentResolver().unregisterContentObserver(this.mRampartObserver);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        if (!booleanValue || isWifiConnected(this.mContext)) {
            Settings.Global.putInt(
                    this.mContext.getContentResolver(), "adb_wifi_enabled", booleanValue ? 1 : 0);
            return true;
        }
        Toast.makeText(this.mContext, R.string.adb_wireless_no_network_msg, 1).show();
        return false;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        this.mContentResolver.registerContentObserver(
                Settings.Global.getUriFor("adb_wifi_enabled"), false, this.mSettingsObserver);
        this.mContentResolver.registerContentObserver(
                Settings.Secure.getUriFor("rampart_blocked_adb_cmd"), false, this.mRampartObserver);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        SecRestrictedSwitchPreferenceScreen secRestrictedSwitchPreferenceScreen =
                (SecRestrictedSwitchPreferenceScreen) preference;
        secRestrictedSwitchPreferenceScreen.setChecked(
                Settings.Global.getInt(this.mContentResolver, "adb_wifi_enabled", 0) != 0);
        if (isUsbDebuggingAllowed$1()) {
            secRestrictedSwitchPreferenceScreen.setEnabled(true);
        } else {
            secRestrictedSwitchPreferenceScreen.setEnabled(false);
        }
        if (!isUsbDebuggingAllowed$1() || isRampartBlockedAdbCommand$1()) {
            secRestrictedSwitchPreferenceScreen.setEnabled(false);
        } else {
            secRestrictedSwitchPreferenceScreen.setEnabled(true);
        }
        if (isRampartBlockedAdbCommand$1()) {
            this.mPreference.setSummary(R.string.restricted_by_auto_blocker);
        } else {
            this.mPreference.setSummary(R.string.enable_adb_wireless_summary);
        }
    }
}
