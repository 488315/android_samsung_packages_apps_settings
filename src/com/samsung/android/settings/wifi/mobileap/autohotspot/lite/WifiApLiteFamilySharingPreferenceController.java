package com.samsung.android.settings.wifi.mobileap.autohotspot.lite;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceScreen;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApFamilySharingSettings;
import com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApFamilySharingSwitchEnabler;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSmartTetheringApkUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApLiteFamilySharingPreferenceController extends BasePreferenceController
        implements LifecycleEventObserver {
    private static final String TAG = "WifiApLiteFamilySharingPreferenceController";
    private Context mContext;
    private final WifiApFamilySharingSwitchEnabler.OnStateChangeListener
            mOnFamilySharingSwitchChangeListener;
    private SettingsPreferenceFragment mSettingsPreferenceFragment;
    SecPreferenceScreen mThisPreferenceScreen;
    private WifiApFamilySharingSwitchEnabler mWifiApFamilySharingSwitchEnabler;

    public WifiApLiteFamilySharingPreferenceController(Context context, String str) {
        super(context, str);
        this.mOnFamilySharingSwitchChangeListener =
                new WifiApFamilySharingSwitchEnabler
                        .OnStateChangeListener() { // from class:
                                                   // com.samsung.android.settings.wifi.mobileap.autohotspot.lite.WifiApLiteFamilySharingPreferenceController.1
                    @Override // com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApFamilySharingSwitchEnabler.OnStateChangeListener
                    public final void onStateChanged(int i) {
                        MainClearConfirm$$ExternalSyntheticOutline0.m(
                                i,
                                "Family Sharing onStateChanged() - resultCode: ",
                                WifiApLiteFamilySharingPreferenceController.TAG);
                        WifiApLiteFamilySharingPreferenceController
                                wifiApLiteFamilySharingPreferenceController =
                                        WifiApLiteFamilySharingPreferenceController.this;
                        wifiApLiteFamilySharingPreferenceController.mThisPreferenceScreen
                                .setEnabled(false);
                        wifiApLiteFamilySharingPreferenceController.mThisPreferenceScreen
                                .setSummary(ApnSettings.MVNO_NONE);
                        if (i == 5 || i == 1) {
                            return;
                        }
                        if (i == 3
                                && !WifiApFrameworkUtils.isFamilySharingSetOn(
                                        wifiApLiteFamilySharingPreferenceController.mContext)) {
                            wifiApLiteFamilySharingPreferenceController.mThisPreferenceScreen
                                    .setSummary(R.string.smart_tethering_internet_not_available);
                            return;
                        }
                        if (i == 4) {
                            wifiApLiteFamilySharingPreferenceController.mThisPreferenceScreen
                                    .setSummary(R.string.wifi_ap_not_signed_in);
                            return;
                        }
                        if (i == 6) {
                            wifiApLiteFamilySharingPreferenceController.mThisPreferenceScreen
                                    .setSummary(R.string.smart_tethering_nearby_can_not_available);
                            return;
                        }
                        wifiApLiteFamilySharingPreferenceController.mThisPreferenceScreen
                                .setEnabled(true);
                        StringBuilder sb = new StringBuilder();
                        if (WifiApSmartTetheringApkUtils.getFamilyGroupId(
                                        wifiApLiteFamilySharingPreferenceController.mContext)
                                .isEmpty()) {
                            sb.append(
                                    wifiApLiteFamilySharingPreferenceController.mContext.getString(
                                            R.string.wifi_ap_invite_your_family_member));
                        } else {
                            int size =
                                    ((ArrayList)
                                                    WifiApSmartTetheringApkUtils
                                                            .getFamilyMemberList(
                                                                    wifiApLiteFamilySharingPreferenceController
                                                                            .mContext))
                                            .size();
                            sb.append(
                                    wifiApLiteFamilySharingPreferenceController
                                            .mContext
                                            .getResources()
                                            .getQuantityString(
                                                    R.plurals.wifi_ap_n_members,
                                                    size,
                                                    Integer.valueOf(size)));
                            if (WifiApSmartTetheringApkUtils.isThereAnyNewInvitation(
                                    wifiApLiteFamilySharingPreferenceController.mContext)) {
                                sb.append(
                                        wifiApLiteFamilySharingPreferenceController.mContext
                                                .getString(R.string.comma));
                                sb.append(
                                        wifiApLiteFamilySharingPreferenceController.mContext
                                                .getString(R.string.separator));
                                sb.append(
                                        wifiApLiteFamilySharingPreferenceController.mContext
                                                .getString(R.string.wifi_ap_new_invitations));
                            }
                        }
                        wifiApLiteFamilySharingPreferenceController.mThisPreferenceScreen
                                .setSummary(sb.toString());
                    }
                };
        this.mContext = context;
        WifiApSmartTetheringApkUtils.startSmartTetheringApk(context, 1, null);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Log.i(TAG, "displayPreference()");
        SecPreferenceScreen secPreferenceScreen =
                (SecPreferenceScreen) preferenceScreen.findPreference(getPreferenceKey());
        this.mThisPreferenceScreen = secPreferenceScreen;
        secPreferenceScreen.setTitle(R.string.family_switch_title);
        this.mThisPreferenceScreen.setFragment(WifiApFamilySharingSettings.class.getName());
        WifiApFamilySharingSwitchEnabler wifiApFamilySharingSwitchEnabler =
                new WifiApFamilySharingSwitchEnabler(this.mSettingsPreferenceFragment);
        this.mWifiApFamilySharingSwitchEnabler = wifiApFamilySharingSwitchEnabler;
        wifiApFamilySharingSwitchEnabler.mOnStateChangeListener =
                this.mOnFamilySharingSwitchChangeListener;
        wifiApFamilySharingSwitchEnabler.updateSwitchState();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        StringBuilder sb = Utils.sBuilder;
        Log.i(
                TAG,
                "isAutoHotspotAvailable:true, isAutoHotspotLiteSupported:false,"
                    + " isAutoHotspotWifiOnlyLiteSupported:false");
        return 3;
    }

    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

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
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        Log.i(TAG, "handlePreferenceTreeClick - Triggered");
        return super.handlePreferenceTreeClick(preference);
    }

    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setHost(SettingsPreferenceFragment settingsPreferenceFragment) {
        this.mSettingsPreferenceFragment = settingsPreferenceFragment;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        Log.i(TAG, "updateState()");
    }

    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}

    @Override // androidx.lifecycle.LifecycleEventObserver
    public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {}
}
