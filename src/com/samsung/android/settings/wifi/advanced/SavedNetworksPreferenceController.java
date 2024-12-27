package com.samsung.android.settings.wifi.advanced;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Debug;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SavedNetworksPreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnResume, OnPause {
    private static final boolean DBG = Debug.semIsProductDev();
    private static final String KEY_MANAGE_NETWORK = "wifi_manage_network";
    private static final String MANAGE_NETWORK = "manage_network";
    private static final String TAG = "ConfigureWifiSettings.ManageNetwork";
    private final IntentFilter mFilter;
    private Preference mManageNetwork;
    private final BroadcastReceiver mReceiver;
    private final WifiManager mWifiManager;

    public SavedNetworksPreferenceController(Context context, String str) {
        super(context, str);
        this.mReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.samsung.android.settings.wifi.advanced.SavedNetworksPreferenceController.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        if ("android.net.wifi.WIFI_STATE_CHANGED".equals(intent.getAction())) {
                            int intExtra = intent.getIntExtra("wifi_state", 4);
                            if (intExtra == 3) {
                                if (SavedNetworksPreferenceController.this.mManageNetwork != null) {
                                    SavedNetworksPreferenceController.this.mManageNetwork
                                            .setEnabled(true);
                                }
                            } else {
                                if (intExtra != 1
                                        || SavedNetworksPreferenceController.this.mManageNetwork
                                                == null) {
                                    return;
                                }
                                SavedNetworksPreferenceController.this.mManageNetwork.setEnabled(
                                        false);
                            }
                        }
                    }
                };
        this.mFilter = new IntentFilter("android.net.wifi.WIFI_STATE_CHANGED");
        this.mWifiManager = (WifiManager) context.getSystemService(WifiManager.class);
    }

    private void togglePreferences() {
        if (this.mManageNetwork != null) {
            this.mManageNetwork.setEnabled(this.mWifiManager.isWifiEnabled());
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mManageNetwork = preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        this.mContext.unregisterReceiver(this.mReceiver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        this.mContext.registerReceiver(this.mReceiver, this.mFilter);
        togglePreferences();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
