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
public final class ZenModeAllBypassingAppsPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin {
    public final ApplicationsState.Session mAppSession;
    public final AnonymousClass1 mAppSessionCallbacks;
    ApplicationsState mApplicationsState;
    public final Fragment mHostFragment;
    public final NotificationBackend mNotificationBackend;
    Context mPrefContext;
    PreferenceCategory mPreferenceCategory;

    public static void $r8$lambda$KLAPWYCnxAQ1uydjeCNS1iA9q_k(
            ZenModeAllBypassingAppsPreferenceController zenModeAllBypassingAppsPreferenceController,
            ApplicationsState.AppEntry appEntry) {
        zenModeAllBypassingAppsPreferenceController.getClass();
        Bundle bundle = new Bundle();
        bundle.putString("package", appEntry.info.packageName);
        bundle.putInt(NetworkAnalyticsConstants.DataPoints.UID, appEntry.info.uid);
        SubSettingLauncher subSettingLauncher =
                new SubSettingLauncher(zenModeAllBypassingAppsPreferenceController.mContext);
        String name = AppChannelsBypassingDndSettings.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mArguments = bundle;
        launchRequest.mUserHandle = UserHandle.getUserHandleForUid(appEntry.info.uid);
        subSettingLauncher.setResultListener(
                zenModeAllBypassingAppsPreferenceController.mHostFragment, 0);
        launchRequest.mSourceMetricsCategory = 1589;
        subSettingLauncher.launch();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ZenModeAllBypassingAppsPreferenceController(
            Context context,
            Application application,
            Fragment fragment,
            NotificationBackend notificationBackend) {
        super(context);
        ApplicationsState applicationsState =
                application == null ? null : ApplicationsState.getInstance(application);
        ApplicationsState.Callbacks callbacks =
                new ApplicationsState
                        .Callbacks() { // from class:
                                       // com.android.settings.notification.modes.ZenModeAllBypassingAppsPreferenceController.1
                    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
                    public final void onLoadEntriesCompleted() {
                        ApplicationsState.Session session =
                                ZenModeAllBypassingAppsPreferenceController.this.mAppSession;
                        if (session == null) {
                            return;
                        }
                        session.rebuild(
                                (Flags.enablePrivateSpaceFeatures()
                                                && Flags.handleInterleavedSettingsForPrivateSpace())
                                        ? ApplicationsState.FILTER_ENABLED_NOT_QUIET
                                        : ApplicationsState.FILTER_ALL_ENABLED,
                                ApplicationsState.ALPHA_COMPARATOR,
                                true);
                    }

                    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
                    public final void onRebuildComplete(ArrayList arrayList) {
                        ZenModeAllBypassingAppsPreferenceController.this.updateAppList(arrayList);
                    }

                    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
                    public final void onAllSizesComputed() {}

                    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
                    public final void onLauncherInfoChanged() {}

                    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
                    public final void onPackageIconChanged() {}

                    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
                    public final void onPackageListChanged() {}

                    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
                    public final void onPackageSizeChanged(String str) {}

                    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
                    public final void onRunningStateChanged(boolean z) {}
                };
        this.mNotificationBackend = notificationBackend;
        this.mApplicationsState = applicationsState;
        this.mHostFragment = fragment;
        if (applicationsState == null || fragment == null) {
            return;
        }
        this.mAppSession = applicationsState.newSession(callbacks, fragment.getLifecycle());
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        this.mPreferenceCategory =
                (PreferenceCategory)
                        preferenceScreen.findPreference("zen_mode_bypassing_apps_list");
        this.mPrefContext = preferenceScreen.getContext();
        ApplicationsState.Session session = this.mAppSession;
        if (session != null) {
            session.rebuild(
                    (Flags.enablePrivateSpaceFeatures()
                                    && Flags.handleInterleavedSettingsForPrivateSpace())
                            ? ApplicationsState.FILTER_ENABLED_NOT_QUIET
                            : ApplicationsState.FILTER_ALL_ENABLED,
                    ApplicationsState.ALPHA_COMPARATOR,
                    true);
        }
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "zen_mode_bypassing_apps_list";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    public void updateAppList(List<ApplicationsState.AppEntry> list) {
        ParceledListSlice emptyList;
        if (this.mPreferenceCategory == null || list == null) {
            return;
        }
        boolean z = false;
        for (final ApplicationsState.AppEntry appEntry : list) {
            ApplicationInfo applicationInfo = appEntry.info;
            String str = applicationInfo.packageName;
            String str2 = "all|" + str + "|" + applicationInfo.uid;
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
            if (size > 0) {
                z = true;
            }
            Preference findPreference = this.mPreferenceCategory.findPreference(str2);
            if (findPreference == null) {
                if (size > 0) {
                    final AppPreference appPreference = new AppPreference(this.mPrefContext);
                    appPreference.setKey(str2);
                    appPreference.setOnPreferenceClickListener(
                            new Preference
                                    .OnPreferenceClickListener() { // from class:
                                                                   // com.android.settings.notification.modes.ZenModeAllBypassingAppsPreferenceController$$ExternalSyntheticLambda0
                                @Override // androidx.preference.Preference.OnPreferenceClickListener
                                public final boolean onPreferenceClick(Preference preference) {
                                    ZenModeAllBypassingAppsPreferenceController
                                            .$r8$lambda$KLAPWYCnxAQ1uydjeCNS1iA9q_k(
                                                    ZenModeAllBypassingAppsPreferenceController
                                                            .this,
                                                    appEntry);
                                    return true;
                                }
                            });
                    appPreference.setTitle(BidiFormatter.getInstance().unicodeWrap(appEntry.label));
                    synchronized (appEntry) {
                        try {
                            Drawable iconFromCache = AppUtils.getIconFromCache(appEntry);
                            if (iconFromCache == null || !appEntry.mounted) {
                                ThreadUtils.postOnBackgroundThread(
                                        new Runnable() { // from class:
                                                         // com.android.settings.notification.modes.ZenModeAllBypassingAppsPreferenceController$$ExternalSyntheticLambda1
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                ZenModeAllBypassingAppsPreferenceController
                                                        zenModeAllBypassingAppsPreferenceController =
                                                                ZenModeAllBypassingAppsPreferenceController
                                                                        .this;
                                                ApplicationsState.AppEntry appEntry2 = appEntry;
                                                Preference preference = appPreference;
                                                final Drawable icon =
                                                        AppUtils.getIcon(
                                                                zenModeAllBypassingAppsPreferenceController
                                                                        .mPrefContext,
                                                                appEntry2);
                                                if (icon != null) {
                                                    final AppPreference appPreference2 =
                                                            (AppPreference) preference;
                                                    ThreadUtils.postOnMainThread(
                                                            new Runnable() { // from class:
                                                                             // com.android.settings.notification.modes.ZenModeAllBypassingAppsPreferenceController$$ExternalSyntheticLambda2
                                                                @Override // java.lang.Runnable
                                                                public final void run() {
                                                                    appPreference2.setIcon(icon);
                                                                }
                                                            });
                                                }
                                            }
                                        });
                            } else {
                                appPreference.setIcon(iconFromCache);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    if (channelCount > size) {
                        appPreference.setSummary(R.string.zen_mode_bypassing_apps_summary_some);
                    } else {
                        appPreference.setSummary(R.string.zen_mode_bypassing_apps_summary_all);
                    }
                    this.mPreferenceCategory.addPreference(appPreference);
                } else {
                    continue;
                }
            } else if (size == 0) {
                this.mPreferenceCategory.removePreference(findPreference);
            }
        }
        Preference findPreference2 = this.mPreferenceCategory.findPreference("all_none");
        if (z) {
            if (findPreference2 != null) {
                this.mPreferenceCategory.removePreference(findPreference2);
            }
        } else {
            if (findPreference2 == null) {
                findPreference2 = new Preference(this.mPrefContext);
                findPreference2.setKey("all_none");
                findPreference2.setTitle(R.string.zen_mode_bypassing_apps_none);
            }
            this.mPreferenceCategory.addPreference(findPreference2);
        }
    }
}
