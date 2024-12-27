package com.android.settings.notification.modes;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.ParceledListSlice;
import android.graphics.drawable.Drawable;
import android.multiuser.Flags;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;

import androidx.core.text.BidiFormatter;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.notification.NotificationBackend;
import com.android.settings.notification.app.AppChannelsBypassingDndSettings;
import com.android.settingslib.applications.AppUtils;
import com.android.settingslib.applications.ApplicationsState;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.utils.ThreadUtils;
import com.android.settingslib.widget.AppPreference;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModeAddBypassingAppsPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin {
    public Preference mAddPreference;
    public ApplicationsState.Session mAppSession;
    public final AnonymousClass1 mAppSessionCallbacks;
    ApplicationsState mApplicationsState;
    public final Fragment mHostFragment;
    public final NotificationBackend mNotificationBackend;
    Context mPrefContext;
    PreferenceCategory mPreferenceCategory;
    PreferenceScreen mPreferenceScreen;

    public static void $r8$lambda$_WPJ_UO5f8r03zYILWSNNqrLZCw(
            ZenModeAddBypassingAppsPreferenceController zenModeAddBypassingAppsPreferenceController,
            ApplicationsState.AppEntry appEntry) {
        zenModeAddBypassingAppsPreferenceController.getClass();
        Bundle bundle = new Bundle();
        bundle.putString("package", appEntry.info.packageName);
        bundle.putInt(NetworkAnalyticsConstants.DataPoints.UID, appEntry.info.uid);
        SubSettingLauncher subSettingLauncher =
                new SubSettingLauncher(zenModeAddBypassingAppsPreferenceController.mContext);
        String name = AppChannelsBypassingDndSettings.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mArguments = bundle;
        subSettingLauncher.setResultListener(
                zenModeAddBypassingAppsPreferenceController.mHostFragment, 0);
        launchRequest.mUserHandle = new UserHandle(UserHandle.getUserId(appEntry.info.uid));
        launchRequest.mSourceMetricsCategory = 1589;
        subSettingLauncher.launch();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ZenModeAddBypassingAppsPreferenceController(
            Context context,
            Application application,
            Fragment fragment,
            NotificationBackend notificationBackend) {
        super(context);
        ApplicationsState applicationsState =
                application == null ? null : ApplicationsState.getInstance(application);
        this.mAppSessionCallbacks = new AnonymousClass1();
        this.mNotificationBackend = notificationBackend;
        this.mApplicationsState = applicationsState;
        this.mHostFragment = fragment;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        this.mPreferenceScreen = preferenceScreen;
        Preference findPreference = preferenceScreen.findPreference("zen_mode_bypassing_apps_add");
        this.mAddPreference = findPreference;
        findPreference.setOnPreferenceClickListener(new AnonymousClass1());
        this.mPrefContext = preferenceScreen.getContext();
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "zen_mode_non_bypassing_apps_list";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    public final void updateAppList() {
        if (this.mAppSession == null) {
            return;
        }
        this.mAppSession.rebuild(
                (Flags.enablePrivateSpaceFeatures()
                                && Flags.handleInterleavedSettingsForPrivateSpace())
                        ? ApplicationsState.FILTER_ENABLED_NOT_QUIET
                        : ApplicationsState.FILTER_ALL_ENABLED,
                ApplicationsState.ALPHA_COMPARATOR,
                true);
    }

    public void updateAppList(List<ApplicationsState.AppEntry> list) {
        ParceledListSlice emptyList;
        if (list == null) {
            return;
        }
        if (this.mPreferenceCategory == null) {
            PreferenceCategory preferenceCategory = new PreferenceCategory(this.mPrefContext);
            this.mPreferenceCategory = preferenceCategory;
            preferenceCategory.setTitle(R.string.zen_mode_bypassing_apps_add_header);
            this.mPreferenceScreen.addPreference(this.mPreferenceCategory);
        }
        boolean z = false;
        for (final ApplicationsState.AppEntry appEntry : list) {
            ApplicationInfo applicationInfo = appEntry.info;
            String str = applicationInfo.packageName;
            String str2 = "add|" + str + "|" + applicationInfo.uid;
            NotificationBackend notificationBackend = this.mNotificationBackend;
            int i = appEntry.info.uid;
            notificationBackend.getClass();
            int channelCount = NotificationBackend.getChannelCount(i, str);
            NotificationBackend notificationBackend2 = this.mNotificationBackend;
            int i2 = appEntry.info.uid;
            notificationBackend2.getClass();
            try {
                emptyList = NotificationBackend.sINM.getNotificationChannelsBypassingDnd(str, i2);
            } catch (Exception e) {
                Log.w("NotificationBackend", "Error calling NoMan", e);
                emptyList = ParceledListSlice.emptyList();
            }
            int size = emptyList.getList().size();
            if (size == 0 && channelCount > 0) {
                z = true;
            }
            Preference findPreference = this.mPreferenceCategory.findPreference(str2);
            if (findPreference == null) {
                if (size == 0 && channelCount > 0) {
                    final AppPreference appPreference = new AppPreference(this.mPrefContext);
                    appPreference.setKey(str2);
                    appPreference.setOnPreferenceClickListener(
                            new Preference
                                    .OnPreferenceClickListener() { // from class:
                                                                   // com.android.settings.notification.modes.ZenModeAddBypassingAppsPreferenceController$$ExternalSyntheticLambda0
                                @Override // androidx.preference.Preference.OnPreferenceClickListener
                                public final boolean onPreferenceClick(Preference preference) {
                                    ZenModeAddBypassingAppsPreferenceController
                                            .$r8$lambda$_WPJ_UO5f8r03zYILWSNNqrLZCw(
                                                    ZenModeAddBypassingAppsPreferenceController
                                                            .this,
                                                    appEntry);
                                    return true;
                                }
                            });
                    appPreference.setTitle(BidiFormatter.getInstance().unicodeWrap(appEntry.label));
                    synchronized (appEntry) {
                        try {
                            Drawable iconFromCache = AppUtils.getIconFromCache(appEntry);
                            if (iconFromCache != null && appEntry.mounted) {
                                appPreference.setIcon(iconFromCache);
                            } else {
                                ThreadUtils.postOnBackgroundThread(
                                        new Runnable() { // from class:
                                                         // com.android.settings.notification.modes.ZenModeAddBypassingAppsPreferenceController$$ExternalSyntheticLambda1
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                ZenModeAddBypassingAppsPreferenceController
                                                        zenModeAddBypassingAppsPreferenceController =
                                                                ZenModeAddBypassingAppsPreferenceController
                                                                        .this;
                                                ApplicationsState.AppEntry appEntry2 = appEntry;
                                                Preference preference = appPreference;
                                                final Drawable icon =
                                                        AppUtils.getIcon(
                                                                zenModeAddBypassingAppsPreferenceController
                                                                        .mPrefContext,
                                                                appEntry2);
                                                if (icon != null) {
                                                    final AppPreference appPreference2 =
                                                            (AppPreference) preference;
                                                    ThreadUtils.postOnMainThread(
                                                            new Runnable() { // from class:
                                                                             // com.android.settings.notification.modes.ZenModeAddBypassingAppsPreferenceController$$ExternalSyntheticLambda2
                                                                @Override // java.lang.Runnable
                                                                public final void run() {
                                                                    appPreference2.setIcon(icon);
                                                                }
                                                            });
                                                }
                                            }
                                        });
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    this.mPreferenceCategory.addPreference(appPreference);
                }
            } else if (size != 0 || channelCount == 0) {
                this.mPreferenceCategory.removePreference(findPreference);
            }
        }
        Preference findPreference2 = this.mPreferenceCategory.findPreference("add_none");
        if (z) {
            if (findPreference2 != null) {
                this.mPreferenceCategory.removePreference(findPreference2);
            }
        } else {
            if (findPreference2 == null) {
                findPreference2 = new Preference(this.mPrefContext);
                findPreference2.setKey("add_none");
                findPreference2.setTitle(R.string.zen_mode_bypassing_apps_none);
            }
            this.mPreferenceCategory.addPreference(findPreference2);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.notification.modes.ZenModeAddBypassingAppsPreferenceController$1, reason: invalid class name */
    public final class AnonymousClass1
            implements Preference.OnPreferenceClickListener, ApplicationsState.Callbacks {
        public /* synthetic */ AnonymousClass1() {}

        @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
        public void onLoadEntriesCompleted() {
            ZenModeAddBypassingAppsPreferenceController.this.updateAppList();
        }

        @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
        public void onPackageIconChanged() {
            ZenModeAddBypassingAppsPreferenceController.this.updateAppList();
        }

        @Override // androidx.preference.Preference.OnPreferenceClickListener
        public boolean onPreferenceClick(Preference preference) {
            Fragment fragment;
            ZenModeAddBypassingAppsPreferenceController
                    zenModeAddBypassingAppsPreferenceController =
                            ZenModeAddBypassingAppsPreferenceController.this;
            zenModeAddBypassingAppsPreferenceController.mAddPreference.setVisible(false);
            ApplicationsState applicationsState =
                    zenModeAddBypassingAppsPreferenceController.mApplicationsState;
            if (applicationsState == null
                    || (fragment = zenModeAddBypassingAppsPreferenceController.mHostFragment)
                            == null) {
                return true;
            }
            zenModeAddBypassingAppsPreferenceController.mAppSession =
                    applicationsState.newSession(
                            zenModeAddBypassingAppsPreferenceController.mAppSessionCallbacks,
                            fragment.getLifecycle());
            return true;
        }

        @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
        public void onRebuildComplete(ArrayList arrayList) {
            ZenModeAddBypassingAppsPreferenceController.this.updateAppList(arrayList);
        }

        @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
        public void onAllSizesComputed() {}

        @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
        public void onLauncherInfoChanged() {}

        @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
        public void onPackageListChanged() {}

        @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
        public void onPackageSizeChanged(String str) {}

        @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
        public void onRunningStateChanged(boolean z) {}
    }
}
