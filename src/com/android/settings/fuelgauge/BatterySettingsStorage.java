package com.android.settings.fuelgauge;

import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.IDeviceIdleController;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.applications.AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0;
import com.android.settings.fuelgauge.batteryusage.AppOptModeSharedPreferencesUtils;
import com.android.settings.fuelgauge.batteryusage.AppOptimizationModeEvent;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.datastore.BackupCodec;
import com.android.settingslib.datastore.BackupContext;
import com.android.settingslib.datastore.BackupRestoreEntity;
import com.android.settingslib.datastore.BackupRestoreStorage;
import com.android.settingslib.datastore.BackupRestoreStorageManager;
import com.android.settingslib.datastore.DataObservable;
import com.android.settingslib.datastore.EntityBackupResult;
import com.android.settingslib.datastore.Observable;
import com.android.settingslib.datastore.RestoreContext;
import com.android.settingslib.fuelgauge.BatterySaverUtils;
import com.android.settingslib.fuelgauge.PowerAllowlistBackend;

import kotlin.jvm.internal.Intrinsics;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BatterySettingsStorage extends BackupRestoreStorage implements Observable {
    public final Application mApplication;
    public byte[] mOptimizationModeBytes;
    public final /* synthetic */ DataObservable $$delegate_0 = new DataObservable();
    public final ArrayMap mDeviceBuildInfoMap = new ArrayMap(6);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class OptimizationModeEntity implements BackupRestoreEntity {
        public final List mAllowlistedApps;

        public OptimizationModeEntity(List list) {
            this.mAllowlistedApps = list;
        }

        @Override // com.android.settingslib.datastore.BackupRestoreEntity
        public final EntityBackupResult backup(
                BackupContext backupContext, OutputStream outputStream) {
            BatterySettingsStorage batterySettingsStorage;
            OptimizationModeEntity optimizationModeEntity = this;
            long currentTimeMillis = System.currentTimeMillis();
            BatterySettingsStorage batterySettingsStorage2 = BatterySettingsStorage.this;
            ArraySet installedApplications =
                    BatteryOptimizeUtils.getInstalledApplications(
                            batterySettingsStorage2.mApplication, AppGlobals.getPackageManager());
            int i = 0;
            ApplicationInfo[] applicationInfoArr = new ApplicationInfo[0];
            if (installedApplications != null && !installedApplications.isEmpty()) {
                applicationInfoArr =
                        (ApplicationInfo[]) installedApplications.toArray(applicationInfoArr);
                Arrays.sort(
                        applicationInfoArr,
                        Comparator.comparing(
                                new BatterySettingsStorage$OptimizationModeEntity$$ExternalSyntheticLambda1(
                                        2)));
            }
            if (applicationInfoArr.length == 0) {
                Log.w("BatterySettingsStorage", "no data found in the getInstalledApplications()");
                return EntityBackupResult.DELETE;
            }
            StringBuilder sb = new StringBuilder();
            AppOpsManager appOpsManager =
                    (AppOpsManager)
                            batterySettingsStorage2.mApplication.getSystemService(
                                    AppOpsManager.class);
            SharedPreferences sharedPreferences =
                    batterySettingsStorage2.mApplication.getSharedPreferences(
                            "battery_optimize_backup_historical_logs", 0);
            Map map =
                    (Map)
                            AppOptModeSharedPreferencesUtils.getAllEvents(
                                            batterySettingsStorage2.mApplication)
                                    .stream()
                                    .collect(
                                            Collectors.toMap(
                                                    new BatterySettingsStorage$OptimizationModeEntity$$ExternalSyntheticLambda1(
                                                            0),
                                                    new BatterySettingsStorage$OptimizationModeEntity$$ExternalSyntheticLambda1(
                                                            1)));
            int length = applicationInfoArr.length;
            int i2 = 0;
            while (i < length) {
                ApplicationInfo applicationInfo = applicationInfoArr[i];
                int i3 = length;
                int resetOptimizationMode =
                        map.containsKey(Integer.valueOf(applicationInfo.uid))
                                ? ((AppOptimizationModeEvent)
                                                map.get(Integer.valueOf(applicationInfo.uid)))
                                        .getResetOptimizationMode()
                                : BatteryOptimizeUtils.getAppOptimizationMode(
                                        appOpsManager.checkOpNoThrow(
                                                70,
                                                applicationInfo.uid,
                                                applicationInfo.packageName),
                                        optimizationModeEntity.mAllowlistedApps.contains(
                                                applicationInfo.packageName));
                if (resetOptimizationMode == 3 || resetOptimizationMode == 0) {
                    batterySettingsStorage = batterySettingsStorage2;
                } else {
                    String str = applicationInfo.packageName;
                    int i4 = applicationInfo.uid;
                    Application application = batterySettingsStorage2.mApplication;
                    batterySettingsStorage = batterySettingsStorage2;
                    if (!BatteryOptimizeUtils.isSystemOrDefaultApp(
                            application, PowerAllowlistBackend.getInstance(application), str, i4)) {
                        String str2 = applicationInfo.packageName + ":" + resetOptimizationMode;
                        sb.append(str2);
                        sb.append(",");
                        Log.d("BatterySettingsStorage", "backupOptimizationMode: " + str2);
                        BatteryOptimizeLogUtils.writeLog(
                                sharedPreferences,
                                BatteryOptimizeHistoricalLogEntry.Action.BACKUP,
                                applicationInfo.packageName,
                                "mode: " + resetOptimizationMode);
                        i2++;
                    }
                }
                i++;
                optimizationModeEntity = this;
                length = i3;
                batterySettingsStorage2 = batterySettingsStorage;
            }
            outputStream.write(sb.toString().getBytes(StandardCharsets.UTF_8));
            Log.d(
                    "BatterySettingsStorage",
                    String.format(
                            "backup getInstalledApplications():%d count=%d in %d/ms",
                            Integer.valueOf(applicationInfoArr.length),
                            Integer.valueOf(i2),
                            BatterySettingsStorage$OptimizationModeEntity$$ExternalSyntheticOutline0
                                    .m(currentTimeMillis)));
            return EntityBackupResult.UPDATE;
        }

        @Override // com.android.settingslib.datastore.BackupRestoreEntity
        public final String getKey() {
            return "optimization_mode_list";
        }

        @Override // com.android.settingslib.datastore.BackupRestoreEntity
        public final void restore(RestoreContext restoreContext, InputStream inputStream) {
            BatterySettingsStorage.this.mOptimizationModeBytes = inputStream.readAllBytes();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class StringEntity implements BackupRestoreEntity {
        public final String mKey;
        public final String mValue;

        public StringEntity(String str, String str2) {
            this.mKey = str;
            this.mValue = str2;
        }

        @Override // com.android.settingslib.datastore.BackupRestoreEntity
        public final EntityBackupResult backup(
                BackupContext backupContext, OutputStream outputStream) {
            StringBuilder sb = new StringBuilder("backup:");
            sb.append(this.mKey);
            sb.append(":");
            String str = this.mValue;
            MainClearConfirm$$ExternalSyntheticOutline0.m(sb, str, "BatterySettingsStorage");
            outputStream.write(str.getBytes(StandardCharsets.UTF_8));
            return EntityBackupResult.UPDATE;
        }

        @Override // com.android.settingslib.datastore.BackupRestoreEntity
        public final String getKey() {
            return this.mKey;
        }

        @Override // com.android.settingslib.datastore.BackupRestoreEntity
        public final void restore(RestoreContext restoreContext, InputStream inputStream) {
            String str = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            ArrayMap arrayMap = BatterySettingsStorage.this.mDeviceBuildInfoMap;
            String str2 = this.mKey;
            arrayMap.put(str2, str);
            StringBuilder sb = new StringBuilder("restore:");
            sb.append(str2);
            AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0.m(
                    sb, ":", str, "BatterySettingsStorage");
        }
    }

    public BatterySettingsStorage(Context context) {
        this.mApplication = (Application) context.getApplicationContext();
    }

    @Override // com.android.settingslib.datastore.Observable
    public final void addObserver$1(
            BackupRestoreStorageManager.StorageWrapper observer, Executor executor) {
        Intrinsics.checkNotNullParameter(observer, "observer");
        Intrinsics.checkNotNullParameter(executor, "executor");
        this.$$delegate_0.addObserver$1(observer, executor);
    }

    @Override // com.android.settingslib.datastore.BackupRestoreStorage
    public final List createBackupRestoreEntities() {
        List list;
        long currentTimeMillis = System.currentTimeMillis();
        try {
            String[] fullPowerWhitelist =
                    IDeviceIdleController.Stub.asInterface(ServiceManager.getService("deviceidle"))
                            .getFullPowerWhitelist();
            if (fullPowerWhitelist == null || fullPowerWhitelist.length == 0) {
                Log.w("BatterySettingsStorage", "no data found in the getFullPowerList()");
                list = Collections.emptyList();
            } else {
                Log.d(
                        "BatterySettingsStorage",
                        String.format(
                                "getFullPowerList() size=%d in %d/ms",
                                Integer.valueOf(fullPowerWhitelist.length),
                                BatterySettingsStorage$OptimizationModeEntity$$ExternalSyntheticOutline0
                                        .m(currentTimeMillis)));
                list = Arrays.asList(fullPowerWhitelist);
            }
        } catch (RemoteException e) {
            Log.e("BatterySettingsStorage", "backupFullPowerList() failed", e);
            list = null;
        }
        if (list == null) {
            return Collections.emptyList();
        }
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        PowerUsageFeatureProviderImpl powerUsageFeatureProvider =
                featureFactoryImpl.getPowerUsageFeatureProvider();
        StringEntity stringEntity = new StringEntity("device_build_brand", Build.BRAND);
        StringEntity stringEntity2 = new StringEntity("device_build_product", Build.PRODUCT);
        StringEntity stringEntity3 =
                new StringEntity("device_build_manufacture", Build.MANUFACTURER);
        StringEntity stringEntity4 =
                new StringEntity("device_build_fingerprint", Build.FINGERPRINT);
        powerUsageFeatureProvider.getClass();
        return Arrays.asList(
                stringEntity,
                stringEntity2,
                stringEntity3,
                stringEntity4,
                new StringEntity("device_build_metadata_1", null),
                new StringEntity("device_build_metadata_2", null),
                new OptimizationModeEntity(list));
    }

    @Override // com.android.settingslib.datastore.BackupRestoreStorage
    public final boolean enableBackup(BackupContext backupContext) {
        return UserHandle.myUserId() == 0;
    }

    @Override // com.android.settingslib.datastore.BackupRestoreStorage
    public final boolean enableRestore() {
        return UserHandle.myUserId() == 0;
    }

    @Override // com.android.settingslib.datastore.BackupRestoreStorage
    public final String getName() {
        return "BatteryBackupHelper";
    }

    @Override // com.android.settingslib.datastore.Observable
    public final void notifyChange(int i) {
        this.$$delegate_0.notifyChange(i);
    }

    @Override // com.android.settingslib.datastore.BackupRestoreStorage
    public final void onRestoreFinished() {
        Application application = this.mApplication;
        BatteryOptimizeUtils batteryOptimizeUtils =
                BatterySettingsMigrateChecker.sBatteryOptimizeUtils;
        Log.d("BatterySettingsMigrateChecker", "invoke verifySaverConfiguration()");
        ContentResolver contentResolver = application.getContentResolver();
        int i = Settings.Global.getInt(contentResolver, "low_power_trigger_level", 0);
        if (i < 20 && i > 0) {
            Settings.Global.putInt(contentResolver, "low_power_trigger_level", 20);
            Log.w(
                    "BatterySettingsMigrateChecker",
                    "Reset invalid scheduled battery level from: " + i);
        }
        BatterySaverUtils.revertScheduleToNoneIfNeeded(application);
        byte[] bArr = this.mOptimizationModeBytes;
        this.mOptimizationModeBytes = null;
        if (bArr == null || bArr.length == 0) {
            return;
        }
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getPowerUsageFeatureProvider().getClass();
    }

    @Override // com.android.settingslib.datastore.Observable
    public final void removeObserver$1(BackupRestoreStorageManager.StorageWrapper storageWrapper) {
        this.$$delegate_0.removeObserver$1(storageWrapper);
    }

    @Override // com.android.settingslib.datastore.BackupRestoreStorage
    public final OutputStream wrapBackupOutputStream(
            BackupCodec backupCodec, OutputStream outputStream) {
        return outputStream;
    }

    @Override // com.android.settingslib.datastore.BackupRestoreStorage
    public final InputStream wrapRestoreInputStream(
            BackupCodec backupCodec, InputStream inputStream) {
        return inputStream;
    }
}
