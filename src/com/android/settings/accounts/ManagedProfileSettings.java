package com.android.settings.accounts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.SearchIndexableResource;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ManagedProfileSettings extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass1();
    public ManagedProfileBroadcastReceiver mManagedProfileBroadcastReceiver;
    public UserHandle mManagedUser;
    public UserManager mUserManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.accounts.ManagedProfileSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.xmlResId = R.xml.managed_profile_settings;
            arrayList.add(searchIndexableResource);
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
            UserHandle managedProfile = Utils.getManagedProfile(userManager);
            if (managedProfile != null) {
                try {
                    UserInfo userInfo = userManager.getUserInfo(managedProfile.getIdentifier());
                    if (userInfo.isUserTypeAppSeparation()) {
                        return false;
                    }
                    return !userInfo.isSecureFolder();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return managedProfile != null;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ManagedProfileBroadcastReceiver extends BroadcastReceiver {
        public ManagedProfileBroadcastReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }
            String action = intent.getAction();
            Log.v("ManagedProfileSettings", "Received broadcast: " + action);
            if ("android.intent.action.MANAGED_PROFILE_REMOVED".equals(action)) {
                if (intent.getIntExtra("android.intent.extra.user_handle", -10000)
                        == ManagedProfileSettings.this.mManagedUser.getIdentifier()) {
                    ManagedProfileSettings.this.getActivity().finish();
                }
            } else {
                Log.w(
                        "ManagedProfileSettings",
                        "Cannot handle received broadcast: " + intent.getAction());
            }
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "ManagedProfileSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 401;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.managed_profile_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        UserHandle managedProfile;
        super.onAttach(context);
        this.mUserManager = (UserManager) getSystemService("user");
        Bundle arguments = getArguments();
        if (arguments == null
                || (managedProfile =
                                (UserHandle) arguments.getParcelable("android.intent.extra.USER"))
                        == null
                || !this.mUserManager.isManagedProfile(managedProfile.getIdentifier())) {
            managedProfile = Utils.getManagedProfile(this.mUserManager);
        }
        this.mManagedUser = managedProfile;
        if (managedProfile == null) {
            getActivity().finish();
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ManagedProfileBroadcastReceiver managedProfileBroadcastReceiver =
                new ManagedProfileBroadcastReceiver();
        this.mManagedProfileBroadcastReceiver = managedProfileBroadcastReceiver;
        FragmentActivity activity = getActivity();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.MANAGED_PROFILE_REMOVED");
        activity.registerReceiver(managedProfileBroadcastReceiver, intentFilter);
        replaceEnterprisePreferenceScreenTitle(
                "Settings.MANAGED_PROFILE_SETTINGS_TITLE", R.string.managed_profile_settings_title);
        replaceEnterpriseStringTitle(
                "work_mode", "Settings.WORK_PROFILE_SETTING", R.string.work_mode_label);
        replaceEnterpriseStringTitle(
                "contacts_search",
                "Settings.WORK_PROFILE_CONTACT_SEARCH_TITLE",
                R.string.managed_profile_contact_search_title);
        replaceEnterpriseStringSummary(
                "contacts_search",
                "Settings.WORK_PROFILE_CONTACT_SEARCH_SUMMARY",
                R.string.managed_profile_contact_search_summary);
        replaceEnterpriseStringTitle(
                "cross_profile_calendar",
                "Settings.CROSS_PROFILE_CALENDAR_TITLE",
                R.string.cross_profile_calendar_title);
        replaceEnterpriseStringSummary(
                "cross_profile_calendar",
                "Settings.CROSS_PROFILE_CALENDAR_SUMMARY",
                R.string.cross_profile_calendar_summary);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        ManagedProfileBroadcastReceiver managedProfileBroadcastReceiver =
                this.mManagedProfileBroadcastReceiver;
        if (managedProfileBroadcastReceiver != null) {
            FragmentActivity activity = getActivity();
            managedProfileBroadcastReceiver.getClass();
            activity.unregisterReceiver(managedProfileBroadcastReceiver);
        }
    }
}
