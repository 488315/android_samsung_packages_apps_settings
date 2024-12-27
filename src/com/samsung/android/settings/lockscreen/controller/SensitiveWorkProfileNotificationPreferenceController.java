package com.samsung.android.settings.lockscreen.controller;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.RestrictedSwitchPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.lockscreen.LockScreenNotificationSettings;
import com.samsung.android.settings.lockscreen.SecConceptBehavior;
import com.samsung.android.settings.lockscreen.SecConceptControllerBehavior;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SensitiveWorkProfileNotificationPreferenceController extends BasePreferenceController
        implements SecConceptControllerBehavior, Preference.OnPreferenceChangeListener {
    static final String KEY = "sensitive_work_profile";
    SecConceptBehavior mContextDashBoard;
    RestrictedSwitchPreference mPreference;
    private final int mProfileUserId;

    public SensitiveWorkProfileNotificationPreferenceController(
            Context context, SecConceptBehavior secConceptBehavior) {
        super(context, KEY);
        this.mContextDashBoard = secConceptBehavior;
        this.mProfileUserId =
                Utils.getManagedProfileId(UserManager.get(context), UserHandle.myUserId());
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (RestrictedSwitchPreference) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        int i;
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) this.mContext.getSystemService("device_policy");
        if (devicePolicyManager != null && (i = this.mProfileUserId) != -10000) {
            int keyguardDisabledFeatures = devicePolicyManager.getKeyguardDisabledFeatures(null, i);
            if ((keyguardDisabledFeatures & 8) != 0 || (keyguardDisabledFeatures & 4) != 0) {
                return 5;
            }
        }
        return this.mProfileUserId == -10000 ? 4 : 0;
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

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        Boolean bool = (Boolean) obj;
        boolean booleanValue = bool.booleanValue();
        this.mPreference.setChecked(booleanValue);
        Settings.Secure.putIntForUser(
                this.mContext.getContentResolver(),
                "lock_screen_allow_private_notifications",
                booleanValue ? 1 : 0,
                this.mProfileUserId);
        ((LockScreenNotificationSettings) this.mContextDashBoard)
                .notifyChange(bool, getPreferenceKey());
        return true;
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
        boolean z = false;
        try {
            if (Settings.Secure.getIntForUser(
                            this.mContext.getContentResolver(),
                            "lock_screen_allow_private_notifications",
                            this.mProfileUserId)
                    == 1) {
                z = true;
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        this.mPreference.setChecked(z);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}

    @Override // com.samsung.android.settings.lockscreen.SecConceptControllerBehavior
    public /* bridge */ /* synthetic */ void updateConfigurationChanged(
            Configuration configuration) {}

    @Override // com.samsung.android.settings.lockscreen.SecConceptControllerBehavior
    public /* bridge */ /* synthetic */ void accept(String str, Object obj) {}
}
