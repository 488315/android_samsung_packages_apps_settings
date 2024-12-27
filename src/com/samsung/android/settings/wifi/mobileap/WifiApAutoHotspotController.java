package com.samsung.android.settings.wifi.mobileap;

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
import androidx.preference.SecPreferenceUtils;
import androidx.preference.SecSwitchPreferenceScreen;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApAutoHotspotSettings;
import com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApAutoHotspotSwitchEnabler;
import com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApFamilySharingSwitchEnabler;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFeatureUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSmartTetheringApkUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSoftApUtils;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApAutoHotspotController extends TogglePreferenceController
        implements LifecycleEventObserver {
    private static String TAG = "WifiApAutoHotspotController";
    private final Context mContext;
    private final WifiApAutoHotspotSwitchEnabler.OnStateChangeListener
            mOnAutoHotspotSwitchChangeListener;
    private final WifiApFamilySharingSwitchEnabler.OnStateChangeListener
            mOnFamilySharingSwitchChangeListener;
    private SecSwitchPreferenceScreen mWifiApAutoHotspotPreference;
    private WifiApAutoHotspotSwitchEnabler mWifiApAutoHotspotSwitchEnabler;
    private WifiApFamilySharingSwitchEnabler mWifiApFamilySharingSwitchEnabler;
    private WifiApSettings mWifiApSettings;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.WifiApAutoHotspotController$1, reason: invalid class name */
    public final class AnonymousClass1
            implements WifiApAutoHotspotSwitchEnabler.OnStateChangeListener,
                    WifiApFamilySharingSwitchEnabler.OnStateChangeListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ WifiApAutoHotspotController this$0;

        public /* synthetic */ AnonymousClass1(
                WifiApAutoHotspotController wifiApAutoHotspotController, int i) {
            this.$r8$classId = i;
            this.this$0 = wifiApAutoHotspotController;
        }

        @Override // com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApAutoHotspotSwitchEnabler.OnStateChangeListener, com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApFamilySharingSwitchEnabler.OnStateChangeListener
        public final void onStateChanged(int i) {
            switch (this.$r8$classId) {
                case 0:
                    WifiApAutoHotspotController wifiApAutoHotspotController = this.this$0;
                    boolean isAutoHotspotSetOn =
                            WifiApFrameworkUtils.isAutoHotspotSetOn(
                                    wifiApAutoHotspotController.mContext);
                    wifiApAutoHotspotController.mWifiApAutoHotspotPreference.setChecked(
                            isAutoHotspotSetOn);
                    wifiApAutoHotspotController.mWifiApAutoHotspotPreference.setEnabled(false);
                    wifiApAutoHotspotController.mWifiApAutoHotspotPreference.setSummary(
                            ApnSettings.MVNO_NONE);
                    if (i != 5 && i != 1) {
                        if (i != 3) {
                            if (i != 6) {
                                wifiApAutoHotspotController.mWifiApAutoHotspotPreference.setEnabled(
                                        true);
                                if (isAutoHotspotSetOn) {
                                    if (!WifiApFrameworkUtils.isFamilySharingSetOn(
                                            wifiApAutoHotspotController.mContext)) {
                                        wifiApAutoHotspotController.mWifiApAutoHotspotPreference
                                                .setSummary(
                                                        R.string.wifi_ap_smart_tethering_summary);
                                        break;
                                    } else {
                                        wifiApAutoHotspotController.mWifiApAutoHotspotPreference
                                                .setSummary(
                                                        R.string
                                                                .wifi_ap_smart_tethering_family_summary);
                                        break;
                                    }
                                }
                            } else {
                                wifiApAutoHotspotController.mWifiApAutoHotspotPreference.setSummary(
                                        R.string.smart_tethering_nearby_can_not_available);
                                break;
                            }
                        } else {
                            wifiApAutoHotspotController.mWifiApAutoHotspotPreference.setSummary(
                                    R.string.smart_tethering_internet_not_available);
                            break;
                        }
                    }
                    break;
                default:
                    MainClearConfirm$$ExternalSyntheticOutline0.m(
                            i,
                            "Family Sharing onStateChanged() - resultCode: ",
                            WifiApAutoHotspotController.TAG);
                    this.this$0.mWifiApAutoHotspotSwitchEnabler.updateSwitchState(false);
                    break;
            }
        }
    }

    public WifiApAutoHotspotController(Context context, String str) {
        super(context, str);
        this.mOnAutoHotspotSwitchChangeListener = new AnonymousClass1(this, 0);
        this.mOnFamilySharingSwitchChangeListener = new AnonymousClass1(this, 1);
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
            SecSwitchPreferenceScreen secSwitchPreferenceScreen = this.mWifiApAutoHotspotPreference;
            secSwitchPreferenceScreen.getClass();
            SecPreferenceUtils.applySummaryColor(secSwitchPreferenceScreen, true);
            WifiApAutoHotspotSwitchEnabler wifiApAutoHotspotSwitchEnabler =
                    new WifiApAutoHotspotSwitchEnabler(this.mWifiApSettings);
            this.mWifiApAutoHotspotSwitchEnabler = wifiApAutoHotspotSwitchEnabler;
            wifiApAutoHotspotSwitchEnabler.mOnStateChangeListener =
                    this.mOnAutoHotspotSwitchChangeListener;
            wifiApAutoHotspotSwitchEnabler.updateSwitchState(false);
            WifiApFamilySharingSwitchEnabler wifiApFamilySharingSwitchEnabler =
                    new WifiApFamilySharingSwitchEnabler(this.mWifiApSettings);
            this.mWifiApFamilySharingSwitchEnabler = wifiApFamilySharingSwitchEnabler;
            wifiApFamilySharingSwitchEnabler.mOnStateChangeListener =
                    this.mOnFamilySharingSwitchChangeListener;
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        WifiApFeatureUtils.isSamsungAccountFamilySupported();
        return 0;
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
                launchRequest.mDestinationName = WifiApAutoHotspotSettings.class.getCanonicalName();
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

    public void onActivityResult(int i, int i2, Intent intent) {
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                "onActivityResult() - requestCode: ",
                ", resultCode(-1 for RESULT_OK) : ",
                i,
                i2,
                TAG);
        this.mWifiApFamilySharingSwitchEnabler.onActivityResult(i, i2, intent);
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
        if (!WifiApFrameworkUtils.isAutoHotspotSetOn(this.mContext)
                || !WifiApSmartTetheringApkUtils.isFamilySharingSwitchChangedAutomatically(
                        this.mContext)) {
            return false;
        }
        this.mWifiApFamilySharingSwitchEnabler.setChecked(z);
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
}
