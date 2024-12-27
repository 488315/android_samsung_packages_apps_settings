package com.samsung.android.settings.privacy;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.util.SemLog;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecurityDashboardDeviceProtectionPreferenceController
        extends SecurityDashboardAppSecurityPreferenceController {
    private static final String TAG = "SecurityDashboardDeviceProtectionPreferenceController";

    public SecurityDashboardDeviceProtectionPreferenceController(Context context, String str) {
        super(context, str);
    }

    private void launchDPIntent() {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(DeviceProtectionUtils.DEVICE_PROTECTION_DEEPLINK_URI);
        try {
            this.mContext.startActivityForResult(null, intent, 0, null);
        } catch (ActivityNotFoundException e) {
            SemLog.d(TAG, "Device Protection Activity Not Found" + e.getMessage());
        }
    }

    private void updateMenuStatus() {
        getDPInfo();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.core.BasePreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        updateMenuStatus();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mIsDPSupported ? 0 : 3;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.android.settings.core.BasePreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if ("key_device_protection".equals(preference.getKey())) {
            launchDPIntent();
            LoggingHelper.insertEventLogging(9032, 56037);
        }
        return super.handlePreferenceTreeClick(preference);
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.android.settings.core.BasePreferenceController
    public void updateNonIndexableKeys(List<String> list) {
        if (this.mIsDPSupported) {
            return;
        }
        list.add("key_device_protection");
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        updateMenuStatus();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
