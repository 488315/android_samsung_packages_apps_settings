package com.samsung.android.settings.privacy;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.preference.Preference;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.restriction.RestrictionPolicy;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecExpandableMenuPreference;
import com.samsung.android.util.SemLog;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecurityDashboardGoogleAccountSecurityPreferenceController
        extends SecurityDashboardAccountsSecurityPreferenceController {
    private static final String TAG = "SecurityDashboardGoogleAccountSecurityPreferenceController";

    public SecurityDashboardGoogleAccountSecurityPreferenceController(Context context, String str) {
        super(context, str);
    }

    private void launchGoogleAccountIntent() {
        Intent intent = new Intent();
        intent.setAction(
                this.mContext.getString(R.string.config_sec_toplevel_google_account_settings_uri));
        intent.putExtra("extra.screenId", 400);
        try {
            this.mContext.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            SemLog.d(TAG, "Google Account Activity Not Found" + e.getMessage());
        }
    }

    private void updateMenuStatus(SecExpandableMenuPreference secExpandableMenuPreference) {
        boolean isAccountSignedIn =
                SecurityDashboardUtils.isAccountSignedIn(
                        this.mContext, RestrictionPolicy.GOOGLE_ACCOUNT_TYPE);
        boolean z = SecurityDashboardPreferenceController.mIsGoogleAccountSupported;
        SecurityDashboardConstants$Status securityDashboardConstants$Status =
                SecurityDashboardConstants$Status.WARNING;
        SecurityDashboardConstants$Status securityDashboardConstants$Status2 =
                SecurityDashboardConstants$Status.OK;
        SecurityDashboardConstants$Status securityDashboardConstants$Status3 =
                z
                        ? isAccountSignedIn
                                ? securityDashboardConstants$Status2
                                : securityDashboardConstants$Status
                        : SecurityDashboardConstants$Status.NONE;
        SecurityDashboardPreferenceController.mGoogleAccountMenuStatus =
                securityDashboardConstants$Status3;
        secExpandableMenuPreference.setStatus(securityDashboardConstants$Status3);
        SecurityDashboardConstants$Status securityDashboardConstants$Status4 =
                SecurityDashboardPreferenceController.mGoogleAccountMenuStatus;
        if (securityDashboardConstants$Status2 == securityDashboardConstants$Status4) {
            secExpandableMenuPreference.seslSetSummaryColor(
                    this.mContext
                            .getResources()
                            .getColor(R.color.security_dashboard_menu_subtext_default_color, null));
            secExpandableMenuPreference.setSummary(
                    this.mContext
                            .getResources()
                            .getString(
                                    R.string
                                            .security_dashboard_google_account_all_good_description));
        } else if (securityDashboardConstants$Status == securityDashboardConstants$Status4) {
            secExpandableMenuPreference.seslSetSummaryColor(
                    this.mContext
                            .getResources()
                            .getColor(
                                    R.color.security_dashboard_menu_subtext_suggestion_color,
                                    null));
            secExpandableMenuPreference.setSummary(
                    this.mContext
                            .getResources()
                            .getString(
                                    R.string.security_dashboard_google_account_signin_description));
        }
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAccountsSecurityPreferenceController, com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return SecurityDashboardPreferenceController.mIsGoogleAccountSupported ? 0 : 3;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAccountsSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAccountsSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAccountsSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAccountsSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAccountsSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAccountsSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAccountsSecurityPreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return ApnSettings.MVNO_NONE;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAccountsSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAccountsSecurityPreferenceController, com.android.settings.core.BasePreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if ("key_google_account_security".equals(preference.getKey())) {
            LoggingHelper.insertEventLogging(9032, 56035);
            launchGoogleAccountIntent();
        }
        return super.handlePreferenceTreeClick(preference);
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAccountsSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAccountsSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAccountsSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAccountsSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAccountsSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAccountsSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAccountsSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAccountsSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAccountsSecurityPreferenceController, com.android.settings.core.BasePreferenceController
    public void updateNonIndexableKeys(List<String> list) {
        if (SecurityDashboardPreferenceController.mIsGoogleAccountSupported) {
            return;
        }
        list.add("key_google_account_security");
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAccountsSecurityPreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        updateMenuStatus((SecExpandableMenuPreference) preference);
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAccountsSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}