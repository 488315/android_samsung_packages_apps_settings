package com.samsung.android.settings.wifi.mobileap.autohotspot;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreferenceScreen;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFeatureUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSmartTetheringApkUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApFamilySharingSwitchController extends TogglePreferenceController
        implements LifecycleEventObserver {
    private static final String TAG = "WifiApFamilySharingSwitchController";
    private final Context mContext;
    private boolean mIsSaFamilyServiceEnabled;
    private boolean mIsSaFamilyServiceSupportedBasedOnCountry;
    private final WifiApFamilySharingSwitchEnabler.OnStateChangeListener
            mOnFamilySharingSwitchChangeListener;
    private WifiApAutoHotspotSettings mWifiApAutoHotspotSettings;
    private WifiApFamilySharingSwitchEnabler mWifiApFamilySharingSwitchEnabler;
    private SecSwitchPreferenceScreen mWifiApFamilySharingSwitchPreference;

    public WifiApFamilySharingSwitchController(Context context, String str) {
        super(context, str);
        this.mOnFamilySharingSwitchChangeListener =
                new WifiApFamilySharingSwitchEnabler.OnStateChangeListener() { // from class:
                    // com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApFamilySharingSwitchController.1
                    @Override // com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApFamilySharingSwitchEnabler.OnStateChangeListener
                    public final void onStateChanged(int i) {
                        MainClearConfirm$$ExternalSyntheticOutline0.m(
                                i,
                                "Family Sharing onStateChanged() - resultCode: ",
                                WifiApFamilySharingSwitchController.TAG);
                        WifiApFamilySharingSwitchController wifiApFamilySharingSwitchController =
                                WifiApFamilySharingSwitchController.this;
                        if (wifiApFamilySharingSwitchController.mWifiApFamilySharingSwitchPreference
                                == null) {
                            Log.d(
                                    WifiApFamilySharingSwitchController.TAG,
                                    "Family Sharing onStateChanged() -"
                                        + " mWifiApFamilySharingSwitchPreference is null");
                            return;
                        }
                        boolean isFamilySharingSetOn =
                                WifiApFrameworkUtils.isFamilySharingSetOn(
                                        wifiApFamilySharingSwitchController.mContext);
                        boolean isActiveNetworkHasInternet =
                                WifiApSettingsUtils.isActiveNetworkHasInternet(
                                        wifiApFamilySharingSwitchController.mContext);
                        boolean isSAFamilySupportedBasedOnCountry =
                                WifiApFeatureUtils.isSAFamilySupportedBasedOnCountry(
                                        wifiApFamilySharingSwitchController.mContext);
                        boolean isSAFamilySupportedInSAFamilyDB =
                                WifiApFeatureUtils.isSAFamilySupportedInSAFamilyDB(
                                        wifiApFamilySharingSwitchController.mContext);
                        wifiApFamilySharingSwitchController.mWifiApFamilySharingSwitchPreference
                                .setChecked(isFamilySharingSetOn);
                        if (isSAFamilySupportedBasedOnCountry) {
                            if (isSAFamilySupportedInSAFamilyDB) {
                                wifiApFamilySharingSwitchController
                                        .mWifiApFamilySharingSwitchPreference.setEnabled(true);
                                if (!isFamilySharingSetOn) {
                                    wifiApFamilySharingSwitchController
                                            .mWifiApFamilySharingSwitchPreference.setEnabled(
                                            isActiveNetworkHasInternet);
                                }
                            } else {
                                wifiApFamilySharingSwitchController
                                        .mWifiApFamilySharingSwitchPreference.setChecked(false);
                                wifiApFamilySharingSwitchController
                                        .mWifiApFamilySharingSwitchPreference.setEnabled(false);
                            }
                        } else if (!isFamilySharingSetOn) {
                            wifiApFamilySharingSwitchController.mWifiApFamilySharingSwitchPreference
                                    .setEnabled(isActiveNetworkHasInternet);
                        }
                        StringBuilder sb = new StringBuilder();
                        if (!isActiveNetworkHasInternet && !isFamilySharingSetOn) {
                            sb.append(
                                    wifiApFamilySharingSwitchController.mContext.getString(
                                            R.string.smart_tethering_internet_not_available));
                        } else if (WifiApSmartTetheringApkUtils.getFamilyGroupId(
                                        wifiApFamilySharingSwitchController.mContext)
                                .isEmpty()) {
                            sb.append(
                                    wifiApFamilySharingSwitchController.mContext.getString(
                                            R.string.wifi_ap_invite_your_family_member));
                        } else if (WifiApFeatureUtils.isSAFamilySupportedBasedOnCountry(
                                wifiApFamilySharingSwitchController.mContext)) {
                            Log.i(WifiApFamilySharingSwitchController.TAG, "No check matched");
                        } else {
                            int size =
                                    ((ArrayList)
                                                    WifiApSmartTetheringApkUtils
                                                            .getFamilyMemberList(
                                                                    wifiApFamilySharingSwitchController
                                                                            .mContext))
                                            .size();
                            sb.append(
                                    wifiApFamilySharingSwitchController
                                            .mContext
                                            .getResources()
                                            .getQuantityString(
                                                    R.plurals.wifi_ap_n_members,
                                                    size,
                                                    Integer.valueOf(size)));
                            if (WifiApSmartTetheringApkUtils.isThereAnyNewInvitation(
                                    wifiApFamilySharingSwitchController.mContext)) {
                                sb.append(
                                        wifiApFamilySharingSwitchController.mContext.getString(
                                                R.string.comma));
                                sb.append(
                                        wifiApFamilySharingSwitchController.mContext.getString(
                                                R.string.separator));
                                sb.append(
                                        wifiApFamilySharingSwitchController.mContext.getString(
                                                R.string.wifi_ap_new_invitations));
                            }
                        }
                        wifiApFamilySharingSwitchController.mWifiApFamilySharingSwitchPreference
                                .setSummary(sb.toString());
                    }
                };
        this.mContext = context;
        this.mIsSaFamilyServiceSupportedBasedOnCountry =
                WifiApFeatureUtils.isSAFamilySupportedBasedOnCountry(context);
        this.mIsSaFamilyServiceEnabled =
                WifiApFeatureUtils.isSAFamilySupportedInSAFamilyDB(context);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mWifiApFamilySharingSwitchPreference =
                (SecSwitchPreferenceScreen) preferenceScreen.findPreference(getPreferenceKey());
        if (WifiApFeatureUtils.isSAFamilySupportedBasedOnCountry(this.mContext)) {
            this.mWifiApFamilySharingSwitchPreference.setTitle("Share with family group");
        } else {
            this.mWifiApFamilySharingSwitchPreference.setTitle(
                    this.mContext.getString(R.string.family_switch_title));
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
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
            String str = TAG;
            Log.i(str, "Family Sharing switch preference screen clicked");
            if (WifiApSettingsUtils.isSamsungAccountLoggedOut(this.mContext)) {
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
            } else if (WifiApFeatureUtils.isSAFamilySupportedBasedOnCountry(this.mContext)) {
                WifiApSmartTetheringApkUtils.launchSaFamilyServiceGroupActivity(
                        this.mWifiApAutoHotspotSettings, -1);
            } else {
                Log.i(str, "Launching Family Sharing SettingsActivity.");
                SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
                int metricsCategory = getMetricsCategory();
                SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
                launchRequest.mSourceMetricsCategory = metricsCategory;
                launchRequest.mDestinationName =
                        WifiApFamilySharingSettings.class.getCanonicalName();
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
        return WifiApFrameworkUtils.isFamilySharingSetOn(this.mContext);
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
        this.mWifiApFamilySharingSwitchEnabler.setChecked(z);
        return false;
    }

    public void setHost(WifiApAutoHotspotSettings wifiApAutoHotspotSettings) {
        this.mWifiApAutoHotspotSettings = wifiApAutoHotspotSettings;
        WifiApFamilySharingSwitchEnabler wifiApFamilySharingSwitchEnabler =
                new WifiApFamilySharingSwitchEnabler(this.mWifiApAutoHotspotSettings);
        this.mWifiApFamilySharingSwitchEnabler = wifiApFamilySharingSwitchEnabler;
        wifiApFamilySharingSwitchEnabler.mOnStateChangeListener =
                this.mOnFamilySharingSwitchChangeListener;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        WifiApFamilySharingSwitchEnabler wifiApFamilySharingSwitchEnabler;
        super.updateState(preference);
        Log.i(TAG, "updateState()");
        if (!isAvailable()
                || (wifiApFamilySharingSwitchEnabler = this.mWifiApFamilySharingSwitchEnabler)
                        == null) {
            return;
        }
        wifiApFamilySharingSwitchEnabler.updateSwitchState();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {}
}
