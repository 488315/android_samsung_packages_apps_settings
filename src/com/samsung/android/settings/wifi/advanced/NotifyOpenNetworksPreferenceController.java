package com.samsung.android.settings.wifi.advanced;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Debug;
import android.provider.Settings;

import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreference;

import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.wifi.SemOpBrandingLoader;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class NotifyOpenNetworksPreferenceController extends TogglePreferenceController
        implements LifecycleObserver, OnResume, OnPause {
    private static boolean DBG = Debug.semIsProductDev();
    private static final String KEY_NOTIFY_OPEN_NETWORKS = "notify_open_networks";
    public static final boolean SUPPORT_NOTIFICATION_MENU;
    private static final String TAG = "ConfigureWifiSettings.NotifyOpenNetworks";
    private static final SemOpBrandingLoader.SemVendor mOpBranding;
    private final IntentFilter mFilter;
    private SecSwitchPreference mNotifyOpenNetworks;
    private final BroadcastReceiver mReceiver;
    private final WifiManager mWifiManager;

    static {
        SUPPORT_NOTIFICATION_MENU = Rune.isUSA() || "CA".equals(Utils.readCountryCode());
        mOpBranding = SemOpBrandingLoader.getInstance().getOpBranding();
    }

    public NotifyOpenNetworksPreferenceController(Context context, String str) {
        super(context, str);
        this.mFilter = new IntentFilter("android.net.wifi.WIFI_STATE_CHANGED");
        this.mReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.samsung.android.settings.wifi.advanced.NotifyOpenNetworksPreferenceController.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        if ("android.net.wifi.WIFI_STATE_CHANGED".equals(intent.getAction())) {
                            int intExtra = intent.getIntExtra("wifi_state", 4);
                            if (intExtra == 3) {
                                if (NotifyOpenNetworksPreferenceController.this.mNotifyOpenNetworks
                                        != null) {
                                    NotifyOpenNetworksPreferenceController.this.mNotifyOpenNetworks
                                            .setEnabled(true);
                                }
                            } else {
                                if (intExtra != 1
                                        || NotifyOpenNetworksPreferenceController.this
                                                        .mNotifyOpenNetworks
                                                == null) {
                                    return;
                                }
                                NotifyOpenNetworksPreferenceController.this.mNotifyOpenNetworks
                                        .setEnabled(false);
                            }
                        }
                    }
                };
        this.mWifiManager = (WifiManager) context.getSystemService(WifiManager.class);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mNotifyOpenNetworks =
                (SecSwitchPreference) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (SUPPORT_NOTIFICATION_MENU) {
            return this.mWifiManager.isWifiEnabled() ? 5 : 0;
        }
        return 3;
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
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return super.getSummary();
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
        return Settings.Global.getInt(
                        this.mContext.getContentResolver(),
                        "wifi_networks_available_notification_on",
                        2)
                == 3;
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

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        this.mContext.unregisterReceiver(this.mReceiver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        this.mContext.registerReceiver(this.mReceiver, this.mFilter);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        Settings.Global.putInt(
                this.mContext.getContentResolver(),
                "wifi_networks_available_notification_on",
                z ? 3 : 2);
        SALogging.insertSALog(z ? 1L : 0L, "WIFI_200", "1233");
        updateState(this.mNotifyOpenNetworks);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
