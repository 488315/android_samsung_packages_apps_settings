package com.samsung.android.settings.privacy;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;

import androidx.core.util.Consumer;
import androidx.preference.Preference;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.account.AccountUtils;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecExpandableMenuPreference;
import com.samsung.android.util.SemLog;

import java.util.List;
import java.util.concurrent.ExecutorService;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecurityDashboardSamsungAccountSecurityPreferenceController
        extends SecurityDashboardAccountsSecurityPreferenceController {
    private static final String TAG = "SecurityDashboardSamsungAccountSecurityPreferenceController";

    public SecurityDashboardSamsungAccountSecurityPreferenceController(
            Context context, String str) {
        super(context, str);
        SecurityDashboardPreferenceController.mSamsungAccountMenuStatus =
                SecurityDashboardConstants$Status.NONE;
        SecurityDashboardPreferenceController.mIsGoogleAccountSupported =
                SecurityDashboardUtils.isChinaModel()
                        ? false
                        : SecurityDashboardUtils.isGoogleServiceEnabled(context);
        this.mIsSamsungAccountSupported = SecurityDashboardUtils.isSamsungAccountSupported(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateMenuStatus$0(
            SecExpandableMenuPreference secExpandableMenuPreference) {
        secExpandableMenuPreference.setStatus(
                SecurityDashboardPreferenceController.mSamsungAccountMenuStatus);
        setSamsungAccountSecuritySummary(secExpandableMenuPreference);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateMenuStatus$1(
            final SecExpandableMenuPreference secExpandableMenuPreference,
            SecurityDashboardConstants$Status securityDashboardConstants$Status) {
        SecurityDashboardPreferenceController.mSamsungAccountMenuStatus =
                securityDashboardConstants$Status;
        this.mHandler.post(
                new Runnable() { // from class:
                                 // com.samsung.android.settings.privacy.SecurityDashboardSamsungAccountSecurityPreferenceController$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SecurityDashboardSamsungAccountSecurityPreferenceController.this
                                .lambda$updateMenuStatus$0(secExpandableMenuPreference);
                    }
                });
    }

    private void launchSamsungAccountIntent(
            SecExpandableMenuPreference secExpandableMenuPreference) {
        SecurityDashboardConstants$Status securityDashboardConstants$Status =
                secExpandableMenuPreference.mStatus;
        Intent intent = new Intent();
        boolean isAccountSignedIn =
                SecurityDashboardUtils.isAccountSignedIn(this.mContext, "com.osp.app.signin");
        try {
            if (securityDashboardConstants$Status != SecurityDashboardConstants$Status.NONE) {
                SecurityDashboardConstants$Status securityDashboardConstants$Status2 =
                        SecurityDashboardConstants$Status.WARNING;
                if (isAccountSignedIn
                        || securityDashboardConstants$Status
                                != securityDashboardConstants$Status2) {
                    if (securityDashboardConstants$Status == securityDashboardConstants$Status2
                            || securityDashboardConstants$Status
                                    == SecurityDashboardConstants$Status.OK) {
                        intent.setAction(
                                "com.samsung.android.samsungaccount.action.OPEN_SECURITY_AND_PRIVACY");
                        intent.setPackage("com.osp.app.signin");
                        intent.putExtra("from_settings", "true");
                    }
                    this.mContext.startActivity(intent);
                    return;
                }
            }
            this.mContext.startActivity(intent);
            return;
        } catch (ActivityNotFoundException e) {
            SemLog.d(TAG, "Samsung Account Activity Not Found" + e.getMessage());
            return;
        }
        intent.setClassName(
                this.mContext.getString(R.string.config_sec_toplevel_samsung_account_package),
                this.mContext.getString(R.string.config_sec_toplevel_samsung_account_class));
        intent.putExtra("from_settings", "true");
    }

    private void setSamsungAccountSecuritySummary(
            SecExpandableMenuPreference secExpandableMenuPreference) {
        SecurityDashboardConstants$Status securityDashboardConstants$Status =
                SecurityDashboardConstants$Status.OK;
        SecurityDashboardConstants$Status securityDashboardConstants$Status2 =
                SecurityDashboardPreferenceController.mSamsungAccountMenuStatus;
        if (securityDashboardConstants$Status == securityDashboardConstants$Status2) {
            secExpandableMenuPreference.seslSetSummaryColor(
                    this.mContext
                            .getResources()
                            .getColor(R.color.security_dashboard_menu_subtext_default_color, null));
            secExpandableMenuPreference.setSummary(
                    this.mContext
                            .getResources()
                            .getString(
                                    R.string
                                            .security_dashboard_samsung_account_all_good_description));
        } else if (SecurityDashboardConstants$Status.WARNING
                == securityDashboardConstants$Status2) {
            secExpandableMenuPreference.seslSetSummaryColor(
                    this.mContext
                            .getResources()
                            .getColor(
                                    R.color.security_dashboard_menu_subtext_suggestion_color,
                                    null));
            Context context = this.mContext;
            Uri uri = SecurityDashboardUtils.FMM_SUPPORT_URI;
            if (AccountUtils.isSamsungAccountExists(context)) {
                secExpandableMenuPreference.setSummary(
                        this.mContext
                                .getResources()
                                .getString(R.string.security_dashboard_samsung_account_check));
            } else {
                secExpandableMenuPreference.setSummary(
                        this.mContext
                                .getResources()
                                .getString(
                                        R.string
                                                .security_dashboard_samsung_account_signin_description));
            }
        }
    }

    private void updateMenuStatus(final SecExpandableMenuPreference secExpandableMenuPreference) {
        Context context = this.mContext;
        ExecutorService executorService = this.mExecutorService;
        Consumer consumer =
                new Consumer() { // from class:
                                 // com.samsung.android.settings.privacy.SecurityDashboardSamsungAccountSecurityPreferenceController$$ExternalSyntheticLambda0
                    @Override // androidx.core.util.Consumer
                    public final void accept(Object obj) {
                        SecurityDashboardSamsungAccountSecurityPreferenceController.this
                                .lambda$updateMenuStatus$1(
                                        secExpandableMenuPreference,
                                        (SecurityDashboardConstants$Status) obj);
                    }
                };
        Uri uri = SecurityDashboardUtils.FMM_SUPPORT_URI;
        executorService.execute(
                new SecurityDashboardUtils$$ExternalSyntheticLambda0(context, consumer));
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAccountsSecurityPreferenceController, com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mIsSamsungAccountSupported ? 0 : 3;
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
        if ("key_samsung_account_security".equals(preference.getKey())) {
            LoggingHelper.insertEventLogging(9032, 56034);
            launchSamsungAccountIntent((SecExpandableMenuPreference) preference);
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
        if (SecurityDashboardUtils.isSamsungAccountSupported(this.mContext)) {
            return;
        }
        list.add("key_samsung_account_security");
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
