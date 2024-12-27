package com.samsung.android.settings.privacy;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecurityDashboardStatusPreference;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecurityDashboardLegacyDeviceFindersPreferenceController
        extends SecurityDashboardPreferenceController {
    private static final String SECURITY_STATUS_FIND_DEVICE = "security_status_find_device";
    protected boolean mIsFMMSupported;
    protected boolean mIsTheftProtectionSupported;
    private SecurityDashboardStatusPreference mStatusPreference;

    public SecurityDashboardLegacyDeviceFindersPreferenceController(Context context, String str) {
        super(context, str);
        this.mIsFMMSupported = SecurityDashboardUtils.isFMMSupported(context);
        this.mIsTheftProtectionSupported =
                SecurityDashboardUtils.isTheftProtectionSupported(context);
    }

    private void updateMenuStatus() {
        boolean isFindMyMobileEnabled = SecurityDashboardUtils.isFindMyMobileEnabled(this.mContext);
        SecurityDashboardConstants$Status securityDashboardConstants$Status =
                SecurityDashboardConstants$Status.WARNING;
        SecurityDashboardConstants$Status securityDashboardConstants$Status2 =
                SecurityDashboardConstants$Status.OK;
        SecurityDashboardConstants$Status securityDashboardConstants$Status3 =
                isFindMyMobileEnabled
                        ? securityDashboardConstants$Status2
                        : securityDashboardConstants$Status;
        this.mPreference.setStatus(securityDashboardConstants$Status3);
        this.mStatusPreference.setMenuStatus(
                "key_device_finders", "FMM", securityDashboardConstants$Status3);
        if (securityDashboardConstants$Status2 == securityDashboardConstants$Status3) {
            this.mPreference.seslSetSummaryColor(
                    this.mContext
                            .getResources()
                            .getColor(R.color.security_dashboard_menu_subtext_default_color, null),
                    false);
            this.mPreference.setSummary(
                    this.mContext
                            .getResources()
                            .getString(
                                    R.string
                                            .security_dashboard_lost_device_protection_all_good_description),
                    false);
        } else if (securityDashboardConstants$Status == securityDashboardConstants$Status3) {
            this.mPreference.seslSetSummaryColor(
                    this.mContext
                            .getResources()
                            .getColor(
                                    R.color.security_dashboard_menu_subtext_suggestion_color, null),
                    false);
            this.mPreference.setSummary(
                    this.mContext
                            .getResources()
                            .getString(R.string.security_dashboard_lost_device_protection_fmm_off),
                    false);
        }
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mStatusPreference =
                (SecurityDashboardStatusPreference)
                        preferenceScreen.findPreference("security_dashboard_status");
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mIsTheftProtectionSupported ? 3 : 0;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if ("key_device_finders".equals(preference.getKey())) {
            LoggingHelper.insertEventLogging(9032, 56043);
        }
        return super.handlePreferenceTreeClick(preference);
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController
    public void setDefaultSummary() {
        this.mPreference.setSummary(
                this.mContext
                        .getResources()
                        .getString(R.string.security_dashboard_find_my_mobile_title),
                true);
        this.mPreference.seslSetSummaryColor(
                this.mContext
                        .getResources()
                        .getColor(R.color.security_dashboard_menu_subtext_default_color, null),
                true);
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public void updateNonIndexableKeys(List<String> list) {
        if (!this.mIsFMMSupported || this.mIsTheftProtectionSupported) {
            list.add("key_device_finders");
        }
        list.add(SECURITY_STATUS_FIND_DEVICE);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        updateMenuStatus();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
