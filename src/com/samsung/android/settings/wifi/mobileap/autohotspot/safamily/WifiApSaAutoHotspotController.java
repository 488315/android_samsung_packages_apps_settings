package com.samsung.android.settings.wifi.mobileap.autohotspot.safamily;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.mobileap.WifiApSettings;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFeatureUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSmartTetheringApkUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSoftApUtils;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApSaAutoHotspotController extends TogglePreferenceController
        implements LifecycleEventObserver {
    private static String TAG = "WifiApSaAutoHotspotController";
    private final Context mContext;
    private final WifiApSaAutoHotspotSwitchEnabler.OnStateChangeListener
            mOnAutoHotspotSwitchChangeListener;
    private SecSwitchPreferenceScreen mWifiApAutoHotspotPreference;
    private WifiApSaAutoHotspotSwitchEnabler mWifiApAutoHotspotSwitchEnabler;
    private WifiApSettings mWifiApSettings;

    public WifiApSaAutoHotspotController(Context context, String str) {
        super(context, str);
        this.mOnAutoHotspotSwitchChangeListener =
                new WifiApSaAutoHotspotSwitchEnabler
                        .OnStateChangeListener() { // from class:
                                                   // com.samsung.android.settings.wifi.mobileap.autohotspot.safamily.WifiApSaAutoHotspotController.1
                    @Override // com.samsung.android.settings.wifi.mobileap.autohotspot.safamily.WifiApSaAutoHotspotSwitchEnabler.OnStateChangeListener
                    public final void onStateChanged(int i) {
                        WifiApSaAutoHotspotController wifiApSaAutoHotspotController =
                                WifiApSaAutoHotspotController.this;
                        boolean isAutoHotspotSetOn =
                                WifiApFrameworkUtils.isAutoHotspotSetOn(
                                        wifiApSaAutoHotspotController.mContext);
                        wifiApSaAutoHotspotController.mWifiApAutoHotspotPreference.setChecked(
                                isAutoHotspotSetOn);
                        wifiApSaAutoHotspotController.mWifiApAutoHotspotPreference.setEnabled(
                                false);
                        wifiApSaAutoHotspotController.mWifiApAutoHotspotPreference.setSummary(
                                ApnSettings.MVNO_NONE);
                        if (i == 5 || i == 1) {
                            return;
                        }
                        if (i == 3) {
                            wifiApSaAutoHotspotController.mWifiApAutoHotspotPreference.setSummary(
                                    R.string.smart_tethering_internet_not_available);
                            return;
                        }
                        if (i == 6) {
                            wifiApSaAutoHotspotController.mWifiApAutoHotspotPreference.setSummary(
                                    R.string.smart_tethering_nearby_can_not_available);
                            return;
                        }
                        wifiApSaAutoHotspotController.mWifiApAutoHotspotPreference.setEnabled(true);
                        if (isAutoHotspotSetOn) {
                            if (WifiApFrameworkUtils.isFamilySharingSetOn(
                                    wifiApSaAutoHotspotController.mContext)) {
                                wifiApSaAutoHotspotController.mWifiApAutoHotspotPreference
                                        .setSummary(
                                                R.string.wifi_ap_smart_tethering_family_summary);
                            } else {
                                wifiApSaAutoHotspotController.mWifiApAutoHotspotPreference
                                        .setSummary(R.string.wifi_ap_smart_tethering_summary);
                            }
                        }
                    }
                };
        this.mContext = context;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        if (this.mWifiApSettings != null) {
            this.mWifiApAutoHotspotPreference =
                    (SecSwitchPreferenceScreen) preferenceScreen.findPreference(getPreferenceKey());
            if (Rune.isJapanModel()) {
                this.mWifiApAutoHotspotPreference.setTitle(
                        R.string.wifi_ap_smart_tethering_title_jpn);
            } else {
                this.mWifiApAutoHotspotPreference.setTitle(R.string.wifi_ap_smart_tethering_title);
            }
            WifiApSaAutoHotspotSwitchEnabler wifiApSaAutoHotspotSwitchEnabler =
                    new WifiApSaAutoHotspotSwitchEnabler(this.mWifiApSettings);
            this.mWifiApAutoHotspotSwitchEnabler = wifiApSaAutoHotspotSwitchEnabler;
            wifiApSaAutoHotspotSwitchEnabler.mOnStateChangeListener =
                    this.mOnAutoHotspotSwitchChangeListener;
            wifiApSaAutoHotspotSwitchEnabler.updateSwitchState();
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        WifiApFeatureUtils.isSamsungAccountFamilySupported();
        return 4;
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            Log.i(TAG, "AutoHotspot switch preference screen clicked");
            SALogging.insertSALog("TETH_010", "8009");
            if ((WifiApSettingsUtils.isCarrierTMO() || WifiApSettingsUtils.isCarrierNEWCO())
                    && WifiApSoftApUtils.isDefaultPassphraseSet(this.mContext)) {
                Log.d(TAG, "AutoHotspot FirstTime Configuration dialog");
                Settings.Secure.putInt(
                        this.mContext.getContentResolver(),
                        "autohotspot_waiting_for_password_change",
                        1);
                WifiApFrameworkUtils.setAutoHotspotDB(this.mContext, false);
                this.mWifiApSettings.launchWifiApEditSettingsActivity(
                        R.string.wifi_ap_first_time_configuration, "intent_value_focus_password");
            } else {
                SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
                int metricsCategory = getMetricsCategory();
                SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
                launchRequest.mSourceMetricsCategory = metricsCategory;
                launchRequest.mDestinationName =
                        WifiApSaAutoHotspotSettings.class.getCanonicalName();
                subSettingLauncher.launch();
            }
        }
        return super.handlePreferenceTreeClick(preference);
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
        Log.i(TAG, "isChecked");
        return WifiApFrameworkUtils.isAutoHotspotSetOn(this.mContext);
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

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        AbsAdapter$$ExternalSyntheticOutline0.m("setChecked: ", TAG, z);
        SALogging.insertSALog(z ? 1L : 0L, "TETH_010", "8010");
        if (!z) {
            WifiApSmartTetheringApkUtils.setFamilySharingSwitchChangedAutomatically(
                    this.mContext, WifiApFrameworkUtils.isFamilySharingSetOn(this.mContext));
        }
        this.mWifiApAutoHotspotSwitchEnabler.setChecked(z);
        WifiApFrameworkUtils.isAutoHotspotSetOn(this.mContext);
        return false;
    }

    public void setHost(WifiApSettings wifiApSettings) {
        this.mWifiApSettings = wifiApSettings;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {}

    public void onActivityResult(int i, int i2, Intent intent) {}
}
