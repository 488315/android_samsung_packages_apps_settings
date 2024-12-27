package com.samsung.android.settings.lockscreen.controller;

import android.app.admin.DevicePolicyManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.UserHandle;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.RestrictedSwitchPreference;

import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.lockscreen.LockScreenNotificationSettings;
import com.samsung.android.settings.lockscreen.SecConceptBehavior;
import com.samsung.android.settings.lockscreen.SecConceptControllerBehavior;
import com.samsung.android.settings.logging.SALogging;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class HideContentPreferenceController extends TogglePreferenceController
        implements SecConceptControllerBehavior {
    static final String KEY = "set_visibility";
    SecConceptBehavior mContextDashBoard;
    LockPatternUtils mLockPatternUtils;
    RestrictedSwitchPreference mPreference;

    public HideContentPreferenceController(Context context, SecConceptBehavior secConceptBehavior) {
        super(context, KEY);
        this.mContextDashBoard = secConceptBehavior;
        this.mLockPatternUtils = new LockPatternUtils(context);
    }

    private void updateDetailSummary() {
        RestrictedSwitchPreference restrictedSwitchPreference = this.mPreference;
        if (restrictedSwitchPreference == null || !restrictedSwitchPreference.isShown()) {
            return;
        }
        this.mPreference.setSummary(ApnSettings.MVNO_NONE);
    }

    @Override // com.samsung.android.settings.lockscreen.SecConceptControllerBehavior
    public void accept(String str, Object obj) {
        str.getClass();
        if (!str.equals("lock_screen_show_notifications")) {
            if (str.equals("notification_details")) {
                updateDetailSummary();
            }
        } else {
            boolean booleanValue = ((Boolean) obj).booleanValue();
            RestrictedSwitchPreference restrictedSwitchPreference = this.mPreference;
            if (restrictedSwitchPreference != null) {
                restrictedSwitchPreference.setEnabled(booleanValue);
            }
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (RestrictedSwitchPreference) preferenceScreen.findPreference(getPreferenceKey());
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) this.mContext.getSystemService("device_policy");
        if (devicePolicyManager != null) {
            int keyguardDisabledFeatures = devicePolicyManager.getKeyguardDisabledFeatures(null);
            if ((keyguardDisabledFeatures & 8) == 0 && (keyguardDisabledFeatures & 4) == 0) {
                return;
            }
            this.mPreference.setEnabled(false);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return !this.mLockPatternUtils.isLockScreenDisabled(UserHandle.semGetMyUserId()) ? 0 : 2;
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
        return Settings.Secure.getInt(
                        this.mContext.getContentResolver(),
                        "lock_screen_allow_private_notifications",
                        0)
                != 0;
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
        ContentResolver contentResolver = this.mContext.getContentResolver();
        int i = !z ? 1 : 0;
        Settings.Secure.putInt(contentResolver, "lock_screen_allow_private_notifications", i);
        int dualAppProfileId = SemDualAppManager.getDualAppProfileId();
        if (SemDualAppManager.isDualAppId(dualAppProfileId)) {
            Settings.Secure.putIntForUser(
                    this.mContext.getContentResolver(),
                    "lock_screen_allow_private_notifications",
                    i,
                    dualAppProfileId);
        }
        SALogging.insertSALog(z ? 1L : 0L, String.valueOf(4435), "4452", (String) null);
        ((LockScreenNotificationSettings) this.mContextDashBoard)
                .notifyChange(Boolean.valueOf(z), getPreferenceKey());
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        RestrictedLockUtils.EnforcedAdmin checkIfKeyguardFeaturesDisabled =
                RestrictedLockUtilsInternal.checkIfKeyguardFeaturesDisabled(
                        this.mContext, 4, UserHandle.semGetMyUserId());
        this.mPreference.setChecked(!getThreadEnabled());
        this.mPreference.setDisabledByAdmin(checkIfKeyguardFeaturesDisabled);
        updateDetailSummary();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    @Override // com.samsung.android.settings.lockscreen.SecConceptControllerBehavior
    public /* bridge */ /* synthetic */ void updateConfigurationChanged(
            Configuration configuration) {}
}
