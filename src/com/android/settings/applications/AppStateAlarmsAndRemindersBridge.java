package com.android.settings.applications;

import android.app.AlarmManager;
import android.app.compat.CompatChanges;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.PowerExemptionManager;
import android.os.UserHandle;
import android.util.Log;

import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.ArrayUtils;
import com.android.settingslib.applications.ApplicationsState;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppStateAlarmsAndRemindersBridge extends AppStateBaseBridge {
    public static final AnonymousClass1 FILTER_CLOCK_APPS = new AnonymousClass1();

    @VisibleForTesting AlarmManager mAlarmManager;

    @VisibleForTesting PackageManager mPackageManager;

    @VisibleForTesting PowerExemptionManager mPowerExemptionManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AlarmsAndRemindersState {
        public boolean mAllowListed;
        public boolean mSeaPermissionGranted;
        public boolean mSeaPermissionRequested;
        public boolean mUeaPermissionRequested;

        public final boolean isAllowed() {
            return this.mSeaPermissionGranted || this.mUeaPermissionRequested || this.mAllowListed;
        }

        public final boolean shouldBeVisible() {
            return (!this.mSeaPermissionRequested
                            || this.mUeaPermissionRequested
                            || this.mAllowListed)
                    ? false
                    : true;
        }
    }

    public AppStateAlarmsAndRemindersBridge(
            Context context,
            ApplicationsState applicationsState,
            AppStateBaseBridge.Callback callback) {
        super(applicationsState, callback);
        this.mPowerExemptionManager =
                (PowerExemptionManager) context.getSystemService(PowerExemptionManager.class);
        this.mAlarmManager = (AlarmManager) context.getSystemService(AlarmManager.class);
        this.mPackageManager = context.getPackageManager();
    }

    public final AlarmsAndRemindersState createPermissionState(int i, String str) {
        String[] strArr;
        int userId = UserHandle.getUserId(i);
        try {
            strArr =
                    this.mPackageManager.getPackageInfoAsUser(str, 4096, userId)
                            .requestedPermissions;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("AlarmsAndRemindersBridge", "Could not find package " + str, e);
            strArr = null;
        }
        boolean z = false;
        boolean z2 =
                ArrayUtils.contains(strArr, "android.permission.SCHEDULE_EXACT_ALARM")
                        && CompatChanges.isChangeEnabled(171306433L, str, UserHandle.of(userId));
        if (ArrayUtils.contains(strArr, "android.permission.USE_EXACT_ALARM")
                && CompatChanges.isChangeEnabled(218533173L, str, UserHandle.of(userId))) {
            z = true;
        }
        boolean hasScheduleExactAlarm = this.mAlarmManager.hasScheduleExactAlarm(str, userId);
        boolean isAllowListed = this.mPowerExemptionManager.isAllowListed(str, true);
        AlarmsAndRemindersState alarmsAndRemindersState = new AlarmsAndRemindersState();
        alarmsAndRemindersState.mSeaPermissionRequested = z2;
        alarmsAndRemindersState.mUeaPermissionRequested = z;
        alarmsAndRemindersState.mSeaPermissionGranted = hasScheduleExactAlarm;
        alarmsAndRemindersState.mAllowListed = isAllowListed;
        return alarmsAndRemindersState;
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
    /* renamed from: com.android.settings.applications.AppStateAlarmsAndRemindersBridge$1, reason: invalid class name */
    public final class AnonymousClass1 implements ApplicationsState.AppFilter {
        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final boolean filterApp(ApplicationsState.AppEntry appEntry) {
            Object obj = appEntry.extraInfo;
            if (obj instanceof AlarmsAndRemindersState) {
                return ((AlarmsAndRemindersState) obj).shouldBeVisible();
            }
            return false;
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final void init() {}
    }
}
