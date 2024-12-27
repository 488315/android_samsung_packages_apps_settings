package com.android.settings.applications.specialaccess.interactacrossprofiles;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.CrossProfileApps;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.IconDrawableFactory;
import android.util.Pair;
import android.view.View;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.applications.AppInfoBase;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.EmptyTextSettings;
import com.android.settingslib.widget.AppPreference;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class InteractAcrossProfilesSettings extends EmptyTextSettings {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.interact_across_profiles);
    public Context mContext;
    public CrossProfileApps mCrossProfileApps;
    public IconDrawableFactory mIconDrawableFactory;
    public PackageManager mPackageManager;
    public UserManager mUserManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.applications.specialaccess.interactacrossprofiles.InteractAcrossProfilesSettings$1, reason: invalid class name */
    public final class AnonymousClass1 implements Preference.OnPreferenceClickListener {
        public final /* synthetic */ ApplicationInfo val$appInfo;
        public final /* synthetic */ String val$packageName;

        public AnonymousClass1(String str, ApplicationInfo applicationInfo) {
            this.val$packageName = str;
            this.val$appInfo = applicationInfo;
        }

        @Override // androidx.preference.Preference.OnPreferenceClickListener
        public final boolean onPreferenceClick(Preference preference) {
            String string =
                    ((SettingsPreferenceFragment) InteractAcrossProfilesSettings.this)
                            .mDevicePolicyManager
                            .getResources()
                            .getString(
                                    "Settings.CONNECTED_WORK_AND_PERSONAL_APPS_TITLE",
                                    new Supplier() { // from class:
                                                     // com.android.settings.applications.specialaccess.interactacrossprofiles.InteractAcrossProfilesSettings$1$$ExternalSyntheticLambda0
                                        @Override // java.util.function.Supplier
                                        public final Object get() {
                                            return InteractAcrossProfilesSettings.this.getString(
                                                    R.string.interact_across_profiles_title);
                                        }
                                    });
            int i = this.val$appInfo.uid;
            InteractAcrossProfilesSettings interactAcrossProfilesSettings =
                    InteractAcrossProfilesSettings.this;
            interactAcrossProfilesSettings.getClass();
            AppInfoBase.startAppInfoFragment(
                    InteractAcrossProfilesDetails.class,
                    string,
                    this.val$packageName,
                    i,
                    interactAcrossProfilesSettings,
                    -1,
                    1829);
            return true;
        }
    }

    public static UserHandle getWorkProfile(UserManager userManager) {
        for (UserInfo userInfo : userManager.getProfiles(UserHandle.myUserId())) {
            if (userManager.isManagedProfile(userInfo.id)) {
                return userInfo.getUserHandle();
            }
        }
        return null;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1829;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.interact_across_profiles;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Context context = getContext();
        this.mContext = context;
        this.mPackageManager = context.getPackageManager();
        this.mUserManager = (UserManager) this.mContext.getSystemService(UserManager.class);
        this.mIconDrawableFactory = IconDrawableFactory.newInstance(this.mContext);
        this.mCrossProfileApps =
                (CrossProfileApps) this.mContext.getSystemService(CrossProfileApps.class);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        ArrayList arrayList;
        super.onResume();
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        preferenceScreen.removeAll();
        replaceEnterprisePreferenceScreenTitle(
                "Settings.CONNECTED_WORK_AND_PERSONAL_APPS_TITLE",
                R.string.interact_across_profiles_title);
        PackageManager packageManager = this.mPackageManager;
        UserManager userManager = this.mUserManager;
        CrossProfileApps crossProfileApps = this.mCrossProfileApps;
        UserHandle workProfile = getWorkProfile(userManager);
        if (workProfile == null) {
            arrayList = new ArrayList();
        } else {
            UserHandle profileParent = userManager.getProfileParent(workProfile);
            if (profileParent == null) {
                arrayList = new ArrayList();
            } else {
                ArrayList arrayList2 = new ArrayList();
                List installedPackagesAsUser =
                        packageManager.getInstalledPackagesAsUser(0, profileParent.getIdentifier());
                List<PackageInfo> installedPackagesAsUser2 =
                        packageManager.getInstalledPackagesAsUser(0, workProfile.getIdentifier());
                ArrayList arrayList3 = new ArrayList(installedPackagesAsUser);
                for (final PackageInfo packageInfo : installedPackagesAsUser2) {
                    if (arrayList3.stream()
                            .noneMatch(
                                    new Predicate() { // from class:
                                                      // com.android.settings.applications.specialaccess.interactacrossprofiles.InteractAcrossProfilesSettings$$ExternalSyntheticLambda0
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            PackageInfo packageInfo2 = packageInfo;
                                            BaseSearchIndexProvider baseSearchIndexProvider =
                                                    InteractAcrossProfilesSettings
                                                            .SEARCH_INDEX_DATA_PROVIDER;
                                            return packageInfo2.packageName.equals(
                                                    ((PackageInfo) obj).packageName);
                                        }
                                    })) {
                        arrayList3.add(packageInfo);
                    }
                }
                Iterator it = arrayList3.iterator();
                while (it.hasNext()) {
                    PackageInfo packageInfo2 = (PackageInfo) it.next();
                    if (crossProfileApps.canUserAttemptToConfigureInteractAcrossProfiles(
                            packageInfo2.packageName)) {
                        arrayList2.add(new Pair(packageInfo2.applicationInfo, profileParent));
                    }
                }
                arrayList = arrayList2;
            }
        }
        Context prefContext = getPrefContext();
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            Pair pair = (Pair) it2.next();
            ApplicationInfo applicationInfo = (ApplicationInfo) pair.first;
            UserHandle userHandle = (UserHandle) pair.second;
            String str = applicationInfo.packageName;
            CharSequence loadLabel = applicationInfo.loadLabel(this.mPackageManager);
            AppPreference appPreference = new AppPreference(prefContext);
            appPreference.setIcon(
                    this.mIconDrawableFactory.getBadgedIcon(
                            applicationInfo, userHandle.getIdentifier()));
            appPreference.setTitle(this.mPackageManager.getUserBadgedLabel(loadLabel, userHandle));
            appPreference.setSummary(
                    prefContext.getString(
                            InteractAcrossProfilesDetails.isInteractAcrossProfilesEnabled(
                                            prefContext, str)
                                    ? R.string.interact_across_profiles_summary_allowed
                                    : R.string.interact_across_profiles_summary_not_allowed));
            appPreference.setOnPreferenceClickListener(new AnonymousClass1(str, applicationInfo));
            preferenceScreen.addPreference(appPreference);
        }
    }

    @Override // com.android.settings.widget.EmptyTextSettings,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        setEmptyText(R.string.no_applications);
    }
}
