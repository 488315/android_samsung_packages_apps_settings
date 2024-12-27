package com.samsung.android.settings.privacy;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.provider.Settings;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreference;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecurityDashboardTheftDetectionLockPreferenceController
        extends BasePreferenceController {
    private static final String ONBOARDING_FRAGMENT =
            "com.samsung.android.settings.privacy.SecurityDashboardTheftProtectionDisclaimerFragment";
    protected boolean mIsScreenLockSet;
    private final boolean mIsTheftDetectionLockSupported;
    private Fragment mParentFragment;
    private SecPreference mPreference;
    protected boolean mTheftDetectionLockOnBoardingStatus;

    public SecurityDashboardTheftDetectionLockPreferenceController(Context context, String str) {
        super(context, str);
        this.mIsTheftDetectionLockSupported =
                SecurityDashboardUtils.isOfflineLockSupported(context);
    }

    private void updateMenuStatus() {
        if (this.mIsScreenLockSet || !this.mTheftDetectionLockOnBoardingStatus) {
            return;
        }
        this.mPreference.seslSetSummaryColor(
                this.mContext
                        .getResources()
                        .getColor(R.color.security_dashboard_menu_subtext_suggestion_color, null));
        this.mPreference.setSummary(
                this.mContext
                        .getResources()
                        .getString(R.string.theft_protection_missing_info_set_screen_lock));
    }

    private void updateParameters() {
        Context context = this.mContext;
        Uri uri = SecurityDashboardUtils.FMM_SUPPORT_URI;
        this.mTheftDetectionLockOnBoardingStatus =
                context.getSharedPreferences("key_theft_protection_preference", 0)
                        .getBoolean("key_theft_detection_lock_on_boarding_status", false);
        this.mIsScreenLockSet = SecurityDashboardUtils.isScreenLockEnabled(this.mContext);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = (SecPreference) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (!this.mIsTheftDetectionLockSupported) {
            return 3;
        }
        updateParameters();
        if (this.mTheftDetectionLockOnBoardingStatus) {
            return (Settings.Secure.getInt(
                                            this.mContext.getContentResolver(),
                                            "theft_detection_lock_setting",
                                            0)
                                    == 0
                            || this.mIsScreenLockSet)
                    ? 2
                    : 0;
        }
        return 0;
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!"key_theft_detection_lock".equals(preference.getKey())) {
            return super.handlePreferenceTreeClick(preference);
        }
        if (!this.mTheftDetectionLockOnBoardingStatus) {
            SecurityDashboardUtils.startFragment(
                    R.string.theft_protection_theft_detection_lock_menu_title,
                    this.mParentFragment,
                    ONBOARDING_FRAGMENT);
            return true;
        }
        if (this.mIsScreenLockSet) {
            return true;
        }
        SecurityDashboardUtils.launchChooseLockScreen(this.mContext);
        return true;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public void init(Fragment fragment) {
        this.mParentFragment = fragment;
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

    @Override // com.android.settings.core.BasePreferenceController
    public void updateNonIndexableKeys(List<String> list) {
        if (getAvailabilityStatus() != 0) {
            list.add(getPreferenceKey());
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        updateParameters();
        updateMenuStatus();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
