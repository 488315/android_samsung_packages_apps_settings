package com.samsung.android.settings.connection.ethernet.controller;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.EthernetManager;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.connection.ConnectionsUtils;
import com.samsung.android.settings.connection.WirelessSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class EthernetSettingsPreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    private static final int ETH_DEVICE_STATE_CONNECTED = 2;
    private static final String KEY_ETHERNET_SETTINGS = "ethernet_settings";
    private static final String TAG = "EthernetSettingsPreferenceController";
    private final ContentObserver mEthernetStateReceiver;
    private final Handler mHandler;
    private Preference mPreference;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class EthernetSettingsObserver extends ContentObserver {
        public EthernetSettingsObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            if (EthernetSettingsPreferenceController.this.mPreference != null) {
                EthernetSettingsPreferenceController ethernetSettingsPreferenceController =
                        EthernetSettingsPreferenceController.this;
                ethernetSettingsPreferenceController.updateState(
                        ethernetSettingsPreferenceController.mPreference);
            }
        }
    }

    public EthernetSettingsPreferenceController(
            Context context, String str, WirelessSettings wirelessSettings) {
        super(context, str);
        Handler handler = new Handler(Looper.getMainLooper());
        this.mHandler = handler;
        this.mEthernetStateReceiver = new EthernetSettingsObserver(handler);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return isAvailable(this.mContext) ? 0 : 3;
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
        return KEY_ETHERNET_SETTINGS;
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
        Log.d(TAG, "handlePreferenceTreeClick: " + preference);
        if (!KEY_ETHERNET_SETTINGS.equals(preference.getKey())) {
            return false;
        }
        LoggingHelper.insertFlowLogging(7190);
        return super.handlePreferenceTreeClick(preference);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public boolean isAvailable(Context context) {
        return ConnectionsUtils.isSupportEthernet();
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

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("eth_device_conn"),
                        false,
                        this.mEthernetStateReceiver,
                        0);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mEthernetStateReceiver);
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
        boolean z;
        if (preference == null) {
            return;
        }
        if (((EthernetManager) this.mContext.getSystemService("ethernet")).getInterfaceList().size()
                > 0) {
            Log.d(TAG, "getExistingInterfaces not null");
            z = true;
        } else {
            z = false;
        }
        int intForUser =
                Settings.System.getIntForUser(
                        this.mContext.getContentResolver(), "eth_device_conn", 0, 0);
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(intForUser, "ETH_DEVICE_CONNECTED: ", TAG);
        if (intForUser == 2 && z) {
            preference.setEnabled(true);
        } else {
            preference.setEnabled(false);
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
