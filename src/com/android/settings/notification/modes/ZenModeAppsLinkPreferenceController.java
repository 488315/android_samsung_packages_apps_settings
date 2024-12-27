package com.android.settings.notification.modes;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.icu.text.MessageFormat;
import android.multiuser.Flags;
import android.os.Bundle;
import android.util.ArraySet;
import android.util.Log;

import androidx.core.text.BidiFormatter;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.notification.NotificationBackend;
import com.android.settingslib.applications.ApplicationsState;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModeAppsLinkPreferenceController extends AbstractZenModePreferenceController {
    public final ApplicationsState.Session mAppSession;
    final ApplicationsState.Callbacks mAppSessionCallbacks;
    public final NotificationBackend mNotificationBackend;
    public Preference mPreference;
    public final ZenModeSummaryHelper mSummaryHelper;
    public ZenMode mZenMode;

    public ZenModeAppsLinkPreferenceController(
            Context context,
            Fragment fragment,
            ApplicationsState applicationsState,
            ZenModesBackend zenModesBackend) {
        super(context, "zen_mode_apps", zenModesBackend);
        this.mNotificationBackend = new NotificationBackend();
        ApplicationsState.Callbacks callbacks =
                new ApplicationsState
                        .Callbacks() { // from class:
                                       // com.android.settings.notification.modes.ZenModeAppsLinkPreferenceController.1
                    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
                    public final void onLoadEntriesCompleted() {
                        ZenModeAppsLinkPreferenceController.this
                                .triggerUpdateAppsBypassingDndSummaryText();
                    }

                    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
                    public final void onPackageListChanged() {
                        ZenModeAppsLinkPreferenceController.this
                                .triggerUpdateAppsBypassingDndSummaryText();
                    }

                    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
                    public final void onRebuildComplete(ArrayList arrayList) {
                        String string;
                        ZenModeAppsLinkPreferenceController zenModeAppsLinkPreferenceController =
                                ZenModeAppsLinkPreferenceController.this;
                        ArraySet<String> appsBypassingDnd =
                                zenModeAppsLinkPreferenceController.getAppsBypassingDnd(arrayList);
                        Preference preference = zenModeAppsLinkPreferenceController.mPreference;
                        ZenMode zenMode = zenModeAppsLinkPreferenceController.mZenMode;
                        ZenModeSummaryHelper zenModeSummaryHelper =
                                zenModeAppsLinkPreferenceController.mSummaryHelper;
                        zenModeSummaryHelper.getClass();
                        if (zenMode.getPolicy().getAllowedChannels() != 1) {
                            string =
                                    zenMode.getPolicy().getAllowedChannels() == 2
                                            ? zenModeSummaryHelper
                                                    .mContext
                                                    .getResources()
                                                    .getString(R.string.zen_mode_apps_none_apps)
                                            : zenMode.getPolicy().getAllowedChannels() == -1
                                                    ? zenModeSummaryHelper
                                                            .mContext
                                                            .getResources()
                                                            .getString(
                                                                    R.string.zen_mode_apps_all_apps)
                                                    : ApnSettings.MVNO_NONE;
                        } else if (appsBypassingDnd == null) {
                            string =
                                    zenModeSummaryHelper
                                            .mContext
                                            .getResources()
                                            .getString(R.string.zen_mode_apps_priority_apps);
                        } else {
                            int size = appsBypassingDnd.size();
                            String[] strArr = (String[]) appsBypassingDnd.toArray(new String[size]);
                            Arrays.sort(strArr);
                            MessageFormat messageFormat =
                                    new MessageFormat(
                                            zenModeSummaryHelper.mContext.getString(
                                                    R.string.zen_mode_apps_subtext),
                                            Locale.getDefault());
                            HashMap hashMap = new HashMap();
                            hashMap.put("count", Integer.valueOf(size));
                            if (size >= 1) {
                                hashMap.put("app_1", strArr[0]);
                                if (size >= 2) {
                                    hashMap.put("app_2", strArr[1]);
                                    if (size == 3) {
                                        hashMap.put("app_3", strArr[2]);
                                    }
                                }
                            }
                            string = messageFormat.format(hashMap);
                        }
                        preference.setSummary(string);
                    }

                    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
                    public final void onAllSizesComputed() {}

                    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
                    public final void onLauncherInfoChanged() {}

                    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
                    public final void onPackageIconChanged() {}

                    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
                    public final void onPackageSizeChanged(String str) {}

                    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
                    public final void onRunningStateChanged(boolean z) {}
                };
        this.mAppSessionCallbacks = callbacks;
        this.mSummaryHelper = new ZenModeSummaryHelper(this.mContext, this.mBackend);
        if (applicationsState == null || fragment == null) {
            return;
        }
        this.mAppSession = applicationsState.newSession(callbacks, fragment.getLifecycle());
    }

    public ArraySet<String> getAppsBypassingDnd(List<ApplicationsState.AppEntry> list) {
        List<String> arrayList;
        ArraySet<String> arraySet = new ArraySet<>();
        HashMap hashMap = new HashMap();
        for (ApplicationsState.AppEntry appEntry : list) {
            ApplicationInfo applicationInfo = appEntry.info;
            if (applicationInfo != null) {
                hashMap.put(applicationInfo.packageName, appEntry.label);
            }
        }
        int userId = this.mContext.getUserId();
        this.mNotificationBackend.getClass();
        try {
            arrayList = NotificationBackend.sINM.getPackagesBypassingDnd(userId, false);
        } catch (Exception e) {
            Log.w("NotificationBackend", "Error calling NoMan", e);
            arrayList = new ArrayList();
        }
        for (String str : arrayList) {
            if (hashMap.get(str) != null) {
                arraySet.add(BidiFormatter.getInstance().unicodeWrap((String) hashMap.get(str)));
            }
        }
        return arraySet;
    }

    public final void triggerUpdateAppsBypassingDndSummaryText() {
        ApplicationsState.Session session = this.mAppSession;
        if (session == null) {
            return;
        }
        session.rebuild(
                (Flags.enablePrivateSpaceFeatures()
                                && Flags.handleInterleavedSettingsForPrivateSpace())
                        ? ApplicationsState.FILTER_ENABLED_NOT_QUIET
                        : ApplicationsState.FILTER_ALL_ENABLED,
                ApplicationsState.ALPHA_COMPARATOR,
                false);
    }

    @Override // com.android.settings.notification.modes.AbstractZenModePreferenceController
    public final void updateState(Preference preference, ZenMode zenMode) {
        Bundle bundle = new Bundle();
        bundle.putString("MODE_ID", zenMode.mId);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = ZenModeAppsFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = 0;
        launchRequest.mArguments = bundle;
        preference.setIntent(subSettingLauncher.toIntent());
        this.mZenMode = zenMode;
        this.mPreference = preference;
        triggerUpdateAppsBypassingDndSummaryText();
    }
}
