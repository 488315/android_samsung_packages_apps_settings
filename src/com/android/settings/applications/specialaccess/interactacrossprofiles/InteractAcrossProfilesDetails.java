package com.android.settings.applications.specialaccess.interactacrossprofiles;

import android.app.ActionBar;
import android.app.admin.DevicePolicyEventLogger;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.PermissionChecker;
import android.content.pm.CrossProfileApps;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.applications.AppInfoBase;
import com.android.settings.applications.AppStoreUtil;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedSwitchPreference;

import com.samsung.android.settings.widget.SecButtonPreference;
import com.samsung.android.settings.widget.SecUnclickablePreference;
import com.samsung.android.util.SemLog;

import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class InteractAcrossProfilesDetails extends AppInfoBase
        implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {
    public String mAppLabel;
    public Context mContext;
    public CrossProfileApps mCrossProfileApps;
    public AlertDialog mDialog;
    public Intent mInstallAppIntent;
    public SecUnclickablePreference mInstallBanner;
    public SecButtonPreference mInstallBannerButton;
    public boolean mInstalledInPersonal;
    public boolean mInstalledInWork;
    public boolean mIsPageLaunchedByApp;
    public PackageManager mPackageManager;
    public UserHandle mPersonalProfile;
    public RestrictedSwitchPreference mSwitchPref;
    public UserManager mUserManager;
    public UserHandle mWorkProfile;

    public static boolean isInteractAcrossProfilesEnabled(Context context, String str) {
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        UserHandle workProfile = InteractAcrossProfilesSettings.getWorkProfile(userManager);
        if (workProfile == null) {
            return false;
        }
        UserHandle profileParent = userManager.getProfileParent(workProfile);
        if (!((CrossProfileApps) context.getSystemService(CrossProfileApps.class))
                .canConfigureInteractAcrossProfiles(str)) {
            return false;
        }
        try {
            if (PermissionChecker.checkPermissionForPreflight(
                            context,
                            "android.permission.INTERACT_ACROSS_PROFILES",
                            -1,
                            context.getPackageManager()
                                    .getApplicationInfoAsUser(str, 0, profileParent)
                                    .uid,
                            str)
                    == 0) {
                return PermissionChecker.checkPermissionForPreflight(
                                context,
                                "android.permission.INTERACT_ACROSS_PROFILES",
                                -1,
                                context.getPackageManager()
                                        .getApplicationInfoAsUser(str, 0, workProfile)
                                        .uid,
                                str)
                        == 0;
            }
            return false;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    @Override // com.android.settings.applications.AppInfoBase
    public final AlertDialog createDialog(int i) {
        return null;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1829;
    }

    public final void handleInstallBannerClick() {
        if (this.mInstallAppIntent != null && !this.mInstalledInWork) {
            if (!this.mContext
                    .getPackageManager()
                    .queryIntentActivitiesAsUser(r0, 0, this.mWorkProfile)
                    .isEmpty()) {
                logEvent(168);
                this.mContext.startActivityAsUser(this.mInstallAppIntent, this.mWorkProfile);
                return;
            }
        }
        if (this.mInstallAppIntent != null && !this.mInstalledInPersonal) {
            if (!this.mContext
                    .getPackageManager()
                    .queryIntentActivitiesAsUser(r0, 0, this.mPersonalProfile)
                    .isEmpty()) {
                logEvent(168);
                this.mContext.startActivityAsUser(this.mInstallAppIntent, this.mPersonalProfile);
                return;
            }
        }
        logEvent(169);
    }

    public final boolean isPackageInstalled(int i, String str) {
        try {
            return this.mContext
                            .createContextAsUser(UserHandle.of(i), 0)
                            .getPackageManager()
                            .getPackageInfo(str, 786432)
                    != null;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public final void logEvent(int i) {
        DevicePolicyEventLogger.createEvent(i)
                .setStrings(new String[] {this.mPackageName})
                .setInt(UserHandle.myUserId())
                .setAdmin(
                        RestrictedLockUtils.getProfileOrDeviceOwner(
                                        this.mContext, null, this.mWorkProfile)
                                .component)
                .write();
    }

    @Override // com.android.settings.applications.AppInfoBase,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Context context = getContext();
        this.mContext = context;
        this.mCrossProfileApps =
                (CrossProfileApps) context.getSystemService(CrossProfileApps.class);
        this.mUserManager = (UserManager) this.mContext.getSystemService(UserManager.class);
        this.mPackageManager = this.mContext.getPackageManager();
        UserHandle workProfile = InteractAcrossProfilesSettings.getWorkProfile(this.mUserManager);
        this.mWorkProfile = workProfile;
        if (workProfile == null) {
            SemLog.e("InteractAcrossProfilesDetails", "Work profile is null");
            finish();
            return;
        }
        this.mPersonalProfile = this.mUserManager.getProfileParent(workProfile);
        this.mInstalledInWork =
                isPackageInstalled(this.mWorkProfile.getIdentifier(), this.mPackageName);
        this.mInstalledInPersonal =
                isPackageInstalled(this.mPersonalProfile.getIdentifier(), this.mPackageName);
        this.mAppLabel =
                this.mPackageInfo.applicationInfo.loadLabel(this.mPackageManager).toString();
        Context context2 = this.mContext;
        String str = this.mPackageName;
        this.mInstallAppIntent =
                AppStoreUtil.getAppStoreLink(
                        context2,
                        (String)
                                AppStoreUtil.getInstallerPackageNameAndInstallSourceInfo(
                                                context2, str)
                                        .first,
                        str);
        addPreferencesFromResource(R.xml.sec_interact_across_profiles_permissions_details);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Intent intent;
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        replaceEnterprisePreferenceScreenTitle(
                "Settings.CONNECTED_WORK_AND_PERSONAL_APPS_TITLE",
                R.string.interact_across_profiles_title);
        replaceEnterpriseStringSummary(
                "interact_across_profiles_summary_1",
                "Settings.CONNECTED_APPS_SHARE_PERMISSIONS_AND_DATA",
                R.string.interact_across_profiles_summary_1);
        replaceEnterpriseStringSummary(
                "interact_across_profiles_summary_2",
                "Settings.ONLY_CONNECT_TRUSTED_APPS",
                R.string.interact_across_profiles_summary_2);
        replaceEnterpriseStringSummary(
                "interact_across_profiles_extra_summary",
                "Settings.HOW_TO_DISCONNECT_APPS",
                R.string.interact_across_profiles_summary_3);
        RestrictedSwitchPreference restrictedSwitchPreference =
                (RestrictedSwitchPreference)
                        findPreference("interact_across_profiles_settings_switch");
        this.mSwitchPref = restrictedSwitchPreference;
        restrictedSwitchPreference.setOnPreferenceClickListener(this);
        this.mSwitchPref.setOnPreferenceChangeListener(this);
        this.mSwitchPref.seslSetRoundedBg(15);
        this.mInstallBanner = (SecUnclickablePreference) findPreference("install_app_banner");
        SecButtonPreference secButtonPreference =
                (SecButtonPreference) findPreference("install_app_banner_button");
        this.mInstallBannerButton = secButtonPreference;
        secButtonPreference.setDefaultRoundButtonStyle();
        this.mInstallBannerButton.mTitle =
                getString(R.string.interact_across_profiles_install_button_text);
        this.mInstallBannerButton.mOnClickListener =
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.applications.specialaccess.interactacrossprofiles.InteractAcrossProfilesDetails.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        InteractAcrossProfilesDetails.this.handleInstallBannerClick();
                    }
                };
        Bundle bundleExtra = getIntent().getBundleExtra(":settings:show_fragment_args");
        boolean z = false;
        if (bundleExtra != null && (intent = (Intent) bundleExtra.get("intent")) != null) {
            z = "android.settings.MANAGE_CROSS_PROFILE_ACCESS".equals(intent.getAction());
        }
        this.mIsPageLaunchedByApp = z;
        if (!refreshUi()) {
            setIntentAndFinish(true);
        }
        ActionBar actionBar = getActivity().getActionBar();
        if (actionBar != null) {
            actionBar.setElevation(0.0f);
        }
        if (!this.mCrossProfileApps.canConfigureInteractAcrossProfiles(this.mPackageName)) {
            if (((DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class))
                    .getAllCrossProfilePackages()
                    .contains(this.mPackageName)) {
                if (this.mInstallBanner == null) {
                    logEvent(167);
                }
                if (!this.mInstalledInPersonal) {
                    logEvent(166);
                } else if (!this.mInstalledInWork) {
                    logEvent(165);
                }
            } else {
                logEvent(164);
            }
        }
        if (this.mIsPageLaunchedByApp) {
            logEvent(162);
        } else {
            logEvent(163);
        }
        return onCreateView;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (preference != this.mSwitchPref) {
            return false;
        }
        if (isInteractAcrossProfilesEnabled(this.mContext, this.mPackageName)) {
            logEvent(172);
            this.mCrossProfileApps.setInteractAcrossProfilesAppOp(this.mPackageName, 1);
            refreshUi();
        } else if (this.mDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.interact_across_profiles_consent_dialog_title);
            builder.setMessage(R.string.interact_across_profiles_consent_dialog_summary);
            final int i = 1;
            builder.setPositiveButton(
                    R.string.share,
                    new DialogInterface.OnClickListener(
                            this) { // from class:
                                    // com.android.settings.applications.specialaccess.interactacrossprofiles.InteractAcrossProfilesDetails.3
                        public final /* synthetic */ InteractAcrossProfilesDetails this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i2) {
                            switch (i) {
                                case 0:
                                    this.this$0.logEvent(171);
                                    this.this$0.refreshUi();
                                    break;
                                default:
                                    this.this$0.logEvent(170);
                                    InteractAcrossProfilesDetails interactAcrossProfilesDetails =
                                            this.this$0;
                                    interactAcrossProfilesDetails.mCrossProfileApps
                                            .setInteractAcrossProfilesAppOp(
                                                    interactAcrossProfilesDetails.mPackageName, 0);
                                    this.this$0.refreshUi();
                                    InteractAcrossProfilesDetails interactAcrossProfilesDetails2 =
                                            this.this$0;
                                    if (interactAcrossProfilesDetails2.mIsPageLaunchedByApp) {
                                        interactAcrossProfilesDetails2.setIntentAndFinish(true);
                                        break;
                                    }
                                    break;
                            }
                        }
                    });
            final int i2 = 0;
            builder.setNegativeButton(
                    R.string.cancel,
                    new DialogInterface.OnClickListener(
                            this) { // from class:
                                    // com.android.settings.applications.specialaccess.interactacrossprofiles.InteractAcrossProfilesDetails.3
                        public final /* synthetic */ InteractAcrossProfilesDetails this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i22) {
                            switch (i2) {
                                case 0:
                                    this.this$0.logEvent(171);
                                    this.this$0.refreshUi();
                                    break;
                                default:
                                    this.this$0.logEvent(170);
                                    InteractAcrossProfilesDetails interactAcrossProfilesDetails =
                                            this.this$0;
                                    interactAcrossProfilesDetails.mCrossProfileApps
                                            .setInteractAcrossProfilesAppOp(
                                                    interactAcrossProfilesDetails.mPackageName, 0);
                                    this.this$0.refreshUi();
                                    InteractAcrossProfilesDetails interactAcrossProfilesDetails2 =
                                            this.this$0;
                                    if (interactAcrossProfilesDetails2.mIsPageLaunchedByApp) {
                                        interactAcrossProfilesDetails2.setIntentAndFinish(true);
                                        break;
                                    }
                                    break;
                            }
                        }
                    });
            builder.P.mOnDismissListener =
                    new DialogInterface
                            .OnDismissListener() { // from class:
                                                   // com.android.settings.applications.specialaccess.interactacrossprofiles.InteractAcrossProfilesDetails.2
                        @Override // android.content.DialogInterface.OnDismissListener
                        public final void onDismiss(DialogInterface dialogInterface) {
                            InteractAcrossProfilesDetails interactAcrossProfilesDetails =
                                    InteractAcrossProfilesDetails.this;
                            if (interactAcrossProfilesDetails.mDialog == dialogInterface) {
                                interactAcrossProfilesDetails.mDialog = null;
                            }
                        }
                    };
            this.mDialog = builder.show();
        }
        return true;
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        if (!refreshUi()) {
            setIntentAndFinish(true);
        }
        if (preference != this.mInstallBanner) {
            return false;
        }
        handleInstallBannerClick();
        return true;
    }

    @Override // com.android.settings.applications.AppInfoBase
    public final boolean refreshUi() {
        PackageInfo packageInfo = this.mPackageInfo;
        if (packageInfo == null || packageInfo.applicationInfo == null) {
            return false;
        }
        if (!this.mCrossProfileApps.canUserAttemptToConfigureInteractAcrossProfiles(
                this.mPackageName)) {
            this.mSwitchPref.setEnabled(false);
            return false;
        }
        if (this.mCrossProfileApps.canConfigureInteractAcrossProfiles(this.mPackageName)) {
            this.mInstallBanner.setVisible(false);
            this.mInstallBannerButton.setVisible(false);
            this.mSwitchPref.setEnabled(true);
            if (isInteractAcrossProfilesEnabled(this.mContext, this.mPackageName)) {
                this.mSwitchPref.setChecked(true);
                this.mSwitchPref.setTitle(R.string.interact_across_profiles_switch_enabled);
            } else {
                this.mSwitchPref.setChecked(false);
                this.mSwitchPref.setTitle(R.string.interact_across_profiles_switch_disabled);
            }
            return true;
        }
        this.mSwitchPref.setChecked(false);
        this.mSwitchPref.setTitle(R.string.interact_across_profiles_switch_disabled);
        if (((DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class))
                .getAllCrossProfilePackages()
                .contains(this.mPackageName)) {
            this.mSwitchPref.setEnabled(false);
            boolean z = this.mInstalledInPersonal;
            if (!z && !this.mInstalledInWork) {
                return false;
            }
            if (!z) {
                final int i = 0;
                this.mInstallBanner.setTitle(
                        this.mDpm
                                .getResources()
                                .getString(
                                        "Settings.INSTALL_IN_PERSONAL_PROFILE_TO_CONNECT_PROMPT",
                                        new Supplier(
                                                this) { // from class:
                                                        // com.android.settings.applications.specialaccess.interactacrossprofiles.InteractAcrossProfilesDetails$$ExternalSyntheticLambda0
                                            public final /* synthetic */
                                            InteractAcrossProfilesDetails f$0;

                                            {
                                                this.f$0 = this;
                                            }

                                            @Override // java.util.function.Supplier
                                            public final Object get() {
                                                int i2 = i;
                                                InteractAcrossProfilesDetails
                                                        interactAcrossProfilesDetails = this.f$0;
                                                switch (i2) {
                                                    case 0:
                                                        return interactAcrossProfilesDetails
                                                                .getString(
                                                                        R.string
                                                                                .interact_across_profiles_install_personal_app_title,
                                                                        interactAcrossProfilesDetails
                                                                                .mAppLabel);
                                                    default:
                                                        return interactAcrossProfilesDetails
                                                                .getString(
                                                                        R.string
                                                                                .interact_across_profiles_install_work_app_title,
                                                                        interactAcrossProfilesDetails
                                                                                .mAppLabel);
                                                }
                                            }
                                        },
                                        this.mAppLabel));
                this.mInstallBannerButton.setVisible(true);
                this.mInstallBanner.setVisible(true);
            } else {
                if (this.mInstalledInWork) {
                    return false;
                }
                final int i2 = 1;
                this.mInstallBanner.setTitle(
                        this.mDpm
                                .getResources()
                                .getString(
                                        "Settings.INSTALL_IN_WORK_PROFILE_TO_CONNECT_PROMPT",
                                        new Supplier(
                                                this) { // from class:
                                                        // com.android.settings.applications.specialaccess.interactacrossprofiles.InteractAcrossProfilesDetails$$ExternalSyntheticLambda0
                                            public final /* synthetic */
                                            InteractAcrossProfilesDetails f$0;

                                            {
                                                this.f$0 = this;
                                            }

                                            @Override // java.util.function.Supplier
                                            public final Object get() {
                                                int i22 = i2;
                                                InteractAcrossProfilesDetails
                                                        interactAcrossProfilesDetails = this.f$0;
                                                switch (i22) {
                                                    case 0:
                                                        return interactAcrossProfilesDetails
                                                                .getString(
                                                                        R.string
                                                                                .interact_across_profiles_install_personal_app_title,
                                                                        interactAcrossProfilesDetails
                                                                                .mAppLabel);
                                                    default:
                                                        return interactAcrossProfilesDetails
                                                                .getString(
                                                                        R.string
                                                                                .interact_across_profiles_install_work_app_title,
                                                                        interactAcrossProfilesDetails
                                                                                .mAppLabel);
                                                }
                                            }
                                        },
                                        this.mAppLabel));
                this.mInstallBannerButton.setVisible(true);
                this.mInstallBanner.setVisible(true);
            }
        } else {
            this.mInstallBanner.setVisible(false);
            this.mInstallBannerButton.setVisible(false);
            this.mSwitchPref.setDisabledByAdmin(
                    RestrictedLockUtils.getProfileOrDeviceOwner(
                            this.mContext, null, this.mWorkProfile));
        }
        return true;
    }
}
