package com.android.settings.applications;

import android.app.AppGlobals;
import android.app.job.JobScheduler;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;

import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.ArrayUtils;
import com.android.settingslib.applications.ApplicationsState;

import libcore.util.EmptyArray;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppStateLongBackgroundTasksBridge extends AppStateBaseBridge {
    public static final AnonymousClass1 FILTER_LONG_JOBS_APPS = new AnonymousClass1();

    @VisibleForTesting JobScheduler mJobScheduler;

    @VisibleForTesting String[] mRequesterPackages;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class LongBackgroundTasksState {
        public boolean mPermissionGranted;
        public boolean mPermissionRequested;
    }

    public AppStateLongBackgroundTasksBridge(
            Context context,
            ApplicationsState applicationsState,
            AppStateBaseBridge.Callback callback) {
        super(applicationsState, callback);
        this.mJobScheduler = (JobScheduler) context.getSystemService(JobScheduler.class);
        try {
            this.mRequesterPackages =
                    AppGlobals.getPackageManager()
                            .getAppOpPermissionPackages(
                                    "android.permission.RUN_USER_INITIATED_JOBS",
                                    context.getUserId());
        } catch (RemoteException e) {
            Log.e("LongBackgroundTasksBridge", "Cannot reach package manager", e);
            this.mRequesterPackages = EmptyArray.STRING;
        }
    }

    public final LongBackgroundTasksState createPermissionState(int i, String str) {
        int userId = UserHandle.getUserId(i);
        boolean contains = ArrayUtils.contains(this.mRequesterPackages, str);
        boolean hasRunUserInitiatedJobsPermission =
                this.mJobScheduler.hasRunUserInitiatedJobsPermission(str, userId);
        LongBackgroundTasksState longBackgroundTasksState = new LongBackgroundTasksState();
        longBackgroundTasksState.mPermissionRequested = contains;
        longBackgroundTasksState.mPermissionGranted = hasRunUserInitiatedJobsPermission;
        return longBackgroundTasksState;
    }

    @Override // com.android.settings.applications.AppStateBaseBridge
    public final void loadAllExtraInfo() {
        ArrayList allApps = this.mAppSession.getAllApps();
        for (int i = 0; i < allApps.size(); i++) {
            ApplicationsState.AppEntry appEntry = (ApplicationsState.AppEntry) allApps.get(i);
            ApplicationInfo applicationInfo = appEntry.info;
            updateExtraInfo(appEntry, applicationInfo.packageName, applicationInfo.uid);
        }
    }

    @Override // com.android.settings.applications.AppStateBaseBridge
    public final void updateExtraInfo(ApplicationsState.AppEntry appEntry, String str, int i) {
        appEntry.extraInfo = createPermissionState(i, str);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.applications.AppStateLongBackgroundTasksBridge$1, reason: invalid class name */
    public final class AnonymousClass1 implements ApplicationsState.AppFilter {
        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final boolean filterApp(ApplicationsState.AppEntry appEntry) {
            Object obj = appEntry.extraInfo;
            if (obj instanceof LongBackgroundTasksState) {
                return ((LongBackgroundTasksState) obj).mPermissionRequested;
            }
            return false;
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final void init() {}
    }
}
