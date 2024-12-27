package com.samsung.android.settings.analyzestorage.external.bnr;

import android.content.Context;

import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.fragment.app.BackStackRecord$$ExternalSyntheticOutline0;
import androidx.room.util.DBUtil;

import com.samsung.android.settings.analyzestorage.data.database.dao.UnusedAppBnRInfoDao_Impl;
import com.samsung.android.settings.analyzestorage.data.database.dao.UnusedAppBnRInfoDao_Impl$$ExternalSyntheticLambda1;
import com.samsung.android.settings.analyzestorage.data.database.repository.RepositoryFactory;
import com.samsung.android.settings.analyzestorage.data.model.UnusedAppBnRInfo;
import com.samsung.android.settings.analyzestorage.data.model.UnusedAppInfo;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.external.database.repository.RepositoryFactoryImpl;
import com.samsung.android.settings.analyzestorage.external.database.repository.UnusedAppInfoRepositoryImpl;
import com.samsung.android.settings.analyzestorage.presenter.page.PageType;
import com.samsung.android.settings.analyzestorage.presenter.utils.fileutils.FileWrapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BackupThread extends Thread {
    public static final String[] sBackupTargetFiles = {
        "manage_storage.db", "manage_storage.db-shm", "manage_storage.db-wal"
    };
    public final Context mContext;
    public final String mDestinationPath;
    public final int mSecurityLevel;
    public final String mSessionTime;
    public final String mSource;

    public BackupThread(Context context, BnrParam bnrParam) {
        this.mContext = context;
        this.mDestinationPath = bnrParam.destinationPath;
        this.mSecurityLevel = bnrParam.securityLevel;
        this.mSource = bnrParam.requestFrom;
        this.mSessionTime = bnrParam.exportSessionTime;
        EncryptionUtils.streamCrypt(bnrParam.encryptionKey);
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        long currentTimeMillis = System.currentTimeMillis();
        FileWrapper fileWrapper = new FileWrapper(this.mDestinationPath);
        if (!fileWrapper.exists()) {
            Log.d(
                    "BackupThread",
                    "createBackupFolderIfNotExist() ] Try to create Backup Folder. result : "
                            + fileWrapper.mkdirs());
        }
        PageType pageType = PageType.ANALYZE_STORAGE_UNUSED_APPS;
        RepositoryFactory.getGenerator().getClass();
        final UnusedAppInfoRepositoryImpl unusedAppInfoRepositoryImpl =
                (UnusedAppInfoRepositoryImpl) RepositoryFactoryImpl.getAppRepository(pageType);
        final List list =
                (List)
                        unusedAppInfoRepositoryImpl.getAppInfoList().parallelStream()
                                .map(
                                        new Function() { // from class:
                                                         // com.samsung.android.settings.analyzestorage.external.database.repository.UnusedAppInfoRepositoryImpl$$ExternalSyntheticLambda0
                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                UnusedAppInfoRepositoryImpl.this.getClass();
                                                UnusedAppBnRInfo unusedAppBnRInfo =
                                                        new UnusedAppBnRInfo();
                                                unusedAppBnRInfo.mPackageName =
                                                        ((UnusedAppInfo) obj).getPackageName();
                                                return unusedAppBnRInfo;
                                            }
                                        })
                                .collect(Collectors.toList());
        synchronized (unusedAppInfoRepositoryImpl.mUnusedAppBnRInfoDao) {
            DBUtil.performBlocking(
                    unusedAppInfoRepositoryImpl.mUnusedAppBnRInfoDao.__db,
                    false,
                    true,
                    new UnusedAppBnRInfoDao_Impl$$ExternalSyntheticLambda1());
            final UnusedAppBnRInfoDao_Impl unusedAppBnRInfoDao_Impl =
                    unusedAppInfoRepositoryImpl.mUnusedAppBnRInfoDao;
            unusedAppBnRInfoDao_Impl.getClass();
        }
        String[] strArr = sBackupTargetFiles;
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i >= 3) {
                break;
            }
            String str = strArr[i];
            if (Thread.currentThread().isInterrupted()) {
                Log.d("BackupThread", "run() ] Current Job is interrupted. So, exit.");
                break;
            }
            File databasePath = this.mContext.getDatabasePath(str);
            StringBuilder sb = new StringBuilder();
            sb.append(this.mDestinationPath);
            String m =
                    BackStackRecord$$ExternalSyntheticOutline0.m(
                            sb, File.separator, "BACKUP#", str);
            Log.d(
                    "BackupThread",
                    "run() ] source : "
                            + Log.getEncodedMsg(databasePath.getAbsolutePath())
                            + " , destinationFile : "
                            + Log.getEncodedMsg(m));
            try {
                if (EncryptionUtils.encrypt(databasePath, m, this.mSecurityLevel).exists()) {
                    i2++;
                } else {
                    Log.d("BackupThread", "run() ] " + Log.getEncodedMsg(m) + " is not created.");
                }
            } catch (IOException e) {
                Log.e("BackupThread", "run() ] IOException e : " + e);
            }
            i++;
        }
        int i3 = 3 == i2 ? 0 : 1;
        StringBuilder m2 =
                RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(
                        "run() ] successCount : ", " , errCode : ", i2, i3, ". Elapsed Time : ");
        m2.append(System.currentTimeMillis() - currentTimeMillis);
        m2.append(" ms.");
        Log.d("BackupThread", m2.toString());
        BnrUtils.sendBnrResult(
                this.mContext,
                "com.samsung.android.intent.action.RESPONSE_BACKUP_MANAGE_STORAGE_SETTING",
                i3,
                this.mSource,
                this.mSessionTime);
    }
}
