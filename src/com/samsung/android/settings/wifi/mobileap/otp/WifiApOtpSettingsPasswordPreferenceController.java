package com.samsung.android.settings.wifi.mobileap.otp;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.BasePreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFeatureUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils;
import com.samsung.android.settings.wifi.mobileap.views.WifiApPreference;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApOtpSettingsPasswordPreferenceController extends BasePreferenceController {
    private static String TAG = "WifiApOtpSettingsPasswordPreferenceController";
    private WifiApPreference mThisPreference;

    public WifiApOtpSettingsPasswordPreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        WifiApPreference wifiApPreference =
                (WifiApPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mThisPreference = wifiApPreference;
        wifiApPreference.mTextGravity = 17;
        wifiApPreference.notifyChanged();
        WifiApPreference wifiApPreference2 = this.mThisPreference;
        wifiApPreference2.getClass();
        Log.i("WifiApPreference", "setTextSizeInSp() - : 32.0");
        wifiApPreference2.mTextSizeInSp = 32.0f;
        wifiApPreference2.notifyChanged();
        WifiApPreference wifiApPreference3 = this.mThisPreference;
        wifiApPreference3.mIsTitleSingleLine = false;
        wifiApPreference3.notifyChanged();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return WifiApFeatureUtils.isOneTimePasswordSupported(this.mContext) ? 0 : 3;
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
        super.updateState(preference);
        Log.i(
                TAG,
                "updateState() - wifiap state: "
                        + WifiApFrameworkUtils.getSemWifiManager(this.mContext).getWifiApState()
                        + ", Otp DB: "
                        + WifiApFrameworkUtils.isOtpPasswordEnabled(this.mContext));
        PreferenceCategory preferenceCategory =
                (PreferenceCategory) this.mThisPreference.getParent();
        if (!WifiApFrameworkUtils.isOtpPasswordEnabled(this.mContext)) {
            this.mThisPreference.setTitle(ApnSettings.MVNO_NONE);
            if (preferenceCategory != null) {
                preferenceCategory.setVisible(false);
                return;
            }
            return;
        }
        WifiApPreference wifiApPreference = this.mThisPreference;
        Context context = this.mContext;
        Log.i("WifiApFrameworkUtils", "getOtpPassword: ");
        String wifiApGuestPassword =
                WifiApFrameworkUtils.getSemWifiManager(context).getWifiApGuestPassword();
        if (!TextUtils.isEmpty(wifiApGuestPassword)) {
            Log.i("WifiApFrameworkUtils", "getOtpPassword is not not empty");
        }
        wifiApPreference.setTitle(wifiApGuestPassword);
        if (preferenceCategory != null) {
            preferenceCategory.setVisible(true);
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
