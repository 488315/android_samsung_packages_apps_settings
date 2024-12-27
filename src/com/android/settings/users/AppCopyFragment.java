package com.android.settings.users;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settingslib.users.AppCopyHelper;
import com.android.settingslib.widget.AppSwitchPreference;

import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AppCopyFragment extends SettingsPreferenceFragment {
    public static final /* synthetic */ int $r8$clinit = 0;
    public PreferenceScreen mAppList;
    public boolean mAppListChanged;
    public AsyncTask mAppLoadingTask;
    public AppCopyHelper mHelper;
    public final AnonymousClass1 mPackageObserver;
    public UserHandle mUser;
    public final AnonymousClass1 mUserBackgrounding;
    public UserManager mUserManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.users.AppCopyFragment$3, reason: invalid class name */
    public final class AnonymousClass3 extends AsyncTask {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ AppCopyFragment this$0;

        public /* synthetic */ AnonymousClass3(AppCopyFragment appCopyFragment, int i) {
            this.$r8$classId = i;
            this.this$0 = appCopyFragment;
        }

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            switch (this.$r8$classId) {
                case 0:
                    this.this$0.mHelper.installSelectedApps();
                    break;
                default:
                    AppCopyHelper appCopyHelper = this.this$0.mHelper;
                    appCopyHelper.getClass();
                    appCopyHelper.mVisibleApps = new ArrayList();
                    appCopyHelper.addSystemApps(
                            new Intent("android.intent.action.MAIN")
                                    .addCategory("android.intent.category.LAUNCHER"),
                            appCopyHelper.mVisibleApps);
                    appCopyHelper.addSystemApps(
                            new Intent("android.appwidget.action.APPWIDGET_UPDATE"),
                            appCopyHelper.mVisibleApps);
                    for (ApplicationInfo applicationInfo :
                            appCopyHelper.mPackageManager.getInstalledApplications(0)) {
                        int i = applicationInfo.flags;
                        if ((8388608 & i) != 0 && (i & 1) == 0 && (i & 128) == 0) {
                            AppCopyHelper.SelectableAppInfo selectableAppInfo =
                                    new AppCopyHelper.SelectableAppInfo();
                            selectableAppInfo.packageName = applicationInfo.packageName;
                            selectableAppInfo.appName =
                                    applicationInfo.loadLabel(appCopyHelper.mPackageManager);
                            selectableAppInfo.icon =
                                    applicationInfo.loadIcon(appCopyHelper.mPackageManager);
                            ((ArrayList) appCopyHelper.mVisibleApps).add(selectableAppInfo);
                        }
                    }
                    HashSet hashSet = new HashSet();
                    for (int size = ((ArrayList) appCopyHelper.mVisibleApps).size() - 1;
                            size >= 0;
                            size--) {
                        AppCopyHelper.SelectableAppInfo selectableAppInfo2 =
                                (AppCopyHelper.SelectableAppInfo)
                                        ((ArrayList) appCopyHelper.mVisibleApps).get(size);
                        if (TextUtils.isEmpty(selectableAppInfo2.packageName)
                                || !hashSet.contains(selectableAppInfo2.packageName)) {
                            hashSet.add(selectableAppInfo2.packageName);
                        } else {
                            ((ArrayList) appCopyHelper.mVisibleApps).remove(size);
                        }
                    }
                    ((ArrayList) appCopyHelper.mVisibleApps)
                            .sort(new AppCopyHelper.AppLabelComparator());
                    HashSet hashSet2 = new HashSet();
                    List installedApplicationsAsUser =
                            appCopyHelper.mPackageManager.getInstalledApplicationsAsUser(
                                    8192, appCopyHelper.mUser.getIdentifier());
                    for (int size2 = installedApplicationsAsUser.size() - 1; size2 >= 0; size2--) {
                        ApplicationInfo applicationInfo2 =
                                (ApplicationInfo) installedApplicationsAsUser.get(size2);
                        if ((applicationInfo2.flags & 8388608) != 0) {
                            hashSet2.add(applicationInfo2.packageName);
                        }
                    }
                    for (int size3 = ((ArrayList) appCopyHelper.mVisibleApps).size() - 1;
                            size3 >= 0;
                            size3--) {
                        AppCopyHelper.SelectableAppInfo selectableAppInfo3 =
                                (AppCopyHelper.SelectableAppInfo)
                                        ((ArrayList) appCopyHelper.mVisibleApps).get(size3);
                        if (!TextUtils.isEmpty(selectableAppInfo3.packageName)
                                && hashSet2.contains(selectableAppInfo3.packageName)) {
                            ((ArrayList) appCopyHelper.mVisibleApps).remove(size3);
                        }
                    }
                    break;
            }
            return null;
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(Object obj) {
            switch (this.$r8$classId) {
                case 1:
                    final AppCopyFragment appCopyFragment = this.this$0;
                    if (Utils.getExistingUser(appCopyFragment.mUser, appCopyFragment.mUserManager)
                            != null) {
                        appCopyFragment.mHelper.mSelectedPackages.clear();
                        appCopyFragment.mAppList.removeAll();
                        Iterator it = ((ArrayList) appCopyFragment.mHelper.mVisibleApps).iterator();
                        while (it.hasNext()) {
                            AppCopyHelper.SelectableAppInfo selectableAppInfo =
                                    (AppCopyHelper.SelectableAppInfo) it.next();
                            if (selectableAppInfo.packageName != null) {
                                AppSwitchPreference appSwitchPreference =
                                        new AppSwitchPreference(appCopyFragment.getPrefContext());
                                Drawable drawable = selectableAppInfo.icon;
                                appSwitchPreference.setIcon(
                                        drawable != null ? drawable.mutate() : null);
                                appSwitchPreference.setChecked(false);
                                appSwitchPreference.setTitle(selectableAppInfo.appName);
                                appSwitchPreference.setKey("pkg_" + selectableAppInfo.packageName);
                                appSwitchPreference.setPersistent();
                                appSwitchPreference.setOnPreferenceChangeListener(
                                        new Preference
                                                .OnPreferenceChangeListener() { // from class:
                                                                                // com.android.settings.users.AppCopyFragment$$ExternalSyntheticLambda0
                                            @Override // androidx.preference.Preference.OnPreferenceChangeListener
                                            public final boolean onPreferenceChange(
                                                    Preference preference, Object obj2) {
                                                int i = AppCopyFragment.$r8$clinit;
                                                AppCopyFragment appCopyFragment2 =
                                                        AppCopyFragment.this;
                                                appCopyFragment2.getClass();
                                                if (!preference.isEnabled()) {
                                                    return false;
                                                }
                                                boolean booleanValue =
                                                        ((Boolean) obj2).booleanValue();
                                                String substring = preference.getKey().substring(4);
                                                AppCopyHelper appCopyHelper =
                                                        appCopyFragment2.mHelper;
                                                if (booleanValue) {
                                                    appCopyHelper.mSelectedPackages.add(substring);
                                                } else {
                                                    appCopyHelper.mSelectedPackages.remove(
                                                            substring);
                                                }
                                                appCopyFragment2.mAppListChanged = true;
                                                return true;
                                            }
                                        });
                                appCopyFragment.mAppList.addPreference(appSwitchPreference);
                            }
                        }
                        appCopyFragment.mAppListChanged = true;
                        break;
                    }
                    break;
                default:
                    super.onPostExecute(obj);
                    break;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settings.users.AppCopyFragment$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settings.users.AppCopyFragment$1] */
    public AppCopyFragment() {
        final int i = 0;
        this.mUserBackgrounding =
                new BroadcastReceiver(
                        this) { // from class: com.android.settings.users.AppCopyFragment.1
                    public final /* synthetic */ AppCopyFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        switch (i) {
                            case 0:
                                AppCopyFragment appCopyFragment = this.this$0;
                                if (appCopyFragment.mAppListChanged) {
                                    appCopyFragment.mHelper.installSelectedApps();
                                    break;
                                }
                                break;
                            default:
                                AppCopyFragment appCopyFragment2 = this.this$0;
                                int i2 = AppCopyFragment.$r8$clinit;
                                appCopyFragment2.getClass();
                                String action = intent.getAction();
                                String schemeSpecificPart =
                                        intent.getData().getSchemeSpecificPart();
                                AppSwitchPreference appSwitchPreference =
                                        (AppSwitchPreference)
                                                appCopyFragment2.findPreference(
                                                        "pkg_" + schemeSpecificPart);
                                if (appSwitchPreference != null) {
                                    if (!"android.intent.action.PACKAGE_REMOVED".equals(action)) {
                                        if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
                                            appSwitchPreference.setEnabled(true);
                                            break;
                                        }
                                    } else {
                                        appSwitchPreference.setEnabled(false);
                                        appSwitchPreference.setChecked(false);
                                        appCopyFragment2.mHelper.mSelectedPackages.remove(
                                                schemeSpecificPart);
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mPackageObserver =
                new BroadcastReceiver(
                        this) { // from class: com.android.settings.users.AppCopyFragment.1
                    public final /* synthetic */ AppCopyFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        switch (i2) {
                            case 0:
                                AppCopyFragment appCopyFragment = this.this$0;
                                if (appCopyFragment.mAppListChanged) {
                                    appCopyFragment.mHelper.installSelectedApps();
                                    break;
                                }
                                break;
                            default:
                                AppCopyFragment appCopyFragment2 = this.this$0;
                                int i22 = AppCopyFragment.$r8$clinit;
                                appCopyFragment2.getClass();
                                String action = intent.getAction();
                                String schemeSpecificPart =
                                        intent.getData().getSchemeSpecificPart();
                                AppSwitchPreference appSwitchPreference =
                                        (AppSwitchPreference)
                                                appCopyFragment2.findPreference(
                                                        "pkg_" + schemeSpecificPart);
                                if (appSwitchPreference != null) {
                                    if (!"android.intent.action.PACKAGE_REMOVED".equals(action)) {
                                        if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
                                            appSwitchPreference.setEnabled(true);
                                            break;
                                        }
                                    } else {
                                        appSwitchPreference.setEnabled(false);
                                        appSwitchPreference.setChecked(false);
                                        appCopyFragment2.mHelper.mSelectedPackages.remove(
                                                schemeSpecificPart);
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1897;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.mUser =
                    new UserHandle(
                            bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID));
        } else {
            Bundle arguments = getArguments();
            if (arguments != null
                    && arguments.containsKey(
                            UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID)) {
                this.mUser =
                        new UserHandle(
                                arguments.getInt(
                                        UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID));
            }
        }
        if (this.mUser == null) {
            throw new IllegalStateException("No user specified.");
        }
        this.mHelper = new AppCopyHelper(getContext(), this.mUser);
        this.mUserManager = (UserManager) getActivity().getSystemService("user");
        addPreferencesFromResource(R.xml.app_copier);
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        this.mAppList = preferenceScreen;
        preferenceScreen.mOrderingAsAdded = false;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(this.mUserBackgrounding);
        getActivity().unregisterReceiver(this.mPackageObserver);
        if (this.mAppListChanged) {
            new AnonymousClass3(this, 0).execute(new Void[0]);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        getActivity()
                .registerReceiver(
                        this.mUserBackgrounding,
                        new IntentFilter("android.intent.action.USER_BACKGROUND"));
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme("package");
        getActivity().registerReceiver(this.mPackageObserver, intentFilter);
        this.mAppListChanged = false;
        AsyncTask asyncTask = this.mAppLoadingTask;
        if (asyncTask == null || asyncTask.getStatus() == AsyncTask.Status.FINISHED) {
            this.mAppLoadingTask = new AnonymousClass3(this, 1).execute(new Void[0]);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(
                UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID, this.mUser.getIdentifier());
    }
}
