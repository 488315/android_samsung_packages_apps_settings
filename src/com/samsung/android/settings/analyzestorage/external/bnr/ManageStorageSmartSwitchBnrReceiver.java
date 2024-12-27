package com.samsung.android.settings.analyzestorage.external.bnr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;

import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.domain.thread.ThreadExecutor;
import com.samsung.android.settings.analyzestorage.external.database.ManageStorageDatabase;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"
        },
        d2 = {
            "Lcom/samsung/android/settings/analyzestorage/external/bnr/ManageStorageSmartSwitchBnrReceiver;",
            "Landroid/content/BroadcastReceiver;",
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes4.dex */
public final class ManageStorageSmartSwitchBnrReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (intent == null) {
            Log.d("ManageStorageSmartSwitchBnrReceiver", "onReceive() ] intent is null.");
            return;
        }
        String action = intent.getAction();
        Log.d("ManageStorageSmartSwitchBnrReceiver", "onReceive() ] action : " + action);
        if (TextUtils.isEmpty(action)) {
            return;
        }
        BnrParam bnrParam = new BnrParam();
        bnrParam.destinationPath = intent.getStringExtra("SAVE_PATH");
        bnrParam.detailAction = intent.getIntExtra("ACTION", 0);
        bnrParam.encryptionKey = intent.getStringExtra("SESSION_KEY");
        bnrParam.requestFrom = intent.getStringExtra("SOURCE");
        bnrParam.exportSessionTime = intent.getStringExtra("EXPORT_SESSION_TIME");
        bnrParam.securityLevel = intent.getIntExtra("SECURITY_LEVEL", 0);
        if (!Intrinsics.areEqual(
                action,
                "com.samsung.android.intent.action.REQUEST_BACKUP_MANAGE_STORAGE_SETTING")) {
            if (Intrinsics.areEqual(
                    action,
                    "com.samsung.android.intent.action.REQUEST_RESTORE_MANAGE_STORAGE_SETTING")) {
                if (!BnrUtils.isValidDestinationPath(bnrParam.destinationPath)) {
                    Log.e(
                            "ManageStorageSmartSwitchBnrReceiver",
                            "handleRestoreRequest() ] Invalid Destination Path - BnrParam : "
                                    + bnrParam);
                    return;
                }
                Log.d(
                        "ManageStorageSmartSwitchBnrReceiver",
                        "createDatabaseIfNotExist() ] manageStorageDBVersion : "
                                + ManageStorageDatabase.getInstance(context)
                                        .getOpenHelper()
                                        .getWritableDatabase()
                                        .getVersion());
                RestoreThread restoreThread = SmartSwitchBnrManager.restoreThread;
                if (restoreThread != null && restoreThread.isAlive()) {
                    Log.e(
                            "SmartSwitchBnrManager",
                            "startRestore() ] Smart Switch Restore is already in progress");
                    return;
                }
                RestoreThread restoreThread2 = new RestoreThread(context, bnrParam);
                SmartSwitchBnrManager.restoreThread = restoreThread2;
                Handler handler = ThreadExecutor.sMainHandler;
                restoreThread2.start();
                return;
            }
            return;
        }
        String str = bnrParam.destinationPath;
        if (!BnrUtils.isValidDestinationPath(str)) {
            Log.e(
                    "ManageStorageSmartSwitchBnrReceiver",
                    "handleBackupRequest() ] Invalid Destination Path - BnrParam : " + bnrParam);
            return;
        }
        int i = bnrParam.detailAction;
        if (i == 0) {
            BackupThread backupThread = SmartSwitchBnrManager.backupThread;
            if (backupThread != null && backupThread.isAlive()) {
                Log.e(
                        "SmartSwitchBnrManager",
                        "startBackup() ] SmartSwitch Backup is already in progress.");
                return;
            }
            BackupThread backupThread2 = new BackupThread(context, bnrParam);
            SmartSwitchBnrManager.backupThread = backupThread2;
            Handler handler2 = ThreadExecutor.sMainHandler;
            backupThread2.start();
            return;
        }
        if (i == 2) {
            Intrinsics.checkNotNull(str);
            BackupThread backupThread3 = SmartSwitchBnrManager.backupThread;
            if (backupThread3 != null && backupThread3.isAlive()) {
                BackupThread backupThread4 = SmartSwitchBnrManager.backupThread;
                Intrinsics.checkNotNull(backupThread4);
                backupThread4.interrupt();
            }
            Log.d("SmartSwitchBnrManager", "cancelBackup() ] Try to delete ".concat(str));
            BnrUtils.deleteBnrFiles(str);
        }
    }
}
