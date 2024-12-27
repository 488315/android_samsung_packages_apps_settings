package com.android.settings.fuelgauge;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.ParceledListSlice;
import android.content.pm.UserInfo;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.ArraySet;
import android.util.Log;

import com.android.settings.MainClear$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settingslib.datastore.BackupRestoreStorageManager;
import com.android.settingslib.fuelgauge.PowerAllowlistBackend;

import kotlin.jvm.internal.Intrinsics;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BatteryOptimizeUtils {
    public static List sBatteryOptimizeModeList;
    public static List sBatteryUnrestrictModeList;
    boolean mAllowListed;
    AppOpsManager mAppOpsManager;
    BatteryUtils mBatteryUtils;
    public final Context mContext;
    int mMode;
    public final String mPackageName;
    PowerAllowlistBackend mPowerAllowListBackend;
    public final int mUid;

    public BatteryOptimizeUtils(Context context, int i, String str) {
        this.mUid = i;
        this.mContext = context;
        this.mPackageName = str;
        this.mAppOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        this.mBatteryUtils = BatteryUtils.getInstance(context);
        this.mPowerAllowListBackend = PowerAllowlistBackend.getInstance(context);
        this.mMode = this.mAppOpsManager.checkOpNoThrow(70, i, str);
        this.mAllowListed = this.mPowerAllowListBackend.isAllowlisted(i, str);
    }

    public static int getAppOptimizationMode(int i, boolean z) {
        if (!z && i == 1) {
            return 1;
        }
        if (z && i == 0) {
            return 2;
        }
        return (z || i != 0) ? 0 : 3;
    }

    public static ArraySet getInstalledApplications(
            Context context, IPackageManager iPackageManager) {
        ArraySet arraySet = new ArraySet();
        for (UserInfo userInfo :
                ((UserManager) context.getSystemService(UserManager.class))
                        .getProfiles(UserHandle.myUserId())) {
            try {
                ParceledListSlice installedApplications =
                        iPackageManager.getInstalledApplications(
                                userInfo.isAdmin() ? 4227584L : 33280L, userInfo.id);
                if (installedApplications != null) {
                    arraySet.addAll(installedApplications.getList());
                }
            } catch (Exception e) {
                Log.e("BatteryOptimizeUtils", "getInstalledApplications() is failed", e);
                return null;
            }
        }
        arraySet.removeIf(new BatteryOptimizeUtils$$ExternalSyntheticLambda0());
        return arraySet;
    }

    public static void resetAppOptimizationModeInternal(
            Context context,
            IPackageManager iPackageManager,
            AppOpsManager appOpsManager,
            PowerAllowlistBackend powerAllowlistBackend,
            BatteryUtils batteryUtils) {
        ArraySet installedApplications = getInstalledApplications(context, iPackageManager);
        if (installedApplications == null || installedApplications.isEmpty()) {
            Log.w("BatteryOptimizeUtils", "no data found in the getInstalledApplications()");
            return;
        }
        Object obj =
                BackupRestoreStorageManager.getInstance(context)
                        .storageWrappers
                        .get("BatteryBackupHelper");
        Intrinsics.checkNotNull(obj);
        ((BatterySettingsStorage) ((BackupRestoreStorageManager.StorageWrapper) obj).storage)
                .notifyChange(2);
        powerAllowlistBackend.refreshList();
        Iterator it = installedApplications.iterator();
        while (it.hasNext()) {
            ApplicationInfo applicationInfo = (ApplicationInfo) it.next();
            int appOptimizationMode =
                    getAppOptimizationMode(
                            appOpsManager.checkOpNoThrow(
                                    70, applicationInfo.uid, applicationInfo.packageName),
                            powerAllowlistBackend.isAllowlisted(
                                    applicationInfo.uid, applicationInfo.packageName));
            if (appOptimizationMode != 3
                    && appOptimizationMode != 0
                    && !isSystemOrDefaultApp(
                            context,
                            powerAllowlistBackend,
                            applicationInfo.packageName,
                            applicationInfo.uid)) {
                setAppUsageStateInternal(
                        context,
                        3,
                        applicationInfo.uid,
                        applicationInfo.packageName,
                        batteryUtils,
                        powerAllowlistBackend,
                        BatteryOptimizeHistoricalLogEntry.Action.RESET);
            }
        }
    }

    public static void setAppUsageStateInternal(
            Context context,
            int i,
            int i2,
            String str,
            BatteryUtils batteryUtils,
            PowerAllowlistBackend powerAllowlistBackend,
            BatteryOptimizeHistoricalLogEntry.Action action) {
        String str2;
        if (i == 0) {
            Log.d("BatteryOptimizeUtils", "set unknown app optimization mode.");
            return;
        }
        int i3 = 0;
        int i4 = i == 1 ? 1 : 0;
        boolean z = i == 2;
        String packageNameWithUserId =
                BatteryOptimizeLogUtils.getPackageNameWithUserId(UserHandle.myUserId(), str);
        if (i4 == 0) {
            i3 = 9;
        } else if (i4 == 1) {
            i3 = 2;
        }
        try {
            batteryUtils.setForceAppStandby(i2, i4, i3, str);
            if (z) {
                powerAllowlistBackend.addApp(i2, str);
            } else {
                powerAllowlistBackend.removeApp(i2, str);
            }
        } catch (Exception e) {
            Log.e("BatteryOptimizeUtils", "set OPTIMIZATION MODE failed for " + str, e);
            i4 = -1;
        }
        if (i4 < 0) {
            str2 = "Apply optimize setting ERROR";
        } else {
            str2 =
                    "\tStandbyMode: "
                            + i4
                            + ", allowListed: "
                            + z
                            + ", mode: "
                            + getAppOptimizationMode(i4, z);
        }
        BatteryOptimizeLogUtils.writeLog(
                BatteryOptimizeLogUtils.getSharedPreferences(context),
                action,
                packageNameWithUserId,
                str2);
        if (action != BatteryOptimizeHistoricalLogEntry.Action.RESET) {
            Object obj =
                    BackupRestoreStorageManager.getInstance(context)
                            .storageWrappers
                            .get("BatteryBackupHelper");
            Intrinsics.checkNotNull(obj);
            ((BatterySettingsStorage) ((BackupRestoreStorageManager.StorageWrapper) obj).storage)
                    .notifyChange(
                            action == BatteryOptimizeHistoricalLogEntry.Action.RESTORE ? 3 : 1);
        }
    }

    public final boolean isDisabledForOptimizeModeOnly() {
        Context context = this.mContext;
        if (sBatteryOptimizeModeList == null) {
            sBatteryOptimizeModeList =
                    Arrays.asList(
                            context.getResources()
                                    .getStringArray(
                                            R.array.config_force_battery_optimize_mode_apps));
        }
        List list = sBatteryOptimizeModeList;
        String str = this.mPackageName;
        return list.contains(str) || this.mBatteryUtils.getPackageUid(str) == -1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0061, code lost:

       return isSystemOrDefaultApp(r10.mContext, r10.mPowerAllowListBackend, r0, r10.mUid);
    */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0054, code lost:

       if (r2 == null) goto L21;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isSystemOrDefaultApp() {
        /*
            r10 = this;
            com.android.settingslib.fuelgauge.PowerAllowlistBackend r0 = r10.mPowerAllowListBackend
            r0.refreshList()
            java.lang.String r0 = r10.mPackageName
            java.lang.String r1 = "BatteryOptimizeUtils"
            r2 = 0
            java.lang.String r3 = "content://com.sec.knox.provider2/ApplicationPolicy"
            android.net.Uri r5 = android.net.Uri.parse(r3)     // Catch: java.lang.Throwable -> L41 java.lang.Exception -> L4f
            android.content.Context r3 = r10.mContext     // Catch: java.lang.Throwable -> L41 java.lang.Exception -> L4f
            android.content.ContentResolver r4 = r3.getContentResolver()     // Catch: java.lang.Throwable -> L41 java.lang.Exception -> L4f
            java.lang.String r7 = "getAllPackagesFromBatteryOptimizationWhiteList"
            r9 = 0
            r6 = 0
            r8 = 0
            android.database.Cursor r2 = r4.query(r5, r6, r7, r8, r9)     // Catch: java.lang.Throwable -> L41 java.lang.Exception -> L4f
            if (r2 == 0) goto L49
            boolean r3 = r2.moveToFirst()     // Catch: java.lang.Throwable -> L41 java.lang.Exception -> L4f
            if (r3 == 0) goto L49
        L27:
            java.lang.String r3 = "getAllPackagesFromBatteryOptimizationWhiteList"
            int r3 = r2.getColumnIndex(r3)     // Catch: java.lang.Throwable -> L41 java.lang.Exception -> L4f
            java.lang.String r3 = r2.getString(r3)     // Catch: java.lang.Throwable -> L41 java.lang.Exception -> L4f
            boolean r3 = r0.equals(r3)     // Catch: java.lang.Throwable -> L41 java.lang.Exception -> L4f
            if (r3 == 0) goto L43
            java.lang.String r3 = "allow list app: true"
            android.util.Log.d(r1, r3)     // Catch: java.lang.Throwable -> L41 java.lang.Exception -> L4f
            r2.close()
            r10 = 1
            return r10
        L41:
            r10 = move-exception
            goto L62
        L43:
            boolean r3 = r2.moveToNext()     // Catch: java.lang.Throwable -> L41 java.lang.Exception -> L4f
            if (r3 != 0) goto L27
        L49:
            if (r2 == 0) goto L57
        L4b:
            r2.close()
            goto L57
        L4f:
            java.lang.String r3 = "failed to get allow list by MDM."
            android.util.Log.d(r1, r3)     // Catch: java.lang.Throwable -> L41
            if (r2 == 0) goto L57
            goto L4b
        L57:
            android.content.Context r1 = r10.mContext
            com.android.settingslib.fuelgauge.PowerAllowlistBackend r2 = r10.mPowerAllowListBackend
            int r10 = r10.mUid
            boolean r10 = isSystemOrDefaultApp(r1, r2, r0, r10)
            return r10
        L62:
            if (r2 == 0) goto L67
            r2.close()
        L67:
            throw r10
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.fuelgauge.BatteryOptimizeUtils.isSystemOrDefaultApp():boolean");
    }

    public final void setAppUsageState(int i, BatteryOptimizeHistoricalLogEntry.Action action) {
        if (getAppOptimizationMode(true) == i) {
            MainClear$$ExternalSyntheticOutline0.m$1(
                    new StringBuilder("set the same optimization mode for: "),
                    this.mPackageName,
                    "BatteryOptimizeUtils");
            return;
        }
        setAppUsageStateInternal(
                this.mContext,
                i,
                this.mUid,
                this.mPackageName,
                this.mBatteryUtils,
                this.mPowerAllowListBackend,
                action);
    }

    public final int getAppOptimizationMode(boolean z) {
        if (z) {
            this.mPowerAllowListBackend.refreshList();
        }
        PowerAllowlistBackend powerAllowlistBackend = this.mPowerAllowListBackend;
        String str = this.mPackageName;
        int i = this.mUid;
        this.mAllowListed = powerAllowlistBackend.isAllowlisted(i, str);
        this.mMode = this.mAppOpsManager.checkOpNoThrow(70, i, str);
        Log.d(
                "BatteryOptimizeUtils",
                String.format(
                        "refresh %s state, allowlisted = %s, mode = %d",
                        str, Boolean.valueOf(this.mAllowListed), Integer.valueOf(this.mMode)));
        return getAppOptimizationMode(this.mMode, this.mAllowListed);
    }

    public static boolean isSystemOrDefaultApp(
            Context context, PowerAllowlistBackend powerAllowlistBackend, String str, int i) {
        if (!powerAllowlistBackend.isSysAllowlisted(str)) {
            if (sBatteryUnrestrictModeList == null) {
                sBatteryUnrestrictModeList =
                        Arrays.asList(
                                context.getResources()
                                        .getStringArray(
                                                R.array.config_force_battery_unrestrict_mode_apps));
            }
            if (!sBatteryUnrestrictModeList.contains(str)
                    && !powerAllowlistBackend.isDefaultActiveApp(i, str)) {
                return false;
            }
        }
        return true;
    }
}
