package com.android.settings.applications.manageapplications;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.NotificationChannel;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.fuelgauge.BatteryOptimizeUtils;
import com.android.settings.fuelgauge.BatteryUtils;
import com.android.settings.fuelgauge.batteryusage.AppOptModeSharedPreferencesUtils;
import com.android.settings.fuelgauge.datasaver.DynamicDenylistManager;
import com.android.settingslib.fuelgauge.PowerAllowlistBackend;

import com.samsung.android.settings.fuelgauge.DeviceCareManager;

import kotlin.jvm.internal.Intrinsics;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class ResetAppsHelper$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ ResetAppsHelper f$0;

    @Override // java.lang.Runnable
    public final void run() {
        NotificationChannel notificationChannelForPackage;
        ResetAppsHelper resetAppsHelper = this.f$0;
        List asList =
                Arrays.asList(
                        resetAppsHelper
                                .mContext
                                .getResources()
                                .getStringArray(R.array.config_skip_reset_apps_package_name));
        Iterator it = resetAppsHelper.mUm.getEnabledProfiles().iterator();
        while (it.hasNext()) {
            int identifier = ((UserHandle) it.next()).getIdentifier();
            for (ApplicationInfo applicationInfo :
                    resetAppsHelper.mPm.getInstalledApplicationsAsUser(512, identifier)) {
                if (!asList.contains(applicationInfo.packageName)) {
                    try {
                        if (resetAppsHelper.mNm.onlyHasDefaultChannel(
                                        applicationInfo.packageName, applicationInfo.uid)
                                && (notificationChannelForPackage =
                                                resetAppsHelper.mNm
                                                        .getNotificationChannelForPackage(
                                                                applicationInfo.packageName,
                                                                applicationInfo.uid,
                                                                "miscellaneous",
                                                                (String) null,
                                                                true))
                                        != null) {
                            notificationChannelForPackage.setImportance(-1000);
                            notificationChannelForPackage.lockFields(4);
                            resetAppsHelper.mNm.updateNotificationChannelForPackage(
                                    applicationInfo.packageName,
                                    applicationInfo.uid,
                                    notificationChannelForPackage);
                        }
                        resetAppsHelper.mNm.clearData(
                                applicationInfo.packageName, applicationInfo.uid, false);
                    } catch (RemoteException unused) {
                    }
                    if (!applicationInfo.enabled) {
                        try {
                            if (resetAppsHelper.mIPm.getApplicationEnabledSetting(
                                            applicationInfo.packageName, identifier)
                                    == 3) {
                                resetAppsHelper.mIPm.setApplicationEnabledSetting(
                                        applicationInfo.packageName,
                                        0,
                                        1,
                                        identifier,
                                        resetAppsHelper.mContext.getPackageName());
                            }
                        } catch (RemoteException e) {
                            Log.e("ResetAppsHelper", "Error during reset disabled apps.", e);
                        }
                    }
                }
            }
        }
        try {
            resetAppsHelper.mIPm.resetApplicationPreferences(UserHandle.myUserId());
        } catch (RemoteException unused2) {
        }
        resetAppsHelper.mAom.resetAllModes();
        Context context = resetAppsHelper.mContext;
        IPackageManager iPackageManager = resetAppsHelper.mIPm;
        AppOpsManager appOpsManager = resetAppsHelper.mAom;
        AppOptModeSharedPreferencesUtils appOptModeSharedPreferencesUtils =
                AppOptModeSharedPreferencesUtils.INSTANCE;
        Intrinsics.checkNotNullParameter(context, "context");
        synchronized (AppOptModeSharedPreferencesUtils.appOptimizationModeLock) {
            AppOptModeSharedPreferencesUtils appOptModeSharedPreferencesUtils2 =
                    AppOptModeSharedPreferencesUtils.INSTANCE;
            AppOptModeSharedPreferencesUtils.getSharedPreferences(context).edit().clear().apply();
        }
        BatteryOptimizeUtils.resetAppOptimizationModeInternal(
                context,
                iPackageManager,
                appOpsManager,
                PowerAllowlistBackend.getInstance(context),
                BatteryUtils.getInstance(context));
        DynamicDenylistManager.getInstance(resetAppsHelper.mContext)
                .resetDenylistIfNeeded(null, true);
        BatteryUtils batteryUtils = BatteryUtils.getInstance(resetAppsHelper.mContext);
        if (batteryUtils.mDeviceCareManager == null) {
            batteryUtils.mDeviceCareManager = new DeviceCareManager(batteryUtils.mContext);
        }
        DeviceCareManager deviceCareManager = batteryUtils.mDeviceCareManager;
        if (deviceCareManager != null) {
            if (deviceCareManager == null) {
                batteryUtils.mDeviceCareManager = new DeviceCareManager(batteryUtils.mContext);
            }
            DeviceCareManager deviceCareManager2 = batteryUtils.mDeviceCareManager;
            deviceCareManager2.getClass();
            Intent intent = new Intent("com.sec.android.sdhms.action.FAS_VERIFICATION");
            intent.setPackage("com.sec.android.sdhms");
            intent.putExtra("fas_verification_type", "init");
            deviceCareManager2.mContext.sendBroadcast(intent);
        }
        int[] uidsWithPolicy = resetAppsHelper.mNpm.getUidsWithPolicy(1);
        int currentUser = ActivityManager.getCurrentUser();
        for (int i : uidsWithPolicy) {
            if (UserHandle.getUserId(i) == currentUser) {
                resetAppsHelper.mNpm.setUidPolicy(i, 0);
            }
        }
    }
}
