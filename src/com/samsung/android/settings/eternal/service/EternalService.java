package com.samsung.android.settings.eternal.service;

import android.app.Service;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.StatFs;
import android.text.TextUtils;

import com.android.settings.R;
import com.android.settings.accessibility.PreferredShortcuts$$ExternalSyntheticOutline0;

import com.samsung.android.lib.episode.EpisodeUtils;
import com.samsung.android.scloud.oem.lib.qbnr.ISCloudQBNRClient;
import com.samsung.android.scloud.oem.lib.qbnr.QBNRClientHelper;
import com.samsung.android.scloud.oem.lib.utils.FileTool;
import com.samsung.android.settings.eternal.controller.EternalController;
import com.samsung.android.settings.eternal.data.BackupInfo;
import com.samsung.android.settings.eternal.data.EpisodeHolder;
import com.samsung.android.settings.eternal.data.RestoreInfo;
import com.samsung.android.settings.eternal.data.SupplierInfo;
import com.samsung.android.settings.eternal.database.DatabaseManager;
import com.samsung.android.settings.eternal.log.EternalFileLog;
import com.samsung.android.settings.eternal.manager.EternalManager;
import com.samsung.android.settings.eternal.manager.SupplierManager;
import com.samsung.android.settings.eternal.scloud.AccessibilityBackupRestoreClient;
import com.samsung.android.settings.eternal.smartswitch.BnRDocumentStorageAccessHelper;
import com.samsung.android.settings.eternal.utils.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class EternalService extends Service implements ISCloudQBNRClient {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Future mBackupFuture;
    public Context mContext;
    public FileUtils mFileUtils;
    public Future mRestoreFuture;
    public final ExecutorService mWorkerExecutor = Executors.newSingleThreadExecutor();

    public static void deleteFiles(String str) {
        EternalFileLog.d("Eternal/EternalService", "deleteFiles()");
        FileUtils.deleteFile(new File(str), "Eternal/EternalService", "deleteFiles()");
    }

    @Override // com.samsung.android.scloud.oem.lib.qbnr.ISCloudQBNRClient
    public final void backup(
            Context context,
            ParcelFileDescriptor parcelFileDescriptor,
            QBNRClientHelper.AnonymousClass2.AnonymousClass1.C00531 c00531) {
        long currentTimeMillis = System.currentTimeMillis();
        EternalFileLog.d("Eternal/EternalService", "backup()");
        EternalController eternalController = new EternalController(context);
        BackupInfo backupInfo = new BackupInfo();
        backupInfo.mRequestFrom = 2;
        File fetchBackup = eternalController.fetchBackup(backupInfo);
        if (fetchBackup != null && fetchBackup.exists()) {
            try {
                if (fetchBackup.length() >= 1) {
                    try {
                        FileOutputStream fileOutputStream =
                                new FileOutputStream(parcelFileDescriptor.getFileDescriptor());
                        try {
                            File compressToGzip = FileUtils.compressToGzip(context, fetchBackup);
                            FileTool.writeToFile(
                                    compressToGzip.getPath(),
                                    compressToGzip.length(),
                                    fileOutputStream,
                                    new EternalService$$ExternalSyntheticLambda3(c00531, 1));
                            fileOutputStream.close();
                            FileUtils.deleteFile(
                                    compressToGzip, "Eternal/EternalService", "backup()");
                            EternalFileLog.d(
                                    "Eternal/EternalService",
                                    "backup() Success took : "
                                            + (System.currentTimeMillis() - currentTimeMillis));
                            c00531.complete(true);
                            return;
                        } catch (Throwable th) {
                            try {
                                fileOutputStream.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                            throw th;
                        }
                    } catch (IOException unused) {
                        EternalFileLog.e("Eternal/EternalService", "backup() IOException");
                        c00531.complete(false);
                        FileUtils.deleteFile(fetchBackup, "Eternal/EternalService", "backup()");
                        return;
                    }
                }
            } catch (Throwable th3) {
                FileUtils.deleteFile(fetchBackup, "Eternal/EternalService", "backup()");
                throw th3;
            }
        }
        EternalFileLog.d("Eternal/EternalService", "backup() backupFile is empty");
        c00531.complete(false);
    }

    @Override // com.samsung.android.scloud.oem.lib.qbnr.ISCloudQBNRClient
    public final String getDescription(Context context) {
        return context.getResources().getString(R.string.settings_label);
    }

    @Override // com.samsung.android.scloud.oem.lib.qbnr.ISCloudQBNRClient
    public final String getLabel(Context context) {
        return context.getResources().getString(R.string.settings_label);
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public final void onDestroy() {
        EternalFileLog.d("Eternal/EternalService", "onDestroy");
        super.onDestroy();
        this.mWorkerExecutor.shutdownNow();
    }

    @Override // android.app.Service
    public final int onStartCommand(final Intent intent, int i, final int i2) {
        final int i3 = 0;
        final int i4 = 1;
        EpisodeUtils.isSettingAppSupportBnR();
        if (intent == null || intent.getAction() == null) {
            EternalFileLog.d("Eternal/EternalService", "onStartCommand() action : null");
            stopSelf();
        }
        this.mContext = getBaseContext();
        if (intent != null && intent.getAction() != null) {
            String action = intent.getAction();
            if ("com.samsung.android.intent.action.SMART_SWITCH_RESTORE_COMPLETED".equals(action)) {
                EternalFileLog.d(
                        "Eternal/EternalService",
                        "handleIntent() action : " + intent.getAction() + " / startId " + i2);
                Future future = this.mRestoreFuture;
                if (future == null || future.isDone()) {
                    final EternalController eternalController = new EternalController(this);
                    this.mRestoreFuture =
                            this.mWorkerExecutor.submit(
                                    new Runnable() { // from class:
                                        // com.samsung.android.settings.eternal.service.EternalService$$ExternalSyntheticLambda0
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            EternalService eternalService = EternalService.this;
                                            EternalController eternalController2 =
                                                    eternalController;
                                            int i5 = i2;
                                            int i6 = EternalService.$r8$clinit;
                                            eternalService.getClass();
                                            try {
                                                try {
                                                    int size =
                                                            new DatabaseManager(
                                                                            eternalService.mContext)
                                                                    .getBackupSceneMap()
                                                                    .size();
                                                    File file =
                                                            FileUtils.getFile(
                                                                    eternalService.mContext);
                                                    ArrayList arrayList = new ArrayList();
                                                    ArrayList arrayList2 = new ArrayList();
                                                    if (!arrayList2.isEmpty()) {
                                                        arrayList.addAll(arrayList2);
                                                    }
                                                    RestoreInfo restoreInfo = new RestoreInfo();
                                                    restoreInfo.mRestoreFile = file;
                                                    restoreInfo.mIsFastTrack = false;
                                                    ArrayList arrayList3 = new ArrayList();
                                                    restoreInfo.mRestoreSkipList = arrayList3;
                                                    arrayList3.addAll(arrayList);
                                                    restoreInfo.mRequestFrom = 0;
                                                    restoreInfo.mIsDeferred = true;
                                                    restoreInfo.mIsNeedValidation = true;
                                                    EpisodeHolder fetchRestore =
                                                            eternalController2.fetchRestore(
                                                                    restoreInfo);
                                                    eternalService.sendResponse(
                                                            "com.samsung.android.intent.action.RESPONSE_RESTORE_SETTINGS",
                                                            (fetchRestore == null
                                                                            || !fetchRestore
                                                                                    .hasRestoreResult())
                                                                    ? 1
                                                                    : 0,
                                                            (fetchRestore == null
                                                                            || !fetchRestore
                                                                                    .hasRestoreResult())
                                                                    ? 1
                                                                    : 0,
                                                            size,
                                                            null,
                                                            null);
                                                } catch (Exception e) {
                                                    EternalFileLog.e(
                                                            "Eternal/EternalService",
                                                            "deferredRestoreDataInternal() "
                                                                    + e.getMessage());
                                                    eternalService.sendResponse(
                                                            "com.samsung.android.intent.action.RESPONSE_BACKUP_SETTINGS",
                                                            1,
                                                            1,
                                                            0,
                                                            null,
                                                            null);
                                                }
                                                EternalService.deleteFiles(
                                                        FileUtils.getFileFullPath(
                                                                eternalService.mContext,
                                                                "SettingsBackup.xml"));
                                                eternalService.stopSelf(i5);
                                            } catch (Throwable th) {
                                                EternalService.deleteFiles(
                                                        FileUtils.getFileFullPath(
                                                                eternalService.mContext,
                                                                "SettingsBackup.xml"));
                                                eternalService.stopSelf(i5);
                                                throw th;
                                            }
                                        }
                                    });
                }
            } else {
                Bundle extras = intent.getExtras();
                if (extras == null) {
                    EternalFileLog.d("Eternal/EternalService", "handleIntent() bundle is null");
                } else {
                    EternalFileLog.d(
                            "Eternal/EternalService",
                            "handleIntent() action : " + intent.getAction() + " / startId " + i2);
                    if (TextUtils.isEmpty(action)) {
                        EternalFileLog.d(
                                "Eternal/EternalService", "handleIntent() - action is empty");
                    } else {
                        EternalFileLog.d("Eternal/EternalService", "handleIntent() - " + action);
                        String string = extras.getString("SESSION_KEY");
                        int i5 = extras.getInt("SECURITY_LEVEL", 0);
                        FileUtils fileUtils = new FileUtils();
                        fileUtils.mSessionKey = string;
                        fileUtils.mSecurityLevel = i5;
                        this.mFileUtils = fileUtils;
                        int i6 = extras.getInt("ACTION", 0);
                        if ("com.samsung.android.intent.action.REQUEST_BACKUP_SETTINGS"
                                .equals(action)) {
                            if (i6 == 2) {
                                extras.getString("SAVE_PATH");
                                EternalFileLog.d("Eternal/EternalService", "cancelBackup()");
                                Future future2 = this.mBackupFuture;
                                if (future2 != null && !future2.isDone()) {
                                    this.mBackupFuture.cancel(true);
                                }
                                this.mWorkerExecutor.execute(
                                        new Runnable(
                                                this) { // from class:
                                                        // com.samsung.android.settings.eternal.service.EternalService$$ExternalSyntheticLambda1
                                            public final /* synthetic */ EternalService f$0;

                                            {
                                                this.f$0 = this;
                                            }

                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                EternalService eternalService;
                                                int i7;
                                                switch (i3) {
                                                    case 0:
                                                        eternalService = this.f$0;
                                                        i7 = i2;
                                                        int i8 = EternalService.$r8$clinit;
                                                        eternalService.getClass();
                                                        try {
                                                            EternalService.deleteFiles(
                                                                    FileUtils.getFileFullPath(
                                                                            eternalService.mContext,
                                                                            "SettingsBackup.xml"));
                                                            return;
                                                        } finally {
                                                            eternalService.stopSelf(i7);
                                                        }
                                                    default:
                                                        eternalService = this.f$0;
                                                        i7 = i2;
                                                        int i9 = EternalService.$r8$clinit;
                                                        return;
                                                }
                                            }
                                        });
                            } else {
                                EternalFileLog.d("Eternal/EternalService", "backupDataInternal()");
                                final String stringExtra = intent.getStringExtra("SOURCE");
                                final String stringExtra2 =
                                        intent.getStringExtra("EXPORT_SESSION_TIME");
                                final String stringExtra3 = intent.getStringExtra("SAVE_PATH");
                                final List pathUris =
                                        BnRDocumentStorageAccessHelper.getPathUris(
                                                this.mContext, intent);
                                if (((ArrayList) pathUris).isEmpty()) {
                                    EternalFileLog.d(
                                            "Eternal/EternalService",
                                            "backupDataInternal() - uris is empty");
                                    sendResponse(
                                            "com.samsung.android.intent.action.RESPONSE_BACKUP_SETTINGS",
                                            1,
                                            1,
                                            0,
                                            stringExtra,
                                            stringExtra2);
                                } else {
                                    String[] strArr = {
                                        "android.permission.READ_EXTERNAL_STORAGE",
                                        "android.permission.WRITE_EXTERNAL_STORAGE"
                                    };
                                    while (true) {
                                        if (i3 >= 2) {
                                            Future future3 = this.mBackupFuture;
                                            if (future3 == null || future3.isDone()) {
                                                final EternalController eternalController2 =
                                                        new EternalController(this);
                                                this.mBackupFuture =
                                                        this.mWorkerExecutor.submit(
                                                                new Runnable() { // from class:
                                                                    // com.samsung.android.settings.eternal.service.EternalService$$ExternalSyntheticLambda7
                                                                    /* JADX WARN: Multi-variable type inference failed */
                                                                    @Override // java.lang.Runnable
                                                                    public final void run() {
                                                                        String str;
                                                                        int i7;
                                                                        String str2;
                                                                        EternalService
                                                                                eternalService =
                                                                                        EternalService
                                                                                                .this;
                                                                        EternalController
                                                                                eternalController3 =
                                                                                        eternalController2;
                                                                        String str3 = stringExtra;
                                                                        String str4 = stringExtra2;
                                                                        String str5 = stringExtra3;
                                                                        List list = pathUris;
                                                                        int i8 = i2;
                                                                        int i9 =
                                                                                EternalService
                                                                                        .$r8$clinit;
                                                                        eternalService.getClass();
                                                                        String str6 =
                                                                                "backupDataInternal()"
                                                                                    + " ";
                                                                        try {
                                                                            try {
                                                                                BackupInfo
                                                                                        backupInfo =
                                                                                                new BackupInfo();
                                                                                backupInfo
                                                                                                .mRequestFrom =
                                                                                        0;
                                                                                File fetchBackup =
                                                                                        eternalController3
                                                                                                .fetchBackup(
                                                                                                        backupInfo);
                                                                                try {
                                                                                    if (fetchBackup
                                                                                                    == null
                                                                                            || !fetchBackup
                                                                                                    .exists()
                                                                                            || fetchBackup
                                                                                                            .length()
                                                                                                    < 1) {
                                                                                        eternalService
                                                                                                .sendResponse(
                                                                                                        "com.samsung.android.intent.action.RESPONSE_BACKUP_SETTINGS",
                                                                                                        1,
                                                                                                        1,
                                                                                                        0,
                                                                                                        str3,
                                                                                                        str4);
                                                                                        str6 = str6;
                                                                                    } else {
                                                                                        long
                                                                                                availableBytes =
                                                                                                        new StatFs(
                                                                                                                        str5)
                                                                                                                .getAvailableBytes();
                                                                                        long
                                                                                                length =
                                                                                                        fetchBackup
                                                                                                                .length();
                                                                                        if (availableBytes
                                                                                                < length) {
                                                                                            eternalService
                                                                                                    .sendResponse(
                                                                                                            "com.samsung.android.intent.action.RESPONSE_BACKUP_SETTINGS",
                                                                                                            1,
                                                                                                            2,
                                                                                                            (int)
                                                                                                                    length,
                                                                                                            str3,
                                                                                                            str4);
                                                                                            str6 =
                                                                                                    str6;
                                                                                        } else {
                                                                                            FileUtils
                                                                                                    fileUtils2 =
                                                                                                            eternalService
                                                                                                                    .mFileUtils;
                                                                                            Context
                                                                                                    context =
                                                                                                            eternalService
                                                                                                                    .mContext;
                                                                                            String
                                                                                                    fileFullPath =
                                                                                                            FileUtils
                                                                                                                    .getFileFullPath(
                                                                                                                            context,
                                                                                                                            "SettingsBackup.xml");
                                                                                            int
                                                                                                    encrypt =
                                                                                                            fileUtils2
                                                                                                                    .encrypt(
                                                                                                                            context,
                                                                                                                            fileFullPath);
                                                                                            EternalFileLog
                                                                                                    .d(
                                                                                                            "Eternal/EternalService",
                                                                                                            "backupDataInternal()"
                                                                                                                + " Available"
                                                                                                                + " space"
                                                                                                                + " : "
                                                                                                                    + availableBytes
                                                                                                                    + " / backupFileSize"
                                                                                                                    + " : "
                                                                                                                    + length
                                                                                                                    + " / encrypt"
                                                                                                                    + " size"
                                                                                                                    + " : "
                                                                                                                    + encrypt);
                                                                                            if (encrypt
                                                                                                    < 1) {
                                                                                                eternalService
                                                                                                        .sendResponse(
                                                                                                                "com.samsung.android.intent.action.RESPONSE_BACKUP_SETTINGS",
                                                                                                                1,
                                                                                                                1,
                                                                                                                (int)
                                                                                                                        length,
                                                                                                                str3,
                                                                                                                str4);
                                                                                                str6 =
                                                                                                        fileFullPath;
                                                                                            } else {
                                                                                                if (FileUtils
                                                                                                        .getFile(
                                                                                                                eternalService
                                                                                                                        .mContext)
                                                                                                        .exists()) {
                                                                                                    Context
                                                                                                            context2 =
                                                                                                                    eternalService
                                                                                                                            .mContext;
                                                                                                    File
                                                                                                            file =
                                                                                                                    FileUtils
                                                                                                                            .getFile(
                                                                                                                                    context2);
                                                                                                    Uri
                                                                                                            uri =
                                                                                                                    (Uri)
                                                                                                                            list
                                                                                                                                    .get(
                                                                                                                                            0);
                                                                                                    i7 =
                                                                                                            BnRDocumentStorageAccessHelper
                                                                                                                    .copyFileToDirUri(
                                                                                                                            context2,
                                                                                                                            file,
                                                                                                                            uri);
                                                                                                    str2 =
                                                                                                            uri;
                                                                                                } else {
                                                                                                    i7 =
                                                                                                            0;
                                                                                                    str2 =
                                                                                                            fileFullPath;
                                                                                                }
                                                                                                EternalFileLog
                                                                                                        .d(
                                                                                                                "Eternal/EternalService",
                                                                                                                "backupDataInternal()"
                                                                                                                    + " Copy"
                                                                                                                    + " uri :"
                                                                                                                    + " "
                                                                                                                        + list
                                                                                                                                .get(
                                                                                                                                        0)
                                                                                                                        + " / copyCount"
                                                                                                                        + " = "
                                                                                                                        + i7);
                                                                                                eternalService
                                                                                                        .sendResponse(
                                                                                                                "com.samsung.android.intent.action.RESPONSE_BACKUP_SETTINGS",
                                                                                                                i7
                                                                                                                                > 0
                                                                                                                        ? 0
                                                                                                                        : 1,
                                                                                                                i7
                                                                                                                                > 0
                                                                                                                        ? 0
                                                                                                                        : 1,
                                                                                                                encrypt,
                                                                                                                str3,
                                                                                                                str4);
                                                                                                str6 =
                                                                                                        str2;
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                } catch (
                                                                                        Exception
                                                                                                e) {
                                                                                    e = e;
                                                                                    EternalFileLog
                                                                                            .e(
                                                                                                    "Eternal/EternalService",
                                                                                                    str
                                                                                                            + e
                                                                                                                    .getMessage());
                                                                                    eternalService
                                                                                            .sendResponse(
                                                                                                    "com.samsung.android.intent.action.RESPONSE_BACKUP_SETTINGS",
                                                                                                    1,
                                                                                                    1,
                                                                                                    0,
                                                                                                    str3,
                                                                                                    str4);
                                                                                }
                                                                            } finally {
                                                                                EternalService
                                                                                        .deleteFiles(
                                                                                                FileUtils
                                                                                                        .getFileFullPath(
                                                                                                                eternalService
                                                                                                                        .mContext,
                                                                                                                "SettingsBackup.xml"));
                                                                                eternalService
                                                                                        .stopSelf(
                                                                                                i8);
                                                                            }
                                                                        } catch (Exception e2) {
                                                                            e = e2;
                                                                            str = str6;
                                                                        }
                                                                    }
                                                                });
                                            } else {
                                                EternalFileLog.d(
                                                        "Eternal/EternalService",
                                                        "backupDataInternal() - Previous backup did"
                                                            + " not completed");
                                                sendResponse(
                                                        "com.samsung.android.intent.action.RESPONSE_BACKUP_SETTINGS",
                                                        1,
                                                        1,
                                                        0,
                                                        stringExtra,
                                                        stringExtra2);
                                            }
                                        } else {
                                            if (this.mContext.checkSelfPermission(strArr[i3])
                                                    != 0) {
                                                EternalFileLog.d(
                                                        "Eternal/EternalService",
                                                        "backupDataInternal() - permission error");
                                                sendResponse(
                                                        "com.samsung.android.intent.action.RESPONSE_BACKUP_SETTINGS",
                                                        1,
                                                        4,
                                                        0,
                                                        stringExtra,
                                                        stringExtra2);
                                                break;
                                            }
                                            i3++;
                                        }
                                    }
                                }
                            }
                        } else if ("com.samsung.android.intent.action.REQUEST_RESTORE_SETTINGS"
                                .equals(action)) {
                            final String stringExtra4 = intent.getStringExtra("SOURCE");
                            final boolean booleanExtra =
                                    intent.getBooleanExtra("FAST_TRACK", false);
                            boolean booleanExtra2 =
                                    intent.getBooleanExtra("FROM_REMOTE_SERVICE", false);
                            final ArrayList<String> stringArrayListExtra =
                                    intent.getStringArrayListExtra("RESTORE_RESTRICTED_LIST");
                            final List pathUris2 =
                                    BnRDocumentStorageAccessHelper.getPathUris(
                                            this.mContext, intent);
                            if (((ArrayList) pathUris2).isEmpty()) {
                                EternalFileLog.d(
                                        "Eternal/EternalService",
                                        "restoreDataInternal() - uris is empty");
                                sendResponse(
                                        "com.samsung.android.intent.action.RESPONSE_RESTORE_SETTINGS",
                                        1,
                                        1,
                                        0,
                                        stringExtra4,
                                        null);
                            } else {
                                EternalFileLog.d(
                                        "Eternal/EternalService",
                                        "restoreDataInternal() isFastTrack = "
                                                + booleanExtra
                                                + " / isFromRemoteService = "
                                                + booleanExtra2);
                                Future future4 = this.mRestoreFuture;
                                if (future4 == null || future4.isDone()) {
                                    final EternalController eternalController3 =
                                            new EternalController(this);
                                    this.mRestoreFuture =
                                            this.mWorkerExecutor.submit(
                                                    new Runnable() { // from class:
                                                        // com.samsung.android.settings.eternal.service.EternalService$$ExternalSyntheticLambda6
                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            EternalService eternalService =
                                                                    EternalService.this;
                                                            List list = pathUris2;
                                                            boolean z = booleanExtra;
                                                            ArrayList arrayList =
                                                                    stringArrayListExtra;
                                                            EternalController eternalController4 =
                                                                    eternalController3;
                                                            String str = stringExtra4;
                                                            int i7 = i2;
                                                            int i8 = EternalService.$r8$clinit;
                                                            eternalService.getClass();
                                                            try {
                                                                try {
                                                                    EternalFileLog.d(
                                                                            "Eternal/EternalService",
                                                                            "restoreDataInternal()"
                                                                                + " Copy uri : "
                                                                                    + list.get(0)
                                                                                    + " / copyCount"
                                                                                    + " = "
                                                                                    + BnRDocumentStorageAccessHelper
                                                                                            .moveUrisToDir(
                                                                                                    eternalService
                                                                                                            .mContext,
                                                                                                    (Uri)
                                                                                                            list
                                                                                                                    .get(
                                                                                                                            0),
                                                                                                    list
                                                                                                            .subList(
                                                                                                                    1,
                                                                                                                    list
                                                                                                                            .size()),
                                                                                                    eternalService
                                                                                                            .mContext
                                                                                                            .getFilesDir()));
                                                                    FileUtils fileUtils2 =
                                                                            eternalService
                                                                                    .mFileUtils;
                                                                    Context context =
                                                                            eternalService.mContext;
                                                                    int decrypt =
                                                                            fileUtils2.decrypt(
                                                                                    context,
                                                                                    FileUtils
                                                                                            .getFileFullPath(
                                                                                                    context,
                                                                                                    "SettingsBackup.xml"));
                                                                    EternalFileLog.d(
                                                                            "Eternal/EternalService",
                                                                            "fileSize = "
                                                                                    + decrypt);
                                                                    File file =
                                                                            FileUtils.getFile(
                                                                                    eternalService
                                                                                            .mContext);
                                                                    ArrayList arrayList2 =
                                                                            new ArrayList();
                                                                    if (arrayList != null
                                                                            && !arrayList
                                                                                    .isEmpty()) {
                                                                        arrayList2.addAll(
                                                                                arrayList);
                                                                    }
                                                                    RestoreInfo restoreInfo =
                                                                            new RestoreInfo();
                                                                    restoreInfo.mRestoreFile = file;
                                                                    restoreInfo.mIsFastTrack = z;
                                                                    ArrayList arrayList3 =
                                                                            new ArrayList();
                                                                    restoreInfo.mRestoreSkipList =
                                                                            arrayList3;
                                                                    arrayList3.addAll(arrayList2);
                                                                    restoreInfo.mRequestFrom = 0;
                                                                    restoreInfo.mIsDeferred = false;
                                                                    restoreInfo.mIsNeedValidation =
                                                                            true;
                                                                    EpisodeHolder fetchRestore =
                                                                            eternalController4
                                                                                    .fetchRestore(
                                                                                            restoreInfo);
                                                                    eternalService.sendResponse(
                                                                            "com.samsung.android.intent.action.RESPONSE_RESTORE_SETTINGS",
                                                                            (fetchRestore == null
                                                                                            || !fetchRestore
                                                                                                    .hasRestoreResult())
                                                                                    ? 1
                                                                                    : 0,
                                                                            (fetchRestore == null
                                                                                            || !fetchRestore
                                                                                                    .hasRestoreResult())
                                                                                    ? 1
                                                                                    : 0,
                                                                            decrypt,
                                                                            str,
                                                                            null);
                                                                } catch (Exception e) {
                                                                    EternalFileLog.e(
                                                                            "Eternal/EternalService",
                                                                            "restoreDataInternal() "
                                                                                    + e
                                                                                            .getMessage());
                                                                    eternalService.sendResponse(
                                                                            "com.samsung.android.intent.action.RESPONSE_BACKUP_SETTINGS",
                                                                            1,
                                                                            1,
                                                                            0,
                                                                            str,
                                                                            null);
                                                                }
                                                                EternalService.deleteFiles(
                                                                        FileUtils.getFileFullPath(
                                                                                eternalService
                                                                                        .mContext,
                                                                                "SettingsBackup.xml"));
                                                                eternalService.stopSelf(i7);
                                                            } catch (Throwable th) {
                                                                EternalService.deleteFiles(
                                                                        FileUtils.getFileFullPath(
                                                                                eternalService
                                                                                        .mContext,
                                                                                "SettingsBackup.xml"));
                                                                eternalService.stopSelf(i7);
                                                                throw th;
                                                            }
                                                        }
                                                    });
                                }
                            }
                        } else if ("com.samsung.android.intent.action.REQUEST_CONVERT_SCLOUD_DATA"
                                .equals(action)) {
                            final EternalController eternalController4 =
                                    new EternalController(this);
                            EternalFileLog.d("Eternal/EternalService", "convertDataInternal()");
                            String action2 = intent.getAction();
                            action2.getClass();
                            if (action2.equals(
                                    "com.samsung.android.intent.action.REQUEST_CONVERT_SCLOUD_DATA")) {
                                this.mWorkerExecutor.submit(
                                        new Runnable() { // from class:
                                            // com.samsung.android.settings.eternal.service.EternalService$$ExternalSyntheticLambda2
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                Intent intent2 = intent;
                                                EternalController eternalController5 =
                                                        eternalController4;
                                                int i7 = EternalService.$r8$clinit;
                                                Bundle extras2 = intent2.getExtras();
                                                extras2.putString("convertType", "sCloud");
                                                eternalController5.getClass();
                                                EternalFileLog.d(
                                                        "Eternal/EternalController",
                                                        "## fetchConvertData() : uid ="
                                                            + " Accessibility");
                                                EternalManager eternalManager =
                                                        eternalController5.mEternalManager;
                                                eternalManager.getClass();
                                                SupplierManager supplierManager =
                                                        eternalManager.mSupplierManager;
                                                Uri uri =
                                                        supplierManager.mSupplierMap.get(
                                                                                "Accessibility")
                                                                        == null
                                                                ? null
                                                                : ((SupplierInfo)
                                                                                supplierManager
                                                                                        .mSupplierMap
                                                                                        .get(
                                                                                                "Accessibility"))
                                                                        .mUri;
                                                try {
                                                    if (uri == null) {
                                                        EternalFileLog.e(
                                                                "Eternal/EternalManager",
                                                                "convertData() uri of uid -"
                                                                    + " Accessibility is null");
                                                        return;
                                                    }
                                                    try {
                                                        ContentProviderClient
                                                                acquireUnstableContentProviderClient =
                                                                        eternalManager
                                                                                .mContext
                                                                                .getContentResolver()
                                                                                .acquireUnstableContentProviderClient(
                                                                                        uri);
                                                        try {
                                                            if (acquireUnstableContentProviderClient
                                                                    == null) {
                                                                EternalFileLog.e(
                                                                        "Eternal/EternalManager",
                                                                        "convertData ["
                                                                                + uri.toString()
                                                                                + "] contentProviderClient"
                                                                                + " is null");
                                                            } else {
                                                                acquireUnstableContentProviderClient
                                                                        .call(
                                                                                "convert_data",
                                                                                null,
                                                                                extras2);
                                                            }
                                                            if (acquireUnstableContentProviderClient
                                                                    != null) {
                                                                acquireUnstableContentProviderClient
                                                                        .close();
                                                            }
                                                        } catch (Throwable th) {
                                                            if (acquireUnstableContentProviderClient
                                                                    != null) {
                                                                try {
                                                                    acquireUnstableContentProviderClient
                                                                            .close();
                                                                } catch (Throwable th2) {
                                                                    th.addSuppressed(th2);
                                                                }
                                                            }
                                                            throw th;
                                                        }
                                                    } catch (Exception unused) {
                                                        EternalFileLog.d(
                                                                "Eternal/EternalManager",
                                                                "convertData() - Exception Uid"
                                                                    + " =Accessibility");
                                                    }
                                                } finally {
                                                    AccessibilityBackupRestoreClient
                                                            .removeRestoreFile(
                                                                    eternalManager.mContext);
                                                }
                                            }
                                        });
                            }
                        }
                    }
                }
            }
            return 2;
        }
        EternalFileLog.d("Eternal/EternalService", "handleIntent() action : null");
        this.mWorkerExecutor.execute(
                new Runnable(
                        this) { // from class:
                                // com.samsung.android.settings.eternal.service.EternalService$$ExternalSyntheticLambda1
                    public final /* synthetic */ EternalService f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        EternalService eternalService;
                        int i7;
                        switch (i4) {
                            case 0:
                                eternalService = this.f$0;
                                i7 = i2;
                                int i8 = EternalService.$r8$clinit;
                                eternalService.getClass();
                                try {
                                    EternalService.deleteFiles(
                                            FileUtils.getFileFullPath(
                                                    eternalService.mContext, "SettingsBackup.xml"));
                                    return;
                                } finally {
                                    eternalService.stopSelf(i7);
                                }
                            default:
                                eternalService = this.f$0;
                                i7 = i2;
                                int i9 = EternalService.$r8$clinit;
                                return;
                        }
                    }
                });
        return 2;
    }

    @Override // com.samsung.android.scloud.oem.lib.qbnr.ISCloudQBNRClient
    public final void restore(
            Context context,
            ParcelFileDescriptor parcelFileDescriptor,
            QBNRClientHelper.AnonymousClass2.AnonymousClass1.C00531 c00531) {
        FileInputStream fileInputStream;
        long currentTimeMillis = System.currentTimeMillis();
        EternalFileLog.i("Eternal/EternalService", "restore()");
        File file = FileUtils.getFile(context);
        try {
            try {
                fileInputStream = new FileInputStream(parcelFileDescriptor.getFileDescriptor());
            } catch (IOException unused) {
                EternalFileLog.e("Eternal/EternalService", "restore() IOException");
                c00531.complete(false);
            }
            try {
                FileTool.writeToFile(
                        fileInputStream,
                        parcelFileDescriptor.getStatSize(),
                        file.getAbsolutePath(),
                        new EternalService$$ExternalSyntheticLambda3(c00531, 0));
                FileUtils.decompressFromGzip(context, file);
                EternalController eternalController = new EternalController(context);
                File file2 = FileUtils.getFile(context);
                ArrayList arrayList = new ArrayList();
                RestoreInfo restoreInfo = new RestoreInfo();
                restoreInfo.mRestoreFile = file2;
                restoreInfo.mIsFastTrack = false;
                ArrayList arrayList2 = new ArrayList();
                restoreInfo.mRestoreSkipList = arrayList2;
                arrayList2.addAll(arrayList);
                restoreInfo.mRequestFrom = 2;
                restoreInfo.mIsDeferred = false;
                restoreInfo.mIsNeedValidation = true;
                EpisodeHolder fetchRestore = eternalController.fetchRestore(restoreInfo);
                if (fetchRestore != null) {
                    if (!fetchRestore.hasRestoreResult()) {}
                    fileInputStream.close();
                    c00531.complete(true);
                    EternalFileLog.d(
                            "Eternal/EternalService",
                            "restore() Success took : "
                                    + (System.currentTimeMillis() - currentTimeMillis));
                }
                c00531.complete(false);
                fileInputStream.close();
                c00531.complete(true);
                EternalFileLog.d(
                        "Eternal/EternalService",
                        "restore() Success took : "
                                + (System.currentTimeMillis() - currentTimeMillis));
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } finally {
            FileUtils.deleteFile(file, "Eternal/EternalService", "restore()");
        }
    }

    public final void sendResponse(String str, int i, int i2, int i3, String str2, String str3) {
        StringBuilder m =
                PreferredShortcuts$$ExternalSyntheticOutline0.m(
                        i, "sendResponse() - action : ", str, " / result : ", " / errorCode : ");
        m.append(i2);
        EternalFileLog.d("Eternal/EternalService", m.toString());
        Intent intent = new Intent(str);
        intent.putExtra("RESULT", i);
        intent.putExtra("ERR_CODE", i2);
        intent.putExtra("REQ_SIZE", i3);
        intent.putExtra("SOURCE", str2);
        if (!TextUtils.isEmpty(str3)) {
            intent.putExtra("EXPORT_SESSION_TIME", str3);
        }
        this.mContext.sendBroadcast(intent, "com.wssnps.permission.COM_WSSNPS");
    }
}
