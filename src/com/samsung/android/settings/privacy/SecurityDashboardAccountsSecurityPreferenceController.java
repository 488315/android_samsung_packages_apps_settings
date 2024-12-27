package com.samsung.android.settings.privacy;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.core.util.Consumer;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;

import com.samsung.android.knox.restriction.RestrictionPolicy;
import com.samsung.android.settings.account.AccountUtils;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.lockscreen.LockUtils;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecExpandableMenuPreference;
import com.samsung.android.settings.widget.SecurityDashboardStatusPreference;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecurityDashboardAccountsSecurityPreferenceController
        extends SecurityDashboardPreferenceController {
    private SecurityDashboardConstants$Status mAccountMenuStatus;
    protected ExecutorService mExecutorService;
    protected Handler mHandler;
    private boolean mIsAccountsMenuSupported;
    private boolean mIsSamsungAccountSignedIn;
    protected boolean mIsSamsungAccountSupported;
    private SecurityDashboardStatusPreference mStatusPreference;

    public SecurityDashboardAccountsSecurityPreferenceController(Context context, String str) {
        super(context, str);
        this.mExecutorService = Executors.newSingleThreadExecutor();
        this.mHandler = new Handler(Looper.getMainLooper());
        SecurityDashboardPreferenceController.mSamsungAccountMenuStatus =
                SecurityDashboardConstants$Status.NONE;
        boolean z = false;
        SecurityDashboardPreferenceController.mIsGoogleAccountSupported =
                SecurityDashboardUtils.isChinaModel()
                        ? false
                        : SecurityDashboardUtils.isGoogleServiceEnabled(context);
        this.mIsSamsungAccountSupported = SecurityDashboardUtils.isSamsungAccountSupported(context);
        if (!Utils.isRestrictedProfile(context)
                && (SecurityDashboardPreferenceController.mIsGoogleAccountSupported
                        || this.mIsSamsungAccountSupported)) {
            z = true;
        }
        this.mIsAccountsMenuSupported = z;
    }

    private SecurityDashboardConstants$Status getStatusForAccountsMenu() {
        SecurityDashboardConstants$Status securityDashboardConstants$Status =
                SecurityDashboardPreferenceController.mSamsungAccountMenuStatus;
        if (securityDashboardConstants$Status == SecurityDashboardConstants$Status.NONE) {
            return SecurityDashboardPreferenceController.mGoogleAccountMenuStatus;
        }
        SecurityDashboardConstants$Status securityDashboardConstants$Status2 =
                SecurityDashboardConstants$Status.WARNING;
        return (securityDashboardConstants$Status == securityDashboardConstants$Status2
                        || SecurityDashboardPreferenceController.mGoogleAccountMenuStatus
                                == securityDashboardConstants$Status2)
                ? securityDashboardConstants$Status2
                : SecurityDashboardConstants$Status.OK;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$prepareAccountsMenuStatus$0(
            SecExpandableMenuPreference secExpandableMenuPreference,
            SecurityDashboardConstants$Status securityDashboardConstants$Status) {
        SecurityDashboardPreferenceController.mSamsungAccountMenuStatus =
                securityDashboardConstants$Status;
        setAccountsMenuStatus(secExpandableMenuPreference);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setAccountsMenuStatus$1(
            SecExpandableMenuPreference secExpandableMenuPreference) {
        secExpandableMenuPreference.setStatus(this.mAccountMenuStatus);
        setAccountSecuritySummary(secExpandableMenuPreference);
        updateStatusAndCardViews();
    }

    private void prepareAccountsMenuStatus(
            final SecExpandableMenuPreference secExpandableMenuPreference) {
        this.mIsSamsungAccountSignedIn =
                SecurityDashboardUtils.isAccountSignedIn(this.mContext, "com.osp.app.signin");
        this.mExecutorService.execute(
                new SecurityDashboardUtils$$ExternalSyntheticLambda0(
                        this.mContext,
                        new Consumer() { // from class:
                                         // com.samsung.android.settings.privacy.SecurityDashboardAccountsSecurityPreferenceController$$ExternalSyntheticLambda1
                            @Override // androidx.core.util.Consumer
                            public final void accept(Object obj) {
                                SecurityDashboardAccountsSecurityPreferenceController.this
                                        .lambda$prepareAccountsMenuStatus$0(
                                                secExpandableMenuPreference,
                                                (SecurityDashboardConstants$Status) obj);
                            }
                        }));
    }

    private void setAccountSecuritySummary(
            SecExpandableMenuPreference secExpandableMenuPreference) {
        SecurityDashboardConstants$Status securityDashboardConstants$Status =
                SecurityDashboardConstants$Status.OK;
        SecurityDashboardConstants$Status securityDashboardConstants$Status2 =
                this.mAccountMenuStatus;
        if (securityDashboardConstants$Status == securityDashboardConstants$Status2) {
            secExpandableMenuPreference.seslSetSummaryColor(
                    this.mContext
                            .getResources()
                            .getColor(R.color.security_dashboard_menu_subtext_default_color, null),
                    false);
            secExpandableMenuPreference.setSummary(
                    this.mContext
                            .getResources()
                            .getString(
                                    R.string
                                            .security_dashboard_account_security_all_good_description),
                    false);
            return;
        }
        if (SecurityDashboardConstants$Status.WARNING == securityDashboardConstants$Status2) {
            secExpandableMenuPreference.seslSetSummaryColor(
                    this.mContext
                            .getResources()
                            .getColor(
                                    R.color.security_dashboard_menu_subtext_suggestion_color, null),
                    false);
            if (!this.mIsSamsungAccountSupported) {
                secExpandableMenuPreference.setSummary(
                        this.mContext
                                .getResources()
                                .getString(
                                        R.string
                                                .security_dashboard_google_account_signin_description),
                        false);
                return;
            }
            if (SecurityDashboardPreferenceController.mSamsungAccountMenuStatus.ordinal()
                    < SecurityDashboardPreferenceController.mGoogleAccountMenuStatus.ordinal()) {
                secExpandableMenuPreference.setSummary(
                        this.mContext
                                .getResources()
                                .getString(
                                        R.string
                                                .security_dashboard_google_account_signin_description),
                        false);
                return;
            }
            Context context = this.mContext;
            Uri uri = SecurityDashboardUtils.FMM_SUPPORT_URI;
            if (AccountUtils.isSamsungAccountExists(context)) {
                secExpandableMenuPreference.setSummary(
                        this.mContext
                                .getResources()
                                .getString(R.string.security_dashboard_samsung_account_check),
                        false);
            } else {
                secExpandableMenuPreference.setSummary(
                        this.mContext
                                .getResources()
                                .getString(
                                        R.string
                                                .security_dashboard_samsung_account_signin_description),
                        false);
            }
        }
    }

    private void setAccountsMenuStatus(
            final SecExpandableMenuPreference secExpandableMenuPreference) {
        this.mAccountMenuStatus = getStatusForAccountsMenu();
        this.mHandler.post(
                new Runnable() { // from class:
                                 // com.samsung.android.settings.privacy.SecurityDashboardAccountsSecurityPreferenceController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SecurityDashboardAccountsSecurityPreferenceController.this
                                .lambda$setAccountsMenuStatus$1(secExpandableMenuPreference);
                    }
                });
    }

    private void updateMenuStatus() {
        if ("key_accounts_security".equals(this.mPreference.getKey())) {
            SecurityDashboardConstants$Status securityDashboardConstants$Status =
                    SecurityDashboardPreferenceController.mIsGoogleAccountSupported
                            ? SecurityDashboardUtils.isAccountSignedIn(
                                            this.mContext, RestrictionPolicy.GOOGLE_ACCOUNT_TYPE)
                                    ? SecurityDashboardConstants$Status.OK
                                    : SecurityDashboardConstants$Status.WARNING
                            : SecurityDashboardConstants$Status.NONE;
            SecurityDashboardPreferenceController.mGoogleAccountMenuStatus =
                    securityDashboardConstants$Status;
            if (this.mIsSamsungAccountSupported) {
                prepareAccountsMenuStatus(this.mPreference);
                return;
            }
            this.mAccountMenuStatus = securityDashboardConstants$Status;
            this.mPreference.setStatus(securityDashboardConstants$Status);
            this.mStatusPreference.setMenuStatus(
                    "key_google_account_security",
                    "GA",
                    SecurityDashboardPreferenceController.mGoogleAccountMenuStatus);
        }
    }

    private void updateMenuStatusWithValues() {
        if ("key_accounts_security".equals(this.mPreference.getKey())) {
            if (this.mIsSamsungAccountSupported) {
                setAccountsMenuStatus(this.mPreference);
                return;
            }
            SecurityDashboardConstants$Status securityDashboardConstants$Status =
                    SecurityDashboardPreferenceController.mGoogleAccountMenuStatus;
            this.mAccountMenuStatus = securityDashboardConstants$Status;
            this.mPreference.setStatus(securityDashboardConstants$Status);
            this.mStatusPreference.setMenuStatus(
                    "key_google_account_security",
                    "GA",
                    SecurityDashboardPreferenceController.mGoogleAccountMenuStatus);
        }
    }

    private void updateStatusAndCardViews() {
        this.mStatusPreference.setMenuStatus(
                "key_google_account_security",
                "GA",
                SecurityDashboardPreferenceController.mGoogleAccountMenuStatus);
        this.mStatusPreference.setMenuStatus(
                "key_samsung_account_security",
                "SA",
                SecurityDashboardPreferenceController.mSamsungAccountMenuStatus);
        this.mStatusPreference.setupStatus(false);
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mStatusPreference =
                (SecurityDashboardStatusPreference)
                        preferenceScreen.findPreference("security_dashboard_status");
        updateMenuStatus();
        if (LockUtils.isLockSettingsBlockonDexMode(this.mContext)) {
            this.mPreference.mIsDividerVisible = false;
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mIsAccountsMenuSupported ? 0 : 3;
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
                this.mIsSamsungAccountSupported
                        ? this.mContext
                                .getResources()
                                .getString(
                                        R.string.security_dashboard_samsung_account_security_title)
                        : null;
        if (!SecurityDashboardPreferenceController.mIsGoogleAccountSupported) {
            return string;
        }
        if (string == null) {
            return this.mContext
                    .getResources()
                    .getString(R.string.security_dashboard_google_account_security_title);
        }
        StringBuilder m =
                EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(string);
        m.append(this.mContext.getResources().getString(R.string.comma));
        m.append(" ");
        m.append(
                this.mContext
                        .getResources()
                        .getString(R.string.security_dashboard_google_account_security_title));
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
        if ("key_accounts_security".equals(preference.getKey())) {
            LoggingHelper.insertEventLogging(9032, 56033);
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
        if (this.mIsSamsungAccountSupported) {
            sb.append(
                    this.mContext
                            .getResources()
                            .getString(R.string.security_dashboard_samsung_account_security_title));
        }
        if (SecurityDashboardPreferenceController.mIsGoogleAccountSupported) {
            if (sb.length() > 0) {
                sb.append(this.mContext.getResources().getString(R.string.comma) + " ");
            }
            sb.append(
                    this.mContext
                            .getResources()
                            .getString(R.string.security_dashboard_google_account_security_title));
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
        if (this.mIsAccountsMenuSupported) {
            return;
        }
        list.add(getPreferenceKey());
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        updateMenuStatusWithValues();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
