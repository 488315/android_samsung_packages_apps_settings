package com.samsung.android.settings.wifi.mobileap.autohotspot.safamily;

import android.app.Activity;
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

import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFeatureUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;
import com.samsung.android.wifi.SemWifiApContentProviderHelper;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApSaFamilySharingSwitchController extends TogglePreferenceController
        implements LifecycleEventObserver {
    private static final String TAG = "WifiApSaFamilySharingSwitchController";
    private final Context mContext;
    private final WifiApSaFamilySharingSwitchEnabler.OnStateChangeListener
            mOnFamilySharingSwitchChangeListener;
    private WifiApSaFamilySharingSwitchEnabler.OnStateChangeListener
            mOnFamilySwitchStateChangeListener;
    private WifiApSaAutoHotspotSettings mWifiApAutoHotspotSettings;
    private WifiApSaFamilySharingSwitchEnabler mWifiApSaFamilySharingSwitchEnabler;
    private SecSwitchPreferenceScreen mWifiApSaFamilySharingSwitchPreference;

    public WifiApSaFamilySharingSwitchController(Context context, String str) {
        super(context, str);
        this.mOnFamilySharingSwitchChangeListener =
                new WifiApSaFamilySharingSwitchEnabler
                        .OnStateChangeListener() { // from class:
                                                   // com.samsung.android.settings.wifi.mobileap.autohotspot.safamily.WifiApSaFamilySharingSwitchController.1
                    @Override // com.samsung.android.settings.wifi.mobileap.autohotspot.safamily.WifiApSaFamilySharingSwitchEnabler.OnStateChangeListener, com.samsung.android.settings.wifi.mobileap.autohotspot.safamily.WifiApSaAutoHotspotSwitchEnabler.OnStateChangeListener
                    public final void onStateChanged(int i) {
                        WifiApSaFamilySharingSwitchController
                                wifiApSaFamilySharingSwitchController =
                                        WifiApSaFamilySharingSwitchController.this;
                        if (wifiApSaFamilySharingSwitchController.mOnFamilySwitchStateChangeListener
                                != null) {
                            wifiApSaFamilySharingSwitchController.mOnFamilySwitchStateChangeListener
                                    .onStateChanged(i);
                        }
                        Log.i(
                                WifiApSaFamilySharingSwitchController.TAG,
                                "Family Sharing onStateChanged() - resultCode: " + i);
                        boolean isFamilySharingSetOn =
                                WifiApFrameworkUtils.isFamilySharingSetOn(
                                        wifiApSaFamilySharingSwitchController.mContext);
                        boolean isActiveNetworkHasInternet =
                                WifiApSettingsUtils.isActiveNetworkHasInternet(
                                        wifiApSaFamilySharingSwitchController.mContext);
                        wifiApSaFamilySharingSwitchController.mWifiApSaFamilySharingSwitchPreference
                                .setChecked(isFamilySharingSetOn);
                        if (!isFamilySharingSetOn) {
                            if (isActiveNetworkHasInternet) {
                                wifiApSaFamilySharingSwitchController
                                        .mWifiApSaFamilySharingSwitchPreference.setEnabled(true);
                            } else {
                                wifiApSaFamilySharingSwitchController
                                        .mWifiApSaFamilySharingSwitchPreference.setEnabled(false);
                            }
                        }
                        StringBuilder sb = new StringBuilder();
                        if (!SaFamilyUtils.isSupportFamilyService(
                                wifiApSaFamilySharingSwitchController.mContext,
                                wifiApSaFamilySharingSwitchController.mContext.getString(
                                        R.string.security_dashboard_sa_client_id))) {
                            wifiApSaFamilySharingSwitchController
                                    .mWifiApSaFamilySharingSwitchPreference.setEnabled(false);
                            SaFamilyUtils.setFamilySharingDB(
                                    wifiApSaFamilySharingSwitchController.mContext, false);
                            sb.append("Family service is not supported currently");
                        } else if (!isActiveNetworkHasInternet && !isFamilySharingSetOn) {
                            sb.append(
                                    wifiApSaFamilySharingSwitchController.mContext.getString(
                                            R.string.smart_tethering_internet_not_available));
                        } else if (SaFamilyUtils.getFamilyGroupId(
                                        wifiApSaFamilySharingSwitchController.mContext)
                                .isEmpty()) {
                            sb.append(
                                    wifiApSaFamilySharingSwitchController.mContext.getString(
                                            R.string.wifi_ap_invite_your_family_member));
                        } else {
                            String str2 =
                                    SemWifiApContentProviderHelper.get(
                                            wifiApSaFamilySharingSwitchController.mContext,
                                            "smart_tethering_family_count");
                            int parseInt = TextUtils.isEmpty(str2) ? 0 : Integer.parseInt(str2);
                            sb.append(
                                    wifiApSaFamilySharingSwitchController
                                            .mContext
                                            .getResources()
                                            .getQuantityString(
                                                    R.plurals.wifi_ap_n_members,
                                                    parseInt,
                                                    Integer.valueOf(parseInt)));
                        }
                        wifiApSaFamilySharingSwitchController.mWifiApSaFamilySharingSwitchPreference
                                .setSummary(sb.toString());
                    }
                };
        this.mContext = context;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        if (this.mWifiApAutoHotspotSettings != null) {
            this.mWifiApSaFamilySharingSwitchPreference =
                    (SecSwitchPreferenceScreen) preferenceScreen.findPreference(getPreferenceKey());
            WifiApSaFamilySharingSwitchEnabler wifiApSaFamilySharingSwitchEnabler =
                    new WifiApSaFamilySharingSwitchEnabler(this.mWifiApAutoHotspotSettings);
            this.mWifiApSaFamilySharingSwitchEnabler = wifiApSaFamilySharingSwitchEnabler;
            wifiApSaFamilySharingSwitchEnabler.mOnStateChangeListener =
                    this.mOnFamilySharingSwitchChangeListener;
            wifiApSaFamilySharingSwitchEnabler.updateSwitchState();
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        WifiApFeatureUtils.isSamsungAccountFamilySupported();
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
            if (!WifiApSettingsUtils.isSamsungAccountLoggedOut(this.mContext)) {
                Log.i(str, "Launching Family Sharing SettingsActivity.");
                Intent intent =
                        new Intent(
                                "com.samsung.android.samsungaccount.action.OPEN_FAMILY_GROUP_MAIN");
                if (TextUtils.isEmpty(SaFamilyUtils.getFamilyGroupId(this.mContext))) {
                    intent.putExtra("launch_mode", "request_create_group");
                }
                this.mWifiApAutoHotspotSettings.startActivity(intent);
            } else if (Rune.isJapanModel()) {
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

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        AbsAdapter$$ExternalSyntheticOutline0.m("setChecked: ", TAG, z);
        WifiApSaFamilySharingSwitchEnabler wifiApSaFamilySharingSwitchEnabler =
                this.mWifiApSaFamilySharingSwitchEnabler;
        wifiApSaFamilySharingSwitchEnabler.getClass();
        Log.d("WifiApSaFamilySharingSwitchEnabler", "setChecked: " + z);
        SaFamilyUtils.setFamilySharingDB(wifiApSaFamilySharingSwitchEnabler.mContext, z);
        if (z) {
            Log.i("WifiApSaFamilySharingSwitchEnabler", "Checking setCheck conditions.");
            boolean z2 =
                    !WifiApSettingsUtils.isActiveNetworkHasInternet(
                            wifiApSaFamilySharingSwitchEnabler.mContext);
            boolean isSamsungAccountLoggedOut =
                    WifiApSettingsUtils.isSamsungAccountLoggedOut(
                            wifiApSaFamilySharingSwitchEnabler.mContext);
            boolean isEmpty =
                    SaFamilyUtils.getFamilyGroupId(wifiApSaFamilySharingSwitchEnabler.mContext)
                            .isEmpty();
            if (!WifiApFrameworkUtils.isAutoHotspotSetOn(
                    wifiApSaFamilySharingSwitchEnabler.mContext)) {
                Log.i(
                        "WifiApSaFamilySharingSwitchEnabler",
                        "Auto Hotspot On check condition failed.");
                WifiApFrameworkUtils.setFamilySharingDB(
                        wifiApSaFamilySharingSwitchEnabler.mContext, false);
                if (Rune.isJapanModel()) {
                    Toast.makeText(
                                    wifiApSaFamilySharingSwitchEnabler.mContext,
                                    R.string.wifi_ap_turn_on_auto_hotspot_to_use_family_sharing_jpn,
                                    1)
                            .show();
                } else {
                    Toast.makeText(
                                    wifiApSaFamilySharingSwitchEnabler.mContext,
                                    R.string.wifi_ap_turn_on_auto_hotspot_to_use_family_sharing,
                                    1)
                            .show();
                }
            } else {
                Activity activity = wifiApSaFamilySharingSwitchEnabler.mActivity;
                if (z2) {
                    Log.i("WifiApSaFamilySharingSwitchEnabler", "Network check condition failed.");
                } else if (isSamsungAccountLoggedOut) {
                    Log.i(
                            "WifiApSaFamilySharingSwitchEnabler",
                            "Samsung Account check condition failed.");
                    WifiApFrameworkUtils.setFamilySharingDB(
                            wifiApSaFamilySharingSwitchEnabler.mContext, false);
                    WifiApSettingsUtils.launchAddSamsungAccountActivity(activity);
                }
                if (isEmpty) {
                    Log.i("WifiApSaFamilySharingSwitchEnabler", "Family group Id check failed.");
                    Intent intent =
                            new Intent(
                                    "com.samsung.android.samsungaccount.action.OPEN_FAMILY_GROUP_MAIN");
                    intent.putExtra("launch_mode", "request_create_group");
                    activity.startActivity(intent);
                }
            }
        }
        wifiApSaFamilySharingSwitchEnabler.updateSwitchState();
        return false;
    }

    public void setFamilyStateListner(
            WifiApSaFamilySharingSwitchEnabler.OnStateChangeListener onStateChangeListener) {
        this.mOnFamilySwitchStateChangeListener = onStateChangeListener;
    }

    public void setHost(WifiApSaAutoHotspotSettings wifiApSaAutoHotspotSettings) {
        this.mWifiApAutoHotspotSettings = wifiApSaAutoHotspotSettings;
    }

    public void updateStates() {
        Log.i(TAG, "updateStates()");
        this.mWifiApSaFamilySharingSwitchEnabler.updateSwitchState();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {}
}
