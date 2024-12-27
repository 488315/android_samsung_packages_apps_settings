package com.samsung.android.settings.privacy;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreference;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.restriction.RestrictionPolicy;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecurityDashboardRemoteLockPreferenceController extends BasePreferenceController {
    private static final String REMOTE_LOCK_INTENT_ACTION =
            "com.google.android.gms.settings.QRL_SETTINGS";
    private static final String REMOTE_LOCK_SETTING = "remote_lock_setting";
    private static final String TAG = "SecurityDashboardSecurityUpdatePreferenceController";
    private final boolean mIsRemoteLockSupported;
    private SecPreference mPreference;

    public SecurityDashboardRemoteLockPreferenceController(Context context, String str) {
        super(context, str);
        this.mIsRemoteLockSupported = SecurityDashboardUtils.isRemoteLockSupported(context);
    }

    private String getErrorMessage() {
        return !SecurityDashboardUtils.isAccountSignedIn(
                        this.mContext, RestrictionPolicy.GOOGLE_ACCOUNT_TYPE)
                ? this.mContext
                        .getResources()
                        .getString(
                                R.string
                                        .theft_protection_remote_lock_missing_info_add_google_account)
                : !SecurityDashboardUtils.isFindMyMobileEnabled(this.mContext)
                        ? this.mContext
                                .getResources()
                                .getString(
                                        R.string
                                                .theft_protection_remote_lock_missing_info_turnon_fmm)
                        : !SecurityDashboardUtils.isScreenLockEnabled(this.mContext)
                                ? this.mContext
                                        .getResources()
                                        .getString(
                                                R.string
                                                        .theft_protection_missing_info_set_screen_lock)
                                : ApnSettings.MVNO_NONE;
    }

    private void launchRemoteLockSettings() {
        this.mContext.startActivity(
                new Intent(REMOTE_LOCK_INTENT_ACTION).setPackage("com.google.android.gms"));
    }

    private void setTheftProtectionRemoteLockSummary() {
        if (Settings.Secure.getInt(this.mContext.getContentResolver(), REMOTE_LOCK_SETTING, 0)
                == 0) {
            this.mPreference.seslSetSummaryColor(
                    this.mContext
                            .getResources()
                            .getColor(R.color.security_dashboard_menu_subtext_default_color, null));
            this.mPreference.setSummary(
                    this.mContext
                            .getResources()
                            .getString(R.string.theft_protection_remote_lock_menu_description));
            return;
        }
        String errorMessage = getErrorMessage();
        if (TextUtils.isEmpty(errorMessage)) {
            this.mPreference.seslSetSummaryColor(
                    this.mContext
                            .getResources()
                            .getColor(R.color.security_dashboard_menu_subtext_default_color, null));
            this.mPreference.setSummary(
                    this.mContext
                            .getResources()
                            .getString(R.string.theft_protection_remote_lock_menu_description));
        } else {
            this.mPreference.seslSetSummaryColor(
                    this.mContext
                            .getResources()
                            .getColor(
                                    R.color.security_dashboard_menu_subtext_suggestion_color,
                                    null));
            this.mPreference.setSummary(errorMessage);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = (SecPreference) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mIsRemoteLockSupported ? 0 : 3;
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
        if (!TextUtils.equals(getPreferenceKey(), preference.getKey())) {
            return super.handlePreferenceTreeClick(preference);
        }
        LoggingHelper.insertEventLogging(54101, 54203);
        Settings.Secure.getInt(this.mContext.getContentResolver(), REMOTE_LOCK_SETTING, 0);
        launchRemoteLockSettings();
        return true;
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

    @Override // com.android.settings.core.BasePreferenceController
    public void updateNonIndexableKeys(List<String> list) {
        if (this.mIsRemoteLockSupported) {
            return;
        }
        list.add(getPreferenceKey());
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        updateMenuStatus();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    private void updateMenuStatus() {}

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
