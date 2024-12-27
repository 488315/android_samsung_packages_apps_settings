package com.samsung.android.settings.privacy;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.UserHandle;

import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;

import com.samsung.android.knox.restriction.RestrictionPolicy;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.softwareupdate.SoftwareUpdateUtils;
import com.samsung.android.settings.widget.SecurityDashboardStatusPreference;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecurityDashboardUpdatesPreferenceController
        extends SecurityDashboardPreferenceController {
    private static final String TAG = "SecurityDashboardUpdatesPreferenceController";
    protected boolean mIsGPSuSupported;
    protected boolean mIsSecurityUpdateSupported;
    private boolean mIsUpdatesSupported;
    private SecurityDashboardStatusPreference mStatusPreference;

    public SecurityDashboardUpdatesPreferenceController(Context context, String str) {
        super(context, str);
        Context context2 = this.mContext;
        Uri uri = SecurityDashboardUtils.FMM_SUPPORT_URI;
        this.mIsSecurityUpdateSupported =
                UserHandle.myUserId() == 0
                        ? SoftwareUpdateUtils.isOTAUpgradeAllowed(context2)
                        : false;
        boolean isGPSuSupported = SecurityDashboardUtils.isGPSuSupported(this.mContext);
        this.mIsGPSuSupported = isGPSuSupported;
        this.mIsUpdatesSupported = this.mIsSecurityUpdateSupported || isGPSuSupported;
    }

    private void setUpdatesSummary(
            SecurityDashboardConstants$Status securityDashboardConstants$Status) {
        if (SecurityDashboardConstants$Status.OK != securityDashboardConstants$Status) {
            if (SecurityDashboardConstants$Status.WARNING == securityDashboardConstants$Status) {
                this.mPreference.seslSetSummaryColor(
                        this.mContext
                                .getResources()
                                .getColor(
                                        R.color.security_dashboard_menu_subtext_suggestion_color,
                                        null),
                        false);
                this.mPreference.setSummary(
                        this.mContext
                                .getResources()
                                .getString(
                                        R.string
                                                .security_dashboard_updates_install_latest_software_description),
                        false);
            }
            return;
        }
        this.mPreference.seslSetSummaryColor(
                this.mContext
                        .getResources()
                        .getColor(R.color.security_dashboard_menu_subtext_default_color, null),
                false);
        String key = this.mPreference.getKey();
        key.getClass();
        switch (key) {
            case "key_security_update":
                this.mPreference.setSummary(SecurityDashboardUtils.getSecurityUpdateSummary());
                break;
            case "key_google_play_system_update":
                this.mPreference.setSummary(
                        SecurityDashboardUtils.getGPSystemUpdateSummary(this.mContext));
                break;
            case "key_updates":
                this.mPreference.setSummary(
                        this.mContext
                                .getResources()
                                .getString(
                                        R.string.security_dashboard_updates_all_good_description),
                        false);
                break;
        }
    }

    private void updateMenuStatus() {
        long daysCountSinceLastGPSystemUpdate =
                this.mIsGPSuSupported
                        ? SecurityDashboardUtils.getDaysCountSinceLastGPSystemUpdate(this.mContext)
                        : 0L;
        boolean isSecurityUpdateInstalled =
                SecurityDashboardUtils.isSecurityUpdateInstalled(this.mContext);
        boolean z = SecurityDashboardUtils.getDaysCountSinceLastSecurityUpdate() > 360;
        boolean isAccountSignedIn =
                SecurityDashboardUtils.isAccountSignedIn(
                        this.mContext, RestrictionPolicy.GOOGLE_ACCOUNT_TYPE);
        SecurityDashboardConstants$Status securityDashboardConstants$Status =
                SecurityDashboardConstants$Status.OK;
        SecurityDashboardConstants$Status securityDashboardConstants$Status2 =
                SecurityDashboardConstants$Status.WARNING;
        SecurityDashboardConstants$Status securityDashboardConstants$Status3 =
                (!isAccountSignedIn
                                ? !(!isSecurityUpdateInstalled || z)
                                : !(!isSecurityUpdateInstalled
                                        || z
                                        || daysCountSinceLastGPSystemUpdate > 360))
                        ? securityDashboardConstants$Status2
                        : securityDashboardConstants$Status;
        this.mPreference.setStatus(securityDashboardConstants$Status3);
        setUpdatesSummary(securityDashboardConstants$Status3);
        SecurityDashboardStatusPreference securityDashboardStatusPreference =
                this.mStatusPreference;
        if (securityDashboardStatusPreference != null) {
            if (this.mIsSecurityUpdateSupported) {
                securityDashboardStatusPreference.setMenuStatus(
                        "key_security_update",
                        "SU",
                        (!isSecurityUpdateInstalled || z)
                                ? securityDashboardConstants$Status2
                                : securityDashboardConstants$Status);
            }
            if (this.mIsGPSuSupported) {
                if (SecurityDashboardUtils.getDaysCountSinceLastGPSystemUpdate(this.mContext)
                        > 360) {
                    securityDashboardConstants$Status = securityDashboardConstants$Status2;
                }
                this.mStatusPreference.setMenuStatus(
                        "key_google_play_system_update", "GPSU", securityDashboardConstants$Status);
            }
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
        return this.mIsUpdatesSupported ? 0 : 3;
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        String string =
                this.mIsSecurityUpdateSupported
                        ? this.mContext.getResources().getString(R.string.security_update)
                        : null;
        if (!this.mIsGPSuSupported) {
            return string;
        }
        if (string == null) {
            return this.mContext
                    .getResources()
                    .getString(R.string.security_dashboard_google_play_system_update_title);
        }
        StringBuilder m =
                EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(string);
        m.append(this.mContext.getResources().getString(R.string.comma));
        m.append(" ");
        m.append(
                this.mContext
                        .getResources()
                        .getString(R.string.security_dashboard_google_play_system_update_title));
        return m.toString();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if ("key_updates".equals(preference.getKey())) {
            LoggingHelper.insertEventLogging(9032, 56038);
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
        StringBuilder sb = new StringBuilder();
        if (this.mIsSecurityUpdateSupported) {
            sb.append(this.mContext.getResources().getString(R.string.security_update));
        }
        if (this.mIsGPSuSupported) {
            if (sb.length() > 0) {
                sb.append(this.mContext.getResources().getString(R.string.comma) + " ");
            }
            sb.append(
                    this.mContext
                            .getResources()
                            .getString(
                                    R.string.security_dashboard_google_play_system_update_title));
        }
        this.mPreference.setSummary(sb, true);
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
        if (this.mIsUpdatesSupported) {
            return;
        }
        list.add("key_updates");
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
