package com.android.settings.applications;

import android.R;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.Utils;
import com.android.settingslib.applications.ApplicationsState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppStateClonedAppsBridge extends AppStateBaseBridge {
    public static final AnonymousClass1 FILTER_APPS_CLONE = new AnonymousClass1();
    public final List mAllowedApps;
    public List mCloneProfileApps;
    public int mCloneUserId;
    public final Context mContext;

    public AppStateClonedAppsBridge(
            FragmentActivity fragmentActivity,
            ApplicationsState applicationsState,
            AppStateBaseBridge.Callback callback) {
        super(applicationsState, callback);
        this.mCloneProfileApps = new ArrayList();
        this.mContext = fragmentActivity;
        this.mAllowedApps =
                Arrays.asList(
                        fragmentActivity
                                .getResources()
                                .getStringArray(R.array.config_defaultNotificationVibePattern));
    }

    @Override // com.android.settings.applications.AppStateBaseBridge
    public final void loadAllExtraInfo() {
        int cloneUserId = Utils.getCloneUserId(this.mContext);
        this.mCloneUserId = cloneUserId;
        if (cloneUserId != -1) {
            this.mCloneProfileApps =
                    this.mContext
                            .getPackageManager()
                            .getInstalledPackagesAsUser(1, this.mCloneUserId)
                            .stream()
                            .map(new AppStateClonedAppsBridge$$ExternalSyntheticLambda0())
                            .toList();
        } else if (!this.mCloneProfileApps.isEmpty()) {
            this.mCloneProfileApps = new ArrayList();
        }
        ArrayList allApps = this.mAppSession.getAllApps();
        for (int i = 0; i < allApps.size(); i++) {
            ApplicationsState.AppEntry appEntry = (ApplicationsState.AppEntry) allApps.get(i);
            ApplicationInfo applicationInfo = appEntry.info;
            updateExtraInfo(appEntry, applicationInfo.packageName, applicationInfo.uid);
        }
    }

    @Override // com.android.settings.applications.AppStateBaseBridge
    public final void updateExtraInfo(ApplicationsState.AppEntry appEntry, String str, int i) {
        if (!this.mAllowedApps.contains(str)
                || (this.mCloneProfileApps.contains(str) && !appEntry.isClonedProfile())) {
            appEntry.extraInfo = Boolean.FALSE;
        } else {
            appEntry.extraInfo = Boolean.TRUE;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.applications.AppStateClonedAppsBridge$1, reason: invalid class name */
    public final class AnonymousClass1 implements ApplicationsState.AppFilter {
        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final boolean filterApp(ApplicationsState.AppEntry appEntry) {
            Object obj = appEntry.extraInfo;
            if (obj != null) {
                return ((Boolean) obj).booleanValue();
            }
            AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0.m(
                    new StringBuilder("["),
                    appEntry.info.packageName,
                    "] has No extra info.",
                    "ClonedAppsBridge");
            return false;
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final void init() {}
    }
}
