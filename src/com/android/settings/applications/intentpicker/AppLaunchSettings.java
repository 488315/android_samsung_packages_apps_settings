package com.android.settings.applications.intentpicker;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.verify.domain.DomainVerificationManager;
import android.content.pm.verify.domain.DomainVerificationUserState;
import android.icu.text.MessageFormat;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;
import com.android.settings.applications.AppInfoBase;
import com.android.settings.applications.ClearDefaultsPreference;
import com.android.settings.applications.appinfo.AppInfoDashboardFragment;
import com.android.settings.applications.manageapplications.ManageApplications;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.widget.EntityHeaderController;
import com.android.settingslib.applications.AppUtils;
import com.android.settingslib.applications.ApplicationsState;

import com.samsung.android.settings.applications.AppDomainsFragment;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecUnclickablePreference;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AppLaunchSettings extends AppInfoBase
        implements Preference.OnPreferenceChangeListener {
    public boolean mActivityCreated;
    public ClearDefaultsPreference mClearDefaultsPreference;
    Context mContext;
    DomainVerificationManager mDomainVerificationManager;
    public String mFromFragment;
    public SecSwitchPreference mMainSwitchPreference;
    public Preference mOpenSupportedLinkPreference;

    @Override // com.android.settings.applications.AppInfoBase
    public final AlertDialog createDialog(int i) {
        return null;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getFragmentTitleResId(Context context) {
        return R.string.sec_default_app_title;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return this.mFromFragment != null
                ? ManageApplications.class.getName()
                : AppInfoDashboardFragment.class.getName();
    }

    public final int getLinksNumber(int i) {
        List linksList;
        DomainVerificationManager domainVerificationManager = this.mDomainVerificationManager;
        if (domainVerificationManager == null
                || (linksList =
                                IntentPickerUtils.getLinksList(
                                        domainVerificationManager, this.mPackageName, i))
                        == null) {
            return 0;
        }
        return linksList.size();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 17;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_apps";
    }

    public String getVerifiedLinksTitle(int i) {
        MessageFormat messageFormat =
                new MessageFormat(
                        getResources().getString(R.string.app_launch_verified_links_title),
                        Locale.getDefault());
        HashMap hashMap = new HashMap();
        hashMap.put("count", Integer.valueOf(i));
        return messageFormat.format(hashMap);
    }

    public final void initOtherDefaultsSection() {
        boolean z;
        PreferenceCategory preferenceCategory =
                (PreferenceCategory) findPreference("app_launch_other_defaults");
        boolean hasBindAppWidgetPermission =
                AppWidgetManager.getInstance(this.mContext)
                        .hasBindAppWidgetPermission(this.mAppEntry.info.packageName);
        boolean z2 = true;
        if (!AppUtils.hasPreferredActivities(this.mPm, this.mPackageName)) {
            if (!TextUtils.equals(
                            this.mPackageName,
                            this.mContext
                                    .getPackageManager()
                                    .getDefaultBrowserPackageNameAsUser(UserHandle.myUserId()))
                    && !AppUtils.hasUsbDefaults(this.mUsbManager, this.mPackageName)) {
                z = false;
                IntentPickerUtils.logd(
                        "isClearDefaultsEnabled hasBindAppWidgetPermission : "
                                + hasBindAppWidgetPermission);
                IntentPickerUtils.logd("isClearDefaultsEnabled isAutoLaunchEnabled : " + z);
                if (!z && !hasBindAppWidgetPermission) {
                    z2 = false;
                }
                preferenceCategory.setVisible(z2);
                this.mClearDefaultsPreference =
                        (ClearDefaultsPreference) findPreference("app_launch_clear_defaults");
            }
        }
        z = true;
        IntentPickerUtils.logd(
                "isClearDefaultsEnabled hasBindAppWidgetPermission : "
                        + hasBindAppWidgetPermission);
        IntentPickerUtils.logd("isClearDefaultsEnabled isAutoLaunchEnabled : " + z);
        if (!z) {
            z2 = false;
        }
        preferenceCategory.setVisible(z2);
        this.mClearDefaultsPreference =
                (ClearDefaultsPreference) findPreference("app_launch_clear_defaults");
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        this.mActivityCreated = false;
    }

    @Override // com.android.settings.applications.AppInfoBase,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ApplicationsState.AppEntry appEntry = this.mAppEntry;
        if (appEntry == null || appEntry.info == null) {
            Log.w("AppLaunchSettings", "onCreate: mAppEntry is null, please check the reason!!!");
            getActivity().finish();
            return;
        }
        addPreferencesFromResource(R.xml.sec_installed_app_launch_settings);
        Bundle arguments = getArguments();
        this.mFromFragment = arguments != null ? arguments.getString("fromFragment") : null;
        this.mDomainVerificationManager =
                (DomainVerificationManager)
                        this.mContext.getSystemService(DomainVerificationManager.class);
        this.mMainSwitchPreference =
                (SecSwitchPreference) findPreference("open_by_default_supported_links");
        initOtherDefaultsSection();
        initOtherDefaultsSection();
        DomainVerificationUserState domainVerificationUserState =
                IntentPickerUtils.getDomainVerificationUserState(
                        this.mDomainVerificationManager, this.mPackageName);
        boolean z = false;
        if (domainVerificationUserState == null) {
            this.mMainSwitchPreference.setChecked(false);
            this.mMainSwitchPreference.setSelectable(false);
            this.mMainSwitchPreference.setEnabled(false);
        } else {
            IntentPickerUtils.logd(
                    "isLinkHandlingAllowed() : "
                            + domainVerificationUserState.isLinkHandlingAllowed());
            this.mMainSwitchPreference.setChecked(
                    domainVerificationUserState.isLinkHandlingAllowed());
            this.mMainSwitchPreference.setOnPreferenceChangeListener(this);
        }
        ((SecUnclickablePreference) findPreference("open_by_default_top_intro"))
                .seslSetSubheaderRoundedBackground(0);
        this.mOpenSupportedLinkPreference =
                findPreference("open_by_default_supported_links_setting");
        int linksNumber = getLinksNumber(2);
        int linksNumber2 = getLinksNumber(0) + getLinksNumber(1);
        Preference preference = this.mOpenSupportedLinkPreference;
        if (linksNumber + linksNumber2 > 0 && this.mMainSwitchPreference.isChecked()) {
            z = true;
        }
        preference.setEnabled(z);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        IntentPickerUtils.logd("onSwitchChanged: isChecked=" + booleanValue);
        SecSwitchPreference secSwitchPreference = this.mMainSwitchPreference;
        if (secSwitchPreference != null) {
            secSwitchPreference.setChecked(booleanValue);
        }
        int linksNumber = getLinksNumber(2);
        int linksNumber2 = getLinksNumber(0) + getLinksNumber(1);
        Preference preference2 = this.mOpenSupportedLinkPreference;
        if (preference2 != null && linksNumber + linksNumber2 > 0) {
            preference2.setEnabled(booleanValue);
        }
        DomainVerificationManager domainVerificationManager = this.mDomainVerificationManager;
        if (domainVerificationManager != null) {
            try {
                domainVerificationManager.setDomainVerificationLinkHandlingAllowed(
                        this.mPackageName, booleanValue);
            } catch (PackageManager.NameNotFoundException e) {
                Log.w("AppLaunchSettings", "onSwitchChanged: " + e.getMessage());
            }
        }
        return true;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        if (this.mOpenSupportedLinkPreference != preference) {
            return super.onPreferenceTreeClick(preference);
        }
        LoggingHelper.insertEventLogging(17, 3873);
        Bundle bundle = new Bundle();
        bundle.putString("packageName", this.mPackageName);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(getContext());
        String name = AppDomainsFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        subSettingLauncher.setTitleRes(R.string.app_launch_supported_domain_urls_title, null);
        launchRequest.mArguments = bundle;
        launchRequest.mSourceMetricsCategory = 17;
        subSettingLauncher.launch();
        return true;
    }

    @Override // com.android.settings.applications.AppInfoBase,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        PreferenceViewHolder preferenceViewHolder;
        super.onResume();
        ClearDefaultsPreference clearDefaultsPreference = this.mClearDefaultsPreference;
        if (clearDefaultsPreference == null
                || (preferenceViewHolder = clearDefaultsPreference.mRootView) == null) {
            return;
        }
        clearDefaultsPreference.updateUI(preferenceViewHolder);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (this.mActivityCreated) {
            Log.w("AppLaunchSettings", "onParentActivityCreated: ignoring duplicate call.");
            return;
        }
        this.mActivityCreated = true;
        if (this.mPackageInfo == null) {
            Log.w("AppLaunchSettings", "onParentActivityCreated: PakcageInfo is null.");
            return;
        }
        FragmentActivity activity = getActivity();
        activity.getString(R.string.app_launch_top_intro_message);
        EntityHeaderController entityHeaderController =
                new EntityHeaderController(activity, this, null);
        entityHeaderController.setIcon(AppUtils.getIconWithoutCache(this.mContext, this.mAppEntry));
        entityHeaderController.mLabel = this.mPackageInfo.applicationInfo.loadLabel(this.mPm);
        entityHeaderController.mIsInstantApp =
                AppUtils.isInstant(this.mPackageInfo.applicationInfo);
        entityHeaderController.mPackageName = this.mPackageName;
        entityHeaderController.mUid = this.mPackageInfo.applicationInfo.uid;
        entityHeaderController.mHasAppInfoLink = true;
        entityHeaderController.mAction1 = 0;
        entityHeaderController.mAction2 = 0;
        getPreferenceScreen().addPreference(entityHeaderController.done(getPrefContext()));
    }

    @Override // com.android.settings.applications.AppInfoBase
    public final boolean refreshUi() {
        ClearDefaultsPreference clearDefaultsPreference = this.mClearDefaultsPreference;
        clearDefaultsPreference.mPackageName = this.mPackageName;
        clearDefaultsPreference.mAppEntry = this.mAppEntry;
        return true;
    }
}
